package io.swagger.api;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import io.swagger.model.soap_proxy.CustomerHistory;
import io.swagger.model.soap_proxy.Event;
import io.swagger.model.soap_proxy.Feedback;
import io.swagger.model.soap_proxy.SoldTicket;
import io.swagger.model.soap_proxy.TicketInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * SOAP proxy Prosumer
 *
 * <p>This is a RESTful API that proxy SOAP requests for the clients.
 *
 */
@Path("/")
public interface SoapProxyApi  {

    /**
     * Create an event.
     *
     */
    @POST
    @Path("/event/create")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @Operation(summary = "Create an event.", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "a", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Long.class))),
        @ApiResponse(responseCode = "400", description = "Invalid event id was provided"),
        @ApiResponse(responseCode = "500", description = "Internal server error has occured.") })
    public Long createEvent1(Event body);

    /**
     * Create ticket info for an event.
     *
     */
    @POST
    @Path("/ticket-info/create")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @Operation(summary = "Create ticket info for an event.", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "a", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Long.class))),
        @ApiResponse(responseCode = "400", description = "Invalid event id was provided"),
        @ApiResponse(responseCode = "500", description = "Internal server error has occured.") })
    public Long createTicketInfo1(TicketInfo body);

    /**
     * Request event catalogue.
     *
     */
    @GET
    @Path("/event/catalogue/customer")
    @Produces({ "application/json" })
    @Operation(summary = "Request event catalogue.", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "a", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Event.class)))),
        @ApiResponse(responseCode = "400", description = "Invalid event id was provided"),
        @ApiResponse(responseCode = "500", description = "Internal server error has occured.") })
    public List<Event> eventCatalogue1(@QueryParam("page")Integer page, @QueryParam("sortby")String sortby);

    /**
     * Fetch the customer purchase history.
     *
     */
    @GET
    @Path("/ticket/history/{customerId}")
    @Produces({ "application/json" })
    @Operation(summary = "Fetch the customer purchase history.", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "a", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerHistory.class))),
        @ApiResponse(responseCode = "400", description = "Invalid event id was provided"),
        @ApiResponse(responseCode = "500", description = "Internal server error has occured.") })
    public CustomerHistory fetchCustomerTicketHistory1(@PathParam("customerId") Long customerId);

    /**
     * Request event ticket availabilities.
     *
     */
    @GET
    @Path("/ticket/availabilities/{eventId}")
    @Produces({ "application/json" })
    @Operation(summary = "Request event ticket availabilities.", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "a", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TicketInfo.class)))),
        @ApiResponse(responseCode = "400", description = "Invalid event id was provided"),
        @ApiResponse(responseCode = "500", description = "Internal server error has occured.") })
    public List<TicketInfo> fetchEventAvailableTickets1(@PathParam("eventId") Long eventId);

    /**
     * Request event info.
     *
     */
    @GET
    @Path("/event/info/{eventId}")
    @Produces({ "application/json" })
    @Operation(summary = "Request event info.", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "a", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Event.class))),
        @ApiResponse(responseCode = "400", description = "Invalid event id was provided"),
        @ApiResponse(responseCode = "500", description = "Internal server error has occured.") })
    public Event fetchEventInfo1(@PathParam("eventId") Long eventId);

    /**
     * Register a ticket purchase.
     *
     */
    @POST
    @Path("/sold-ticket/create")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @Operation(summary = "Register a ticket purchase.", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "a", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Long.class))),
        @ApiResponse(responseCode = "400", description = "Invalid event id was provided"),
        @ApiResponse(responseCode = "500", description = "Internal server error has occured.") })
    public Long purchaseTicket1(SoldTicket body);

    /**
     * Request organizer event catalogue.
     *
     */
    @GET
    @Path("/event/catalogue/organizer/{organizerId}")
    @Produces({ "application/json" })
    @Operation(summary = "Request organizer event catalogue.", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "a", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Event.class)))),
        @ApiResponse(responseCode = "400", description = "Invalid event id was provided"),
        @ApiResponse(responseCode = "500", description = "Internal server error has occured.") })
    public List<Event> requestOrganizerEventsPage1(@PathParam("organizerId") Long organizerId, @QueryParam("page")Integer page, @QueryParam("sortby")String sortby);

    /**
     * Submit a feedback to an event.
     *
     */
    @POST
    @Path("/feedback/create")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @Operation(summary = "Submit a feedback to an event.", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "a", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "400", description = "Invalid event id was provided"),
        @ApiResponse(responseCode = "500", description = "Internal server error has occured.") })
    public String submitFeedback1(Feedback body);
}
