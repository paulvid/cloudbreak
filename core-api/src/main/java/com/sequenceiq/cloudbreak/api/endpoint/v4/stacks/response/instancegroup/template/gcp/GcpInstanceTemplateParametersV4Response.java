package com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.response.instancegroup.template.gcp;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.base.EncryptionType;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.base.KeyEncryptionMethod;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.base.InstanceTemplateParameterV4Base;
import com.sequenceiq.cloudbreak.doc.ModelDescriptions.TemplateModelDescription;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class GcpInstanceTemplateParametersV4Response extends InstanceTemplateParameterV4Base {

    @ApiModelProperty(TemplateModelDescription.ENCRYPTION)
    private GcpEncryptionV4Response encryption;

    @ApiModelProperty
    private Boolean preemptible;

    public GcpEncryptionV4Response getEncryption() {
        return encryption;
    }

    public void setEncryption(GcpEncryptionV4Response encryption) {
        this.encryption = encryption;
    }

    @Override
    public Map<String, Object> asMap() {
        Map<String, Object> map = super.asMap();
        map.put("key", encryption.getKey());
        map.put("keyEncryptionMethod", encryption.getKeyEncryptionMethod());
        map.put("type", encryption.getType());
        map.put("preemptible", preemptible);
        return map;
    }

    @Override
    public <T> T toClass(Map<String, Object> parameters) {
        GcpInstanceTemplateParametersV4Response ret = new GcpInstanceTemplateParametersV4Response();
        ret.setPlatformType(getParameterOrNull(parameters, "platformType"));
        GcpEncryptionV4Response encryption = new GcpEncryptionV4Response();
        encryption.setKey(getParameterOrNull(parameters, "key"));
        String keyEncryptionMethod = getParameterOrNull(parameters, "keyEncryptionMethod");
        if (keyEncryptionMethod != null) {
            encryption.setKeyEncryptionMethod(KeyEncryptionMethod.valueOf(keyEncryptionMethod));
        }
        String type = getParameterOrNull(parameters, "type");
        if (type != null) {
            encryption.setType(EncryptionType.valueOf(type));
        }
        ret.encryption = encryption;
        String preemptible = getParameterOrNull(parameters, "preemptible");
        if (preemptible != null) {
            ret.preemptible = Boolean.valueOf(preemptible);
        }
        return (T) ret;
    }
}
