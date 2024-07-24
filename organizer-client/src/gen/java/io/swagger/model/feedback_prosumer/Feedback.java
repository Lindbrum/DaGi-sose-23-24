package io.swagger.model.feedback_prosumer;


import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

public class Feedback   {
  
  @Schema(description = "")
  private Long id = null;
  
  @Schema(description = "")
  private Long userId = null;
  
  @Schema(description = "")
  private Long eventId = null;
  
  @Schema(description = "")
  private Integer rating = null;
  
  @Schema(description = "")
  private String body = null;
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

  public Feedback id(Long id) {
    this.id = id;
    return this;
  }

 /**
   * Get userId
   * @return userId
  **/
  @JsonProperty("userId")
  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Feedback userId(Long userId) {
    this.userId = userId;
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

  public Feedback eventId(Long eventId) {
    this.eventId = eventId;
    return this;
  }

 /**
   * Get rating
   * @return rating
  **/
  @JsonProperty("rating")
  public Integer getRating() {
    return rating;
  }

  public void setRating(Integer rating) {
    this.rating = rating;
  }

  public Feedback rating(Integer rating) {
    this.rating = rating;
    return this;
  }

 /**
   * Get body
   * @return body
  **/
  @JsonProperty("body")
  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public Feedback body(String body) {
    this.body = body;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Feedback {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    eventId: ").append(toIndentedString(eventId)).append("\n");
    sb.append("    rating: ").append(toIndentedString(rating)).append("\n");
    sb.append("    body: ").append(toIndentedString(body)).append("\n");
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
