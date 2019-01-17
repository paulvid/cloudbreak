package com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.response.instancegroup.gcp;

import java.util.Map;

import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.base.InstanceGroupParametersV4Base;

public class GcpInstanceGroupParametersV4Response extends InstanceGroupParametersV4Base {

    private String opId;

    public String getOpId() {
        return opId;
    }

    public void setOpId(String opId) {
        this.opId = opId;
    }

    @Override
    public Map<String, Object> asMap() {
        Map<String, Object> map = super.asMap();
        map.put("opid", opId);
        return map;
    }

    @Override
    public <T> T toClass(Map<String, Object> parameters) {
        GcpInstanceGroupParametersV4Response ret = new GcpInstanceGroupParametersV4Response();
        ret.setDiscoveryName(getParameterOrNull(parameters, "discoveryName"));
        ret.setInstanceName(getParameterOrNull(parameters, "instanceName"));
        ret.opId = getParameterOrNull(parameters, "opid");
        return (T) ret;
    }
}
