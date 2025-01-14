package it.univaq.sose.dagi.soap_proxy_prosumer_rest;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.univaq.sose.dagi.soap_proxy_prosumer_rest.model.CustomerHistory;
import it.univaq.sose.dagi.soap_proxy_prosumer_rest.model.Event;
import it.univaq.sose.dagi.soap_proxy_prosumer_rest.model.Feedback;
import it.univaq.sose.dagi.soap_proxy_prosumer_rest.model.SoldTicket;
import it.univaq.sose.dagi.soap_proxy_prosumer_rest.model.TicketInfo;
import it.univaq.sose.dagi.wsdltypes.ServiceException_Exception;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/")
public interface SoapProxyProsumerApi {

	// Event endpoints
	@Operation(summary = "Request event info.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Ok", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Event.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid event id was provided", content = {
					@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "500", description = "Internal server error has occured.", content = {
					@Content(mediaType = "application/json") }) })
	@GET
	@Path("event/info/{eventId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Event fetchEventInfo(@PathParam("eventId") Long eventId);

	// Event endpoints
	@Operation(summary = "Request event catalogue.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Event.class)))

	}), @ApiResponse(responseCode = "400", description = "Invalid event id was provided", content = {
			@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "500", description = "Internal server error has occured.", content = {
					@Content(mediaType = "application/json") }) })
	@GET
	@Path("event/catalogue/customer")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Event> eventCatalogue(@QueryParam(value = "page") int page,
			@QueryParam(value = "sortby") String sortBy);

	@Operation(summary = "Create an event.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Ok", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Long.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid event id was provided", content = {
					@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "500", description = "Internal server error has occured.", content = {
					@Content(mediaType = "application/json") }) })
	@POST
	@Path("event/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Long createEvent(@RequestBody Event newEvent);

	@Operation(summary = "Request organizer event catalogue.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Event.class)))

	}), @ApiResponse(responseCode = "400", description = "Invalid event id was provided", content = {
			@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "500", description = "Internal server error has occured.", content = {
					@Content(mediaType = "application/json") }) })
	@GET
	@Path("event/catalogue/organizer/{organizerId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Event> requestOrganizerEventsPage(@PathParam("organizerId") Long customerId,
			@QueryParam(value = "page") int page, @QueryParam(value = "sortby") String sortBy)
			throws ServiceException_Exception;

	// Feedback endpoints
	@Operation(summary = "Submit a feedback to an event.")
	@ApiResponses(value = { 
		  @ApiResponse(
				  responseCode = "200",
						  description = "Ok",
				  content = { 
						  @Content(
								  mediaType = "application/json", 
								  schema = @Schema(implementation = String.class)
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
	@POST
	@Path("feedback/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String submitFeedback(@RequestBody Feedback newFeedback) throws ServiceException_Exception;

	// Ticket endpoints
	@Operation(summary = "Request event ticket availabilities.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TicketInfo.class)))

	}), @ApiResponse(responseCode = "400", description = "Invalid event id was provided", content = {
			@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "500", description = "Internal server error has occured.", content = {
					@Content(mediaType = "application/json") }) })
	@GET
	@Path("ticket/availabilities/{eventId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<TicketInfo> fetchEventAvailableTickets(@PathParam("eventId") Long eventId)
			throws ServiceException_Exception;

	@Operation(summary = "Register a ticket purchase.")
	@ApiResponses(value = { 
		  @ApiResponse(
				  responseCode = "200", 
						  description = "Ok",
				  content = { 
						  @Content(
								  mediaType = "application/json", 
								  schema = @Schema(implementation = Long.class)
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
	@POST
	@Path("sold-ticket/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Long purchaseTicket(@RequestBody SoldTicket newSoldTicket) throws ServiceException_Exception;

	@Operation(summary = "Fetch the customer purchase history.")
	@ApiResponses(value = { 
		  @ApiResponse(
				  responseCode = "200", 
						  description = "Ok",
				  content = { 
						  @Content(
								  mediaType = "application/json", 
								  schema = @Schema(implementation = CustomerHistory.class)
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
	@Path("ticket/history/{customerId}")
	@Produces(MediaType.APPLICATION_JSON)
	public CustomerHistory fetchCustomerTicketHistory(@PathParam("customerId") Long userId)
			throws ServiceException_Exception;

	@Operation(summary = "Create ticket info for an event.")
	@ApiResponses(value = { 
		  @ApiResponse(
				  responseCode = "200",
						  description = "Ok",
				  content = { 
						  @Content(
								  mediaType = "application/json", 
								  schema = @Schema(implementation = Long.class)
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
	@POST
	@Path("ticket-info/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Long createTicketInfo(@RequestBody TicketInfo newTicketInfo) throws ServiceException_Exception;
}
