package com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.response.instancegroup.azure.parameter;

import com.sequenceiq.cloudbreak.api.model.JsonEntity;

import io.swagger.annotations.ApiModelProperty;

public class AzureAvailabiltySetV4Response implements JsonEntity {

    @ApiModelProperty
    private String name;

    @ApiModelProperty
    private String faultDomainCount;

    @ApiModelProperty
    private String updateDomainCount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFaultDomainCount() {
        return faultDomainCount;
    }

    public void setFaultDomainCount(String faultDomainCount) {
        this.faultDomainCount = faultDomainCount;
    }

    public String getUpdateDomainCount() {
        return updateDomainCount;
    }

    public void setUpdateDomainCount(String updateDomainCount) {
        this.updateDomainCount = updateDomainCount;
    }
}
