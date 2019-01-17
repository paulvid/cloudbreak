package com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.response.instancegroup.template.aws;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.base.EncryptionType;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.base.InstanceTemplateParameterV4Base;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.base.parameter.template.AwsEncryptionParametersV4;
import com.sequenceiq.cloudbreak.doc.ModelDescriptions.TemplateModelDescription;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class AwsInstanceTemplateParametersV4Response extends InstanceTemplateParameterV4Base {

    @ApiModelProperty(TemplateModelDescription.AWS_SPOT_PRICE)
    private Double spotPrice;

    @ApiModelProperty(TemplateModelDescription.ENCRYPTION)
    private AwsEncryptionV4Response encryption;

    @ApiModelProperty(TemplateModelDescription.ENCRYPTED)
    private Boolean encrypted;

    public AwsEncryptionV4Response getEncryption() {
        return encryption;
    }

    public void setEncryption(AwsEncryptionV4Response encryption) {
        this.encryption = encryption;
    }

    public Boolean isEncrypted() {
        return encrypted;
    }

    public void setEncrypted(Boolean encrypted) {
        this.encrypted = encrypted;
    }

    public Double getSpotPrice() {
        return spotPrice;
    }

    public void setSpotPrice(Double spotPrice) {
        this.spotPrice = spotPrice;
    }

    @Override
    public Map<String, Object> asMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("spotPrice", spotPrice);
        map.put("key", encryption.getKey());
        map.put("type", encryption.getType());
        return map;
    }

    @Override
    public <T> T toClass(Map<String, Object> parameters) {
        AwsInstanceTemplateParametersV4Response ret = new AwsInstanceTemplateParametersV4Response();
        String spotPrice = getParameterOrNull(parameters, "spotPrice");
        if (spotPrice != null) {
            ret.spotPrice = Double.parseDouble(spotPrice);
        }
        AwsEncryptionParametersV4 encription = new AwsEncryptionParametersV4();
        encription.setKey(getParameterOrNull(parameters, "key"));
        String type = getParameterOrNull(parameters, "type");
        if (type != null) {
            encription.setType(EncryptionType.valueOf(type));
        }
        return (T) ret;
    }
}
