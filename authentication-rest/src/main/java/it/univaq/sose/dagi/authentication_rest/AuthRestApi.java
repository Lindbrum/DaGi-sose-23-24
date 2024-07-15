package it.univaq.sose.dagi.authentication_rest;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.univaq.sose.dagi.authentication_rest.model.Credentials;
import it.univaq.sose.dagi.authentication_rest.model.Customer;
import it.univaq.sose.dagi.authentication_rest.model.Organizer;

@Path("/auth")
public interface AuthRestApi {

	// ====================
	@Operation(description = "Sign up a customer", responses = {
			@ApiResponse(description = "The response for a sign up of a customer", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Integer.class)) }) })
	@POST
	@Path("/customer/signup")
	// @Produces({MediaType.TEXT_XML, MediaType.APPLICATION_XML,
	// MediaType.APPLICATION_JSON})
	public Long signUpCustomer(
			@RequestBody(description = "Data of the Customer that is signing up", required = true, content = {
					@Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Customer.class)), }) Customer customer);

	//====================
	@Operation(description = "Sign up a organizer", responses = {
			@ApiResponse(description = "The response for a sign up of a organizer", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Integer.class)) }) })
	@POST
	@Path("/organizer/signup")
	// @Produces({MediaType.TEXT_XML, MediaType.APPLICATION_XML,
	// MediaType.APPLICATION_JSON})
	public Long signUpOrganizer(
			@RequestBody(description = "Data of the Organizer that is signing up", required = true, content = {
					@Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Organizer.class)), }) Organizer organizer);

	//====================
	@Operation(description = "This operation is about the sign in of a customer", responses = {
			@ApiResponse(description = "The response for a sign in of a customer", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Organizer.class))) })
	@POST
	@Path("/customer/signin")
	public Long signInCustomer(@RequestBody(description = "Data of the Customer that is signing in", required = true, content = {
			@Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Credentials.class)), }) Credentials credentials);
	
	//====================
	@POST
	@Path("/organizer/signin")
	public Long signInOrganizer(@RequestBody(description = "Data of the Organizer that is signing in", required = true, content = {
			@Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Credentials.class)), }) Credentials credentials);

	//====================
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
