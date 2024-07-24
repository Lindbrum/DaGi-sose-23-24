package io.swagger.model.event_merch_prosumer;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

public class EventWithMerch   {
  
  @Schema(description = "")
  private Event event = null;
  
  @Schema(description = "")
  private List<Merchandise> merchandise = null;
 /**
   * Get event
   * @return event
  **/
  @JsonProperty("event")
  public Event getEvent() {
    return event;
  }

  public void setEvent(Event event) {
    this.event = event;
  }

  public EventWithMerch event(Event event) {
    this.event = event;
    return this;
  }

 /**
   * Get merchandise
   * @return merchandise
  **/
  @JsonProperty("merchandise")
  public List<Merchandise> getMerchandise() {
    return merchandise;
  }

  public void setMerchandise(List<Merchandise> merchandise) {
    this.merchandise = merchandise;
  }

  public EventWithMerch merchandise(List<Merchandise> merchandise) {
    this.merchandise = merchandise;
    return this;
  }

  public EventWithMerch addMerchandiseItem(Merchandise merchandiseItem) {
    this.merchandise.add(merchandiseItem);
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EventWithMerch {\n");
    
    sb.append("    event: ").append(toIndentedString(event)).append("\n");
    sb.append("    merchandise: ").append(toIndentedString(merchandise)).append("\n");
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
