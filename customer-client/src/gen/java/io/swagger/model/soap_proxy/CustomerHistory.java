package io.swagger.model.soap_proxy;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

public class CustomerHistory   {
  
  @Schema(description = "")
  private List<SoldTicket> boughtTickets = null;
  
  @Schema(description = "")
  private List<Event> ticketRelatedEvents = null;
 /**
   * Get boughtTickets
   * @return boughtTickets
  **/
  @JsonProperty("boughtTickets")
  public List<SoldTicket> getBoughtTickets() {
    return boughtTickets;
  }

  public void setBoughtTickets(List<SoldTicket> boughtTickets) {
    this.boughtTickets = boughtTickets;
  }

  public CustomerHistory boughtTickets(List<SoldTicket> boughtTickets) {
    this.boughtTickets = boughtTickets;
    return this;
  }

  public CustomerHistory addBoughtTicketsItem(SoldTicket boughtTicketsItem) {
    this.boughtTickets.add(boughtTicketsItem);
    return this;
  }

 /**
   * Get ticketRelatedEvents
   * @return ticketRelatedEvents
  **/
  @JsonProperty("ticketRelatedEvents")
  public List<Event> getTicketRelatedEvents() {
    return ticketRelatedEvents;
  }

  public void setTicketRelatedEvents(List<Event> ticketRelatedEvents) {
    this.ticketRelatedEvents = ticketRelatedEvents;
  }

  public CustomerHistory ticketRelatedEvents(List<Event> ticketRelatedEvents) {
    this.ticketRelatedEvents = ticketRelatedEvents;
    return this;
  }

  public CustomerHistory addTicketRelatedEventsItem(Event ticketRelatedEventsItem) {
    this.ticketRelatedEvents.add(ticketRelatedEventsItem);
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CustomerHistory {\n");
    
    sb.append("    boughtTickets: ").append(toIndentedString(boughtTickets)).append("\n");
    sb.append("    ticketRelatedEvents: ").append(toIndentedString(ticketRelatedEvents)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private static String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
