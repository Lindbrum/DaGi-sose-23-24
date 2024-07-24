package io.swagger.model.soap_proxy;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

public class TicketInfo   {
  
  @Schema(description = "")
  private Long id = null;
  
  @Schema(description = "")
  private Long eventId = null;
  
  @Schema(description = "")
  private LocalDateTime referenceDate = null;
  
  @Schema(description = "")
  private Integer availableTickets = null;
 /**
   * Get id
   * @return id
  **/
  @JsonProperty("id")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public TicketInfo id(Long id) {
    this.id = id;
    return this;
  }

 /**
   * Get eventId
   * @return eventId
  **/
  @JsonProperty("eventId")
  public Long getEventId() {
    return eventId;
  }

  public void setEventId(Long eventId) {
    this.eventId = eventId;
  }

  public TicketInfo eventId(Long eventId) {
    this.eventId = eventId;
    return this;
  }

 /**
   * Get referenceDate
   * @return referenceDate
  **/
  @JsonProperty("referenceDate")
  public LocalDateTime getReferenceDate() {
    return referenceDate;
  }

  public void setReferenceDate(LocalDateTime referenceDate) {
    this.referenceDate = referenceDate;
  }

  public TicketInfo referenceDate(LocalDateTime referenceDate) {
    this.referenceDate = referenceDate;
    return this;
  }

 /**
   * Get availableTickets
   * @return availableTickets
  **/
  @JsonProperty("availableTickets")
  public Integer getAvailableTickets() {
    return availableTickets;
  }

  public void setAvailableTickets(Integer availableTickets) {
    this.availableTickets = availableTickets;
  }

  public TicketInfo availableTickets(Integer availableTickets) {
    this.availableTickets = availableTickets;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TicketInfo {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    eventId: ").append(toIndentedString(eventId)).append("\n");
    sb.append("    referenceDate: ").append(toIndentedString(referenceDate)).append("\n");
    sb.append("    availableTickets: ").append(toIndentedString(availableTickets)).append("\n");
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
