package com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.response.instancegroup.template.openstack;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.base.InstanceTemplateParameterV4Base;

import io.swagger.annotations.ApiModel;

@ApiModel
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class OpenStackInstanceTemplateParametersV4Response extends InstanceTemplateParameterV4Base {

    @Override
    public <T> T toClass(Map<String, Object> parameters) {
        OpenStackInstanceTemplateParametersV4Response ret = new OpenStackInstanceTemplateParametersV4Response();
        ret.setPlatformType(getParameterOrNull(parameters, "platformType"));
        return (T) ret;
    }
}
