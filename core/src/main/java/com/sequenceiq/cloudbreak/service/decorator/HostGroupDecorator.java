package com.sequenceiq.cloudbreak.service.decorator;

import static org.springframework.util.StringUtils.isEmpty;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.HostGroupV4Request;
import com.sequenceiq.cloudbreak.controller.exception.BadRequestException;
import com.sequenceiq.cloudbreak.domain.Recipe;
import com.sequenceiq.cloudbreak.domain.stack.Stack;
import com.sequenceiq.cloudbreak.domain.stack.cluster.Cluster;
import com.sequenceiq.cloudbreak.domain.stack.cluster.host.HostGroup;
import com.sequenceiq.cloudbreak.domain.workspace.Workspace;
import com.sequenceiq.cloudbreak.repository.ConstraintRepository;
import com.sequenceiq.cloudbreak.repository.InstanceGroupRepository;
import com.sequenceiq.cloudbreak.service.cluster.ClusterService;
import com.sequenceiq.cloudbreak.service.constraint.ConstraintTemplateService;
import com.sequenceiq.cloudbreak.service.hostgroup.HostGroupService;
import com.sequenceiq.cloudbreak.service.recipe.RecipeService;

@Component
public class HostGroupDecorator {
    private static final Logger LOGGER = LoggerFactory.getLogger(HostGroupDecorator.class);

    @Inject
    private InstanceGroupRepository instanceGroupRepository;

    @Inject
    private ConstraintTemplateService constraintTemplateService;

    @Inject
    private ConstraintRepository constraintRepository;

    @Inject
    private HostGroupService hostGroupService;

    @Inject
    private RecipeService recipeService;

    @Inject
    private ConversionService conversionService;

    @Inject
    private ClusterService clusterService;

    public HostGroup decorate(HostGroup subject, HostGroupV4Request hostGroupRequest, Stack stack, boolean postRequest) {
        Set<String> recipeNames = hostGroupRequest.getRecipeNames();
        LOGGER.debug("Decorating hostgroup on [{}] request.", postRequest ? "POST" : "PUT");
        HostGroup result = getHostGroup(stack, hostGroupRequest, subject);
        subject.getRecipes().clear();
        if (recipeNames != null && !recipeNames.isEmpty()) {
            prepareRecipesByName(subject, stack.getWorkspace(), recipeNames);
        }
        return subject;
    }

    private void prepareRecipesByName(HostGroup subject, Workspace workspace, Collection<String> recipeNames) {
        Set<Recipe> recipes = recipeService.getRecipesByNamesForWorkspace(workspace, recipeNames);
        subject.getRecipes().addAll(recipes);
    }

    private HostGroup getHostGroup(Stack stack, HostGroupV4Request hostGroupV4Request, HostGroup subject) {
        String instanceGroupName = hostGroupV4Request.getInstanceGroupName();
        Cluster cluster = clusterService.retrieveClusterByStackIdWithoutAuth(stack.getId());
        if (!isEmpty(instanceGroupName)) {
            return getHostGroupByInstanceGroupName(subject, cluster, hostGroupV4Request);
        } else {
            throw new BadRequestException("The hostgroup field must contain the 'instanceGroupName' parameter!");
        }
    }

    private HostGroup getHostGroupByInstanceGroupName(HostGroup subject, Cluster cluster, HostGroupV4Request hostGroupV4Request) {
        Set<HostGroup> hostGroups = hostGroupService.getByCluster(cluster.getId());
        if (hostGroups.isEmpty() && cluster.getStack() == null) {
            String msg = String.format("There is no stack associated to cluster (id:'%s', name: '%s')!", cluster.getId(), cluster.getName());
            throw new BadRequestException(msg);
        } else {
            return getDetailsFromExistingHostGroup(subject, hostGroupV4Request, hostGroups);
        }
    }

}
