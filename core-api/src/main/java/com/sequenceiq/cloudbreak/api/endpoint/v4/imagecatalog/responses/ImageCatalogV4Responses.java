package com.sequenceiq.cloudbreak.api.endpoint.v4.imagecatalog.responses;

import java.util.Set;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sequenceiq.cloudbreak.api.endpoint.v4.common.responses.GeneralSetV4Response;

import io.swagger.annotations.ApiModel;

@ApiModel
@JsonInclude(Include.NON_NULL)
@NotNull
public class ImageCatalogV4Responses extends GeneralSetV4Response<ImageCatalogV4Response> {
    public ImageCatalogV4Responses(Set<ImageCatalogV4Response> responses) {
        super(responses);
    }
}