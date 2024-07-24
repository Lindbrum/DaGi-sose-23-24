package io.swagger.model.event_merch_prosumer;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

public class HttpHeadersHostAddress   {
  
  @Schema(description = "")
  private String hostAddress = null;
  
  @Schema(description = "")
  private List<byte[]> address = null;
  
  @Schema(description = "")
  private String hostName = null;
  
  @Schema(description = "")
  private Boolean linkLocalAddress = null;
  
  @Schema(description = "")
  private Boolean multicastAddress = null;
  
  @Schema(description = "")
  private Boolean anyLocalAddress = null;
  
  @Schema(description = "")
  private Boolean loopbackAddress = null;
  
  @Schema(description = "")
  private Boolean siteLocalAddress = null;
  
  @Schema(description = "")
  private Boolean mcglobal = null;
  
  @Schema(description = "")
  private Boolean mcnodeLocal = null;
  
  @Schema(description = "")
  private Boolean mclinkLocal = null;
  
  @Schema(description = "")
  private Boolean mcsiteLocal = null;
  
  @Schema(description = "")
  private Boolean mcorgLocal = null;
  
  @Schema(description = "")
  private String canonicalHostName = null;
 /**
   * Get hostAddress
   * @return hostAddress
  **/
  @JsonProperty("hostAddress")
  public String getHostAddress() {
    return hostAddress;
  }

  public void setHostAddress(String hostAddress) {
    this.hostAddress = hostAddress;
  }

  public HttpHeadersHostAddress hostAddress(String hostAddress) {
    this.hostAddress = hostAddress;
    return this;
  }

 /**
   * Get address
   * @return address
  **/
  @JsonProperty("address")
  public List<byte[]> getAddress() {
    return address;
  }

  public void setAddress(List<byte[]> address) {
    this.address = address;
  }

  public HttpHeadersHostAddress address(List<byte[]> address) {
    this.address = address;
    return this;
  }

  public HttpHeadersHostAddress addAddressItem(byte[] addressItem) {
    this.address.add(addressItem);
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

  public HttpHeadersHostAddress hostName(String hostName) {
    this.hostName = hostName;
    return this;
  }

 /**
   * Get linkLocalAddress
   * @return linkLocalAddress
  **/
  @JsonProperty("linkLocalAddress")
  public Boolean isLinkLocalAddress() {
    return linkLocalAddress;
  }

  public void setLinkLocalAddress(Boolean linkLocalAddress) {
    this.linkLocalAddress = linkLocalAddress;
  }

  public HttpHeadersHostAddress linkLocalAddress(Boolean linkLocalAddress) {
    this.linkLocalAddress = linkLocalAddress;
    return this;
  }

 /**
   * Get multicastAddress
   * @return multicastAddress
  **/
  @JsonProperty("multicastAddress")
  public Boolean isMulticastAddress() {
    return multicastAddress;
  }

  public void setMulticastAddress(Boolean multicastAddress) {
    this.multicastAddress = multicastAddress;
  }

  public HttpHeadersHostAddress multicastAddress(Boolean multicastAddress) {
    this.multicastAddress = multicastAddress;
    return this;
  }

 /**
   * Get anyLocalAddress
   * @return anyLocalAddress
  **/
  @JsonProperty("anyLocalAddress")
  public Boolean isAnyLocalAddress() {
    return anyLocalAddress;
  }

  public void setAnyLocalAddress(Boolean anyLocalAddress) {
    this.anyLocalAddress = anyLocalAddress;
  }

  public HttpHeadersHostAddress anyLocalAddress(Boolean anyLocalAddress) {
    this.anyLocalAddress = anyLocalAddress;
    return this;
  }

 /**
   * Get loopbackAddress
   * @return loopbackAddress
  **/
  @JsonProperty("loopbackAddress")
  public Boolean isLoopbackAddress() {
    return loopbackAddress;
  }

  public void setLoopbackAddress(Boolean loopbackAddress) {
    this.loopbackAddress = loopbackAddress;
  }

  public HttpHeadersHostAddress loopbackAddress(Boolean loopbackAddress) {
    this.loopbackAddress = loopbackAddress;
    return this;
  }

 /**
   * Get siteLocalAddress
   * @return siteLocalAddress
  **/
  @JsonProperty("siteLocalAddress")
  public Boolean isSiteLocalAddress() {
    return siteLocalAddress;
  }

  public void setSiteLocalAddress(Boolean siteLocalAddress) {
    this.siteLocalAddress = siteLocalAddress;
  }

  public HttpHeadersHostAddress siteLocalAddress(Boolean siteLocalAddress) {
    this.siteLocalAddress = siteLocalAddress;
    return this;
  }

 /**
   * Get mcglobal
   * @return mcglobal
  **/
  @JsonProperty("mcglobal")
  public Boolean isMcglobal() {
    return mcglobal;
  }

  public void setMcglobal(Boolean mcglobal) {
    this.mcglobal = mcglobal;
  }

  public HttpHeadersHostAddress mcglobal(Boolean mcglobal) {
    this.mcglobal = mcglobal;
    return this;
  }

 /**
   * Get mcnodeLocal
   * @return mcnodeLocal
  **/
  @JsonProperty("mcnodeLocal")
  public Boolean isMcnodeLocal() {
    return mcnodeLocal;
  }

  public void setMcnodeLocal(Boolean mcnodeLocal) {
    this.mcnodeLocal = mcnodeLocal;
  }

  public HttpHeadersHostAddress mcnodeLocal(Boolean mcnodeLocal) {
    this.mcnodeLocal = mcnodeLocal;
    return this;
  }

 /**
   * Get mclinkLocal
   * @return mclinkLocal
  **/
  @JsonProperty("mclinkLocal")
  public Boolean isMclinkLocal() {
    return mclinkLocal;
  }

  public void setMclinkLocal(Boolean mclinkLocal) {
    this.mclinkLocal = mclinkLocal;
  }

  public HttpHeadersHostAddress mclinkLocal(Boolean mclinkLocal) {
    this.mclinkLocal = mclinkLocal;
    return this;
  }

 /**
   * Get mcsiteLocal
   * @return mcsiteLocal
  **/
  @JsonProperty("mcsiteLocal")
  public Boolean isMcsiteLocal() {
    return mcsiteLocal;
  }

  public void setMcsiteLocal(Boolean mcsiteLocal) {
    this.mcsiteLocal = mcsiteLocal;
  }

  public HttpHeadersHostAddress mcsiteLocal(Boolean mcsiteLocal) {
    this.mcsiteLocal = mcsiteLocal;
    return this;
  }

 /**
   * Get mcorgLocal
   * @return mcorgLocal
  **/
  @JsonProperty("mcorgLocal")
  public Boolean isMcorgLocal() {
    return mcorgLocal;
  }

  public void setMcorgLocal(Boolean mcorgLocal) {
    this.mcorgLocal = mcorgLocal;
  }

  public HttpHeadersHostAddress mcorgLocal(Boolean mcorgLocal) {
    this.mcorgLocal = mcorgLocal;
    return this;
  }

 /**
   * Get canonicalHostName
   * @return canonicalHostName
  **/
  @JsonProperty("canonicalHostName")
  public String getCanonicalHostName() {
    return canonicalHostName;
  }

  public void setCanonicalHostName(String canonicalHostName) {
    this.canonicalHostName = canonicalHostName;
  }

  public HttpHeadersHostAddress canonicalHostName(String canonicalHostName) {
    this.canonicalHostName = canonicalHostName;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class HttpHeadersHostAddress {\n");
    
    sb.append("    hostAddress: ").append(toIndentedString(hostAddress)).append("\n");
    sb.append("    address: ").append(toIndentedString(address)).append("\n");
    sb.append("    hostName: ").append(toIndentedString(hostName)).append("\n");
    sb.append("    linkLocalAddress: ").append(toIndentedString(linkLocalAddress)).append("\n");
    sb.append("    multicastAddress: ").append(toIndentedString(multicastAddress)).append("\n");
    sb.append("    anyLocalAddress: ").append(toIndentedString(anyLocalAddress)).append("\n");
    sb.append("    loopbackAddress: ").append(toIndentedString(loopbackAddress)).append("\n");
    sb.append("    siteLocalAddress: ").append(toIndentedString(siteLocalAddress)).append("\n");
    sb.append("    mcglobal: ").append(toIndentedString(mcglobal)).append("\n");
    sb.append("    mcnodeLocal: ").append(toIndentedString(mcnodeLocal)).append("\n");
    sb.append("    mclinkLocal: ").append(toIndentedString(mclinkLocal)).append("\n");
    sb.append("    mcsiteLocal: ").append(toIndentedString(mcsiteLocal)).append("\n");
    sb.append("    mcorgLocal: ").append(toIndentedString(mcorgLocal)).append("\n");
    sb.append("    canonicalHostName: ").append(toIndentedString(canonicalHostName)).append("\n");
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
