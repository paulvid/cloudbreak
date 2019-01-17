package com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.response.instancegroup.aws;

import java.util.Map;

import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.base.InstanceGroupParametersV4Base;

public class AwsInstanceGroupParametersV4Response extends InstanceGroupParametersV4Base {

    @Override
    public <T> T toClass(Map<String, Object> parameters) {
        AwsInstanceGroupParametersV4Response ret = new AwsInstanceGroupParametersV4Response();
        ret.setDiscoveryName(getParameterOrNull(parameters, "discoveryName"));
        ret.setInstanceName(getParameterOrNull(parameters, "instanceName"));
        return (T) ret;
    }
}
