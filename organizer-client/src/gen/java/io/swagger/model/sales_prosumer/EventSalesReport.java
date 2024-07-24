package io.swagger.model.sales_prosumer;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

public class EventSalesReport   {
  
  @Schema(description = "")
  private List<SoldTicket> eventSoldTickets = null;
  
  @Schema(description = "")
  private Map<String, Integer> ageCounts = null;
  
  @Schema(description = "")
  private Float averageCustomerAge = null;
  
  @Schema(description = "")
  private Map<String, Integer> genderCounts = null;
  
  @Schema(description = "")
  private Map<String, Integer> dateCounts = null;
 /**
   * Get eventSoldTickets
   * @return eventSoldTickets
  **/
  @JsonProperty("eventSoldTickets")
  public List<SoldTicket> getEventSoldTickets() {
    return eventSoldTickets;
  }

  public void setEventSoldTickets(List<SoldTicket> eventSoldTickets) {
    this.eventSoldTickets = eventSoldTickets;
  }

  public EventSalesReport eventSoldTickets(List<SoldTicket> eventSoldTickets) {
    this.eventSoldTickets = eventSoldTickets;
    return this;
  }

  public EventSalesReport addEventSoldTicketsItem(SoldTicket eventSoldTicketsItem) {
    this.eventSoldTickets.add(eventSoldTicketsItem);
    return this;
  }

 /**
   * Get ageCounts
   * @return ageCounts
  **/
  @JsonProperty("ageCounts")
  public Map<String, Integer> getAgeCounts() {
    return ageCounts;
  }

  public void setAgeCounts(Map<String, Integer> ageCounts) {
    this.ageCounts = ageCounts;
  }

  public EventSalesReport ageCounts(Map<String, Integer> ageCounts) {
    this.ageCounts = ageCounts;
    return this;
  }

  public EventSalesReport putAgeCountsItem(String key, Integer ageCountsItem) {
    this.ageCounts.put(key, ageCountsItem);
    return this;
  }

 /**
   * Get averageCustomerAge
   * @return averageCustomerAge
  **/
  @JsonProperty("averageCustomerAge")
  public Float getAverageCustomerAge() {
    return averageCustomerAge;
  }

  public void setAverageCustomerAge(Float averageCustomerAge) {
    this.averageCustomerAge = averageCustomerAge;
  }

  public EventSalesReport averageCustomerAge(Float averageCustomerAge) {
    this.averageCustomerAge = averageCustomerAge;
    return this;
  }

 /**
   * Get genderCounts
   * @return genderCounts
  **/
  @JsonProperty("genderCounts")
  public Map<String, Integer> getGenderCounts() {
    return genderCounts;
  }

  public void setGenderCounts(Map<String, Integer> genderCounts) {
    this.genderCounts = genderCounts;
  }

  public EventSalesReport genderCounts(Map<String, Integer> genderCounts) {
    this.genderCounts = genderCounts;
    return this;
  }

  public EventSalesReport putGenderCountsItem(String key, Integer genderCountsItem) {
    this.genderCounts.put(key, genderCountsItem);
    return this;
  }

 /**
   * Get dateCounts
   * @return dateCounts
  **/
  @JsonProperty("dateCounts")
  public Map<String, Integer> getDateCounts() {
    return dateCounts;
  }

  public void setDateCounts(Map<String, Integer> dateCounts) {
    this.dateCounts = dateCounts;
  }

  public EventSalesReport dateCounts(Map<String, Integer> dateCounts) {
    this.dateCounts = dateCounts;
    return this;
  }

  public EventSalesReport putDateCountsItem(String key, Integer dateCountsItem) {
    this.dateCounts.put(key, dateCountsItem);
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EventSalesReport {\n");
    
    sb.append("    eventSoldTickets: ").append(toIndentedString(eventSoldTickets)).append("\n");
    sb.append("    ageCounts: ").append(toIndentedString(ageCounts)).append("\n");
    sb.append("    averageCustomerAge: ").append(toIndentedString(averageCustomerAge)).append("\n");
    sb.append("    genderCounts: ").append(toIndentedString(genderCounts)).append("\n");
    sb.append("    dateCounts: ").append(toIndentedString(dateCounts)).append("\n");
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
