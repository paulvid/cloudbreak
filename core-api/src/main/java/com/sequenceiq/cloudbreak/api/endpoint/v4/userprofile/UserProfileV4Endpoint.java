package com.sequenceiq.cloudbreak.api.endpoint.v4.userprofile;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sequenceiq.cloudbreak.api.endpoint.v4.userprofile.requests.UserProfileV4Request;
import com.sequenceiq.cloudbreak.api.endpoint.v4.userprofile.responses.UserEvictV4Response;
import com.sequenceiq.cloudbreak.api.endpoint.v4.userprofile.responses.UserProfileV4Response;
import com.sequenceiq.cloudbreak.doc.ContentType;
import com.sequenceiq.cloudbreak.doc.ControllerDescription;
import com.sequenceiq.cloudbreak.doc.Notes;
import com.sequenceiq.cloudbreak.doc.OperationDescriptions;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Path("/v4/{workspaceId}/user_profiles")
@Consumes(MediaType.APPLICATION_JSON)
@Api(value = "/v4/{workspaceId}/user_profiles", description = ControllerDescription.USER_PROFILES_V4_DESCRIPTION, protocols = "http,https")
public interface UserProfileV4Endpoint {

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = OperationDescriptions.UserProfileOpDescription.GET_USER_PROFILE_IN_WORKSPACE, produces = ContentType.JSON,
            notes = Notes.USER_PROFILE_NOTES, nickname = "getUserProfileInWorkspace")
    UserProfileV4Response get(@PathParam("workspaceId") Long workspaceId);

    @PUT
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = OperationDescriptions.UserProfileOpDescription.MODIFT_USER_PROFILE_IN_WORKSPACE, produces = ContentType.JSON,
            notes = Notes.USER_PROFILE_NOTES, nickname = "modifyUserProfileInWorkspace")
    UserProfileV4Response modify(@PathParam("workspaceId") Long workspaceId, UserProfileV4Request userProfileV4Request);

    @DELETE
    @Path("evict")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = OperationDescriptions.UserProfileOpDescription.CURRENT_USER_DETAILS_EVICT, produces = ContentType.JSON,
            notes = Notes.USER_PROFILE_NOTES, nickname = "evictCurrentUserDetails")
    UserEvictV4Response evictCurrentUserDetails(@PathParam("workspaceId") Long workspaceId);
}