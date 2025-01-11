package it.univaq.sose.dagi.event_merch_prosumer_rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;

import it.univaq.sose.dagi.event_merch_prosumer_rest.clients.EventSOAPClient;
import it.univaq.sose.dagi.event_merch_prosumer_rest.clients.MerchandiseRESTClient;
import it.univaq.sose.dagi.event_merch_prosumer_rest.clients.MerchandiseRESTFeignClient;
import it.univaq.sose.dagi.event_merch_prosumer_rest.model.Event;
import it.univaq.sose.dagi.event_merch_prosumer_rest.model.EventWithMerch;
import it.univaq.sose.dagi.event_merch_prosumer_rest.model.Merchandise;
import it.univaq.sose.dagi.wsdltypes.ServiceException_Exception;

@Service
public class EventMerchProsumerApiImpl implements EventMerchProsumerApi {

	private MerchandiseRESTFeignClient merchClient;
	private EventSOAPClient eventClient;
	
	//This constructor initializes the class with MerchandiseRESTClient and EventSOAPClient instances.
	//The @Autowired annotation is inferred by Spring Boot when there is a single public constructor.
	public EventMerchProsumerApiImpl(MerchandiseRESTFeignClient merchClient, EventSOAPClient eventClient) {
		this.merchClient=merchClient;
		this.eventClient=eventClient;
	}
	
	//This method fetches merchandise and event information for a given eventId.
	//It first attempts to retrieve merchandise data from the MerchandiseRESTClient.
	//If successful, it maps the JSON response to a list of Merchandise objects.
	//Next, it retrieves the event details from EventSOAPClient and combines both into an EventWithMerch object.
	//If any errors occur during these operations, they are caught and handled, with relevant exceptions thrown if needed.
	@Override
	public EventWithMerch getEventInfo(Long eventId) throws ServiceException_Exception {
		JsonNode jsonMerchandise;
		try {
			jsonMerchandise = merchClient.findEventMerch(eventId);
		} catch (Exception e) {
			e.printStackTrace();
			//throw new ServiceException_Exception("Internal server error.");
			jsonMerchandise = null;
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
		
		//return new ResponseEntity<EventWithMerch>(eventWithMerch, HttpStatus.OK);
		return eventWithMerch;
	}

}
