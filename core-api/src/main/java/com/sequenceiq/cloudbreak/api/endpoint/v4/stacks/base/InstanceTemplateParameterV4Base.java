package com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.base;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sequenceiq.cloudbreak.api.endpoint.v4.common.mappable.CloudPlatform;
import com.sequenceiq.cloudbreak.api.endpoint.v4.common.mappable.Mappable;
import com.sequenceiq.cloudbreak.api.model.JsonEntity;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class InstanceTemplateParameterV4Base implements JsonEntity, Mappable {

    @ApiModelProperty
    private CloudPlatform platformType;

    public CloudPlatform getPlatformType() {
        return platformType;
    }

    public void setPlatformType(CloudPlatform platformType) {
        this.platformType = platformType;
    }

    @Override
    public Map<String, Object> asMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("platformType", platformType.name());
        return map;
    }
}
