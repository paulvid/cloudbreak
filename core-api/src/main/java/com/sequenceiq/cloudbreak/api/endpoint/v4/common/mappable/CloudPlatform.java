package com.sequenceiq.cloudbreak.api.endpoint.v4.common.mappable;

import java.util.Map;

public enum CloudPlatform {
    AWS, GCP, AZURE, OPENSTACK, CUMULUS_YARN, YARN, MOCK;

    public <T> T to(Map<String, Object> parameters, ProviderParametersBase base) {
        switch (this) {
            case AWS:
                return base.aws().toClass(parameters);
            case GCP:
                return base.gcp().toClass(parameters);
            case CUMULUS_YARN:
                return base.yarn().toClass(parameters);
            case OPENSTACK:
                return base.openstack().toClass(parameters);
            case AZURE:
                return base.azure().toClass(parameters);
            case YARN:
                return base.yarn().toClass(parameters);
            case MOCK:
                return base.mock().toClass(parameters);
        }
        return null;
    }
}
