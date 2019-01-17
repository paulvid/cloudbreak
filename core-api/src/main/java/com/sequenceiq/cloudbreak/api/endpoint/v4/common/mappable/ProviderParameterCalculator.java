package com.sequenceiq.cloudbreak.api.endpoint.v4.common.mappable;

import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class ProviderParameterCalculator {

    public Mappable get(ProviderParametersBase source) {
        if (source.getCloudPlatform() == null) {
            return Mappable.EMPTY;
        }
        switch (source.getCloudPlatform()) {
            case AWS:
                return source.aws();
            case GCP:
                return source.gcp();
            case MOCK:
                return source.mock();
            case YARN:
                return source.yarn();
            case AZURE:
                return source.azure();
            case OPENSTACK:
                return source.openstack();
            case CUMULUS_YARN:
                return source.yarn();
        }
        return Mappable.EMPTY;
    }

    public void to(Map<String, Object> parameters, ProviderParametersBase base) {
        CloudPlatform cloudPlatform = CloudPlatform.valueOf(parameters.get("cloudPlatform").toString());
        cloudPlatform.to(parameters, base);
    }
}
