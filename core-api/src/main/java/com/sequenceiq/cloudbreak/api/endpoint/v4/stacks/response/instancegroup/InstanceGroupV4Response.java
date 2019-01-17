package com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.response.instancegroup;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sequenceiq.cloudbreak.api.endpoint.v4.common.mappable.Mappable;
import com.sequenceiq.cloudbreak.api.endpoint.v4.common.mappable.ProviderParametersBase;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.response.instancegroup.aws.AwsInstanceGroupParametersV4Response;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.response.instancegroup.azure.AzureInstanceGroupParametersV4Response;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.response.instancegroup.gcp.GcpInstanceGroupParametersV4Response;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.response.instancegroup.instancemetadata.InstanceMetaDataV4Response;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.response.instancegroup.openstack.OpenStackInstanceGroupParametersV4Response;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.response.instancegroup.template.InstanceTemplateV4Response;
import com.sequenceiq.cloudbreak.api.model.JsonEntity;
import com.sequenceiq.cloudbreak.api.model.SecurityGroupResponse;
import com.sequenceiq.cloudbreak.doc.ModelDescriptions;
import com.sequenceiq.cloudbreak.doc.ModelDescriptions.InstanceGroupModelDescription;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class InstanceGroupV4Response extends ProviderParametersBase implements JsonEntity {

    @ApiModelProperty(InstanceGroupModelDescription.AZURE_PARAMETERS)
    private AzureInstanceGroupParametersV4Response azure;

    @ApiModelProperty(InstanceGroupModelDescription.GCP_PARAMETERS)
    private GcpInstanceGroupParametersV4Response gcp;

    @ApiModelProperty(InstanceGroupModelDescription.AWS_PARAMETERS)
    private AwsInstanceGroupParametersV4Response aws;

    @ApiModelProperty(InstanceGroupModelDescription.OPENSTACK_PARAMETERS)
    private OpenStackInstanceGroupParametersV4Response openstack;

    @ApiModelProperty(ModelDescriptions.ID)
    private Long id;

    @ApiModelProperty(InstanceGroupModelDescription.METADATA)
    private Set<InstanceMetaDataV4Response> metadata = new HashSet<>();

    @ApiModelProperty(InstanceGroupModelDescription.TEMPLATE)
    private InstanceTemplateV4Response template;

    @ApiModelProperty(InstanceGroupModelDescription.SECURITYGROUP)
    private SecurityGroupResponse securityGroup;

    public AzureInstanceGroupParametersV4Response getAzure() {
        return azure;
    }

    public void setAzure(AzureInstanceGroupParametersV4Response azure) {
        this.azure = azure;
    }

    public GcpInstanceGroupParametersV4Response getGcp() {
        return gcp;
    }

    public void setGcp(GcpInstanceGroupParametersV4Response gcp) {
        this.gcp = gcp;
    }

    public AwsInstanceGroupParametersV4Response getAws() {
        return aws;
    }

    public void setAws(AwsInstanceGroupParametersV4Response aws) {
        this.aws = aws;
    }

    public OpenStackInstanceGroupParametersV4Response getOpenstack() {
        return openstack;
    }

    public void setOpenstack(OpenStackInstanceGroupParametersV4Response openstack) {
        this.openstack = openstack;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<InstanceMetaDataV4Response> getMetadata() {
        return metadata;
    }

    public void setMetadata(Set<InstanceMetaDataV4Response> metadata) {
        this.metadata = metadata;
    }

    public InstanceTemplateV4Response getTemplate() {
        return template;
    }

    public void setTemplate(InstanceTemplateV4Response template) {
        this.template = template;
    }

    public SecurityGroupResponse getSecurityGroup() {
        return securityGroup;
    }

    public void setSecurityGroup(SecurityGroupResponse securityGroup) {
        this.securityGroup = securityGroup;
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
        return Mappable.EMPTY;
    }

    @Override
    public Mappable mock() {
        return Mappable.EMPTY;
    }
}
