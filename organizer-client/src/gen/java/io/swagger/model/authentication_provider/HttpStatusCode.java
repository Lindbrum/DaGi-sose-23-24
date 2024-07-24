package io.swagger.model.authentication_provider;


import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

public class HttpStatusCode   {
  
  @Schema(description = "")
  private Boolean error = null;
  
  @Schema(description = "")
  private Boolean is5xxServerError = null;
  
  @Schema(description = "")
  private Boolean is4xxClientError = null;
  
  @Schema(description = "")
  private Boolean is3xxRedirection = null;
  
  @Schema(description = "")
  private Boolean is2xxSuccessful = null;
  
  @Schema(description = "")
  private Boolean is1xxInformational = null;
 /**
   * Get error
   * @return error
  **/
  @JsonProperty("error")
  public Boolean isError() {
    return error;
  }

  public void setError(Boolean error) {
    this.error = error;
  }

  public HttpStatusCode error(Boolean error) {
    this.error = error;
    return this;
  }

 /**
   * Get is5xxServerError
   * @return is5xxServerError
  **/
  @JsonProperty("is5xxServerError")
  public Boolean isIs5xxServerError() {
    return is5xxServerError;
  }

  public void setIs5xxServerError(Boolean is5xxServerError) {
    this.is5xxServerError = is5xxServerError;
  }

  public HttpStatusCode is5xxServerError(Boolean is5xxServerError) {
    this.is5xxServerError = is5xxServerError;
    return this;
  }

 /**
   * Get is4xxClientError
   * @return is4xxClientError
  **/
  @JsonProperty("is4xxClientError")
  public Boolean isIs4xxClientError() {
    return is4xxClientError;
  }

  public void setIs4xxClientError(Boolean is4xxClientError) {
    this.is4xxClientError = is4xxClientError;
  }

  public HttpStatusCode is4xxClientError(Boolean is4xxClientError) {
    this.is4xxClientError = is4xxClientError;
    return this;
  }

 /**
   * Get is3xxRedirection
   * @return is3xxRedirection
  **/
  @JsonProperty("is3xxRedirection")
  public Boolean isIs3xxRedirection() {
    return is3xxRedirection;
  }

  public void setIs3xxRedirection(Boolean is3xxRedirection) {
    this.is3xxRedirection = is3xxRedirection;
  }

  public HttpStatusCode is3xxRedirection(Boolean is3xxRedirection) {
    this.is3xxRedirection = is3xxRedirection;
    return this;
  }

 /**
   * Get is2xxSuccessful
   * @return is2xxSuccessful
  **/
  @JsonProperty("is2xxSuccessful")
  public Boolean isIs2xxSuccessful() {
    return is2xxSuccessful;
  }

  public void setIs2xxSuccessful(Boolean is2xxSuccessful) {
    this.is2xxSuccessful = is2xxSuccessful;
  }

  public HttpStatusCode is2xxSuccessful(Boolean is2xxSuccessful) {
    this.is2xxSuccessful = is2xxSuccessful;
    return this;
  }

 /**
   * Get is1xxInformational
   * @return is1xxInformational
  **/
  @JsonProperty("is1xxInformational")
  public Boolean isIs1xxInformational() {
    return is1xxInformational;
  }

  public void setIs1xxInformational(Boolean is1xxInformational) {
    this.is1xxInformational = is1xxInformational;
  }

  public HttpStatusCode is1xxInformational(Boolean is1xxInformational) {
    this.is1xxInformational = is1xxInformational;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class HttpStatusCode {\n");
    
    sb.append("    error: ").append(toIndentedString(error)).append("\n");
    sb.append("    is5xxServerError: ").append(toIndentedString(is5xxServerError)).append("\n");
    sb.append("    is4xxClientError: ").append(toIndentedString(is4xxClientError)).append("\n");
    sb.append("    is3xxRedirection: ").append(toIndentedString(is3xxRedirection)).append("\n");
    sb.append("    is2xxSuccessful: ").append(toIndentedString(is2xxSuccessful)).append("\n");
    sb.append("    is1xxInformational: ").append(toIndentedString(is1xxInformational)).append("\n");
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
