package io.swagger.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import io.swagger.model.authentication_provider.Credentials;
import io.swagger.model.authentication_provider.Customer;
import io.swagger.model.authentication_provider.Organizer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * Authentication API
 *
 * <p>This API is about the authentication of customers and organizers
 *
 */
@Path("/")
public interface AuthenticationApi  {

    @GET
    @Path("/auth/customer/info/{userId}")
    @Produces({ "application/json" })
    @Operation(summary = "", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "A response entity object containing the info of the customer (sensitive data is hidden).", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Customer.class))) })
    public Customer fetchCustomerInfo(@PathParam("userId") Long userId);

    @GET
    @Path("/auth/customer/infos/{userIds}")
    @Produces({ "application/json" })
    @Operation(summary = "", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "A response entity object containing the info of the customer (sensitive data is hidden).", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Customer.class))) })
    public Customer fetchCustomerInfoList(@PathParam("userIds") String userIds);

    @POST
    @Path("/auth/customer/signin")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @Operation(summary = "", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "A response entity object containing the id of the customer who signed in.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Long.class))) })
    public Long signInCustomer(Credentials body);

    @POST
    @Path("/auth/organizer/signin")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @Operation(summary = "", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "A response entity object containing the id of the organizer who signed in.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Long.class))) })
    public Long signInOrganizer(Credentials body);

    @POST
    @Path("/auth/customer/signup")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @Operation(summary = "", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "A response entity object containing the id of the created customer.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Long.class))) })
    public Long signUpCustomer(Customer body);

    @POST
    @Path("/auth/organizer/signup")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @Operation(summary = "", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "A response entity object containing the id of the created organizer.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Long.class))) })
    public Long signUpOrganizer(Organizer body);
}
