package it.univaq.sose.dagi.sales_analysis_prosumer_rest.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

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
import jakarta.ws.rs.ServiceUnavailableException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SoldTicketsSOAPClient {

	private ObjectFactory factory;
	private URL lastUrl;
	private EurekaClient eurekaClient;
	private EventManagementImplService service;
	private final List<InstanceInfo> lastInstancesCache = Collections.synchronizedList(new ArrayList<>());
	private static final Logger log = LoggerFactory.getLogger(SoldTicketsSOAPClient.class);

	private String wsdlUri = "event-management-soap/event-management-soap/?wsdl";

	public SoldTicketsSOAPClient(EurekaClient eurekaClient) {
		this.eurekaClient = eurekaClient;
		this.service = null;
		this.factory = new ObjectFactory();
	}

	// Returns an instance of the SOAP service among the ones registered to the
	// discovery server
	private EventManagementPort getService() {
		try {
			List<InstanceInfo> instances = Optional
					.ofNullable(eurekaClient.getInstancesByVipAddress("event-management-soap", false))
					.filter(list -> !list.isEmpty()).orElseGet(() -> {

						log.warn("Using cached copy of the event management SOAP service");
						log.warn("lastInstancesCache {}", lastInstancesCache);
						synchronized (lastInstancesCache) {
							return new ArrayList<>(lastInstancesCache); // Return a copy of the cached instances
						}
					});

			// Update the instance cache synchronously
			synchronized (lastInstancesCache) {
				lastInstancesCache.clear();
				lastInstancesCache.addAll(deepCopyInstanceInfoList(instances));
			}

			// Remove the last used instance from the list
			if (lastUrl != null) {
				instances.removeIf(instance -> {
					try {
						return Objects.equals(new URL(instance.getHomePageUrl() + wsdlUri), lastUrl);
					} catch (MalformedURLException e) {
						log.error("Malformed URL while filtering instances: {}", e.getMessage(), e);
						return false;
					}
				});
			}

			// If no alternative instances are available, use the last used instance
			if (instances.isEmpty()) {
				log.warn("No alternative instances available for event-management-soap, using the last used instance");
				if (service != null) {
					return service.getEventManagementImplPort();
				} else {
					throw new ServiceUnavailableException(
							"event-management-soap: No alternative instances available and no previously used instance available");
				}
			}

			// Shuffle the list to select a random instance
			Collections.shuffle(instances);
			InstanceInfo instance = instances.get(0);
			String eurekaUrl = instance.getHomePageUrl() + wsdlUri;
			URL url = new URL(eurekaUrl);
			service = new EventManagementImplService(url);
			log.info("New Retrieved Event Management SOAP URL: {}", url);
			lastUrl = url;

			return service.getEventManagementImplPort();
		} catch (MalformedURLException e) {
			log.error("Malformed URL: {}", e.getMessage(), e);
			throw new ServiceUnavailableException("Malformed URL: " + e.getMessage());
		} catch (Exception e) {
			log.error("Failed to retrieve Event Management SOAP URL: {}", e.getMessage(), e);
			throw new ServiceUnavailableException("Failed to retrieve Event Management SOAP URL: " + e.getMessage());
		}
	}

	private List<InstanceInfo> deepCopyInstanceInfoList(List<InstanceInfo> instances) {
		return instances.stream().map(InstanceInfo::new) // Use the copy constructor
				.collect(Collectors.toList());
	}

	// This method in the SoldTicketsSOAPClient class retrieves sold ticket details
	// for a specified event.
	// It takes an eventId as input, constructs a SOAP request using this ID, and
	// communicates with a web service to
	// fetch the ticket data. The response is parsed to create a list of SoldTicket
	// objects, which includes details such
	// as ticket ID, event ID, reference date, and user ID. This list is then
	// returned. If an error occurs in creating the URL
	// for the web service, the method catches the exception and prints the stack
	// trace, returning null if the process fails.
	public List<SoldTicket> fetchEventSoldTicketsInfo(Long eventId) throws ServiceException_Exception {

		EventManagementPort port = getService(); // load balancing
		FetchEventSoldTicketsRequest requestParams = factory.createFetchEventSoldTicketsRequest();
		requestParams.setEventId(eventId);
		FetchEventSoldTicketsResponse response = port.fetchEventSoldTickets(requestParams);
		SoldTicketsList xmlList = response.getSoldTicketsList();
		List<SoldTicket> soldTicketsInfo = new ArrayList<>();
		for (SoldTicketData xmlTicket : xmlList.getSoldTicketData()) {
			SoldTicket ticket = new SoldTicket();
			ticket.setEventId(eventId);
			ticket.setId(xmlTicket.getSoldTicketId());
			ticket.setReferenceDate(Utility.toLocalDateTime(xmlTicket.getReferenceDate()));
			ticket.setUserId(xmlTicket.getUserId());
			soldTicketsInfo.add(ticket);
		}

		System.out.println("\nThe response is " + response);
		return soldTicketsInfo;
	}

}