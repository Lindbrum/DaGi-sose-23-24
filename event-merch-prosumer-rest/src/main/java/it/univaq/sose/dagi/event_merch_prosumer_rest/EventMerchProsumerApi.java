package it.univaq.sose.dagi.event_merch_prosumer_rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.univaq.sose.dagi.event_merch_prosumer_rest.model.EventWithMerch;
import it.univaq.sose.dagi.wsdltypes.ServiceException_Exception;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/")
public interface EventMerchProsumerApi {

	@Operation(summary = "Show the aggregation for the Event with its own Merchandise.")
	@ApiResponses(value = { 
		  @ApiResponse(
				  responseCode = "200", 
				  description = "The event info and merchandise is returned in json format.", 
				  content = { 
						  @Content(
								  mediaType = "application/json", 
								  schema = @Schema(implementation = EventWithMerch.class)
								  ) 
				  }),
		  @ApiResponse(
				  responseCode = "400",
				  description = "Invalid event id was provided", 
				  content = {
						  @Content(
								  mediaType = "application/json"
								  )
				  }), 
		  @ApiResponse(
				  responseCode = "500",
				  description = "Internal server error has occured.", 
				  content = {
						  @Content(
								  mediaType = "application/json"
								  )
				  }) 
	})
	@GET
	@Path("event/{eventId}")
	@Produces(MediaType.APPLICATION_JSON)
	public EventWithMerch getEventInfo(@Parameter(description = "The ID of the event to fetch the merchandise for.") @PathParam("eventId") Long eventId) throws ServiceException_Exception;
}
