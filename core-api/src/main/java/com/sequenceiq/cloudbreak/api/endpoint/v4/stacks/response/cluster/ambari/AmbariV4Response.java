package com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.response.cluster.ambari;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sequenceiq.cloudbreak.api.endpoint.v4.blueprints.responses.BlueprintV4Response;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.base.ConfigStrategy;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.response.cluster.ambari.ambarirepository.AmbariRepositoryV4Response;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.response.cluster.ambari.stackrepository.StackRepositoryV4Response;
import com.sequenceiq.cloudbreak.api.model.JsonEntity;
import com.sequenceiq.cloudbreak.api.model.SecretResponse;
import com.sequenceiq.cloudbreak.doc.ModelDescriptions.BlueprintModelDescription;
import com.sequenceiq.cloudbreak.doc.ModelDescriptions.ClusterModelDescription;
import com.sequenceiq.cloudbreak.doc.ModelDescriptions.StackModelDescription;
import com.sequenceiq.cloudbreak.structuredevent.json.Base64Deserializer;
import com.sequenceiq.cloudbreak.structuredevent.json.Base64Serializer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class AmbariV4Response implements JsonEntity {

    @ApiModelProperty(ClusterModelDescription.BLUEPRINT)
    private BlueprintV4Response blueprint;

    @ApiModelProperty(StackModelDescription.AMBARI_IP)
    private String serverIp;

    @ApiModelProperty(StackModelDescription.AMBARI_URL)
    private String serverUrl;

    @NotNull
    @ApiModelProperty(value = StackModelDescription.USERNAME, required = true)
    private SecretResponse userName;

    @NotNull
    @ApiModelProperty(value = StackModelDescription.PASSWORD, required = true)
    private SecretResponse password;

    @ApiModelProperty(StackModelDescription.DP_AMBARI_USERNAME)
    private SecretResponse dpUser;

    @ApiModelProperty(StackModelDescription.DP_AMBARI_PASSWORD)
    private SecretResponse dpPassword;

    @Valid
    @ApiModelProperty(ClusterModelDescription.AMBARI_STACK_DETAILS)
    private StackRepositoryV4Response stackRepository;

    @Valid
    @ApiModelProperty(ClusterModelDescription.AMBARI_REPO_DETAILS)
    private AmbariRepositoryV4Response repository;

    @ApiModelProperty(ClusterModelDescription.CONFIG_STRATEGY)
    private ConfigStrategy configStrategy = ConfigStrategy.ALWAYS_APPLY_DONT_OVERRIDE_CUSTOM_VALUES;

    @ApiModelProperty(ClusterModelDescription.AMBARI_SECURITY_MASTER_KEY)
    private SecretResponse securityMasterKey;

    @ApiModelProperty(BlueprintModelDescription.AMBARI_BLUEPRINT)
    @JsonSerialize(using = Base64Serializer.class)
    @JsonDeserialize(using = Base64Deserializer.class)
    private String extendedBlueprintText;

    public BlueprintV4Response getBlueprint() {
        return blueprint;
    }

    public void setBlueprint(BlueprintV4Response blueprint) {
        this.blueprint = blueprint;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public SecretResponse getUserName() {
        return userName;
    }

    public void setUserName(SecretResponse userName) {
        this.userName = userName;
    }

    public SecretResponse getPassword() {
        return password;
    }

    public void setPassword(SecretResponse password) {
        this.password = password;
    }

    public SecretResponse getDpUser() {
        return dpUser;
    }

    public void setDpUser(SecretResponse dpUser) {
        this.dpUser = dpUser;
    }

    public SecretResponse getDpPassword() {
        return dpPassword;
    }

    public void setDpPassword(SecretResponse dpPassword) {
        this.dpPassword = dpPassword;
    }

    public StackRepositoryV4Response getStackRepository() {
        return stackRepository;
    }

    public void setStackRepository(StackRepositoryV4Response stackRepository) {
        this.stackRepository = stackRepository;
    }

    public AmbariRepositoryV4Response getRepository() {
        return repository;
    }

    public void setRepository(AmbariRepositoryV4Response repository) {
        this.repository = repository;
    }

    public ConfigStrategy getConfigStrategy() {
        return configStrategy;
    }

    public void setConfigStrategy(ConfigStrategy configStrategy) {
        this.configStrategy = configStrategy;
    }

    public SecretResponse getSecurityMasterKey() {
        return securityMasterKey;
    }

    public void setSecurityMasterKey(SecretResponse securityMasterKey) {
        this.securityMasterKey = securityMasterKey;
    }

    public String getExtendedBlueprintText() {
        return extendedBlueprintText;
    }

    public void setExtendedBlueprintText(String extendedBlueprintText) {
        this.extendedBlueprintText = extendedBlueprintText;
    }
}
