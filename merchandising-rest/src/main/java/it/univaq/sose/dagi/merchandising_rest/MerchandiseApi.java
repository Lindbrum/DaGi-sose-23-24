package it.univaq.sose.dagi.merchandising_rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
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
import jakarta.ws.rs.core.MediaType;

@Path("/merch")
public interface MerchandiseApi {
	@Operation(summary = "Return the entire list of merchandise.")
	@ApiResponses(value = { 
		  @ApiResponse(
				  responseCode = "200", 
				  description = "Return all the content of the database.", 
				  content = { 
						  @Content(
								  mediaType = "application/json", 
								  schema = @Schema(implementation = Merchandise.class)
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
	public ResponseEntity<List<Merchandise>> getAll();
	
	@Operation(summary = "Return the list of merchandise for an event.")
	@ApiResponses(value = { 
		  @ApiResponse(
				  responseCode = "200", 
				  description = "Return the merchandise of the event.", 
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
	@GET
	@Path("/event/{eventId}")
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseEntity<List<Merchandise>> getEventMerchandise(@PathParam("eventId") Long eventId);
	
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
	public ResponseEntity<Merchandise> create(@RequestBody Merchandise newMerch);
	
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
	public ResponseEntity<Merchandise> addToEvent(@PathParam("eventId") Long eventId, @PathParam("merchId") Long merchId);
}
