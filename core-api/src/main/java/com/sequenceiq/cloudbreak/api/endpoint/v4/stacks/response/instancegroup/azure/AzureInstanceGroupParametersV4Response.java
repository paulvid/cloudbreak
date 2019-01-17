package com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.response.instancegroup.azure;

import java.util.Map;

import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.base.InstanceGroupParametersV4Base;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.response.instancegroup.azure.parameter.AzureAvailabiltySetV4Response;

import io.swagger.annotations.ApiModelProperty;

public class AzureInstanceGroupParametersV4Response extends InstanceGroupParametersV4Base {

    @ApiModelProperty
    private AzureAvailabiltySetV4Response availabilitySet;

    public AzureAvailabiltySetV4Response getAvailabilitySet() {
        return availabilitySet;
    }

    public void setAvailabilitySet(AzureAvailabiltySetV4Response availabilitySet) {
        this.availabilitySet = availabilitySet;
    }

    @Override
    public Map<String, Object> asMap() {
        Map<String, Object> map = super.asMap();
        map.put("faultDomainCount", availabilitySet.getFaultDomainCount());
        map.put("name", availabilitySet.getName());
        map.put("updateDomainCount", availabilitySet.getUpdateDomainCount());
        return map;
    }

    @Override
    public <T> T toClass(Map<String, Object> parameters) {
        AzureInstanceGroupParametersV4Response ret = new AzureInstanceGroupParametersV4Response();
        ret.setDiscoveryName(getParameterOrNull(parameters, "discoveryName"));
        ret.setInstanceName(getParameterOrNull(parameters, "instanceName"));
        AzureAvailabiltySetV4Response availabiltySet = new AzureAvailabiltySetV4Response();
        availabiltySet.setFaultDomainCount(getParameterOrNull(parameters, "faultDomainCount"));
        availabiltySet.setName(getParameterOrNull(parameters, "name"));
        availabiltySet.setUpdateDomainCount(getParameterOrNull(parameters, "updateDomainCount"));
        ret.availabilitySet = availabiltySet;
        return (T) ret;
    }
}
