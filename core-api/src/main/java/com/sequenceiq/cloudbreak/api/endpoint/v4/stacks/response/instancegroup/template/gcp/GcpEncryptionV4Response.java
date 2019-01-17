package com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.response.instancegroup.template.gcp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.base.EncryptionType;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.base.KeyEncryptionMethod;
import com.sequenceiq.cloudbreak.api.model.JsonEntity;
import com.sequenceiq.cloudbreak.doc.ModelDescriptions.TemplateModelDescription;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
@JsonIgnoreProperties(ignoreUnknown = true)
public class GcpEncryptionV4Response implements JsonEntity {

    @ApiModelProperty(value = TemplateModelDescription.GCP_ENCRYPTION_TYPE, allowableValues = "DEFAULT,CUSTOM")
    private EncryptionType type;

    @ApiModelProperty(value = TemplateModelDescription.ENCRYPTION_METHOD, allowableValues = "RAW,RSA,KMS")
    private KeyEncryptionMethod keyEncryptionMethod;

    @ApiModelProperty(TemplateModelDescription.ENCRYPTION_KEY)
    private String key;

    public EncryptionType getType() {
        return type;
    }

    public void setType(EncryptionType type) {
        this.type = type;
    }

    public KeyEncryptionMethod getKeyEncryptionMethod() {
        return keyEncryptionMethod;
    }

    public void setKeyEncryptionMethod(KeyEncryptionMethod keyEncryptionMethod) {
        this.keyEncryptionMethod = keyEncryptionMethod;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
