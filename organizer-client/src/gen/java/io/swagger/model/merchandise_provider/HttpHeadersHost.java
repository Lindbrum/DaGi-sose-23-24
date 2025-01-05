package io.swagger.model.merchandise_provider;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

public class HttpHeadersHost   {
  
  @Schema(description = "")
  private String hostString = null;
  
  @Schema(description = "")
  private HttpHeadersHostAddress address = null;
  
  @Schema(description = "")
  private Integer port = null;
  
  @Schema(description = "")
  private Boolean unresolved = null;
  
  @Schema(description = "")
  private String hostName = null;
 /**
   * Get hostString
   * @return hostString
  **/
  @JsonProperty("hostString")
  public String getHostString() {
    return hostString;
  }

  public void setHostString(String hostString) {
    this.hostString = hostString;
  }

  public HttpHeadersHost hostString(String hostString) {
    this.hostString = hostString;
    return this;
  }

 /**
   * Get address
   * @return address
  **/
  @JsonProperty("address")
  public HttpHeadersHostAddress getAddress() {
    return address;
  }

  public void setAddress(HttpHeadersHostAddress address) {
    this.address = address;
  }

  public HttpHeadersHost address(HttpHeadersHostAddress address) {
    this.address = address;
    return this;
  }

 /**
   * Get port
   * @return port
  **/
  @JsonProperty("port")
  public Integer getPort() {
    return port;
  }

  public void setPort(Integer port) {
    this.port = port;
  }

  public HttpHeadersHost port(Integer port) {
    this.port = port;
    return this;
  }

 /**
   * Get unresolved
   * @return unresolved
  **/
  @JsonProperty("unresolved")
  public Boolean isUnresolved() {
    return unresolved;
  }

  public void setUnresolved(Boolean unresolved) {
    this.unresolved = unresolved;
  }

  public HttpHeadersHost unresolved(Boolean unresolved) {
    this.unresolved = unresolved;
    return this;
  }

 /**
   * Get hostName
   * @return hostName
  **/
  @JsonProperty("hostName")
  public String getHostName() {
    return hostName;
  }

  public void setHostName(String hostName) {
    this.hostName = hostName;
  }

  public HttpHeadersHost hostName(String hostName) {
    this.hostName = hostName;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class HttpHeadersHost {\n");
    
    sb.append("    hostString: ").append(toIndentedString(hostString)).append("\n");
    sb.append("    address: ").append(toIndentedString(address)).append("\n");
    sb.append("    port: ").append(toIndentedString(port)).append("\n");
    sb.append("    unresolved: ").append(toIndentedString(unresolved)).append("\n");
    sb.append("    hostName: ").append(toIndentedString(hostName)).append("\n");
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
