package com.sequenceiq.it.cloudbreak.newway.cloud;

import java.util.Collections;
import java.util.Map;

import org.apache.commons.lang3.NotImplementedException;

import com.sequenceiq.cloudbreak.api.endpoint.v4.credentials.parameters.openstack.KeystoneV2Parameters;
import com.sequenceiq.cloudbreak.api.endpoint.v4.credentials.parameters.openstack.KeystoneV3Parameters;
import com.sequenceiq.cloudbreak.api.endpoint.v4.credentials.parameters.openstack.OpenstackCredentialV4Parameters;
import com.sequenceiq.cloudbreak.api.endpoint.v4.credentials.parameters.openstack.ProjectKeystoneV3Parameters;
import com.sequenceiq.cloudbreak.api.model.AmbariStackDetailsJson;
import com.sequenceiq.cloudbreak.api.model.stack.StackAuthenticationRequest;
import com.sequenceiq.cloudbreak.api.model.v2.AmbariV2Request;
import com.sequenceiq.cloudbreak.api.model.v2.NetworkV2Request;
import com.sequenceiq.cloudbreak.api.model.v2.TemplateV2Request;
import com.sequenceiq.it.cloudbreak.newway.Cluster;
import com.sequenceiq.it.cloudbreak.newway.Credential;
import com.sequenceiq.it.cloudbreak.newway.CredentialEntity;
import com.sequenceiq.it.cloudbreak.newway.TestParameter;

public class OpenstackCloudProvider extends CloudProviderHelper {

    public static final String OPENSTACK = "openstack";

    public static final String OPENSTACK_CAPITAL = "OPENSTACK";

    private static final String CREDENTIAL_DEFAULT_NAME = "autotesting-os-cred";

    private static final String OPENSTACK_CLUSTER_DEFAULT_NAME = "autotesting-os-cluster";

    private static final String BLUEPRINT_DEFAULT_NAME = "Data Science: Apache Spark 2, Apache Zeppelin";

    private static final String NETWORK_DEFAULT_NAME = "autotesting-os-net";

    private static final String VPC_DEFAULT_ID = "f955d535-8a7a-456f-a90a-430d45f1c92b";

    private static final String SUBNET_DEFAULT_ID = "7a2c4679-1312-4cf6-91a5-76a2c1e3faa8";

    private static final String PUBLIC_NETWORK_ID = "999e09bc-cf75-4a19-98fb-c0b4ddee6d93";

    private static final String ROUTER_ID = "aa402f0a-8652-4799-904d-e73c95c1a711";

    private static final String INTERNET_GATEWAY_ID = null;

    private static final String DEFAULT_SUBNET_CIDR = "10.0.0.0/16";

    private static final String NETWORK_DEFAULT_DESCRIPTION = "autotesting os network";

    private static final String NETWORKING_DEFAULT_OPTION = "self-service";

    private static final String CREDENTIAL_ENGENDPOINT_URL = "integrationtest.openstackEngcredential.endpoint";

    private final ResourceHelper<?> resourceHelper;

    public OpenstackCloudProvider(TestParameter testParameter) {
        super(testParameter);
        resourceHelper = null;
    }

    public String engOpenStackEndpoint() {
        return getTestParameter().get(CREDENTIAL_ENGENDPOINT_URL);
    }

    @Override
    public CredentialEntity aValidCredential(boolean create) {
        CredentialEntity credential = create ? Credential.created() : Credential.request();
        return credential
                .withName(getCredentialName())
                .withDescription(CREDENTIAL_DEFAULT_DESCRIPTION)
                .withCloudPlatform(OPENSTACK_CAPITAL)
                .withOpenstackParameters(openstackCredentialDetailsKilo());
    }

    @Override
    public String availabilityZone() {
        return getTestParameter().getWithDefault("openstackAvailibilityZone", "nova");
    }

    @Override
    public String region() {
        return getTestParameter().getWithDefault("openstackRegion", "RegionOne");
    }

    @Override
    public StackAuthenticationRequest stackauth() {
        StackAuthenticationRequest stackauth = new StackAuthenticationRequest();

        stackauth.setPublicKey(getTestParameter().get(CloudProviderHelper.INTEGRATIONTEST_PUBLIC_KEY_FILE).substring(BEGIN_INDEX));
        return stackauth;
    }

    @Override
    public TemplateV2Request template() {
        TemplateV2Request t = new TemplateV2Request();

        t.setInstanceType(getTestParameter().getWithDefault("openstackInstanceType", "m1.xlarge"));
        t.setVolumeCount(Integer.parseInt(getTestParameter().getWithDefault("openstackInstanceVolumeCount", "0")));
        t.setVolumeSize(Integer.parseInt(getTestParameter().getWithDefault("openstackInstanceVolumeSize", "50")));
        t.setVolumeType(getTestParameter().getWithDefault("openstackInstanceVolumeType", "HDD"));

        return t;
    }

    @Override
    public String getClusterName() {
        return getTestParameter().getWithDefault("openstackClusterName", OPENSTACK_CLUSTER_DEFAULT_NAME);
    }

    @Override
    public String getPlatform() {
        return OPENSTACK_CAPITAL;
    }

    @Override
    public String getCredentialName() {
        return getTestParameter().getWithDefault("openstackCredentialName", CREDENTIAL_DEFAULT_NAME);
    }

    @Override
    public String getBlueprintName() {
        return getTestParameter().getWithDefault("openstackBlueprintName", BLUEPRINT_DEFAULT_NAME);

    }

    @Override
    public String getNetworkName() {
        return getTestParameter().getWithDefault("openstackNetworkName", NETWORK_DEFAULT_NAME);

    }

    @Override
    public String getSubnetCIDR() {
        return getTestParameter().getWithDefault("openstackSubnetCIDR", DEFAULT_SUBNET_CIDR);

    }

    @Override
    public String getVpcId() {
        return getTestParameter().getWithDefault("openstackVcpId", VPC_DEFAULT_ID);

    }

    @Override
    public String getSubnetId() {
        return getTestParameter().getWithDefault("openstackSubnetId", SUBNET_DEFAULT_ID);

    }

    public String getNetworkingOption() {
        return getTestParameter().getWithDefault("networkingOption", NETWORKING_DEFAULT_OPTION);

    }

    public String getPublicNetId() {
        return getTestParameter().getWithDefault("publicNetId", PUBLIC_NETWORK_ID);

    }

    public String getRouterId() {
        return getTestParameter().getWithDefault("routerId", ROUTER_ID);

    }

    public String getInternetGatewayId() {
        return getTestParameter().getWithDefault("openstackInternetGatewayId", INTERNET_GATEWAY_ID);

    }

    @Override
    public Map<String, Object> newNetworkProperties() {
        return Map.of("publicNetId", getPublicNetId());
    }

    @Override
    public Map<String, Object> networkProperties() {
        return Map.of("publicNetId", getPublicNetId(), "networkId", getVpcId(), "routerId",
                getRouterId());
    }

    @Override
    public Map<String, Object> subnetProperties() {
        return Map.of("networkingOption", getNetworkingOption(), "publicNetId", getPublicNetId(), "subnetId", getSubnetId(),
                "networkId", getVpcId(), "routerId", getRouterId(), "internetGatewayId", getInternetGatewayId());
    }

    @Override
    public NetworkV2Request newNetwork() {
        NetworkV2Request network = new NetworkV2Request();
        network.setSubnetCIDR(getSubnetCIDR());
        network.setParameters(newNetworkProperties());
        return network;
    }

    @Override
    public NetworkV2Request existingNetwork() {
        NetworkV2Request network = new NetworkV2Request();
        network.setSubnetCIDR(getSubnetCIDR());
        network.setParameters(networkProperties());
        return network;
    }

    @Override
    public NetworkV2Request existingSubnet() {
        NetworkV2Request network = new NetworkV2Request();
        network.setParameters(subnetProperties());
        return network;
    }

    @Override
    public AmbariV2Request getAmbariRequestWithNoConfigStrategyAndEmptyMpacks(String blueprintName) {
        var ambari = ambariRequestWithBlueprintName(blueprintName);
        var stackDetails = new AmbariStackDetailsJson();
        stackDetails.setMpacks(Collections.emptyList());
        ambari.setConfigStrategy(null);
        ambari.setAmbariStackDetails(stackDetails);
        return ambari;
    }

    @Override
    public ResourceHelper<?> getResourceHelper() {
        throw new NotImplementedException("Resource helper for Openstack is not implemented yet");
    }

    @Override
    public Cluster aValidDatalakeCluster() {
        throw new NotImplementedException("not implemented!");
    }

    @Override
    public Cluster aValidAttachedCluster(String datalakeClusterName) {
        throw new NotImplementedException("not implemented!");
    }

    public OpenstackCredentialV4Parameters openstackCredentialDetailsKilo() {
        OpenstackCredentialV4Parameters credentialParameters = new OpenstackCredentialV4Parameters();
        credentialParameters.setPassword(getTestParameter().get("integrationtest.openstackcredential.password"));
        credentialParameters.setUserName(getTestParameter().get("integrationtest.openstackcredential.userName"));
        credentialParameters.setEndpoint(getTestParameter().get("integrationtest.openstackcredential.endpoint"));
        KeystoneV2Parameters keystoneV2Parameters = new KeystoneV2Parameters();
        keystoneV2Parameters.setTenantName(getTestParameter().get("integrationtest.openstackcredential.tenantName"));
        return credentialParameters;
    }

    public OpenstackCredentialV4Parameters openstackCredentialDetailsEngineering() {
        OpenstackCredentialV4Parameters credentialParameters = new OpenstackCredentialV4Parameters();
        credentialParameters.setUserName(getTestParameter().get("integrationtest.openstackEngcredential.userName"));
        credentialParameters.setEndpoint(getTestParameter().get("integrationtest.openstackEngcredential.endpoint"));
        credentialParameters.setPassword(getTestParameter().get("integrationtest.openstackEngcredential.password"));
        KeystoneV2Parameters keystoneV2Parameters = new KeystoneV2Parameters();
        keystoneV2Parameters.setTenantName(getTestParameter().get("integrationtest.openstackEngcredential.tenantName"));
        credentialParameters.setKeystoneV2Parameters(keystoneV2Parameters);
        return credentialParameters;
    }

    public OpenstackCredentialV4Parameters openstackCredentialDetailsField() {
        OpenstackCredentialV4Parameters credentialParameters = new OpenstackCredentialV4Parameters();
        credentialParameters.setPassword(getTestParameter().get("integrationtest.openstackFieldcredential.password"));
        credentialParameters.setUserName(getTestParameter().get("integrationtest.openstackFieldcredential.userName"));
        credentialParameters.setEndpoint(getTestParameter().get("integrationtest.openstackFieldcredential.endpoint"));
        credentialParameters.setFacing("internal");
        KeystoneV3Parameters keystoneV3Parameters = new KeystoneV3Parameters();
        ProjectKeystoneV3Parameters projectKeystoneV3Parameters = new ProjectKeystoneV3Parameters();
        projectKeystoneV3Parameters.setUserDomain(getTestParameter().get("integrationtest.openstackFieldcredential.userDomain"));
        projectKeystoneV3Parameters.setProjectDomainName(getTestParameter().get("integrationtest.openstackFieldcredential.projectDomainName"));
        projectKeystoneV3Parameters.setProjectName(getTestParameter().get("integrationtest.openstackFieldcredential.projectName"));
        keystoneV3Parameters.setProject(projectKeystoneV3Parameters);
        return credentialParameters;
    }

    public OpenstackCredentialV4Parameters openstackCredentialDetailsInvalidUser() {
        OpenstackCredentialV4Parameters credentialParameters = new OpenstackCredentialV4Parameters();
        credentialParameters.setUserName("kisnyul");
        credentialParameters.setEndpoint(getTestParameter().get("integrationtest.openstackcredential.endpoint"));
        credentialParameters.setPassword(getTestParameter().get("integrationtest.openstackcredential.password"));
        KeystoneV2Parameters keystoneV2Parameters = new KeystoneV2Parameters();
        keystoneV2Parameters.setTenantName(getTestParameter().get("integrationtest.openstackcredential.tenantName"));
        credentialParameters.setKeystoneV2Parameters(keystoneV2Parameters);
        return credentialParameters;
    }

    public OpenstackCredentialV4Parameters openstackCredentialDetailsInvalidEndpoint() {
        OpenstackCredentialV4Parameters credentialParameters = new OpenstackCredentialV4Parameters();
        credentialParameters.setUserName(getTestParameter().get("integrationtest.openstackcredential.userName"));
        credentialParameters.setEndpoint("https://index.hu/");
        credentialParameters.setPassword(getTestParameter().get("integrationtest.openstackcredential.password"));
        KeystoneV2Parameters keystoneV2Parameters = new KeystoneV2Parameters();
        keystoneV2Parameters.setTenantName(getTestParameter().get("integrationtest.openstackcredential.tenantName"));
        return credentialParameters;
    }
}