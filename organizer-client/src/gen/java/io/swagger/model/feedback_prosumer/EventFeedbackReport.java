package io.swagger.model.feedback_prosumer;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

public class EventFeedbackReport   {
  
  @Schema(description = "")
  private List<Feedback> eventFeedbacks = null;
  
  @Schema(description = "")
  private Float averageRating = null;
  
  @Schema(description = "")
  private Float averageCustomerAge = null;
  
  @Schema(description = "")
  private Map<String, Integer> keywordsCount = null;
 /**
   * Get eventFeedbacks
   * @return eventFeedbacks
  **/
  @JsonProperty("eventFeedbacks")
  public List<Feedback> getEventFeedbacks() {
    return eventFeedbacks;
  }

  public void setEventFeedbacks(List<Feedback> eventFeedbacks) {
    this.eventFeedbacks = eventFeedbacks;
  }

  public EventFeedbackReport eventFeedbacks(List<Feedback> eventFeedbacks) {
    this.eventFeedbacks = eventFeedbacks;
    return this;
  }

  public EventFeedbackReport addEventFeedbacksItem(Feedback eventFeedbacksItem) {
    this.eventFeedbacks.add(eventFeedbacksItem);
    return this;
  }

 /**
   * Get averageRating
   * @return averageRating
  **/
  @JsonProperty("averageRating")
  public Float getAverageRating() {
    return averageRating;
  }

  public void setAverageRating(Float averageRating) {
    this.averageRating = averageRating;
  }

  public EventFeedbackReport averageRating(Float averageRating) {
    this.averageRating = averageRating;
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

  public EventFeedbackReport averageCustomerAge(Float averageCustomerAge) {
    this.averageCustomerAge = averageCustomerAge;
    return this;
  }

 /**
   * Get keywordsCount
   * @return keywordsCount
  **/
  @JsonProperty("keywordsCount")
  public Map<String, Integer> getKeywordsCount() {
    return keywordsCount;
  }

  public void setKeywordsCount(Map<String, Integer> keywordsCount) {
    this.keywordsCount = keywordsCount;
  }

  public EventFeedbackReport keywordsCount(Map<String, Integer> keywordsCount) {
    this.keywordsCount = keywordsCount;
    return this;
  }

  public EventFeedbackReport putKeywordsCountItem(String key, Integer keywordsCountItem) {
    this.keywordsCount.put(key, keywordsCountItem);
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EventFeedbackReport {\n");
    
    sb.append("    eventFeedbacks: ").append(toIndentedString(eventFeedbacks)).append("\n");
    sb.append("    averageRating: ").append(toIndentedString(averageRating)).append("\n");
    sb.append("    averageCustomerAge: ").append(toIndentedString(averageCustomerAge)).append("\n");
    sb.append("    keywordsCount: ").append(toIndentedString(keywordsCount)).append("\n");
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
