package com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.instancegroup.template;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sequenceiq.cloudbreak.api.endpoint.v4.common.mappable.Mappable;
import com.sequenceiq.cloudbreak.api.endpoint.v4.common.mappable.ProviderParametersBase;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.instancegroup.custominstance.CustomInstanceV4Request;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.base.parameter.template.AwsInstanceTemplateParametersV4;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.base.parameter.template.AzureInstanceTemplateParametersV4;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.base.parameter.template.GcpInstanceTemplateParametersV4;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.base.parameter.template.OpenStackInstanceTemplateParametersV4;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.instancegroup.template.volume.VolumeV4Request;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.base.parameter.template.YarnInstanceTemplateParametersV4;
import com.sequenceiq.cloudbreak.api.model.JsonEntity;
import com.sequenceiq.cloudbreak.doc.ModelDescriptions.TemplateModelDescription;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class InstanceTemplateV4Request extends ProviderParametersBase implements JsonEntity {

    private VolumeV4Request rootVolume;

    private VolumeV4Request ephemeralVolume;

    private Set<VolumeV4Request> attachedVolumes;

    @ApiModelProperty(TemplateModelDescription.AWS_PARAMETERS)
    private AwsInstanceTemplateParametersV4 aws;

    @ApiModelProperty(TemplateModelDescription.AZURE_PARAMETERS)
    private AzureInstanceTemplateParametersV4 azure;

    @ApiModelProperty(TemplateModelDescription.GCP_PARAMETERS)
    private GcpInstanceTemplateParametersV4 gcp;

    @ApiModelProperty(TemplateModelDescription.OPENSTACK_PARAMETERS)
    private OpenStackInstanceTemplateParametersV4 openStack;

    @ApiModelProperty(TemplateModelDescription.YARN_PARAMETERS)
    private YarnInstanceTemplateParametersV4 yarn;

    @ApiModelProperty(TemplateModelDescription.INSTANCE_TYPE)
    private String instanceType;

    @ApiModelProperty(TemplateModelDescription.CUSTOM_INSTANCE_TYPE)
    private CustomInstanceV4Request customInstance;

    public String getInstanceType() {
        return instanceType;
    }

    public void setInstanceType(String instanceType) {
        this.instanceType = instanceType;
    }

    public AwsInstanceTemplateParametersV4 getAws() {
        return aws;
    }

    public void setAws(AwsInstanceTemplateParametersV4 aws) {
        this.aws = aws;
    }

    public AzureInstanceTemplateParametersV4 getAzure() {
        return azure;
    }

    public void setAzure(AzureInstanceTemplateParametersV4 azure) {
        this.azure = azure;
    }

    public GcpInstanceTemplateParametersV4 getGcp() {
        return gcp;
    }

    public void setGcp(GcpInstanceTemplateParametersV4 gcp) {
        this.gcp = gcp;
    }

    public OpenStackInstanceTemplateParametersV4 getOpenStack() {
        return openStack;
    }

    public void setOpenStack(OpenStackInstanceTemplateParametersV4 openStack) {
        this.openStack = openStack;
    }

    public YarnInstanceTemplateParametersV4 getYarn() {
        return yarn;
    }

    public void setYarn(YarnInstanceTemplateParametersV4 yarn) {
        this.yarn = yarn;
    }

    public CustomInstanceV4Request getCustomInstance() {
        return customInstance;
    }

    public void setCustomInstance(CustomInstanceV4Request customInstance) {
        this.customInstance = customInstance;
    }

    public VolumeV4Request getRootVolume() {
        return rootVolume;
    }

    public void setRootVolume(VolumeV4Request rootVolume) {
        this.rootVolume = rootVolume;
    }

    public VolumeV4Request getEphemeralVolume() {
        return ephemeralVolume;
    }

    public void setEphemeralVolume(VolumeV4Request ephemeralVolume) {
        this.ephemeralVolume = ephemeralVolume;
    }

    public Set<VolumeV4Request> getAttachedVolumes() {
        return attachedVolumes;
    }

    public void setAttachedVolumes(Set<VolumeV4Request> attachedVolumes) {
        this.attachedVolumes = attachedVolumes;
    }

    @Override
    public Mappable aws() {
        return aws;
    }

    @Override
    public Mappable gcp() {
        return gcp;
    }

    @Override
    public Mappable azure() {
        return azure;
    }

    @Override
    public Mappable openstack() {
        return openStack;
    }

    @Override
    public Mappable yarn() {
        return yarn;
    }

    @Override
    public Mappable mock() {
        return Mappable.EMPTY;
    }
}
