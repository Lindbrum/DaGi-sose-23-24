package it.univaq.sose.dagi.feedback_prosumer_rest;

import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.univaq.sose.dagi.feedback_prosumer_rest.model.EventFeedbackReport;
import it.univaq.sose.dagi.wsdltypes.ServiceException_Exception;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/feedback-report")
//@Tag(name="feedback-report")
public interface FeedbackProsumerApi {

	@Operation(description = "Generate a report on the feedback given to an event. A list of keywords can be provided as a query parameter "
			+ "(with keywords separated either by commas or semi-colons) to have them counted in the event feedbacks.", responses = {
			@ApiResponse(description = "A report with a list of all feedbacks, an average rating and keyword counts.", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = EventFeedbackReport.class)) }) })
	@GET
	@Path("/{eventId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
  //This method generates a report on the feedback for a specified event. It is needed to provide the event ID as a path parameter.
	//Additionally, you can include a list of keywords as a query parameter.
	//The keywords, which can be separated by commas or semi-colons, will be counted in the event's feedback.
	//The response will be a ResponseEntity<EventFeedbackReport> that includes a detailed report with all feedbacks, the average rating of the event, and counts of the provided keywords.
	//This method consumes and produces data in JSON format. If an error occurs while processing the request, a ServiceException_Exception is thrown.
	public EventFeedbackReport getEventFeedbackReport(@Parameter(description = "The ID of the event to generate the report for.") @PathParam(value = "eventId") long eventId, @QueryParam(value = "keywords") String keywords) throws ServiceException_Exception;
  