package com.sequenceiq.cloudbreak.api.model.stack;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import com.sequenceiq.cloudbreak.api.endpoint.v4.blueprints.requests.BlueprintV4Request;
import com.sequenceiq.cloudbreak.api.endpoint.v4.credentials.requests.CredentialV4Request;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.HostGroupV4Request;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.cluster.storage.CloudStorageV4Request;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.instancegroup.InstanceGroupV4Request;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.network.NetworkV4Request;
import com.sequenceiq.cloudbreak.api.model.JsonEntity;
import com.sequenceiq.cloudbreak.doc.ModelDescriptions;
import com.sequenceiq.cloudbreak.doc.ModelDescriptions.ClusterModelDescription;
import com.sequenceiq.cloudbreak.doc.ModelDescriptions.StackModelDescription;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class StackValidationRequest implements JsonEntity {
    @ApiModelProperty(value = ClusterModelDescription.HOSTGROUPS, required = true)
    private Set<HostGroupV4Request> hostGroups = new HashSet<>();

    @ApiModelProperty(value = StackModelDescription.INSTANCE_GROUPS, required = true)
    private Set<InstanceGroupV4Request> instanceGroups = new HashSet<>();

    @ApiModelProperty(ClusterModelDescription.BLUEPRINT_ID)
    private Long blueprintId;

    @ApiModelProperty(ClusterModelDescription.BLUEPRINT_NAME)
    private String blueprintName;

    @ApiModelProperty(ClusterModelDescription.BLUEPRINT)
    private BlueprintV4Request blueprint;

    @ApiModelProperty(StackModelDescription.NETWORK_ID)
    private Long networkId;

    @ApiModelProperty(StackModelDescription.NETWORK)
    private NetworkV4Request network;

    @NotNull
    @ApiModelProperty(value = ModelDescriptions.CLOUD_PLATFORM, required = true)
    private String platform;

    @ApiModelProperty(StackModelDescription.ENVIRONMENT)
    private String environment;

    @ApiModelProperty(StackModelDescription.CREDENTIAL_ID)
    private Long credentialId;

    @ApiModelProperty(StackModelDescription.CREDENTIAL_NAME)
    private String credentialName;

    @ApiModelProperty(StackModelDescription.CREDENTIAL)
    private CredentialV4Request credential;

    @ApiModelProperty(StackModelDescription.FILESYSTEM)
    private CloudStorageV4Request fileSystem;

    public Set<HostGroupV4Request> getHostGroups() {
        return hostGroups;
    }

    public void setHostGroups(Set<HostGroupV4Request> hostGroups) {
        this.hostGroups = hostGroups;
    }

    public Set<InstanceGroupV4Request> getInstanceGroups() {
        return instanceGroups;
    }

    public void setInstanceGroups(Set<InstanceGroupV4Request> instanceGroups) {
        this.instanceGroups = instanceGroups;
    }

    public Long getBlueprintId() {
        return blueprintId;
    }

    public void setBlueprintId(Long blueprintId) {
        this.blueprintId = blueprintId;
    }

    public String getBlueprintName() {
        return blueprintName;
    }

    public void setBlueprintName(String blueprintName) {
        this.blueprintName = blueprintName;
    }

    public BlueprintV4Request getBlueprint() {
        return blueprint;
    }

    public void setBlueprint(BlueprintV4Request blueprint) {
        this.blueprint = blueprint;
    }

    public Long getNetworkId() {
        return networkId;
    }

    public void setNetworkId(Long networkId) {
        this.networkId = networkId;
    }

    public NetworkV4Request getNetwork() {
        return network;
    }

    public void setNetwork(NetworkV4Request network) {
        this.network = network;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public Long getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(Long credentialId) {
        this.credentialId = credentialId;
    }

    public String getCredentialName() {
        return credentialName;
    }

    public void setCredentialName(String credentialName) {
        this.credentialName = credentialName;
    }

    public CredentialV4Request getCredential() {
        return credential;
    }

    public void setCredential(CredentialV4Request credential) {
        this.credential = credential;
    }

    public CloudStorageV4Request getFileSystem() {
        return fileSystem;
    }

    public void setFileSystem(CloudStorageV4Request fileSystem) {
        this.fileSystem = fileSystem;
    }
}
