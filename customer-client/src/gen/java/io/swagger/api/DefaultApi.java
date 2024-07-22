package io.swagger.api;

import io.swagger.model.EventWithMerch;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;

/**
 * Event-Merchandise aggregator prosumer
 *
 * <p>This is a RESTful API that fetch and aggregates info on an event with the list of merchandise.
 *
 */
@Path("/")
public interface DefaultApi  {

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
