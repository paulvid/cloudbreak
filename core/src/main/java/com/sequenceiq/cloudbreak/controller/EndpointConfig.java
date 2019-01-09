package com.sequenceiq.cloudbreak.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.ext.ExceptionMapper;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import com.google.api.client.repackaged.com.google.common.base.Strings;
import com.sequenceiq.cloudbreak.api.CoreApi;
import com.sequenceiq.cloudbreak.api.endpoint.v4.kubernetes.KubernetesV4Endpoint;
import com.sequenceiq.cloudbreak.controller.mapper.DefaultExceptionMapper;
import com.sequenceiq.cloudbreak.controller.mapper.WebApplicaitonExceptionMapper;
import com.sequenceiq.cloudbreak.controller.v4.AuditEventV4Controller;
import com.sequenceiq.cloudbreak.controller.v4.BlueprintV4Controller;
import com.sequenceiq.cloudbreak.controller.v4.ClusterTemplateV4Controller;
import com.sequenceiq.cloudbreak.controller.v4.DatabaseV4Controller;
import com.sequenceiq.cloudbreak.controller.v4.ImageCatalogV4Controller;
import com.sequenceiq.cloudbreak.controller.v4.EnvironmentV4Controller;
import com.sequenceiq.cloudbreak.controller.v4.KerberosConfigV4Controller;
import com.sequenceiq.cloudbreak.controller.v4.LdapV4Controller;
import com.sequenceiq.cloudbreak.controller.v4.ProxyV4Controller;
import com.sequenceiq.cloudbreak.controller.v4.RecipesV4Controller;
import com.sequenceiq.cloudbreak.controller.v4.UserProfileV4Controller;
import com.sequenceiq.cloudbreak.controller.v4.WorkspaceV4Controller;
import com.sequenceiq.cloudbreak.structuredevent.rest.StructuredEventFilter;
import com.sequenceiq.cloudbreak.util.FileReaderUtils;

import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.config.SwaggerConfigLocator;
import io.swagger.jaxrs.config.SwaggerContextService;

@ApplicationPath(CoreApi.API_ROOT_CONTEXT)
@Controller
public class EndpointConfig extends ResourceConfig {

    private static final List<Class<?>> CONTROLLERS = Arrays.asList(
            AuditEventV4Controller.class,

            AccountPreferencesController.class,
            BlueprintV4Controller.class,
            CloudbreakEventController.class,
            CloudbreakEventV3Controller.class,
            ClusterV1Controller.class,
            ClusterTemplateV4Controller.class,
            CredentialController.class,
            CredentialV3Controller.class,
            DatabaseV4Controller.class,
            EnvironmentV4Controller.class,
            FlexSubscriptionController.class,
            FlexSubscriptionV3Controller.class,
            ImageCatalogV4Controller.class,
            KerberosConfigV4Controller.class,
            KnoxServicesV3Controller.class,
            LdapV4Controller.class,
            ManagementPackController.class,
            ManagementPackV3Controller.class,
            KubernetesV4Endpoint.class,
            WorkspaceV4Controller.class,
            PlatformParameterV1Controller.class,
            PlatformParameterV2Controller.class,
            PlatformParameterV3Controller.class,
            ProxyV4Controller.class,
            RecipesV4Controller.class,
            RepositoryConfigValidationController.class,
            SecurityRuleController.class,
            SettingsController.class,
            SmartSenseSubscriptionController.class,
            SmartSenseSubscriptionV3Controller.class,
            StackV1Controller.class,
            StackV2Controller.class,
            StackV3Controller.class,
            SubscriptionController.class,
            UserProfileV4Controller.class,
            UtilController.class,
            UtilV3Controller.class,
            FileSystemV3Controller.class,
            AutoscaleController.class
    );

    private static final String VERSION_UNAVAILABLE = "unspecified";

    @Value("${info.app.version:}")
    private String cbVersion;

    @Value("${cb.structuredevent.rest.enabled:false}")
    private Boolean auditEnabled;

    @Inject
    private List<ExceptionMapper<?>> exceptionMappers;

    @PostConstruct
    private void init() {
        if (auditEnabled) {
            register(StructuredEventFilter.class);
        }
        registerEndpoints();
        registerExceptionMappers();
    }

    @PostConstruct
    private void registerSwagger() throws IOException {
        BeanConfig swaggerConfig = new BeanConfig();
        swaggerConfig.setTitle("Cloudbreak API");
        swaggerConfig.setDescription(FileReaderUtils.readFileFromClasspath("swagger/cloudbreak-introduction"));
        if (Strings.isNullOrEmpty(cbVersion)) {
            swaggerConfig.setVersion(VERSION_UNAVAILABLE);
        } else {
            swaggerConfig.setVersion(cbVersion);
        }
        swaggerConfig.setSchemes(new String[]{"http", "https"});
        swaggerConfig.setBasePath(CoreApi.API_ROOT_CONTEXT);
        swaggerConfig.setLicenseUrl("https://github.com/hortonworks/cloudbreak/blob/master/LICENSE");
        swaggerConfig.setResourcePackage("com.sequenceiq.cloudbreak.api");
        swaggerConfig.setScan(true);
        swaggerConfig.setContact("https://hortonworks.com/contact-sales/");
        swaggerConfig.setPrettyPrint(true);
        SwaggerConfigLocator.getInstance().putConfig(SwaggerContextService.CONFIG_ID_DEFAULT, swaggerConfig);
    }

    private void registerExceptionMappers() {
        for (ExceptionMapper<?> mapper : exceptionMappers) {
            register(mapper);
        }
        register(WebApplicaitonExceptionMapper.class);
        register(DefaultExceptionMapper.class);
    }

    private void registerEndpoints() {
        CONTROLLERS.forEach(this::register);

        register(io.swagger.jaxrs.listing.ApiListingResource.class);
        register(io.swagger.jaxrs.listing.SwaggerSerializers.class);
        register(io.swagger.jaxrs.listing.AcceptHeaderApiListingResource.class);
    }
}
