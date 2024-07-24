package it.univaq.sose.dagi.event_management_soap.config;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import it.univaq.sose.dagi.event_management_soap.service.EventServiceDummyImpl;
import it.univaq.sose.dagi.event_management_soap.service.FeedbackServiceDummyImpl;
import it.univaq.sose.dagi.event_management_soap.service.SoldTicketServiceDummyImpl;
import it.univaq.sose.dagi.event_management_soap.service.TicketInfoServiceDummyImpl;
import it.univaq.sose.dagi.wsdltypes.EventManagementImpl;
import jakarta.xml.ws.Endpoint;

@Configuration
public class WebServiceConfig {

	//Use the @Autowired annotation to automatically inject the necessary components.
	//These include the Apache CXF bus (bus) and several service implementations (eventService, feedbackService, soldTicketService, ticketInfoService).
	//These services provide the business logic needed to manage web service operations.
    @Autowired
    private Bus bus;
    @Autowired
    private EventServiceDummyImpl eventService;
    @Autowired
	private FeedbackServiceDummyImpl feedbackService;
	@Autowired
	private SoldTicketServiceDummyImpl soldTicketService;
	@Autowired
	private TicketInfoServiceDummyImpl ticketInfoService;
    
	//In this method, an EndpointImpl is created, which is a concrete implementation of a SOAP endpoint provided by Apache CXF.
	//This endpoint is configured with the bus and an instance of EventManagementImpl, which is the class that contains the business
	//logic and uses the injected service implementations. The endpoint is then published to the URL /event-management-soap, making
	//it accessible to SOAP clients who wish to invoke service operations.
    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, new EventManagementImpl(eventService, feedbackService, soldTicketService, ticketInfoService));
        endpoint.publish("/event-management-soap");
        return endpoint;
    }
}
