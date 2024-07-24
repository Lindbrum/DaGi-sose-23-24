package io.swagger.model.event_merch_prosumer;


import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

public class ContentDispositionCharset   {
  
  @Schema(description = "")
  private Boolean registered = null;
 /**
   * Get registered
   * @return registered
  **/
  @JsonProperty("registered")
  public Boolean isRegistered() {
    return registered;
  }

  public void setRegistered(Boolean registered) {
    this.registered = registered;
  }

  public ContentDispositionCharset registered(Boolean registered) {
    this.registered = registered;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ContentDispositionCharset {\n");
    
    sb.append("    registered: ").append(toIndentedString(registered)).append("\n");
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
