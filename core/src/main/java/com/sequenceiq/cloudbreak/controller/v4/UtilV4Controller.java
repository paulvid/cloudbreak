package com.sequenceiq.cloudbreak.controller.v4;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.sequenceiq.cloudbreak.api.endpoint.v4.common.responses.GeneralSetV4Response;
import com.sequenceiq.cloudbreak.api.endpoint.v4.util.UtilV4Endpoint;
import com.sequenceiq.cloudbreak.api.endpoint.v4.util.filter.ClientVersionV4Filter;
import com.sequenceiq.cloudbreak.api.endpoint.v4.util.filter.SecurityRulesV4Filter;
import com.sequenceiq.cloudbreak.api.endpoint.v4.util.filter.StackVersionV4Filter;
import com.sequenceiq.cloudbreak.api.endpoint.v4.util.requests.RepoConfigValidationV4Request;
import com.sequenceiq.cloudbreak.api.endpoint.v4.util.responses.CloudStorageSupportedV4Response;
import com.sequenceiq.cloudbreak.api.endpoint.v4.util.requests.SubscriptionV4Request;
import com.sequenceiq.cloudbreak.api.endpoint.v4.util.responses.RepoConfigValidationV4Response;
import com.sequenceiq.cloudbreak.api.endpoint.v4.util.responses.SecurityRulesV4Response;
import com.sequenceiq.cloudbreak.api.endpoint.v4.util.responses.StackMatrixV4;
import com.sequenceiq.cloudbreak.api.endpoint.v4.util.responses.SubscriptionV4Response;
import com.sequenceiq.cloudbreak.api.endpoint.v4.util.responses.VersionCheckV4Result;
import com.sequenceiq.cloudbreak.controller.common.NotificationController;
import com.sequenceiq.cloudbreak.controller.validation.rds.RdsConnectionBuilder;
import com.sequenceiq.cloudbreak.domain.Subscription;
import com.sequenceiq.cloudbreak.service.CloudbreakRestRequestThreadLocalService;
import com.sequenceiq.cloudbreak.service.ServiceEndpointCollector;
import com.sequenceiq.cloudbreak.service.StackMatrixService;
import com.sequenceiq.cloudbreak.service.blueprint.BlueprintService;
import com.sequenceiq.cloudbreak.service.cluster.RepositoryConfigValidationService;
import com.sequenceiq.cloudbreak.service.datalake.DatalakePrerequisiteService;
import com.sequenceiq.cloudbreak.service.filesystem.FileSystemSupportMatrixService;
import com.sequenceiq.cloudbreak.service.securityrule.SecurityRuleService;
import com.sequenceiq.cloudbreak.service.subscription.SubscriptionService;
import com.sequenceiq.cloudbreak.service.user.UserService;
import com.sequenceiq.cloudbreak.service.workspace.WorkspaceService;
import com.sequenceiq.cloudbreak.util.ClientVersionUtil;

@Controller
public class UtilV4Controller extends NotificationController implements UtilV4Endpoint {

    @Inject
    private StackMatrixService stackMatrixService;

    @Inject
    private FileSystemSupportMatrixService fileSystemSupportMatrixService;

    @Inject
    private DatalakePrerequisiteService datalakePrerequisiteService;

    @Inject
    private RepositoryConfigValidationService validationService;

    @Inject
    private RdsConnectionBuilder rdsConnectionBuilder;

    @Inject
    private BlueprintService blueprintService;

    @Inject
    private ServiceEndpointCollector serviceEndpointCollector;

    @Autowired
    @Qualifier("conversionService")
    private ConversionService conversionService;

    @Inject
    private WorkspaceService workspaceService;

    @Inject
    private UserService userService;

    @Inject
    private CloudbreakRestRequestThreadLocalService restRequestThreadLocalService;

    @Inject
    private SecurityRuleService securityRuleService;

    @Inject
    private SubscriptionService subscriptionService;

    @Value("${info.app.version:}")
    private String cbVersion;

    @Override
    public VersionCheckV4Result checkClientVersion(ClientVersionV4Filter version) {
        boolean compatible = ClientVersionUtil.checkVersion(cbVersion, version.getVersion());
        if (compatible) {
            return new VersionCheckV4Result(true);

        }
        return new VersionCheckV4Result(false, String.format("Versions not compatible: [server: '%s', client: '%s']", cbVersion, version.getVersion()));
    }

    @Override
    public StackMatrixV4 getStackMatrix() {
        return stackMatrixService.getStackMatrix();
    }

    @Override
    public GeneralSetV4Response<CloudStorageSupportedV4Response> getCloudStorageMatrix(StackVersionV4Filter stackVersionV4Filter) {
        return GeneralSetV4Response.propagateResponses(fileSystemSupportMatrixService.getCloudStorageMatrix(stackVersionV4Filter.getStackVersion()));
    }

    @Override
    public RepoConfigValidationV4Response repositoryConfigValidationRequest(RepoConfigValidationV4Request repoConfigValidationV4Request) {
        return validationService.validate(repoConfigValidationV4Request);
    }

    @Override
    public SecurityRulesV4Response getDefaultSecurityRules(SecurityRulesV4Filter securityRulesV4Filter) {
        return securityRuleService.getDefaultSecurityRules(securityRulesV4Filter.isKnoxEnabled());
    }

    @Override
    public SubscriptionV4Response subscribe(SubscriptionV4Request subscriptionV4Request) {
        Subscription subscription = new Subscription(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString(),
                subscriptionV4Request.getEndpointUrl());
        return new SubscriptionV4Response(subscriptionService.subscribe(subscription));
    }
}
