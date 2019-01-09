package com.sequenceiq.cloudbreak.controller.v4;

import static com.sequenceiq.cloudbreak.api.endpoint.v4.environment.responses.SimpleEnvironmentResponses.simpleEnvironmentResponses;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;

import com.sequenceiq.cloudbreak.api.endpoint.v4.environment.EnvironmentV4Endpoint;
import com.sequenceiq.cloudbreak.api.endpoint.v4.environment.requests.EnvironmentAttachV4Request;
import com.sequenceiq.cloudbreak.api.endpoint.v4.environment.requests.EnvironmentChangeCredentialRequest;
import com.sequenceiq.cloudbreak.api.endpoint.v4.environment.requests.EnvironmentDetachRequest;
import com.sequenceiq.cloudbreak.api.endpoint.v4.environment.requests.EnvironmentRequest;
import com.sequenceiq.cloudbreak.api.endpoint.v4.environment.requests.RegisterDatalakeV4Request;
import com.sequenceiq.cloudbreak.api.endpoint.v4.environment.responses.DetailedEnvironmentV4Response;
import com.sequenceiq.cloudbreak.api.endpoint.v4.environment.responses.SimpleEnvironmentResponses;
import com.sequenceiq.cloudbreak.api.endpoint.v4.environment.responses.SimpleEnvironmentV4Response;
import com.sequenceiq.cloudbreak.service.environment.EnvironmentService;

@Controller
@Transactional(TxType.NEVER)
public class EnvironmentV4Controller implements EnvironmentV4Endpoint {

    @Inject
    private EnvironmentService environmentService;

    @Override
    public DetailedEnvironmentV4Response create(Long workspaceId, @Valid EnvironmentRequest request) {
        return environmentService.createForLoggedInUser(request, workspaceId);
    }

    @Override
    public SimpleEnvironmentResponses list(Long workspaceId) {
        return simpleEnvironmentResponses(environmentService.listByWorkspaceId(workspaceId));
    }

    @Override
    public DetailedEnvironmentV4Response get(Long workspaceId, String environmentName) {
        return environmentService.get(environmentName, workspaceId);
    }

    @Override
    public SimpleEnvironmentV4Response delete(Long workspaceId, String environmentName) {
        return environmentService.delete(environmentName, workspaceId);
    }

    @Override
    public DetailedEnvironmentV4Response attach(Long workspaceId, String environmentName, @Valid EnvironmentAttachV4Request request) {
        return environmentService.attachResources(environmentName, request, workspaceId);
    }

    @Override
    public DetailedEnvironmentV4Response detach(Long workspaceId, String environmentName, @Valid EnvironmentDetachRequest request) {
        return environmentService.detachResources(environmentName, request, workspaceId);
    }

    @Override
    public DetailedEnvironmentV4Response changeCredential(Long workspaceId, String environmentName, @Valid EnvironmentChangeCredentialRequest request) {
        return environmentService.changeCredential(environmentName, workspaceId, request);
    }

    @Override
    public DetailedEnvironmentV4Response registerExternalDatalake(Long workspaceId, String environmentName, @Valid RegisterDatalakeV4Request request) {
        return environmentService.registerExternalDatalake(environmentName, workspaceId, request);
    }
}
