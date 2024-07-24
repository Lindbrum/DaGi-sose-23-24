package it.univaq.sose.dagi.sales_analysis_prosumer_rest;

import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.univaq.sose.dagi.sales_analysis_prosumer_rest.model.EventSalesReport;
import it.univaq.sose.dagi.wsdltypes.ServiceException_Exception;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/sales")
public interface SalesAnalysisProsumerApi {

	@Operation(description = "Generate a report on the tickets sold for an event to provide stats on parameters like customer ages/genders and sales by date.", responses = {
			@ApiResponse(description = "A report with a list of all tickets sold, and statistics on ages and genders.", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = EventSalesReport.class)) }) })
	
	//This method retrieves a sales report for a specified event, identified by its event ID, and returns the report in JSON format.
	//It throws a ServiceException_Exception in case of any service-related errors.
	@GET
	@Path("/{eventId}")
	@Produces(MediaType.APPLICATION_JSON)
	public EventSalesReport getEventSalesReport(@Parameter(description = "The ID of the event to generate the report for.") @PathParam(value = "eventId") long eventId) throws ServiceException_Exception;
}
