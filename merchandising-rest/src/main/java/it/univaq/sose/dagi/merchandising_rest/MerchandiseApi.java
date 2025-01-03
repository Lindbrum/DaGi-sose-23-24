package it.univaq.sose.dagi.merchandising_rest;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.univaq.sose.dagi.merchandising_rest.model.Merchandise;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/merch")
public interface MerchandiseApi {
	
	//This method returns the entire list of merchandise available in the database. A successful response (HTTP 200)
	//contains the list of all merchandise items in JSON format. The response code 400 indicates invalid request parameters were provided.
	//The response code 500 indicates an unexpected error occurred on the server.
	@Operation(summary = "Return the entire list of merchandise.")
	@ApiResponses(value = { 
		  @ApiResponse(
				  responseCode = "200", 
				  description = "Return all the content of the database.", 
				  content = { 
						  @Content(
								  mediaType = "application/json", 
								  array = @ArraySchema(
										  schema = @Schema(implementation = Merchandise.class)
										  )
								  )
				  }),
//		  @ApiResponse(
//				  responseCode = "400",
//				  description = "Invalid addendum(s) provided", 
//				  content = {
//						  @Content(
//								  mediaType = "application/json"
//								  )
//				  }), 
//		  @ApiResponse(
//				  responseCode = "500",
//				  description = "Message template not found", 
//				  content = {
//						  @Content(
//								  mediaType = "application/json"
//								  )
//				  }) 
	})
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Merchandise> getAll();
	
	//This method returns a page of merchandise available in the database. A successful response (HTTP 200)
	//contains a list of merchandise items in JSON format located at a certain page and sorted by either creating date or alphabetically. The response code 400 indicates invalid request parameters were provided.
	//The response code 500 indicates an unexpected error occurred on the server.
	@Operation(summary = "Return a page of merchandise recorded on the database, sorted using one of 4 methods (ID_ASC, ID_DESC, ALPHABETICAL_ASC, ALPHABETICAL_DESC).")
	@ApiResponses(value = { 
		  @ApiResponse(
				  responseCode = "200", 
				  description = "Return the selected page of merchandise, using the provided sorting method.", 
						  content = { 
								  @Content(
										  mediaType = "application/json", 
										  array = @ArraySchema(
												  schema = @Schema(implementation = Merchandise.class)
												  )
										  )
						  }),
		  @ApiResponse(
				  responseCode = "400",
				  description = "Invalid parameters provided.", 
				  content = {
						  @Content(
								  mediaType = "application/json"
								  )
				  }), 
		  @ApiResponse(
				  responseCode = "500",
				  description = "Unexpected error occured on server.", 
				  content = {
						  @Content(
								  mediaType = "application/json"
								  )
				  }) 
	})
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Merchandise> getMerchandisePage(@QueryParam(value = "page") int page,
			@QueryParam(value = "sortby") String sortBy);
	
	//This method retrieves the list of merchandise associated with a specific event. It requires an event ID as a path parameter. The responses code are the same of the previous method.
	@Operation(summary = "Return the list of merchandise for an event.")
	@ApiResponses(value = { 
		  @ApiResponse(
				  responseCode = "200", 
				  description = "Return the merchandise of the event.", 
						  content = { 
								  @Content(
										  mediaType = "application/json", 
										  array = @ArraySchema(
												  schema = @Schema(implementation = Merchandise.class)
												  )
										  )
						  }),
		  @ApiResponse(
				  responseCode = "400",
				  description = "Invalid parameters provided.", 
				  content = {
						  @Content(
								  mediaType = "application/json"
								  )
				  }), 
		  @ApiResponse(
				  responseCode = "500",
				  description = "Unexpected error occured on server.", 
				  content = {
						  @Content(
								  mediaType = "application/json"
								  )
				  }) 
	})
	@GET
	@Path("/event/{eventId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Merchandise> getEventMerchandise(@PathParam("eventId") Long eventId);
	
	//This method adds a new merchandise item to the database. It requires a Merchandise object in the request body. The responses code are the same of the previous method.
	@Operation(summary = "Add a new merchandise article to the database.")
	@ApiResponses(value = { 
		  @ApiResponse(
				  responseCode = "200", 
				  description = "Return the merchandise object with the newly generated id.", 
				  content = { 
						  @Content(
								  mediaType = "application/json", 
								  schema = @Schema(implementation = Merchandise.class)
								  ) 
				  }),
		  @ApiResponse(
				  responseCode = "400",
				  description = "Invalid parameters provided.", 
				  content = {
						  @Content(
								  mediaType = "application/json"
								  )
				  }), 
		  @ApiResponse(
				  responseCode = "500",
				  description = "Unexpected error occured on server.", 
				  content = {
						  @Content(
								  mediaType = "application/json"
								  )
				  }) 
	})
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Merchandise create(@RequestBody Merchandise newMerch);
	
	//This method registers a merchandise item to an event. It requires both the event ID and merchandise ID as path parameters. On success (HTTP 200), it returns the updated merchandise item with the new event ID.
	@Operation(summary = "Register this merchandise to an event in database.")
	@ApiResponses(value = { 
		  @ApiResponse(
				  responseCode = "200", 
				  description = "Return the merchandise object with the new event id.", 
				  content = { 
						  @Content(
								  mediaType = "application/json", 
								  schema = @Schema(implementation = Merchandise.class)
								  ) 
				  }),
		  @ApiResponse(
				  responseCode = "400",
				  description = "Either ID was invalid or null/missing.", 
				  content = {
						  @Content(
								  mediaType = "application/json"
								  )
				  }), 
		  @ApiResponse(
				  responseCode = "500",
				  description = "Unexpected error occured on server.", 
				  content = {
						  @Content(
								  mediaType = "application/json"
								  )
				  }) 
	})
	@PUT
	@Path("/addevent/{eventId}/to/{merchId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Merchandise addToEvent(@PathParam("eventId") String eventId, @PathParam("merchId") Long merchId);
	
}
