package it.univaq.sose.dagi.event_merch_prosumer_rest;

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

//The EventMerchProsumerApi interface is designed to provide information about events and their associated merchandise.
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
	
	//The getEventInfo method retrieves details for a specific event and its merchandise.
	//It uses the HTTP GET method and is accessed via the path /event/{eventId}. This method expects an eventId as a path parameter and produces a response in JSON format.
	//The response will include detailed information about the event and its merchandise if the request is successful (HTTP 200).
	//If the provided event ID is invalid, it will return a HTTP 400 response, and if there is a server-side issue, it will result in a HTTP 500 response.
	//The method may throw a ServiceException_Exception in case of operational issues.
	@GET
	@Path("event/{eventId}")
	@Produces(MediaType.APPLICATION_JSON)
	public EventWithMerch getEventInfo(@Parameter(description = "The ID of the event to fetch the merchandise for.") @PathParam("eventId") Long eventId) throws ServiceException_Exception;
}
