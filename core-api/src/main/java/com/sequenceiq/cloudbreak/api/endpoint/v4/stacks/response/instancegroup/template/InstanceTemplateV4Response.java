package com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.response.instancegroup.template;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sequenceiq.cloudbreak.api.endpoint.v4.common.mappable.Mappable;
import com.sequenceiq.cloudbreak.api.endpoint.v4.common.mappable.ProviderParametersBase;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.response.instancegroup.template.aws.AwsInstanceTemplateParametersV4Response;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.response.instancegroup.template.azure.AzureInstanceTemplateParametersV4Response;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.response.instancegroup.template.gcp.GcpInstanceTemplateParametersV4Response;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.response.instancegroup.template.openstack.OpenStackInstanceTemplateParametersV4Response;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.response.instancegroup.template.volume.VolumeV4Response;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.response.instancegroup.template.yarn.YarnInstanceTemplateParametersV4Response;
import com.sequenceiq.cloudbreak.api.model.JsonEntity;
import com.sequenceiq.cloudbreak.doc.ModelDescriptions;
import com.sequenceiq.cloudbreak.doc.ModelDescriptions.TemplateModelDescription;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class InstanceTemplateV4Response extends ProviderParametersBase implements JsonEntity {

    @ApiModelProperty(ModelDescriptions.ID)
    private Long id;

    private VolumeV4Response rootVolume;

    private VolumeV4Response ephemeralVolume;

    private Set<VolumeV4Response> attachedVolumes;

    @ApiModelProperty(TemplateModelDescription.AWS_PARAMETERS)
    private AwsInstanceTemplateParametersV4Response aws;

    @ApiModelProperty(TemplateModelDescription.AZURE_PARAMETERS)
    private AzureInstanceTemplateParametersV4Response azure;

    @ApiModelProperty(TemplateModelDescription.GCP_PARAMETERS)
    private GcpInstanceTemplateParametersV4Response gcp;

    @ApiModelProperty(TemplateModelDescription.OPENSTACK_PARAMETERS)
    private OpenStackInstanceTemplateParametersV4Response openstack;

    @ApiModelProperty(TemplateModelDescription.YARN_PARAMETERS)
    private YarnInstanceTemplateParametersV4Response yarn;

    @ApiModelProperty(TemplateModelDescription.INSTANCE_TYPE)
    private String instanceType;

    @ApiModelProperty(TemplateModelDescription.CUSTOM_INSTANCE_TYPE)
    private CustomInstanceV4Response customInstance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VolumeV4Response getRootVolume() {
        return rootVolume;
    }

    public void setRootVolume(VolumeV4Response rootVolume) {
        this.rootVolume = rootVolume;
    }

    public VolumeV4Response getEphemeralVolume() {
        return ephemeralVolume;
    }

    public void setEphemeralVolume(VolumeV4Response ephemeralVolume) {
        this.ephemeralVolume = ephemeralVolume;
    }

    public Set<VolumeV4Response> getAttachedVolumes() {
        return attachedVolumes;
    }

    public void setAttachedVolumes(Set<VolumeV4Response> attachedVolumes) {
        this.attachedVolumes = attachedVolumes;
    }

    public AwsInstanceTemplateParametersV4Response getAws() {
        return aws;
    }

    public void setAws(AwsInstanceTemplateParametersV4Response aws) {
        this.aws = aws;
    }

    public AzureInstanceTemplateParametersV4Response getAzure() {
        return azure;
    }

    public void setAzure(AzureInstanceTemplateParametersV4Response azure) {
        this.azure = azure;
    }

    public GcpInstanceTemplateParametersV4Response getGcp() {
        return gcp;
    }

    public void setGcp(GcpInstanceTemplateParametersV4Response gcp) {
        this.gcp = gcp;
    }

    public OpenStackInstanceTemplateParametersV4Response getOpenstack() {
        return openstack;
    }

    public void setOpenstack(OpenStackInstanceTemplateParametersV4Response openstack) {
        this.openstack = openstack;
    }

    public YarnInstanceTemplateParametersV4Response getYarn() {
        return yarn;
    }

    public void setYarn(YarnInstanceTemplateParametersV4Response yarn) {
        this.yarn = yarn;
    }

    public String getInstanceType() {
        return instanceType;
    }

    public void setInstanceType(String instanceType) {
        this.instanceType = instanceType;
    }

    public CustomInstanceV4Response getCustomInstance() {
        return customInstance;
    }

    public void setCustomInstance(CustomInstanceV4Response customInstance) {
        this.customInstance = customInstance;
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
        return openstack;
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
