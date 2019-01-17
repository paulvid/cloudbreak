package com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.base;

import static com.sequenceiq.cloudbreak.doc.ModelDescriptions.MpackDetailsDescription.FORCE;
import static com.sequenceiq.cloudbreak.doc.ModelDescriptions.MpackDetailsDescription.MPACK_URL;
import static com.sequenceiq.cloudbreak.doc.ModelDescriptions.MpackDetailsDescription.PURGE;
import static com.sequenceiq.cloudbreak.doc.ModelDescriptions.MpackDetailsDescription.PURGE_LIST;

import java.util.Collections;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.sequenceiq.cloudbreak.api.model.JsonEntity;
import com.sequenceiq.cloudbreak.doc.ModelDescriptions;

import io.swagger.annotations.ApiModelProperty;

public abstract class ManagementPackV4Base implements JsonEntity {

    @ApiModelProperty(ModelDescriptions.NAME)
    @NotNull
    @Size(max = 100, min = 5, message = "The length of the management pack's name has to be in range of 5 to 100")
    @Pattern(regexp = "(^[a-z][-a-z0-9]*[a-z0-9]$)",
            message = "The management pack's name can only contain lowercase alphanumeric characters and hyphens and has start with an alphanumeric character")
    private String name;

    @ApiModelProperty(ModelDescriptions.DESCRIPTION)
    @Size(max = 1000)
    private String description;

    @ApiModelProperty(MPACK_URL)
    @NotNull
    @Pattern(regexp = "^http[s]?://.*", message = "The URL should start with the protocol (http, https)")
    private String url;

    @ApiModelProperty(PURGE)
    private Boolean purge;

    @ApiModelProperty(PURGE_LIST)
    private List<String> purgeList = Collections.emptyList();

    @ApiModelProperty(FORCE)
    private Boolean force;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean isPurge() {
        return purge;
    }

    public void setPurge(Boolean purge) {
        this.purge = purge;
    }

    public List<String> getPurgeList() {
        return purgeList;
    }

    public void setPurgeList(List<String> purgeList) {
        this.purgeList = purgeList;
    }

    public Boolean isForce() {
        return force;
    }

    public void setForce(Boolean force) {
        this.force = force;
    }
}
