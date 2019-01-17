package com.sequenceiq.cloudbreak.api.endpoint.v4.common.mappable;

public abstract class ProviderParametersBase {

    private CloudPlatform cloudPlatform;

    public CloudPlatform getCloudPlatform() {
        return cloudPlatform;
    }

    public void setCloudPlatform(CloudPlatform cloudPlatform) {
        this.cloudPlatform = cloudPlatform;
    }

    public abstract Mappable aws();

    public abstract Mappable gcp();

    public abstract Mappable azure();

    public abstract Mappable openstack();

    public abstract Mappable yarn();

    public abstract Mappable mock();
}
