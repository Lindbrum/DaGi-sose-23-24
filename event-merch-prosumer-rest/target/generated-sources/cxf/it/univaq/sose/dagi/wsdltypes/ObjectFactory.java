
package it.univaq.sose.dagi.wsdltypes;

import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.univaq.sose.dagi.wsdltypes package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.univaq.sose.dagi.wsdltypes
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link EventCatalogueResponse }
     * 
     */
    public EventCatalogueResponse createEventCatalogueResponse() {
        return new EventCatalogueResponse();
    }

    /**
     * Create an instance of {@link CreateEventRequest }
     * 
     */
    public CreateEventRequest createCreateEventRequest() {
        return new CreateEventRequest();
    }

    /**
     * Create an instance of {@link CreateEventResponse }
     * 
     */
    public CreateEventResponse createCreateEventResponse() {
        return new CreateEventResponse();
    }

    /**
     * Create an instance of {@link EventCatalogueRequest }
     * 
     */
    public EventCatalogueRequest createEventCatalogueRequest() {
        return new EventCatalogueRequest();
    }

    /**
     * Create an instance of {@link EventCatalogueResponse.EventList }
     * 
     */
    public EventCatalogueResponse.EventList createEventCatalogueResponseEventList() {
        return new EventCatalogueResponse.EventList();
    }

    /**
     * Create an instance of {@link FetchEventInfoRequest }
     * 
     */
    public FetchEventInfoRequest createFetchEventInfoRequest() {
        return new FetchEventInfoRequest();
    }

    /**
     * Create an instance of {@link FetchEventInfoResponse }
     * 
     */
    public FetchEventInfoResponse createFetchEventInfoResponse() {
        return new FetchEventInfoResponse();
    }

    /**
     * Create an instance of {@link EventData }
     * 
     */
    public EventData createEventData() {
        return new EventData();
    }

    /**
     * Create an instance of {@link PurchaseMenuRequest }
     * 
     */
    public PurchaseMenuRequest createPurchaseMenuRequest() {
        return new PurchaseMenuRequest();
    }

    /**
     * Create an instance of {@link PurchaseMenuResponse }
     * 
     */
    public PurchaseMenuResponse createPurchaseMenuResponse() {
        return new PurchaseMenuResponse();
    }

    /**
     * Create an instance of {@link TicketAvailability }
     * 
     */
    public TicketAvailability createTicketAvailability() {
        return new TicketAvailability();
    }

    /**
     * Create an instance of {@link CreateFeedbackRequest }
     * 
     */
    public CreateFeedbackRequest createCreateFeedbackRequest() {
        return new CreateFeedbackRequest();
    }

    /**
     * Create an instance of {@link CreateFeedbackResponse }
     * 
     */
    public CreateFeedbackResponse createCreateFeedbackResponse() {
        return new CreateFeedbackResponse();
    }

    /**
     * Create an instance of {@link ServiceException }
     * 
     */
    public ServiceException createServiceException() {
        return new ServiceException();
    }

}
