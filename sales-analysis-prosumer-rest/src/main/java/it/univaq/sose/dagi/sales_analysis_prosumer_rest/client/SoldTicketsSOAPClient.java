package it.univaq.sose.dagi.sales_analysis_prosumer_rest.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import it.univaq.sose.dagi.sales_analysis_prosumer_rest.Utility;
import it.univaq.sose.dagi.sales_analysis_prosumer_rest.model.SoldTicket;
import it.univaq.sose.dagi.wsdltypes.EventManagementImplService;
import it.univaq.sose.dagi.wsdltypes.EventManagementPort;
import it.univaq.sose.dagi.wsdltypes.FetchEventSoldTicketsRequest;
import it.univaq.sose.dagi.wsdltypes.FetchEventSoldTicketsResponse;
import it.univaq.sose.dagi.wsdltypes.FetchEventSoldTicketsResponse.SoldTicketsList;
import it.univaq.sose.dagi.wsdltypes.ObjectFactory;
import it.univaq.sose.dagi.wsdltypes.ServiceException_Exception;
import it.univaq.sose.dagi.wsdltypes.SoldTicketData;

@Component
public class SoldTicketsSOAPClient {

	private ObjectFactory factory;
	@Value("${client.event.wsdl}")
	private String wsdlUrl;
	
	public SoldTicketsSOAPClient() {
		this.factory = new ObjectFactory();
	}

	public List<SoldTicket> fetchEventSoldTicketsInfo(Long eventId) throws ServiceException_Exception {
		try {
			URL url = new URL(wsdlUrl);
			EventManagementImplService service = new EventManagementImplService(url);
			EventManagementPort port = service.getEventManagementImplPort();
			
			FetchEventSoldTicketsRequest requestParams = factory.createFetchEventSoldTicketsRequest();
			requestParams.setEventId(eventId);
			FetchEventSoldTicketsResponse response = port.fetchEventSoldTickets(requestParams);
			SoldTicketsList xmlList = response.getSoldTicketsList();
			List<SoldTicket> soldTicketsInfo = new ArrayList<>();
			for(SoldTicketData xmlTicket : xmlList.getSoldTicketData()) {
				SoldTicket ticket = new SoldTicket();
				ticket.setEventId(eventId);
				ticket.setId(xmlTicket.getSoldTicketId());
				ticket.setReferenceDate(Utility.toLocalDateTime(xmlTicket.getReferenceDate()));
				ticket.setUserId(xmlTicket.getUserId());
				soldTicketsInfo.add(ticket);
			}
			
			System.out.println("\nThe response is " + response);
			return soldTicketsInfo;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}

}