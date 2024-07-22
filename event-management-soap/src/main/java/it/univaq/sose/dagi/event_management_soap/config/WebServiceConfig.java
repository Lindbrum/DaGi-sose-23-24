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
    

    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, new EventManagementImpl(eventService, feedbackService, soldTicketService, ticketInfoService));
        endpoint.publish("/event-management-soap");
        return endpoint;
    }
}
