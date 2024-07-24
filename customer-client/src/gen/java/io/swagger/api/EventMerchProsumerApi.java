package io.swagger.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import io.swagger.model.event_merch_prosumer.EventWithMerch;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * Event-Merchandise aggregator prosumer
 *
 * <p>This is a RESTful API that fetch and aggregates info on an event with the list of merchandise.
 *
 */
@Path("/")
public interface EventMerchProsumerApi  {

    /**
     * Show the aggregation for the Event with its own Merchandise.
     *
     */
    @GET
    @Path("/event/{eventId}")
    @Produces({ "application/json" })
    @Operation(summary = "Show the aggregation for the Event with its own Merchandise.", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "The event info and merchandise is returned in json format.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventWithMerch.class))),
        @ApiResponse(responseCode = "400", description = "Invalid event id was provided"),
        @ApiResponse(responseCode = "500", description = "Internal server error has occured.") })
    public EventWithMerch getEventInfo1(@PathParam("eventId") Long eventId);
}
