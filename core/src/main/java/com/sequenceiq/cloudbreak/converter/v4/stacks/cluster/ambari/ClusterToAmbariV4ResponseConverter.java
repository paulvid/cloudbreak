package com.sequenceiq.cloudbreak.converter.v4.stacks.cluster.ambari;

import static com.sequenceiq.cloudbreak.structuredevent.json.AnonymizerUtil.anonymize;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import java.io.IOException;
import java.util.Optional;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import com.sequenceiq.cloudbreak.api.endpoint.v4.blueprints.responses.BlueprintV4Response;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.response.cluster.ambari.AmbariV4Response;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.response.cluster.ambari.ambarirepository.AmbariRepositoryV4Response;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.response.cluster.ambari.stackrepository.StackRepositoryV4Response;
import com.sequenceiq.cloudbreak.api.model.SecretResponse;
import com.sequenceiq.cloudbreak.cloud.model.AmbariRepo;
import com.sequenceiq.cloudbreak.cloud.model.component.StackRepoDetails;
import com.sequenceiq.cloudbreak.common.type.ComponentType;
import com.sequenceiq.cloudbreak.controller.exception.BadRequestException;
import com.sequenceiq.cloudbreak.converter.AbstractConversionServiceAwareConverter;
import com.sequenceiq.cloudbreak.domain.stack.cluster.Cluster;
import com.sequenceiq.cloudbreak.domain.stack.cluster.ClusterComponent;
import com.sequenceiq.cloudbreak.service.ServiceEndpointCollector;
import com.sequenceiq.cloudbreak.util.StackUtil;

public class ClusterToAmbariV4ResponseConverter extends AbstractConversionServiceAwareConverter<Cluster, AmbariV4Response> {

    @Inject
    private StackUtil stackUtil;

    @Inject
    private ServiceEndpointCollector serviceEndpointCollector;

    @Value("${cb.disable.show.blueprint:false}")
    private boolean disableShowBlueprint;

    @Override
    public AmbariV4Response convert(Cluster source) {
        AmbariV4Response response = new AmbariV4Response();
        response.setBlueprint(getConversionService().convert(source.getBlueprint(), BlueprintV4Response.class));
        response.setConfigStrategy(source.getConfigStrategy());
        convertDpSecrets(source, response);
        response.setExtendedBlueprintText(getExtendedBlueprintText(source));
        response.setRepository(getComponent(source, ComponentType.AMBARI_REPO_DETAILS, AmbariRepo.class, AmbariRepositoryV4Response.class));
        response.setSecurityMasterKey(getConversionService().convert(source.getAmbariSecurityMasterKey(), SecretResponse.class));
        String ambariIp = stackUtil.extractAmbariIp(source.getStack());
        response.setServerIp(ambariIp);
        response.setServerUrl(serviceEndpointCollector.getAmbariServerUrl(source, ambariIp));
        response.setStackRepository(getComponent(source, ComponentType.HDP_REPO_DETAILS, StackRepoDetails.class, StackRepositoryV4Response.class));
        response.setUserName(getConversionService().convert(source.getUserName(), SecretResponse.class));
        return response;
    }

    private void convertDpSecrets(Cluster source, AmbariV4Response response) {
        if (isNotEmpty(source.getDpAmbariUserSecret()) && isNotEmpty(source.getDpAmbariPasswordSecret())) {
            response.setDpUser(getConversionService().convert(source.getDpAmbariUserSecret(), SecretResponse.class));
            response.setDpPassword(getConversionService().convert(source.getDpAmbariPasswordSecret(), SecretResponse.class));
        }
    }

    private <T> T getComponent(Cluster source, ComponentType hdpRepoDetails, Class<?> srcClss, Class<T> responseClss) {
        try {
            Optional<ClusterComponent> repoComponent = source.getComponents().stream().filter(it -> it.getComponentType() == hdpRepoDetails).findFirst();
            if (repoComponent.isPresent()) {
                return getConversionService().convert(repoComponent.get().getAttributes().get(srcClss), responseClss);
            }
            return null;
        } catch (IOException e) {
            throw new BadRequestException("Cannot deserialize the compnent: " + responseClss, e);
        }
    }

    private String getExtendedBlueprintText(Cluster source) {
        if (StringUtils.isNoneEmpty(source.getExtendedBlueprintText()) && !disableShowBlueprint) {
            String fromVault = source.getExtendedBlueprintText();
            return anonymize(fromVault);
        }

        return null;
    }
}
