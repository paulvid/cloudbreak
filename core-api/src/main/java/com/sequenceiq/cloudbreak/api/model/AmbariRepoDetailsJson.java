package com.sequenceiq.cloudbreak.api.model;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sequenceiq.cloudbreak.doc.ModelDescriptions.AmbariRepoDetailsDescription;
import com.sequenceiq.cloudbreak.validation.ValidAmbariRepo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ValidAmbariRepo
@ApiModel
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class AmbariRepoDetailsJson implements JsonEntity {

    @NotNull
    @ApiModelProperty(value = AmbariRepoDetailsDescription.VERSION, required = true)
    private String version;

    @NotNull
    @ApiModelProperty(value = AmbariRepoDetailsDescription.AMBARI_BASE_URL, required = true)
    private String baseUrl;

    @ApiModelProperty(AmbariRepoDetailsDescription.AMBARI_REPO_GPG_KEY)
    private String gpgKeyUrl;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getGpgKeyUrl() {
        return gpgKeyUrl;
    }

    public void setGpgKeyUrl(String gpgKeyUrl) {
        this.gpgKeyUrl = gpgKeyUrl;
    }
}
