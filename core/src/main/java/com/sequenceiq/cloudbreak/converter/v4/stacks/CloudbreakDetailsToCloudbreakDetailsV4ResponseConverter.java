package com.sequenceiq.cloudbreak.converter.v4.stacks;

import org.springframework.stereotype.Component;

import com.sequenceiq.cloudbreak.api.model.CloudbreakDetailsJson;
import com.sequenceiq.cloudbreak.cloud.model.CloudbreakDetails;
import com.sequenceiq.cloudbreak.converter.AbstractConversionServiceAwareConverter;

@Component
public class CloudbreakDetailsToCloudbreakDetailsV4ResponseConverter extends AbstractConversionServiceAwareConverter<CloudbreakDetails, CloudbreakDetailsJson>  {

    @Override
    public CloudbreakDetailsJson convert(CloudbreakDetails source) {
        CloudbreakDetailsJson json = new CloudbreakDetailsJson();
        json.setVersion(source.getVersion());
        return json;
    }
}
