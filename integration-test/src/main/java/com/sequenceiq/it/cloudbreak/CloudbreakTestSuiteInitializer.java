package com.sequenceiq.it.cloudbreak;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.sequenceiq.cloudbreak.api.endpoint.common.StackEndpoint;
import com.sequenceiq.cloudbreak.api.endpoint.v1.CredentialEndpoint;
import com.sequenceiq.cloudbreak.client.CloudbreakClient;
import com.sequenceiq.cloudbreak.client.CloudbreakClient.CloudbreakClientBuilder;
import com.sequenceiq.it.IntegrationTestContext;
import com.sequenceiq.it.SuiteContext;
import com.sequenceiq.it.cloudbreak.config.ITProps;
import com.sequenceiq.it.cloudbreak.v2.CloudbreakV2Constants;
import com.sequenceiq.it.config.IntegrationTestConfiguration;
import com.sequenceiq.it.util.CleanupService;

@ContextConfiguration(classes = IntegrationTestConfiguration.class, initializers = ConfigFileApplicationContextInitializer.class)
public class CloudbreakTestSuiteInitializer extends AbstractTestNGSpringContextTests {

    private static final int WITH_TYPE_LENGTH = 4;

    private static final Logger LOG = LoggerFactory.getLogger(CloudbreakTestSuiteInitializer.class);

    @Value("${integrationtest.cloudbreak.server}")
    private String defaultCloudbreakServer;

    @Value("${integrationtest.testsuite.cleanUpOnFailure}")
    private boolean cleanUpOnFailure;

    @Value("${integrationtest.defaultBlueprintName}")
    private String defaultBlueprintName;

    @Value("${integrationtest.testsuite.skipRemainingTestsAfterOneFailed}")
    private boolean skipRemainingSuiteTestsAfterOneFailed;

    @Value("${integrationtest.cleanup.cleanupBeforeStart}")
    private boolean cleanUpBeforeStart;

    @Value("${integrationtest.ambari.defaultAmbariUser}")
    private String defaultAmbariUser;

    @Value("${integrationtest.ambari.defaultAmbariPassword}")
    private String defaultAmbariPassword;

    @Value("${integrationtest.ambari.defaultAmbariPort}")
    private String defaultAmbariPort;

    @Value("${integrationtest.caas.protocol:}")
    private String caasProtocol;

    @Value("${integrationtest.caas.address:}")
    private String caasAddress;

    @Value("${server.contextPath:/cb}")
    private String cbRootContextPath;

    @Inject
    private ITProps itProps;

    @Inject
    private TemplateAdditionHelper templateAdditionHelper;

    @Inject
    private SuiteContext suiteContext;

    @Inject
    private CleanupService cleanUpService;

    private IntegrationTestContext itContext;

    @BeforeSuite(dependsOnGroups = "suiteInit")
    public void initContext(ITestContext testContext) throws Exception {
        // Workaround of https://jira.spring.io/browse/SPR-4072
        springTestContextBeforeTestClass();
        springTestContextPrepareTestInstance();

        itContext = suiteContext.getItContext(testContext.getSuite().getName());
    }

    @BeforeSuite(dependsOnMethods = "initContext")
    @Parameters({"cloudbreakServer", "cloudProvider", "credentialName", "instanceGroups", "hostGroups", "blueprintName",
            "stackName", "networkName", "securityGroupName"})
    public void initCloudbreakSuite(@Optional("") String cloudbreakServer, @Optional("") String cloudProvider, @Optional("") String credentialName,
            @Optional("") String instanceGroups, @Optional("") String hostGroups, @Optional("") String blueprintName,
            @Optional("") String stackName, @Optional("") String networkName, @Optional("") String securityGroupName) {
        cloudbreakServer = StringUtils.hasLength(cloudbreakServer) ? cloudbreakServer : defaultCloudbreakServer;
        String cbServerRoot = cloudbreakServer + cbRootContextPath;
        String refreshToken = itContext.getContextParam(IntegrationTestContext.REFRESH_TOKEN);
        itContext.putContextParam(CloudbreakITContextConstants.SKIP_REMAINING_SUITETEST_AFTER_ONE_FAILED, skipRemainingSuiteTestsAfterOneFailed);
        itContext.putContextParam(CloudbreakITContextConstants.CLOUDBREAK_SERVER, cloudbreakServer);
        itContext.putContextParam(CloudbreakITContextConstants.CLOUDBREAK_SERVER_ROOT, cbServerRoot);
        itContext.putContextParam(CloudbreakITContextConstants.CLOUDPROVIDER, cloudProvider);

        String[] cloudbreakServerSplit = cloudbreakServer.split("://");
        if (StringUtils.isEmpty(caasProtocol)) {
            caasProtocol = cloudbreakServerSplit[0];
        }
        if (StringUtils.isEmpty(caasAddress)) {
            caasAddress = cloudbreakServerSplit[1];
        }

        itContext.putContextParam(CloudbreakITContextConstants.CAAS_PROTOCOL, caasProtocol);
        itContext.putContextParam(CloudbreakITContextConstants.CAAS_ADDRESS, caasAddress);

        CloudbreakClient cloudbreakClient = new CloudbreakClientBuilder(cbServerRoot, caasProtocol, caasAddress)
                .withCertificateValidation(false)
                .withIgnorePreValidation(true)
                .withDebug(true)
                .withCredential(refreshToken)
                .build();

        itContext.putContextParam(CloudbreakITContextConstants.CLOUDBREAK_CLIENT, cloudbreakClient);
        if (cleanUpBeforeStart) {
            cleanUpService.deleteTestStacksAndResources(cloudbreakClient);
        }
           putCredentialToContext(
                itContext.getContextParam(CloudbreakITContextConstants.CLOUDBREAK_CLIENT, CloudbreakClient.class).credentialEndpoint(), cloudProvider,
                credentialName);
        putStackToContextIfExist(
                itContext.getContextParam(CloudbreakITContextConstants.CLOUDBREAK_CLIENT, CloudbreakClient.class).stackV1Endpoint(), stackName);
        if (StringUtils.hasLength(instanceGroups)) {
            List<String[]> instanceGroupStrings = templateAdditionHelper.parseCommaSeparatedRows(instanceGroups);
        }
        if (StringUtils.hasLength(hostGroups)) {
            List<String[]> hostGroupStrings = templateAdditionHelper.parseCommaSeparatedRows(hostGroups);
            itContext.putContextParam(CloudbreakITContextConstants.HOSTGROUP_ID, createHostGroups(hostGroupStrings));
        }
    }

    @BeforeSuite(dependsOnMethods = "initContext")
    @Parameters({"ambariUser", "ambariPassword", "ambariPort"})
    public void initAmbariCredentials(@Optional("") String ambariUser, @Optional("") String ambariPassword, @Optional("") String ambariPort) {
        putAmbariCredentialsToContext(ambariUser, ambariPassword, ambariPort);
    }

    private void putAmbariCredentialsToContext(String ambariUser, String ambariPassword, String ambariPort) {
        if (StringUtils.isEmpty(ambariUser)) {
            ambariUser = defaultAmbariUser;
        }
        if (StringUtils.isEmpty(ambariPassword)) {
            ambariPassword = defaultAmbariPassword;
        }
        if (StringUtils.isEmpty(ambariPort)) {
            ambariPort = defaultAmbariPort;
        }

        itContext.putContextParam(CloudbreakITContextConstants.AMBARI_USER_ID, ambariUser);
        itContext.putContextParam(CloudbreakITContextConstants.AMBARI_PASSWORD_ID, ambariPassword);
        itContext.putContextParam(CloudbreakITContextConstants.AMBARI_PORT_ID, ambariPort);
    }

    private void putStackToContextIfExist(StackEndpoint endpoint, String stackName) {
        if (StringUtils.hasLength(stackName)) {
            Long resourceId = endpoint.getStackFromDefaultWorkspace(stackName, new HashSet<>()).getId();
            itContext.putContextParam(CloudbreakITContextConstants.STACK_ID, resourceId.toString());
            itContext.putContextParam(CloudbreakV2Constants.STACK_NAME, stackName);
        }
    }

    private void putCredentialToContext(CredentialEndpoint endpoint, String cloudProvider, String credentialName) {
        if (StringUtils.isEmpty(credentialName)) {
            String defaultCredentialName = itProps.getCredentialName(cloudProvider);
            if (!"__ignored__".equals(defaultCredentialName)) {
                credentialName = defaultCredentialName;
            }
        }
        if (StringUtils.hasLength(credentialName)) {
            Long resourceId = endpoint.getPublic(credentialName).getId();
            itContext.putContextParam(CloudbreakITContextConstants.CREDENTIAL_ID, resourceId.toString());
        }
    }

    private List<HostGroup> createHostGroups(Iterable<String[]> hostGroupStrings) {
        List<HostGroup> hostGroups = new ArrayList<>();
        for (String[] hostGroupStr : hostGroupStrings) {
            hostGroups.add(new HostGroup(hostGroupStr[0], hostGroupStr[1], Integer.valueOf(hostGroupStr[2])));
        }
        return hostGroups;
    }

    @AfterSuite(alwaysRun = true)
    @Parameters("cleanUp")
    public void cleanUp(@Optional("true") boolean cleanUp) {
        if (isCleanUpNeeded(cleanUp)) {
            CloudbreakClient cloudbreakClient = itContext.getContextParam(CloudbreakITContextConstants.CLOUDBREAK_CLIENT, CloudbreakClient.class);
            String stackId = itContext.getCleanUpParameter(CloudbreakITContextConstants.STACK_ID);
            cleanUpService.deleteStackAndWait(cloudbreakClient, stackId);
            List<InstanceGroup> instanceGroups = itContext.getCleanUpParameter(CloudbreakITContextConstants.TEMPLATE_ID, List.class);
            if (instanceGroups != null && !instanceGroups.isEmpty()) {
                Collection<String> deletedTemplates = new HashSet<>();
                for (InstanceGroup ig : instanceGroups) {
                    if (!deletedTemplates.contains(ig.getTemplateId())) {
                        deletedTemplates.add(ig.getTemplateId());
                    }
                }
            }
            cleanUpService.deleteCredential(cloudbreakClient, itContext.getCleanUpParameter(CloudbreakITContextConstants.CREDENTIAL_ID));

        }
    }

    private boolean isCleanUpNeeded(boolean cleanUp) {
        boolean noTestsFailed = CollectionUtils.isEmpty(itContext.getContextParam(CloudbreakITContextConstants.FAILED_TESTS, List.class));
        return cleanUp && (cleanUpOnFailure || noTestsFailed);
    }
}
