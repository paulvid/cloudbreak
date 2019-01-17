package com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.response.instancegroup.template.azure;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.base.InstanceTemplateParameterV4Base;
import com.sequenceiq.cloudbreak.doc.ModelDescriptions.TemplateModelDescription;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class AzureInstanceTemplateParametersV4Response extends InstanceTemplateParameterV4Base {

    @ApiModelProperty(TemplateModelDescription.AZURE_PRIVATE_ID)
    private String privateId;

    public String getPrivateId() {
        return privateId;
    }

    public void setPrivateId(String privateId) {
        this.privateId = privateId;
    }

    @Override
    public <T> T toClass(Map<String, Object> parameters) {
        AzureInstanceTemplateParametersV4Response ret = new AzureInstanceTemplateParametersV4Response();
        ret.privateId = getParameterOrNull(parameters, "privateId");
        ret.setPlatformType(getParameterOrNull(parameters, "platformType"));
        return (T) ret;
    }
}
