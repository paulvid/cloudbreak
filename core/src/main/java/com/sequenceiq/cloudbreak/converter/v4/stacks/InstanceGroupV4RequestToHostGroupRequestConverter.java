package com.sequenceiq.cloudbreak.converter.v4.stacks;

import java.util.Collections;

import org.springframework.stereotype.Component;

import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.instancegroup.InstanceGroupV4Request;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.HostGroupConstraintV4Request;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.HostGroupV4Request;
import com.sequenceiq.cloudbreak.converter.AbstractConversionServiceAwareConverter;

@Component
public class InstanceGroupV4RequestToHostGroupRequestConverter extends AbstractConversionServiceAwareConverter<InstanceGroupV4Request, HostGroupV4Request> {

    @Override
    public HostGroupV4Request convert(InstanceGroupV4Request source) {
        HostGroupV4Request hostGroupRequest = new HostGroupV4Request();
        hostGroupRequest.setName(source.getName());
        hostGroupRequest.setRecipeNames(source.getRecipeNames());
        hostGroupRequest.setRecipes(Collections.emptySet());
        HostGroupConstraintV4Request constraintJson = new HostGroupConstraintV4Request();
        constraintJson.setHostCount(source.getCount());
        constraintJson.setInstanceGroupName(source.getName());
        hostGroupRequest.setConstraint(constraintJson);
        hostGroupRequest.setRecipeIds(Collections.emptySet());
        hostGroupRequest.setRecoveryMode(source.getRecoveryMode());
        return hostGroupRequest;
    }
}
