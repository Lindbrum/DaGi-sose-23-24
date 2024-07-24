package io.swagger.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import io.swagger.model.sales_prosumer.EventSalesReport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * Feedback report Prosumer
 *
 * <p>This is a RESTful API that collects the feedbacks left for an event and generates a report.
 *
 */
@Path("/")
public interface SalesProsumerApi  {

    @GET
    @Path("/sales/{eventId}")
    @Produces({ "application/json" })
    @Operation(summary = "", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "A report with a list of all tickets sold, and statistics on ages and genders.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventSalesReport.class))) })
    public EventSalesReport getEventSalesReport1(@PathParam("eventId") Long eventId);
}
