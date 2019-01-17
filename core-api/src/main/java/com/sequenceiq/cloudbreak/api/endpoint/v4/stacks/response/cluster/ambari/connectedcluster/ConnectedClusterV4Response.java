package com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.response.cluster.ambari.connectedcluster;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sequenceiq.cloudbreak.api.model.JsonEntity;
import com.sequenceiq.cloudbreak.doc.ModelDescriptions.ClusterModelDescription;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ConnectedClusterV4Response implements JsonEntity {

    @ApiModelProperty(ClusterModelDescription.SOURCE_CLUSTER_ID)
    private Long sourceClusterId;

    @ApiModelProperty(ClusterModelDescription.SOURCE_CLUSTER_NAME)
    private String sourceClusterName;

    public Long getSourceClusterId() {
        return sourceClusterId;
    }

    public void setSourceClusterId(Long sourceClusterId) {
        this.sourceClusterId = sourceClusterId;
    }

    public String getSourceClusterName() {
        return sourceClusterName;
    }

    public void setSourceClusterName(String sourceClusterName) {
        this.sourceClusterName = sourceClusterName;
    }
}
