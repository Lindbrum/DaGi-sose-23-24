# Event and ticket management - Project for S.O.S.E. (A.Y. 23-24).

<p align="center">
  <img src="Docs/FinalLogo.png" width="50%" alt="Logo">
</p>

### Members names and contacts:
1) Dario D'Ercole - (288643) - dario.dercole@student.univaq.it
2) Giovanni Spaziani - (295397) - giovanni.spaziani@student.univaq.it

# Table of Contents

- [Introduction](#introduction)
- [Scenarios](#scenarios)
  - [Creating an event](#--creating-an-event)
  - [Viewing the event catalogue and previewing merchandising](#--viewing-the-event-catalogue-and-previewing-merchandising)
  - [Viewing and purchasing tickets](#--viewing-and-purchasing-tickets)
  - [Report generation and feedback](#--report-generation-and-feedback)
- [Component diagram](#component-diagram)
- [UseCase diagram](#usecase-diagram)
- [Profile diagram](#profile-diagram)
- [S.D. Event and ticket management](#sd-event-and-ticket-management)
- [S.D. Explore event catalogue](#sd-explore-event-catalogue)
- [S.D. Generating a report](#sd-generating-a-report)
- [S.D. Merchandising](#sd-merchandising)
- [S.D. Submit a feedback](#sd-submit-a-feedback)
- [S.D. Ticket history](#sd-ticket-history)
- [S.D. Tickets visualization and purchase](#sd-tickets-visualization-and-purchase)
- [S.D. Visualize event](#sd-visualize-event)

## Introduction

#### Built with: [![Docker][Docker.com]][Docker-url] [![Spring][Spring.io]][Spring-url] [![MySql][MySql.com]][MySql-url]

#### First of all, ensure to clone the repository, by writing the following command: git clone https://github.com/Lindbrum/DaGi-sose-23-24.git
1) #### Install the applications: "Docker Desktop" and "SprintToolSuite4".
2) #### To see the code, open the IDE "SpringToolSuit4" and import the projects by right click on the package explorer, then: import -> Maven -> Existing Maven Project -> Browse -> and then select the repository where u cloned the projects.
3) #### Open "Docker Desktop".
4) #### Run the command "docker-compose up" in a terminal pointing to the folder where is the cloned repository. You have to wait about 15 minutes to download all dependencies.

5) Services pages can be visited with the following URLs:

   - ##### discovery-server: http://localhost:8761
   - ##### event-management-soap: http://localhost:8081/event-management-soap/
   - ##### feedback-prosumer-rest: http://localhost:8082/api/feedback-prosumer/services
   - ##### event-merch-prosumer-rest: http://localhost:8083/api/event-merch-prosumer-rest/services
   - ##### sales-analysis-prosumer-rest: http://localhost:8084/api/sales-analysis-prosumer-rest/services
   - ##### merchandising-rest: http://localhost:8085/api/merchandise-rest/services
   - ##### authentication-rest: http://localhost:8086/api/authentication-rest/services
   - ##### soap-proxy-prosumer-rest: http://localhost:8087/api/soap-proxy/services

6) #### Open SwaggerUI by clicking the link given in the service page.
7) #### To test the APIs, you have to copy-and paste the path given in the URL query parameter in the search bar of SwaggerUI to open our Swagger definition.
8) To play with the clients, go to SprintToolSuite4, search for "customer-client" and "organizer-client" and run as java application with:
   - Main named "CustomerClientapplication" for the "customer-client". Run it and play with the console.
   - Main named "OrganizerClientapplication" for the "organizer-client". Run it and play with the console.

<p align="right">
  <a href="#table-of-contents">Go back to "Table of contents"</a>
</p>

## Scenarios

### - Creating an event.

| Description | Services involved |
| --- | --- |
| An event organizer uses the system to create and manage a new event. The SOAP event management service is used to enter event information, such as title, description, dates, location, and maximum number of participants. | 1.&nbsp;EventManagementSOAP. <br> 2.&nbsp;SOAPProxyProsumerREST. |

#### Interactions:
1) The organizer enters the event details into the client.
2) The client sends a request to the SOAP proxy requesting to save a new Event.
3) The SOAP proxy forwards the request to the Event SOAP endpoint.
4) The SOAP service stores the event details in the database and assigns an event ID.
5) The SOAP service then notifies the SOAP proxy of the operation, which in turn notifies the client.
6) The client notifies the organizer that the event was successfully created.

<p align="right">
  <a href="#table-of-contents">Go back to "Table of contents"</a>
</p>

### - Viewing the event catalogue and previewing merchandising.

| Description | Services involved |
| --- | --- |
| A user of the platform wants to browse the catalogue of available events and view the details of a specific event, including a preview of the merchandise items that will be available during the event. Use the client to search, view and get all the information needed. | 1.&nbsp;EventManagementSOAP. <br> 2.&nbsp;SOAPProxyProsumerREST. <br> 3.&nbsp;MerchandisingREST. <br> 4.&nbsp;EventMerchProsumerREST. |

#### Interactions:
1)	The user logs into the client and selects the option to view the event catalogue.
2)	The client sends a request to the SOAP proxy requesting the first page of the event catalogue, sorted by newest.
3)	The SOAP proxy forwards the request to the Event SOAP endpoint, which answers with a list containing a list of events.
4)	The SOAP proxy relays the answer to the customer’s client, which displays the catalogue page, showing for each event the name, a short description and the running date
   4.1)	The customer can then navigate between catalogue pages, in which case points 2 and 3 repeats but on the selected catalogue page
   4.2)	The customer can changed the sorting method of the catalogue between “newest”, “oldest”, “A to Z” and “Z to A”, in which case points 2 and 3 repeats but with a new sorting method
5)	The user selects an event from the list to view full details.
6)	The client sends a request to the EventMerch prosumer, providing the id of the event.
7)	The EventMerch prosumer requests the full details of the event to the Event SOAP endpoint.
8)	The EventMerch prosumer requests the list of merchandise to the Merchandising service.
9)	Once both providers answer, the prosumer returns the data aggregated in a single object.
10)	The client after receiving the answer from the prosumer displays the event details and the merchandise list.

<p align="right">
  <a href="#table-of-contents">Go back to "Table of contents"</a>
</p>

### - Viewing and purchasing tickets.

| Description | Services involved |
| --- | --- |
| A user is viewing the details of an event and decides to purchase a ticket. The Ticket SOAP service shows available tickets and the customer decides the one to buy. At this point the service process the purchase resulting in ticket payment and recording of the sale info. | 1.&nbsp;EventManagementSOAP. <br> 2.&nbsp;SOAPProxyProsumerREST. <br> 3.&nbsp;MerchandisingREST. |

#### Interactions:

1)	The customer is viewing the event details, and selects the option to purchase a ticket.
2)	The client sends a request to the SOAP proxy requesting the ticket availabilities.
3)	The SOAP proxy forwards the request to the Ticket SOAP endpoint.
4)	The Ticket service answers with a list of the available tickets, which the proxy returns to the customer’s client.
5)	At this point the client displays the available tickets and the customer selects one of them.
6) The client send a request to the SOAP proxy requesting to purchase the selected ticket.
7)	The SOAP proxy forwards the request to the Ticket SOAP endpoint, which in turn register the ticket sale.
8)	The Ticket service notifies the SOAP proxy of the operation, which In turn notifies the client.
9)	The customer’s client shows a confirmation message of the purchase.

<p align="right">
  <a href="#table-of-contents">Go back to "Table of contents"</a>
</p>

### - Report generation and feedback.

| Description | Services involved |
| --- | --- |
| After the conclusion of an event, the organizer wants to obtain a detailed report that includes user feedback and sales statistics. The FeedbackProsumer and SalesAnalysisProsumer work together to generate this report. | 1.&nbsp;FeedbackProsumerREST. <br> 2.&nbsp;SalesAnalysisProsumerREST. <br> 3.&nbsp;EventManagementSOAP. |

#### Interactions:

1)	The organizer logs in to the client and requests a report for a specific event (asynchrony refers to point 2, 2.1 and 2.2).
2)	The client sends asynchronous requests to the FeedbackProsumer and SalesAnalysisProsumer:
   2.1)	The FeedbackProsumer collects event feedback from the "Feedback" service, within EventManagementSOAP.
   2.2)	At the same time, the SalesAnalysis prosumer collects ticket sales data from the Tickets service, within EventManagementSOAP.
3)	The two prosumers synchronize and aggregate the collected data into a single report.
4)	The aggregate report is then sent to the client and displayed to the organizer.

<p align="right">
  <a href="#table-of-contents">Go back to "Table of contents"</a>
</p>

## Component diagram

<p align="center">
  <img src="Docs/Diagrams/component__Component_diagram.jpg" width="75%" alt="Logo">
</p>

The Component diagram is composed of:
  * AuthenticationREST: this component is responsible for authenticating users. It is classified as a WebService, is part of the Provider category and uses the REST architectural style.
  * SOAPProxyProsumerREST: this component is responsible for proxies SOAP requests as REST requests. This is because generally SOAP services should not be exposed to the outside (industry standard).
  * MerchandisingREST: this component manages all merchandising operations. It has been classified as a WebService, is part of the Provider category and uses the REST architectural style.
  * EventManagementSOAP: manages all operations relating to events, tickets and feedback.
  * Events: this component manages event information. It is classified as a WebService, is part of the Provider category and uses the SOAP protocol. Inside it contains the fetchEvents operation (with sortBy and page parameters) inherited from the interface connected with UserWebApp, which is used to retrieve the events in ascending/descending order for both the ID and the alphabetical sequence of letters. It is also possible to retrieve events based on the pages in which they were cataloged.
  * Tickets: this component manages ticket information and operations. It is classified as a WebService, is part of the Provider category and uses the SOAP protocol. Inside it contains the availableTickets operation (with eventId parameter), which is used to check the remaining tickets relating to a specific event.
  * Feedback: this component manages user feedback on events. It is classified as a WebService, is part of the Provider category and uses the SOAP protocol.
  * OrganizerClient: it is an interface for Event manager, and is used to manage events, various merchandise items and collect feedback. Inside it contains the formatReport operation (with aggregatedFeedback and salesData parameters), which is used to give a structure to the report, collecting all the feedback for an event, together with its sales data.
  * CustomerClient: it is an interface for Customer, and is used to view events, purchase tickets and send feedback.
  * EventMerchProsumerREST: this component aggregates data relating to events and available merchandising. It is classified as a WebService, is part of the Prosumer category and uses the REST architectural style.
  * SalesAnalysisProsumerREST: this component analyzes event sales data. It is classified as a WebService, is part of the Prosumer category and uses the REST architectural style. Inside it contains the analyze operation (with ticketList parameter), which is used by the organizer to retrieve the list of tickets sold for an event, useful in the future for analyzing ticket sales data. 
  * FeedbackProsumerREST: this component aggregates all user feedback regarding an event. It is classified as a WebService, is part of the Prosumer category and uses the REST architectural style. Inside it contains the aggregate operation (with feedbackList parameter), which is used to return a list of all the aggregated feedback to the organizer.

The interfaces that have been inserted are:
  * Authentication: it is an interface connected to the AuthenticationREST, OrganizerClient and Customer components, and is used to authenticate users to access all the services made available. Inside it contains the operations LogIn, SignUp and SignOut.
  * FeedbackSubmition: it is an interface connected to the SOAPProxyProsumerREST and Feedback components and is used for submitting feedback on events. Inside it contains the submit operation (with parameters userId, eventId, rating and body), which is used to submit feedback by entering the identifying ID, the event id to to whom the feedback is addressed, the rating from 1 to 5, and the comment on the feedback. 
  * EventManagement: it is an interface connected to the SOAPProxyProsumerREST and Events components and is used for creating events. Inside it contains the createEvent operation (with parameters name, startDate, endDate, location, description and nrTickets), which is used to provide a general overview of the newly created event.
  * EventFeedback: it is an interface connected to the OrganizerClient and FeedbackProsumerREST components and is used to request all the feedback for a specific event from the feedback aggregator. Inside it contains the requestFeedback operation (with eventId parameter).
  * TicketsManagement: it is an interface connected to the SOAPProxyProsumerREST and Tickets components and is used for creating tickets for events. Inside it contains the createTickets operation (with parameters eventId, referenceDate, price and amountNumber).
  * CollectFeedback: it is an interface connected to the Feedback and FeedbackProsumerREST components and is used to collect all the feedback relating to an event. Inside it contains the fetchEventFeedback operation (with eventId parameter), which is used to retrieve the feedback of an event, passing the ID of the latter for identification. 
  * ReportGeneration: it is an interface connected to the SalesAnalysisProsumerREST and FeedbackProsumerREST components and is used to combine data from sales data and feedback, to generate the final report for a specific event.
  * SalesData: it is an interface connected to the Tickets and SalesAnalysisProsumerREST components and is used to collect all ticket sales data. Inside it has the fetchTicketInfo operation (with eventId parameter), which is used to fetch all the ticket information, identifying the event to which it refers via an identifying ID.
  * TicketsAnalysis: it is an interface connected to the OrganizerClient and SalesAnalysisProsumerREST components and is used to carry out ticket sales analysis. Inside it contains the requestSalesAnalysis operation (with eventId parameter), which is used by the organizer's interface to request the analysis of the sale of an event, which is passed via identifying ID.
  * EventInfo: it is an interface connected to the Events and EventMerchProsumerREST components and is used to retrieve event information. Inside it contains the fetchInfo operation (with eventId parameter).
  * EventMerch: it is an interface connected to the MerchandisingREST and EventMerchProsumerREST components and is used to retrieve the merchandise associated with an event. Inside it contains the fetchEventMerch operation (with eventId parameter).
  * EventsCatalogue: it is an interface connected to the SOAPProxyProsumerREST and Events components and is used by the customer to explore scheduled events. Inside it contains the operations fetchEvents (with sortBy and page parameters) and viewEvent (with eventId parameter). The first is used to retrieve events in ascending/descending order both for the ID and for the alphabetical sequence of letters. It is also possible to retrieve events based on the pages in which they were cataloged; the second instead is simply used to display the event, passing the ID to which it refers.
  * MerchManagement: it is an interface connected to the OrganizerClient and MerchandisingREST components and is used to correlate an event with the related merchandise. Inside it contains the operations addMerchItem (with parameters name, description and barCode) and addMerchToEvent (with parameters merchId and eventId). The first is used to add merchandise objects, passing the name, the description of the object, and its barcode. The second operation instead is used to correlate the merchandise to an event. 
  * TicketsPurchase: it is an interface connected to the SOAPProxyProsumerREST and Tickets components and is used for the purchase of one/more tickets. Inside it contains the operations openPurchaseMenu (with parameter eventId), purchaseTickets (with parameters userId, eventId, quantity, date), and ticketsRefund. The first is used to open the ticket purchase menu by passing the event ID. The second is used to purchase the ticket(s), passing the various identification IDs, the quantity of tickets, and the reference date of the event. Finally, the last operation is used to refund the purchased ticket(s).
  * EventView: it is an interface connected to the CustomerClient and EventMerchProsumerREST components and is used for displaying events. Inside it contains the loadEventPage operation (with eventId parameter), which is used to open the page with all the details of the event, passed via identifying ID.
  * FeedbackSubmitionREST: Provides a REST interface to the FeedbackSubmition operations exposed by EventManagementSOAP for the clients.
  * TicketsPurchaseREST: Provides a REST interface to the TicketsPurchase operations exposed by EventManagementSOAP for the clients.
  * EventsCatalogueREST: Provides a REST interface to the EventsCatalogue operations exposed by EventManagementSOAP for the clients.
  * TicketsManagementREST: Provides a REST interface to the TicketsManagement operations exposed by EventManagementSOAP for the clients.
  * EventManagementREST:Provides a REST interface to the EventManagement operations exposed by EventManagementSOAP for the clients.

<p align="right">
  <a href="#table-of-contents">Go back to "Table of contents"</a>
</p>

## UseCase diagram

<p align="center">
  <img src="Docs/Diagrams/uc__UseCase_diagram.jpg" width="75%" alt="Logo">
</p>

In our domain, the protagonists are therefore the Customer and the Organizer. The system has been divided into two main parts, namely FrontEnd and BackEnd. The use cases concerning the Customer are:

| Use Case | Description |
| --- | --- |
| Tickets visualizazion & purchase. | The Customer can view the tickets available for the events, and if he chooses to take them, he can proceed with the purchase operation. |
| Ticket history | The Customer can visualize the history of tickets that he bought. |
| Submit a feedack | The Customer can send feedback regarding each event they have been a part of. |
| Visualize event | The Customer can view the information of an event in detail. |
| Explore event catalogue | The Customer can explore the catalogue of available events. |

Instead, the use cases regarding the Organizer are:

| Use Case | Description |
| --- | --- |
| Event and ticket management | The Organizer can both create an event and ticket for a given event. |
| Generating a report | The Organizer can generate a report to display certain statistics of an event. |
| Merchandising | The Organizer can manage merchandising operations linked to a specific event. |

<p align="right">
  <a href="#table-of-contents">Go back to "Table of contents"</a>
</p>

## Profile diagram

<p align="center">
  <img src="Docs/Diagrams/profile__UML_profile__Profile_diagram.jpg" width="50%" alt="Logo">
</p>

Our Profile diagram consists of:
  * Component: UML metaclass that represents a module of the system and is used to create new elements via stereotypes.
  * WebService: this stereotype extends the Component metaclass, indicating that any component of the system can be classified as a WebService. The attributes present within it are:
    * Category: this attribute uses the ServiceCategory enumeration, useful for classifying the web service.
    * Protocol: this attribute uses the ServiceProtocol enumeration, useful for classifying the web service protocol.
  * Client: this stereotype represents a client using the web service.
  * ServiceCategory: this enumeration defines three categories of services:
    * Provider: a service that provides data and/or functionality.
    * Prosumer: a service that can both provide and consume data and/or functionality.
    * SOA: a service used to indicate a Software Oriented Architecture.
  * ServiceProtocol: this enumeration defines the communication protocols used by the various web services:
    * REST: indicates an architectural style for distributed systems and represents a data transmission system over HTTP.
    * SOAP: indicates a protocol for exchanging messages between software components. 
  *	EventFeedback: represents event feedback data.
  * SalesData: represents sales data.
  *	TicketInfo: represents ticket information.
  *	AggregatedFeedback: represents all the feedback, regarding each event, that has been left by users.

<p align="right">
  <a href="#table-of-contents">Go back to "Table of contents"</a>
</p>

## S.D. Event and ticket management

<p align="center">
  <img src="Docs/Diagrams/sd__Event_and_ticket_management__Event_and_ticket_management.jpg" width="75%" alt="Logo">
</p>

This Sequence diagram starts with two optionals:
  * If the Organizer wants to create an event, it enters the first block. First, they add the details of an event to the OrganizerClient interface (1). Through this interface, the event is created (2), by passing to the SOAPProxyProsumer these parameters: organizerId, event name, event startDate, event endDate, event location, description, and nrTickets available for the event. The proxy forwards the request to the Event SOAP endpoint and the new event is recorded (3). The creation confirmation is returned to the proxy (4), which passes it along to the organizer’s client (5). Finally, the interface takes care of showing the Organizer a confirmation of the addition of the event (6).
  * If the Organizer wants to create tickets for an event, the procedure is almost the same as the block above. First, the Organizer adds the ticket details to the OrganizerClient’s interface (7). Through this interface, the ticket is created (8), by passing to the SOAPProxyProsumer these parameters: eventID, event referenceDate, ticket price and amountNumber of tickets available. The proxy forwards the request to the Ticket SOAP endpoint and the new event ticket is recorded (9). The creation confirmation is returned to the proxy (10), which passes it along to the organizer’s client (11). Finally, the interface takes care of showing the Organizer a confirmation of the addition of the event ticket (12).

<p align="right">
  <a href="#table-of-contents">Go back to "Table of contents"</a>
</p>

## S.D. Explore event catalogue

<p align="center">
  <img src="Docs/Diagrams/sd__Explore_event_catalogue__Explore_event_catalogue.jpg" width="50%" alt="Logo">
</p>

This Sequence Diagram begins with the Customer's request to the CustomerClient interface to open the event catalogue (1). The interface takes care of contacting the Events component to retrieve all the events, sorted by id, and viewable by page (2). The Events component takes care of returning the list of events to the interface (3). The latter then returns the first page showing the events to the Customer (4). At this point there are two options:
  * The Customer can choose to view another page. He starts by selecting the desired page via the interface (5), the interface takes care of retrieving all the events present on that page sorting them by id (6). The Events component then returns the events present on that page to the interface (7). Finally, the interface returns the selected page to the Customer (8).
  *	The Customer can choose to view the events by sorting them alphabetically. He starts by selecting through the interface to show all events in alphabetical order (9). The interface takes care of contacting the Events component to retrieve events (10). The latter returns the list of events sorted alphabetically to the Customer interface (11). Finally, the interface shows the Customer the new ordered list of events (12).

<p align="right">
  <a href="#table-of-contents">Go back to "Table of contents"</a>
</p>

## S.D. Generating a report

<p align="center">
  <img src="Docs/Diagrams/sd__Generating_a_report__Generating_a_report.jpg" width="75%" alt="Logo">
</p>

This Sequence diagram starts with the Event manager's request to report for an event (1). At this point, two sequences of operations are performed in parallel:
  * The first sequence of operations deals with getting all the feedback for an event. It starts with the organizer interface to request feedback for a selected event via Id (2). FeedbackAggregation asks the Feedback component to retrieve the feedback for that event, always passing its identifying id (3). A list of feedback (4) is then returned to the FeedbackAggregation. It then scrolls through all the feedback, counts the keywords that the organizer asks it to count, calculates the average age of those who reviewed the event, and finally calculates the average rating for that event (5). Finally, the list of all aggregated feedback is returned to the organizer's interface (6).
  * The second sequence of operations deals with getting all the sales data for an event. It starts with the request from the OrganizerWebApp interface to acquire the sales data for an event, identified with an Id (7). The prosumer asks to retrieve the ticket information, taken via the eventId (8). Subsequently, a list of tickets sold (9) is returned to him. The SalesAnalysis prosumer at this point analyzes all the data, i.e. the list of tickets sold, age distribution of buyers, count of tickets sold on a date (if an event has multiple dates), count of the genders of those who purchased the tickets, calculation of the average age of buyers (10). Finally, all ticket sales data is returned to the OrganizerWebApp interface (11).
At this point in the Sequence Diagram, the interface formats all the data it receives (12), and then shows the report to the EventManager user (13).


<p align="right">
  <a href="#table-of-contents">Go back to "Table of contents"</a>
</p>

## S.D. Merchandising

<p align="center">
  <img src="Docs/Diagrams/sd__Merchandising__Merchandising.jpg" width="75%" alt="Logo">
</p>

This Sequence diagram begins with the Organizer user who enters the merchandise details via the OrganizerClient interface (1). The interface contacts the Merchandising provider to add a merchandise item, writing the item name, description and barCode (2). Then, the provider returns the confirmation of insertion of the merchandise (3). Finally, the interface takes care of showing the inserted object to the Organizer user (4). At this point there is an optional: if the Organizer user wants to associate the merchandise with an event, he continues with the selection of the event to which to associate the objects (5). The OrganizerClient interface takes care of requesting the association of the merchandise with the event (6). Subsequently, the addition confirmation (7) is returned. Finally, the Event manager user receives the confirmed association message (8).

<p align="right">
  <a href="#table-of-contents">Go back to "Table of contents"</a>
</p>

## S.D. Submit a feedback

<p align="center">
  <img src="Docs/Diagrams/sd__Submit_a_feedback__Submit_a_feedback.jpg" width="75%" alt="Logo">
</p>

This Sequence diagram begins with the Customer pressing the button to leave feedback for an event (1). Through the CustomerClient, the feedback submition operation is initialized, passing the userId, the eventId, the rating for the event, and the feedback body to the SOAPProxyProsumerREST (2), which forwards the request to the SOAP endpoint (3). At this point the Feedback provider returns the confirmation of the added feedback to the interface (4-5), which finally returns the confirmation of submission of the feedback to the Customer (6).

<p align="right">
  <a href="#table-of-contents">Go back to "Table of contents"</a>
</p>

## S.D. Ticket history

<p align="center">
  <img src="Docs/Diagrams/sd__Ticket_history__Ticket_history.jpg" width="75%" alt="Logo">
</p>

This Sequence diagram starts with the Customer selecting the option to check their ticket history from the CustomerClient’s interface (1). The client requests the ticket history to the SOAPproxyProsumer by providing the customer’s id (2). The proxy forwards this request to the Tickets SOAP endpoint (3), which answers with the list of purchased tickets (4). The proxy relays the response to the client (5), which displays the ticket history to the Customer (6). Optionally, the customer can initiate feedback submission from this screen.

<p align="right">
  <a href="#table-of-contents">Go back to "Table of contents"</a>
</p>

## S.D. Tickets visualization and purchase

<p align="center">
  <img src="Docs/Diagrams/sd__Tickets_visualization_&_purchase__Tickets_visualization_&_purchase.jpg" width="75%" alt="Logo">
</p>

This Sequence diagram starts with the Customer pressing the button to view the ticket options (1) from the event details screen. It continues with the CustomerClient interface which requests the list of available tickets to the SOAPProxyProsumer provider to open the ticket purchase menu, passing the eventId (2) as a parameter. The proxy forwards the request to the Tickets SOAP endpoint (3). The Tickets endpoint returns a list of available tickets to the proxy (4), which relays it to the client (5). The Customer is then shown the ticket purchase menu (6). At this point of the Sequence Diagram, there is an optional, which concerns the case in which the Customer decides to buy the ticket(s). First, the Customer selects the day and number of tickets to purchase (7). Through the CustomerClient interface, the purchase operation of the ticket(s) is started, passing to the SOAPProxyProsumer the userId, the eventId, the quantity of tickets to be purchased and the date of the event (8). The proxy forwards the request to the Tickets SOAP endpoint (9), which record the ticket sale. At this point a confirmation answer is sent through the proxy (10) to the client (11). Finally, the purchase confirmation is shown to the Customer (12).

<p align="right">
  <a href="#table-of-contents">Go back to "Table of contents"</a>
</p>

## S.D. Visualize event

<p align="center">
  <img src="Docs/Diagrams/sd__Visualize_event__Visualize_event.jpg" width="75%" alt="Logo">
</p>

This Sequence diagram begins with the Customer's request to open the detail page of an event (1). Via the CustomerCient interface, the event page is loaded (2). At this point, two sequences of operations are performed in parallel:
  * The first sequence of operations deals with recovering the event data. It starts with the request from EventMerchProsumer to retrieve the information, passing the eventId (3). The event data is then returned to the EventMerchProsumer (4).
  * The second sequence of operations deals with recovering the list of merchandise for that event. It therefore starts with the request from EventMerchProsumer to recover the merchandise associated with the event, passed through Id (5). The list of merchandise associated with that event is then returned to the EventMerchProsumer (6).

The penultimate operation instead concerns the return to the event page interface (7). Finally, the interface takes care of showing the Customer the event details page (8).

<p align="right">
  <a href="#table-of-contents">Go back to "Table of contents"</a>
</p>

<ins>However, if you prefer, all images at optimal resolution are located within the "Docs/Diagrams" folder.</ins>

<!-- MARKDOWN LINKS & IMAGES -->
[Docker.com]: https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white
[Docker-url]: https://www.docker.com/
[Spring.io]: https://img.shields.io/badge/SpringBoot-6DB33F?style=flat-square&logo=Spring&logoColor=white
[Spring-url]: https://spring.io/projects/spring-boot
[MySql.com]: https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white
[MySql-url]: https://www.mysql.com/
