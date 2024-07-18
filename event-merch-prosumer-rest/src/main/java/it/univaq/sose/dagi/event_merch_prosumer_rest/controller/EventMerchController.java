package it.univaq.sose.dagi.event_merch_prosumer_rest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.univaq.sose.dagi.event_merch_prosumer_rest.clients.EventSOAPClient;
import it.univaq.sose.dagi.event_merch_prosumer_rest.clients.MerchandiseRESTClient;
import it.univaq.sose.dagi.event_merch_prosumer_rest.model.Event;
import it.univaq.sose.dagi.event_merch_prosumer_rest.model.EventWithMerch;
import it.univaq.sose.dagi.event_merch_prosumer_rest.model.Merchandise;
import it.univaq.sose.dagi.wsdltypes.ServiceException_Exception;

@RestController
@RequestMapping("/eventmerchprosumer")
public class EventMerchController {
	
	private MerchandiseRESTClient merchClient;
	private EventSOAPClient eventClient;
	//@Autowired is inferred by Spring Boot when there is a single public constructor
	//@Autowired
	public EventMerchController(MerchandiseRESTClient merchClient, EventSOAPClient eventClient) {
		this.merchClient=merchClient;
		this.eventClient=eventClient;
	}

	@Operation(summary = "Show the aggregation for the Event with its own Merchandise.")
	@ApiResponses(value = { 
		  @ApiResponse(
				  responseCode = "200", 
				  description = "The sum was calculated and the message template found.", 
				  content = { 
						  @Content(
								  mediaType = "application/json", 
								  schema = @Schema(implementation = EventWithMerch.class)
								  ) 
				  }),
		  @ApiResponse(
				  responseCode = "400",
				  description = "Invalid addendum(s) provided", 
				  content = {
						  @Content(
								  mediaType = "application/json"
								  )
				  }), 
		  @ApiResponse(
				  responseCode = "500",
				  description = "Message template not found", 
				  content = {
						  @Content(
								  mediaType = "application/json"
								  )
				  }) 
	})
	@GetMapping("/event/{eventId}")
	public ResponseEntity<EventWithMerch> getEventInfo(@PathVariable Long eventId) throws ServiceException_Exception {

		JsonNode jsonMerchandise;
		try {
			jsonMerchandise = merchClient.findEventMerch(eventId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException_Exception("Internal server error.");
		}
		Optional.ofNullable(jsonMerchandise).orElseThrow();
		EventWithMerch eventWithMerch = new EventWithMerch();
		List<Merchandise> merchandise = new ArrayList<>();
		if (jsonMerchandise.isArray()) {	 
			for (JsonNode merch : jsonMerchandise) {
				Merchandise current = new Merchandise();
				current.setId(merch.findValue(MerchandiseRESTClient.FIELD_ID).asLong());
				current.setEventId(merch.findValue(MerchandiseRESTClient.FIELD_EVENTID).asLong());
				current.setBarCode(merch.findValue(MerchandiseRESTClient.FIELD_BARCODE).asLong());
				current.setName(merch.findValue(MerchandiseRESTClient.FIELD_NAME).asText());
				current.setDescription(merch.findValue(MerchandiseRESTClient.FIELD_DESCRIPTION).asText());
				merchandise.add(current);
			}
				
		}
		eventWithMerch.setMerchandise(merchandise);
		try {
			Event event = eventClient.requestEventInfo(eventId);
			eventWithMerch.setEvent(event);
		} catch (ServiceException_Exception e) {
			e.printStackTrace();
			throw new ServiceException_Exception("Internal server error.");
		}
		
		return new ResponseEntity<EventWithMerch>(eventWithMerch, HttpStatus.OK);
	}
}
