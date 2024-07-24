package io.swagger.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import io.swagger.model.feedback_prosumer.EventFeedbackReport;
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
public interface FeedbackProsumerApi  {

    @GET
    @Path("/feedback-report/{eventId}")
    @Produces({ "application/json" })
    @Operation(summary = "", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "A report with a list of all feedbacks, an average rating and keyword counts.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventFeedbackReport.class))) })
    public EventFeedbackReport getEventFeedbackReport1(@PathParam("eventId") Long eventId, @QueryParam("keywords")String keywords);
}
