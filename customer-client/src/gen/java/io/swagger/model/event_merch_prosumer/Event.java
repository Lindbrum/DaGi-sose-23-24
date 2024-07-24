package io.swagger.model.event_merch_prosumer;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

public class Event   {
  
  @Schema(description = "")
  private Long id = null;
  
  @Schema(description = "")
  private String name = null;
  
  @Schema(description = "")
  private String description = null;
  
  @Schema(description = "")
  private Long organizerId = null;
  
  @Schema(description = "")
  private String location = null;
  
  @Schema(description = "")
  private LocalDateTime startDate = null;
  
  @Schema(description = "")
  private LocalDateTime endDate = null;
  
  @Schema(description = "")
  private Integer nrTickets = null;
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

  public Event id(Long id) {
    this.id = id;
    return this;
  }

 /**
   * Get name
   * @return name
  **/
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Event name(String name) {
    this.name = name;
    return this;
  }

 /**
   * Get description
   * @return description
  **/
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Event description(String description) {
    this.description = description;
    return this;
  }

 /**
   * Get organizerId
   * @return organizerId
  **/
  @JsonProperty("organizerId")
  public Long getOrganizerId() {
    return organizerId;
  }

  public void setOrganizerId(Long organizerId) {
    this.organizerId = organizerId;
  }

  public Event organizerId(Long organizerId) {
    this.organizerId = organizerId;
    return this;
  }

 /**
   * Get location
   * @return location
  **/
  @JsonProperty("location")
  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public Event location(String location) {
    this.location = location;
    return this;
  }

 /**
   * Get startDate
   * @return startDate
  **/
  @JsonProperty("startDate")
  public LocalDateTime getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDateTime startDate) {
    this.startDate = startDate;
  }

  public Event startDate(LocalDateTime startDate) {
    this.startDate = startDate;
    return this;
  }

 /**
   * Get endDate
   * @return endDate
  **/
  @JsonProperty("endDate")
  public LocalDateTime getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDateTime endDate) {
    this.endDate = endDate;
  }

  public Event endDate(LocalDateTime endDate) {
    this.endDate = endDate;
    return this;
  }

 /**
   * Get nrTickets
   * @return nrTickets
  **/
  @JsonProperty("nrTickets")
  public Integer getNrTickets() {
    return nrTickets;
  }

  public void setNrTickets(Integer nrTickets) {
    this.nrTickets = nrTickets;
  }

  public Event nrTickets(Integer nrTickets) {
    this.nrTickets = nrTickets;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Event {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    organizerId: ").append(toIndentedString(organizerId)).append("\n");
    sb.append("    location: ").append(toIndentedString(location)).append("\n");
    sb.append("    startDate: ").append(toIndentedString(startDate)).append("\n");
    sb.append("    endDate: ").append(toIndentedString(endDate)).append("\n");
    sb.append("    nrTickets: ").append(toIndentedString(nrTickets)).append("\n");
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
