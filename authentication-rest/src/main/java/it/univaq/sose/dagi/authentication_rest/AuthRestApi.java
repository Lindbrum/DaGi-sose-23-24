package it.univaq.sose.dagi.authentication_rest;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.univaq.sose.dagi.authentication_rest.model.Credentials;
import it.univaq.sose.dagi.authentication_rest.model.Customer;
import it.univaq.sose.dagi.authentication_rest.model.Organizer;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

//This interface is annotated with the following path. This means that it specifies
//the default path for all the APIs for the authentication.
@Path("/auth")
public interface AuthRestApi {

	// ====================
	//This method handles a customer's registration. It uses @POST to indicate that it is a POST endpoint and @Path("/customer/signup") to specify the path.
	//The JSON response contains the created customer ID.
	@Operation(description = "Sign up a customer", responses = {
			@ApiResponse(description = "A response entity object containing the id of the created customer.", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Long.class)) }) })
	@POST
	@Path("/customer/signup")
	@Produces({ MediaType.APPLICATION_JSON })
	public Long signUpCustomer(
			@RequestBody(description = "Data of the Customer that is signing up", required = true, content = {
					@Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Customer.class)), }) Customer customer);

	// ====================
	//This is similar to the previous method.
	//The JSON response contains the created organized ID.
	@Operation(description = "Sign up a organizer", responses = {
			@ApiResponse(description = "A response entity object containing the id of the created organizer.", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Long.class)) }) })
	@POST
	@Path("/organizer/signup")
	@Produces({ MediaType.APPLICATION_JSON })
	public Long signUpOrganizer(
			@RequestBody(description = "Data of the Organizer that is signing up", required = true, content = {
					@Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Organizer.class)), }) Organizer organizer);

	// ====================
	//This method handles a customer's login. It uses @POST and @Path("/customer/signin") for the path. 
	//The JSON response contains the ID of the logged in customer.
	@Operation(description = "Sign in a customer", responses = {
			@ApiResponse(description = "A response entity object containing the id of the customer who signed in.", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Long.class))) })
	@POST
	@Path("/customer/signin")
	@Produces({ MediaType.APPLICATION_JSON })
	public Long signInCustomer(
			@RequestBody(description = "Data of the Customer that is signing in", required = true, content = {
					@Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Credentials.class)), }) Credentials credentials);

	// ====================
	//This is similar to the previous method.
	//The JSON response contains the ID of the logged in organizer.
	@Operation(description = "Sign in an organizer", responses = {
			@ApiResponse(description = "A response entity object containing the id of the organizer who signed in.", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Long.class))) })
	@POST
	@Path("/organizer/signin")
	@Produces({ MediaType.APPLICATION_JSON })
	public Long signInOrganizer(
			@RequestBody(description = "Data of the Organizer that is signing in", required = true, content = {
					@Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Credentials.class)), }) Credentials credentials);

	// ====================
	//This method retrieves the information of a specific customer. It uses @GET to indicate that it is a GET endpoint
	//and @Path("/customer/info/{userId}") to specify the path with a userId parameter.
	@Operation(description = "Fetch the info of a customer", responses = {
			@ApiResponse(description = "A response entity object containing the info of the customer (sensitive data is hidden).", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Customer.class))) })
	@GET
	@Path("/customer/info/{userId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Customer fetchCustomerInfo(
			@Parameter(description = "The id of the user we want to fetch.") @PathParam(value = "userId") long userId);

	// ====================
	//This method retrieves information from a customer list. It uses @GET and @Path("/customer/infos/{userIds}") to specify the path
	//with a userIds parameter that contains a comma-separated list of IDs.
	@Operation(description = "Fetch the info of a list of customer", responses = {
			@ApiResponse(description = "A response entity object containing the info of the customer (sensitive data is hidden).", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Customer.class))) })
	@GET
	@Path("/customer/infos/{userIds}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Customer> fetchCustomerInfoList(
			@Parameter(description = "The list of ids of users we want to fetch.") @PathParam(value = "userIds") String userIds);
	// ====================
	
	//API ANNOTATIONS
	
	// - @Operation: Describes the endpoint operation and possible responses.
	// - @ApiResponse: Describes the API response.
	// - @Content: Specifies the media type and response schema.
	// - @RequestBody: Describes the body of the request.
	// - @Parameter: Describes an endpoint parameter.
	// - @Path: Specifies the path to the endpoint.
	// - @POST and @GET: Indicate the type of HTTP request.
	// - @Produces: Specifies the types of media the endpoint can produce.
	
	// ====================
}
