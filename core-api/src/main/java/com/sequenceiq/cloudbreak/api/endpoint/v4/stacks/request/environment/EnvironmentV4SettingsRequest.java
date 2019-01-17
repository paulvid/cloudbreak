package com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.environment;

import static com.sequenceiq.cloudbreak.doc.ModelDescriptions.StackModelDescription.CREDENTIAL_NAME;
import static com.sequenceiq.cloudbreak.doc.ModelDescriptions.StackModelDescription.ENVIRONMENT;
import static com.sequenceiq.cloudbreak.doc.ModelDescriptions.StackModelDescription.PLACEMENT_SETTINGS;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.environment.placement.PlacementSettingsV4Request;
import com.sequenceiq.cloudbreak.api.model.JsonEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class EnvironmentV4SettingsRequest implements JsonEntity {

    @ApiModelProperty(ENVIRONMENT)
    private String name;

    @NotNull
    @ApiModelProperty(value = PLACEMENT_SETTINGS, required = true)
    @Valid
    private PlacementSettingsV4Request placement;

    @ApiModelProperty(CREDENTIAL_NAME)
    private String credentialName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlacementSettingsV4Request getPlacement() {
        return placement;
    }

    public void setPlacement(PlacementSettingsV4Request placement) {
        this.placement = placement;
    }

    public String getCredentialName() {
        return credentialName;
    }

    public void setCredentialName(String credentialName) {
        this.credentialName = credentialName;
    }
}
