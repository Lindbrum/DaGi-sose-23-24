package io.swagger.model.event_merch_prosumer;


import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

public class HttpHeadersAcceptLanguage   {
  
  @Schema(description = "")
  private String range = null;
  
  @Schema(description = "")
  private Double weight = null;
 /**
   * Get range
   * @return range
  **/
  @JsonProperty("range")
  public String getRange() {
    return range;
  }

  public void setRange(String range) {
    this.range = range;
  }

  public HttpHeadersAcceptLanguage range(String range) {
    this.range = range;
    return this;
  }

 /**
   * Get weight
   * @return weight
  **/
  @JsonProperty("weight")
  public Double getWeight() {
    return weight;
  }

  public void setWeight(Double weight) {
    this.weight = weight;
  }

  public HttpHeadersAcceptLanguage weight(Double weight) {
    this.weight = weight;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class HttpHeadersAcceptLanguage {\n");
    
    sb.append("    range: ").append(toIndentedString(range)).append("\n");
    sb.append("    weight: ").append(toIndentedString(weight)).append("\n");
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
