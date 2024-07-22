package io.swagger.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

public class HttpHeaders extends HashMap<String, List<?>>  {
  
  private static final long serialVersionUID = -3306945593273492606L;

@Schema(description = "")
  private Boolean empty = null;
  
  @Schema(description = "")
  private String location = null;
  
  @Schema(description = "")
  private HttpHeadersHost host = null;
  
  @Schema(description = "")
  private Map<String, String> all = null;
  
  @Schema(description = "")
  private Long lastModified = null;
  
  @Schema(description = "")
  private Long contentLength = null;
  
  @Schema(description = "")
  private Long date = null;
  
  @Schema(description = "")
  private List<HttpRange> range = null;
  
  @Schema(description = "")
  private ContentDisposition contentDisposition = null;
  
  @Schema(description = "")
  private List<ContentDispositionCharset> acceptCharset = null;
  
  @Schema(description = "")
  private HttpHeadersContentLanguage contentLanguage = null;
  
  @Schema(description = "")
  private List<HttpMethod> allow = null;
  
  @Schema(description = "")
  private String etag = null;
  
  @Schema(description = "")
  private String cacheControl = null;
  
  @Schema(description = "")
  private List<String> connection = null;
  
  @Schema(description = "")
  private List<MediaType> accept = null;
  
  @Schema(description = "")
  private List<HttpMethod> accessControlAllowMethods = null;
  
  @Schema(description = "")
  private HttpMethod accessControlRequestMethod = null;
  
  @Schema(description = "")
  private String accessControlAllowOrigin = null;
  
  @Schema(description = "")
  private Long accessControlMaxAge = null;
  
  @Schema(description = "")
  private List<String> accessControlExposeHeaders = null;
  
  @Schema(description = "")
  private List<String> accessControlAllowHeaders = null;
  
  @Schema(description = "")
  private List<String> accessControlRequestHeaders = null;
  
  @Schema(description = "")
  private Boolean accessControlAllowCredentials = null;
  
  @Schema(description = "")
  private List<HttpHeadersContentLanguage> acceptLanguageAsLocales = null;
  
  @Schema(description = "")
  private Long ifUnmodifiedSince = null;
  
  @Schema(description = "")
  private List<MediaType> acceptPatch = null;
  
  @Schema(description = "")
  private List<HttpHeadersAcceptLanguage> acceptLanguage = null;
  
  @Schema(description = "")
  private String basicAuth = null;
  
  @Schema(description = "")
  private List<String> ifNoneMatch = null;
  
  @Schema(description = "")
  private List<String> ifMatch = null;
  
  @Schema(description = "")
  private String origin = null;
  
  @Schema(description = "")
  private Long expires = null;
  
  @Schema(description = "")
  private String upgrade = null;
  
  @Schema(description = "")
  private String pragma = null;
  
  @Schema(description = "")
  private List<String> vary = null;
  
  @Schema(description = "")
  private String bearerAuth = null;
  
  @Schema(description = "")
  private MediaType contentType = null;
  
  @Schema(description = "")
  private Long ifModifiedSince = null;
 /**
   * Get empty
   * @return empty
  **/
  @JsonProperty("empty")
  public boolean isEmpty() {
    return empty;
  }

  public void setEmpty(Boolean empty) {
    this.empty = empty;
  }

  public HttpHeaders empty(Boolean empty) {
    this.empty = empty;
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

  public HttpHeaders location(String location) {
    this.location = location;
    return this;
  }

 /**
   * Get host
   * @return host
  **/
  @JsonProperty("host")
  public HttpHeadersHost getHost() {
    return host;
  }

  public void setHost(HttpHeadersHost host) {
    this.host = host;
  }

  public HttpHeaders host(HttpHeadersHost host) {
    this.host = host;
    return this;
  }

 /**
   * Get all
   * @return all
  **/
  @JsonProperty("all")
  public Map<String, String> getAll() {
    return all;
  }

  public void setAll(Map<String, String> all) {
    this.all = all;
  }

  public HttpHeaders all(Map<String, String> all) {
    this.all = all;
    return this;
  }

  public HttpHeaders putAllItem(String key, String allItem) {
    this.all.put(key, allItem);
    return this;
  }

 /**
   * Get lastModified
   * @return lastModified
  **/
  @JsonProperty("lastModified")
  public Long getLastModified() {
    return lastModified;
  }

  public void setLastModified(Long lastModified) {
    this.lastModified = lastModified;
  }

  public HttpHeaders lastModified(Long lastModified) {
    this.lastModified = lastModified;
    return this;
  }

 /**
   * Get contentLength
   * @return contentLength
  **/
  @JsonProperty("contentLength")
  public Long getContentLength() {
    return contentLength;
  }

  public void setContentLength(Long contentLength) {
    this.contentLength = contentLength;
  }

  public HttpHeaders contentLength(Long contentLength) {
    this.contentLength = contentLength;
    return this;
  }

 /**
   * Get date
   * @return date
  **/
  @JsonProperty("date")
  public Long getDate() {
    return date;
  }

  public void setDate(Long date) {
    this.date = date;
  }

  public HttpHeaders date(Long date) {
    this.date = date;
    return this;
  }

 /**
   * Get range
   * @return range
  **/
  @JsonProperty("range")
  public List<HttpRange> getRange() {
    return range;
  }

  public void setRange(List<HttpRange> range) {
    this.range = range;
  }

  public HttpHeaders range(List<HttpRange> range) {
    this.range = range;
    return this;
  }

  public HttpHeaders addRangeItem(HttpRange rangeItem) {
    this.range.add(rangeItem);
    return this;
  }

 /**
   * Get contentDisposition
   * @return contentDisposition
  **/
  @JsonProperty("contentDisposition")
  public ContentDisposition getContentDisposition() {
    return contentDisposition;
  }

  public void setContentDisposition(ContentDisposition contentDisposition) {
    this.contentDisposition = contentDisposition;
  }

  public HttpHeaders contentDisposition(ContentDisposition contentDisposition) {
    this.contentDisposition = contentDisposition;
    return this;
  }

 /**
   * Get acceptCharset
   * @return acceptCharset
  **/
  @JsonProperty("acceptCharset")
  public List<ContentDispositionCharset> getAcceptCharset() {
    return acceptCharset;
  }

  public void setAcceptCharset(List<ContentDispositionCharset> acceptCharset) {
    this.acceptCharset = acceptCharset;
  }

  public HttpHeaders acceptCharset(List<ContentDispositionCharset> acceptCharset) {
    this.acceptCharset = acceptCharset;
    return this;
  }

  public HttpHeaders addAcceptCharsetItem(ContentDispositionCharset acceptCharsetItem) {
    this.acceptCharset.add(acceptCharsetItem);
    return this;
  }

 /**
   * Get contentLanguage
   * @return contentLanguage
  **/
  @JsonProperty("contentLanguage")
  public HttpHeadersContentLanguage getContentLanguage() {
    return contentLanguage;
  }

  public void setContentLanguage(HttpHeadersContentLanguage contentLanguage) {
    this.contentLanguage = contentLanguage;
  }

  public HttpHeaders contentLanguage(HttpHeadersContentLanguage contentLanguage) {
    this.contentLanguage = contentLanguage;
    return this;
  }

 /**
   * Get allow
   * @return allow
  **/
  @JsonProperty("allow")
  public List<HttpMethod> getAllow() {
    return allow;
  }

  public void setAllow(List<HttpMethod> allow) {
    this.allow = allow;
  }

  public HttpHeaders allow(List<HttpMethod> allow) {
    this.allow = allow;
    return this;
  }

  public HttpHeaders addAllowItem(HttpMethod allowItem) {
    this.allow.add(allowItem);
    return this;
  }

 /**
   * Get etag
   * @return etag
  **/
  @JsonProperty("etag")
  public String getEtag() {
    return etag;
  }

  public void setEtag(String etag) {
    this.etag = etag;
  }

  public HttpHeaders etag(String etag) {
    this.etag = etag;
    return this;
  }

 /**
   * Get cacheControl
   * @return cacheControl
  **/
  @JsonProperty("cacheControl")
  public String getCacheControl() {
    return cacheControl;
  }

  public void setCacheControl(String cacheControl) {
    this.cacheControl = cacheControl;
  }

  public HttpHeaders cacheControl(String cacheControl) {
    this.cacheControl = cacheControl;
    return this;
  }

 /**
   * Get connection
   * @return connection
  **/
  @JsonProperty("connection")
  public List<String> getConnection() {
    return connection;
  }

  public void setConnection(List<String> connection) {
    this.connection = connection;
  }

  public HttpHeaders connection(List<String> connection) {
    this.connection = connection;
    return this;
  }

  public HttpHeaders addConnectionItem(String connectionItem) {
    this.connection.add(connectionItem);
    return this;
  }

 /**
   * Get accept
   * @return accept
  **/
  @JsonProperty("accept")
  public List<MediaType> getAccept() {
    return accept;
  }

  public void setAccept(List<MediaType> accept) {
    this.accept = accept;
  }

  public HttpHeaders accept(List<MediaType> accept) {
    this.accept = accept;
    return this;
  }

  public HttpHeaders addAcceptItem(MediaType acceptItem) {
    this.accept.add(acceptItem);
    return this;
  }

 /**
   * Get accessControlAllowMethods
   * @return accessControlAllowMethods
  **/
  @JsonProperty("accessControlAllowMethods")
  public List<HttpMethod> getAccessControlAllowMethods() {
    return accessControlAllowMethods;
  }

  public void setAccessControlAllowMethods(List<HttpMethod> accessControlAllowMethods) {
    this.accessControlAllowMethods = accessControlAllowMethods;
  }

  public HttpHeaders accessControlAllowMethods(List<HttpMethod> accessControlAllowMethods) {
    this.accessControlAllowMethods = accessControlAllowMethods;
    return this;
  }

  public HttpHeaders addAccessControlAllowMethodsItem(HttpMethod accessControlAllowMethodsItem) {
    this.accessControlAllowMethods.add(accessControlAllowMethodsItem);
    return this;
  }

 /**
   * Get accessControlRequestMethod
   * @return accessControlRequestMethod
  **/
  @JsonProperty("accessControlRequestMethod")
  public HttpMethod getAccessControlRequestMethod() {
    return accessControlRequestMethod;
  }

  public void setAccessControlRequestMethod(HttpMethod accessControlRequestMethod) {
    this.accessControlRequestMethod = accessControlRequestMethod;
  }

  public HttpHeaders accessControlRequestMethod(HttpMethod accessControlRequestMethod) {
    this.accessControlRequestMethod = accessControlRequestMethod;
    return this;
  }

 /**
   * Get accessControlAllowOrigin
   * @return accessControlAllowOrigin
  **/
  @JsonProperty("accessControlAllowOrigin")
  public String getAccessControlAllowOrigin() {
    return accessControlAllowOrigin;
  }

  public void setAccessControlAllowOrigin(String accessControlAllowOrigin) {
    this.accessControlAllowOrigin = accessControlAllowOrigin;
  }

  public HttpHeaders accessControlAllowOrigin(String accessControlAllowOrigin) {
    this.accessControlAllowOrigin = accessControlAllowOrigin;
    return this;
  }

 /**
   * Get accessControlMaxAge
   * @return accessControlMaxAge
  **/
  @JsonProperty("accessControlMaxAge")
  public Long getAccessControlMaxAge() {
    return accessControlMaxAge;
  }

  public void setAccessControlMaxAge(Long accessControlMaxAge) {
    this.accessControlMaxAge = accessControlMaxAge;
  }

  public HttpHeaders accessControlMaxAge(Long accessControlMaxAge) {
    this.accessControlMaxAge = accessControlMaxAge;
    return this;
  }

 /**
   * Get accessControlExposeHeaders
   * @return accessControlExposeHeaders
  **/
  @JsonProperty("accessControlExposeHeaders")
  public List<String> getAccessControlExposeHeaders() {
    return accessControlExposeHeaders;
  }

  public void setAccessControlExposeHeaders(List<String> accessControlExposeHeaders) {
    this.accessControlExposeHeaders = accessControlExposeHeaders;
  }

  public HttpHeaders accessControlExposeHeaders(List<String> accessControlExposeHeaders) {
    this.accessControlExposeHeaders = accessControlExposeHeaders;
    return this;
  }

  public HttpHeaders addAccessControlExposeHeadersItem(String accessControlExposeHeadersItem) {
    this.accessControlExposeHeaders.add(accessControlExposeHeadersItem);
    return this;
  }

 /**
   * Get accessControlAllowHeaders
   * @return accessControlAllowHeaders
  **/
  @JsonProperty("accessControlAllowHeaders")
  public List<String> getAccessControlAllowHeaders() {
    return accessControlAllowHeaders;
  }

  public void setAccessControlAllowHeaders(List<String> accessControlAllowHeaders) {
    this.accessControlAllowHeaders = accessControlAllowHeaders;
  }

  public HttpHeaders accessControlAllowHeaders(List<String> accessControlAllowHeaders) {
    this.accessControlAllowHeaders = accessControlAllowHeaders;
    return this;
  }

  public HttpHeaders addAccessControlAllowHeadersItem(String accessControlAllowHeadersItem) {
    this.accessControlAllowHeaders.add(accessControlAllowHeadersItem);
    return this;
  }

 /**
   * Get accessControlRequestHeaders
   * @return accessControlRequestHeaders
  **/
  @JsonProperty("accessControlRequestHeaders")
  public List<String> getAccessControlRequestHeaders() {
    return accessControlRequestHeaders;
  }

  public void setAccessControlRequestHeaders(List<String> accessControlRequestHeaders) {
    this.accessControlRequestHeaders = accessControlRequestHeaders;
  }

  public HttpHeaders accessControlRequestHeaders(List<String> accessControlRequestHeaders) {
    this.accessControlRequestHeaders = accessControlRequestHeaders;
    return this;
  }

  public HttpHeaders addAccessControlRequestHeadersItem(String accessControlRequestHeadersItem) {
    this.accessControlRequestHeaders.add(accessControlRequestHeadersItem);
    return this;
  }

 /**
   * Get accessControlAllowCredentials
   * @return accessControlAllowCredentials
  **/
  @JsonProperty("accessControlAllowCredentials")
  public Boolean isAccessControlAllowCredentials() {
    return accessControlAllowCredentials;
  }

  public void setAccessControlAllowCredentials(Boolean accessControlAllowCredentials) {
    this.accessControlAllowCredentials = accessControlAllowCredentials;
  }

  public HttpHeaders accessControlAllowCredentials(Boolean accessControlAllowCredentials) {
    this.accessControlAllowCredentials = accessControlAllowCredentials;
    return this;
  }

 /**
   * Get acceptLanguageAsLocales
   * @return acceptLanguageAsLocales
  **/
  @JsonProperty("acceptLanguageAsLocales")
  public List<HttpHeadersContentLanguage> getAcceptLanguageAsLocales() {
    return acceptLanguageAsLocales;
  }

  public void setAcceptLanguageAsLocales(List<HttpHeadersContentLanguage> acceptLanguageAsLocales) {
    this.acceptLanguageAsLocales = acceptLanguageAsLocales;
  }

  public HttpHeaders acceptLanguageAsLocales(List<HttpHeadersContentLanguage> acceptLanguageAsLocales) {
    this.acceptLanguageAsLocales = acceptLanguageAsLocales;
    return this;
  }

  public HttpHeaders addAcceptLanguageAsLocalesItem(HttpHeadersContentLanguage acceptLanguageAsLocalesItem) {
    this.acceptLanguageAsLocales.add(acceptLanguageAsLocalesItem);
    return this;
  }

 /**
   * Get ifUnmodifiedSince
   * @return ifUnmodifiedSince
  **/
  @JsonProperty("ifUnmodifiedSince")
  public Long getIfUnmodifiedSince() {
    return ifUnmodifiedSince;
  }

  public void setIfUnmodifiedSince(Long ifUnmodifiedSince) {
    this.ifUnmodifiedSince = ifUnmodifiedSince;
  }

  public HttpHeaders ifUnmodifiedSince(Long ifUnmodifiedSince) {
    this.ifUnmodifiedSince = ifUnmodifiedSince;
    return this;
  }

 /**
   * Get acceptPatch
   * @return acceptPatch
  **/
  @JsonProperty("acceptPatch")
  public List<MediaType> getAcceptPatch() {
    return acceptPatch;
  }

  public void setAcceptPatch(List<MediaType> acceptPatch) {
    this.acceptPatch = acceptPatch;
  }

  public HttpHeaders acceptPatch(List<MediaType> acceptPatch) {
    this.acceptPatch = acceptPatch;
    return this;
  }

  public HttpHeaders addAcceptPatchItem(MediaType acceptPatchItem) {
    this.acceptPatch.add(acceptPatchItem);
    return this;
  }

 /**
   * Get acceptLanguage
   * @return acceptLanguage
  **/
  @JsonProperty("acceptLanguage")
  public List<HttpHeadersAcceptLanguage> getAcceptLanguage() {
    return acceptLanguage;
  }

  public void setAcceptLanguage(List<HttpHeadersAcceptLanguage> acceptLanguage) {
    this.acceptLanguage = acceptLanguage;
  }

  public HttpHeaders acceptLanguage(List<HttpHeadersAcceptLanguage> acceptLanguage) {
    this.acceptLanguage = acceptLanguage;
    return this;
  }

  public HttpHeaders addAcceptLanguageItem(HttpHeadersAcceptLanguage acceptLanguageItem) {
    this.acceptLanguage.add(acceptLanguageItem);
    return this;
  }

 /**
   * Get basicAuth
   * @return basicAuth
  **/
  @JsonProperty("basicAuth")
  public String getBasicAuth() {
    return basicAuth;
  }

  public void setBasicAuth(String basicAuth) {
    this.basicAuth = basicAuth;
  }

  public HttpHeaders basicAuth(String basicAuth) {
    this.basicAuth = basicAuth;
    return this;
  }

 /**
   * Get ifNoneMatch
   * @return ifNoneMatch
  **/
  @JsonProperty("ifNoneMatch")
  public List<String> getIfNoneMatch() {
    return ifNoneMatch;
  }

  public void setIfNoneMatch(List<String> ifNoneMatch) {
    this.ifNoneMatch = ifNoneMatch;
  }

  public HttpHeaders ifNoneMatch(List<String> ifNoneMatch) {
    this.ifNoneMatch = ifNoneMatch;
    return this;
  }

  public HttpHeaders addIfNoneMatchItem(String ifNoneMatchItem) {
    this.ifNoneMatch.add(ifNoneMatchItem);
    return this;
  }

 /**
   * Get ifMatch
   * @return ifMatch
  **/
  @JsonProperty("ifMatch")
  public List<String> getIfMatch() {
    return ifMatch;
  }

  public void setIfMatch(List<String> ifMatch) {
    this.ifMatch = ifMatch;
  }

  public HttpHeaders ifMatch(List<String> ifMatch) {
    this.ifMatch = ifMatch;
    return this;
  }

  public HttpHeaders addIfMatchItem(String ifMatchItem) {
    this.ifMatch.add(ifMatchItem);
    return this;
  }

 /**
   * Get origin
   * @return origin
  **/
  @JsonProperty("origin")
  public String getOrigin() {
    return origin;
  }

  public void setOrigin(String origin) {
    this.origin = origin;
  }

  public HttpHeaders origin(String origin) {
    this.origin = origin;
    return this;
  }

 /**
   * Get expires
   * @return expires
  **/
  @JsonProperty("expires")
  public Long getExpires() {
    return expires;
  }

  public void setExpires(Long expires) {
    this.expires = expires;
  }

  public HttpHeaders expires(Long expires) {
    this.expires = expires;
    return this;
  }

 /**
   * Get upgrade
   * @return upgrade
  **/
  @JsonProperty("upgrade")
  public String getUpgrade() {
    return upgrade;
  }

  public void setUpgrade(String upgrade) {
    this.upgrade = upgrade;
  }

  public HttpHeaders upgrade(String upgrade) {
    this.upgrade = upgrade;
    return this;
  }

 /**
   * Get pragma
   * @return pragma
  **/
  @JsonProperty("pragma")
  public String getPragma() {
    return pragma;
  }

  public void setPragma(String pragma) {
    this.pragma = pragma;
  }

  public HttpHeaders pragma(String pragma) {
    this.pragma = pragma;
    return this;
  }

 /**
   * Get vary
   * @return vary
  **/
  @JsonProperty("vary")
  public List<String> getVary() {
    return vary;
  }

  public void setVary(List<String> vary) {
    this.vary = vary;
  }

  public HttpHeaders vary(List<String> vary) {
    this.vary = vary;
    return this;
  }

  public HttpHeaders addVaryItem(String varyItem) {
    this.vary.add(varyItem);
    return this;
  }

 /**
   * Get bearerAuth
   * @return bearerAuth
  **/
  @JsonProperty("bearerAuth")
  public String getBearerAuth() {
    return bearerAuth;
  }

  public void setBearerAuth(String bearerAuth) {
    this.bearerAuth = bearerAuth;
  }

  public HttpHeaders bearerAuth(String bearerAuth) {
    this.bearerAuth = bearerAuth;
    return this;
  }

 /**
   * Get contentType
   * @return contentType
  **/
  @JsonProperty("contentType")
  public MediaType getContentType() {
    return contentType;
  }

  public void setContentType(MediaType contentType) {
    this.contentType = contentType;
  }

  public HttpHeaders contentType(MediaType contentType) {
    this.contentType = contentType;
    return this;
  }

 /**
   * Get ifModifiedSince
   * @return ifModifiedSince
  **/
  @JsonProperty("ifModifiedSince")
  public Long getIfModifiedSince() {
    return ifModifiedSince;
  }

  public void setIfModifiedSince(Long ifModifiedSince) {
    this.ifModifiedSince = ifModifiedSince;
  }

  public HttpHeaders ifModifiedSince(Long ifModifiedSince) {
    this.ifModifiedSince = ifModifiedSince;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class HttpHeaders {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    empty: ").append(toIndentedString(empty)).append("\n");
    sb.append("    location: ").append(toIndentedString(location)).append("\n");
    sb.append("    host: ").append(toIndentedString(host)).append("\n");
    sb.append("    all: ").append(toIndentedString(all)).append("\n");
    sb.append("    lastModified: ").append(toIndentedString(lastModified)).append("\n");
    sb.append("    contentLength: ").append(toIndentedString(contentLength)).append("\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("    range: ").append(toIndentedString(range)).append("\n");
    sb.append("    contentDisposition: ").append(toIndentedString(contentDisposition)).append("\n");
    sb.append("    acceptCharset: ").append(toIndentedString(acceptCharset)).append("\n");
    sb.append("    contentLanguage: ").append(toIndentedString(contentLanguage)).append("\n");
    sb.append("    allow: ").append(toIndentedString(allow)).append("\n");
    sb.append("    etag: ").append(toIndentedString(etag)).append("\n");
    sb.append("    cacheControl: ").append(toIndentedString(cacheControl)).append("\n");
    sb.append("    connection: ").append(toIndentedString(connection)).append("\n");
    sb.append("    accept: ").append(toIndentedString(accept)).append("\n");
    sb.append("    accessControlAllowMethods: ").append(toIndentedString(accessControlAllowMethods)).append("\n");
    sb.append("    accessControlRequestMethod: ").append(toIndentedString(accessControlRequestMethod)).append("\n");
    sb.append("    accessControlAllowOrigin: ").append(toIndentedString(accessControlAllowOrigin)).append("\n");
    sb.append("    accessControlMaxAge: ").append(toIndentedString(accessControlMaxAge)).append("\n");
    sb.append("    accessControlExposeHeaders: ").append(toIndentedString(accessControlExposeHeaders)).append("\n");
    sb.append("    accessControlAllowHeaders: ").append(toIndentedString(accessControlAllowHeaders)).append("\n");
    sb.append("    accessControlRequestHeaders: ").append(toIndentedString(accessControlRequestHeaders)).append("\n");
    sb.append("    accessControlAllowCredentials: ").append(toIndentedString(accessControlAllowCredentials)).append("\n");
    sb.append("    acceptLanguageAsLocales: ").append(toIndentedString(acceptLanguageAsLocales)).append("\n");
    sb.append("    ifUnmodifiedSince: ").append(toIndentedString(ifUnmodifiedSince)).append("\n");
    sb.append("    acceptPatch: ").append(toIndentedString(acceptPatch)).append("\n");
    sb.append("    acceptLanguage: ").append(toIndentedString(acceptLanguage)).append("\n");
    sb.append("    basicAuth: ").append(toIndentedString(basicAuth)).append("\n");
    sb.append("    ifNoneMatch: ").append(toIndentedString(ifNoneMatch)).append("\n");
    sb.append("    ifMatch: ").append(toIndentedString(ifMatch)).append("\n");
    sb.append("    origin: ").append(toIndentedString(origin)).append("\n");
    sb.append("    expires: ").append(toIndentedString(expires)).append("\n");
    sb.append("    upgrade: ").append(toIndentedString(upgrade)).append("\n");
    sb.append("    pragma: ").append(toIndentedString(pragma)).append("\n");
    sb.append("    vary: ").append(toIndentedString(vary)).append("\n");
    sb.append("    bearerAuth: ").append(toIndentedString(bearerAuth)).append("\n");
    sb.append("    contentType: ").append(toIndentedString(contentType)).append("\n");
    sb.append("    ifModifiedSince: ").append(toIndentedString(ifModifiedSince)).append("\n");
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
