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

@Path("/auth")
public interface AuthRestApi {

	// ====================
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
	@Operation(description = "Sign in a customer", responses = {
			@ApiResponse(description = "A response entity object containing the id of the customer who signed in.", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Long.class))) })
	@POST
	@Path("/customer/signin")
	@Produces({ MediaType.APPLICATION_JSON })
	public Long signInCustomer(
			@RequestBody(description = "Data of the Customer that is signing in", required = true, content = {
					@Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Credentials.class)), }) Credentials credentials);

	// ====================
	@Operation(description = "Sign in an organizer", responses = {
			@ApiResponse(description = "A response entity object containing the id of the organizer who signed in.", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Long.class))) })
	@POST
	@Path("/organizer/signin")
	@Produces({ MediaType.APPLICATION_JSON })
	public Long signInOrganizer(
			@RequestBody(description = "Data of the Organizer that is signing in", required = true, content = {
					@Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Credentials.class)), }) Credentials credentials);

	// ====================
	@Operation(description = "Fetch the info of a customer", responses = {
			@ApiResponse(description = "A response entity object containing the info of the customer (sensitive data is hidden).", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Customer.class))) })
	@GET
	@Path("/customer/info/{userId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Customer fetchCustomerInfo(
			@Parameter(description = "The id of the user we want to fetch.") @PathParam(value = "userId") long userId);

	// ====================
	@Operation(description = "Fetch the info of a customer", responses = {
			@ApiResponse(description = "A response entity object containing the info of the customer (sensitive data is hidden).", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Customer.class))) })
	@GET
	@Path("/customer/infos/{userIds}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Customer> fetchCustomerInfoList(
			@Parameter(description = "The list of ids of users we want to fetch.") @PathParam(value = "userIds") String userIds);
	// ====================
//	@Operation(description = "The description of the create operation goes here!", responses = {
//			@ApiResponse(description = "The description of the Response goes here!", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Boolean.class))) })
//	@POST
//	public boolean create(
//			@RequestBody(description = "The description of the input parameter goes here!", required = true, content = {
//					@Content(mediaType = MediaType.APPLICATION_XML, schema = @Schema(implementation = Todo.class)),
//					@Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Todo.class)), }) Todo todo);
//
//	@PUT
//	@Consumes(MediaType.APPLICATION_JSON)
//	public boolean update(Todo todo);
//
//	@DELETE
//	@Path("{id}")
//	public boolean delete(@PathParam("id") String id);

}
