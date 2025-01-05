package io.swagger.model.merchandise_provider;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

public class MediaType   {
  
  @Schema(description = "")
  private String type = null;
  
  @Schema(description = "")
  private String subtype = null;
  
  @Schema(description = "")
  private Map<String, String> parameters = null;
  
  @Schema(description = "")
  private Double qualityValue = null;
  
  @Schema(description = "")
  private ContentDispositionCharset charset = null;
  
  @Schema(description = "")
  private Boolean concrete = null;
  
  @Schema(description = "")
  private Boolean wildcardType = null;
  
  @Schema(description = "")
  private Boolean wildcardSubtype = null;
  
  @Schema(description = "")
  private String subtypeSuffix = null;
 /**
   * Get type
   * @return type
  **/
  @JsonProperty("type")
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public MediaType type(String type) {
    this.type = type;
    return this;
  }

 /**
   * Get subtype
   * @return subtype
  **/
  @JsonProperty("subtype")
  public String getSubtype() {
    return subtype;
  }

  public void setSubtype(String subtype) {
    this.subtype = subtype;
  }

  public MediaType subtype(String subtype) {
    this.subtype = subtype;
    return this;
  }

 /**
   * Get parameters
   * @return parameters
  **/
  @JsonProperty("parameters")
  public Map<String, String> getParameters() {
    return parameters;
  }

  public void setParameters(Map<String, String> parameters) {
    this.parameters = parameters;
  }

  public MediaType parameters(Map<String, String> parameters) {
    this.parameters = parameters;
    return this;
  }

  public MediaType putParametersItem(String key, String parametersItem) {
    this.parameters.put(key, parametersItem);
    return this;
  }

 /**
   * Get qualityValue
   * @return qualityValue
  **/
  @JsonProperty("qualityValue")
  public Double getQualityValue() {
    return qualityValue;
  }

  public void setQualityValue(Double qualityValue) {
    this.qualityValue = qualityValue;
  }

  public MediaType qualityValue(Double qualityValue) {
    this.qualityValue = qualityValue;
    return this;
  }

 /**
   * Get charset
   * @return charset
  **/
  @JsonProperty("charset")
  public ContentDispositionCharset getCharset() {
    return charset;
  }

  public void setCharset(ContentDispositionCharset charset) {
    this.charset = charset;
  }

  public MediaType charset(ContentDispositionCharset charset) {
    this.charset = charset;
    return this;
  }

 /**
   * Get concrete
   * @return concrete
  **/
  @JsonProperty("concrete")
  public Boolean isConcrete() {
    return concrete;
  }

  public void setConcrete(Boolean concrete) {
    this.concrete = concrete;
  }

  public MediaType concrete(Boolean concrete) {
    this.concrete = concrete;
    return this;
  }

 /**
   * Get wildcardType
   * @return wildcardType
  **/
  @JsonProperty("wildcardType")
  public Boolean isWildcardType() {
    return wildcardType;
  }

  public void setWildcardType(Boolean wildcardType) {
    this.wildcardType = wildcardType;
  }

  public MediaType wildcardType(Boolean wildcardType) {
    this.wildcardType = wildcardType;
    return this;
  }

 /**
   * Get wildcardSubtype
   * @return wildcardSubtype
  **/
  @JsonProperty("wildcardSubtype")
  public Boolean isWildcardSubtype() {
    return wildcardSubtype;
  }

  public void setWildcardSubtype(Boolean wildcardSubtype) {
    this.wildcardSubtype = wildcardSubtype;
  }

  public MediaType wildcardSubtype(Boolean wildcardSubtype) {
    this.wildcardSubtype = wildcardSubtype;
    return this;
  }

 /**
   * Get subtypeSuffix
   * @return subtypeSuffix
  **/
  @JsonProperty("subtypeSuffix")
  public String getSubtypeSuffix() {
    return subtypeSuffix;
  }

  public void setSubtypeSuffix(String subtypeSuffix) {
    this.subtypeSuffix = subtypeSuffix;
  }

  public MediaType subtypeSuffix(String subtypeSuffix) {
    this.subtypeSuffix = subtypeSuffix;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MediaType {\n");
    
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    subtype: ").append(toIndentedString(subtype)).append("\n");
    sb.append("    parameters: ").append(toIndentedString(parameters)).append("\n");
    sb.append("    qualityValue: ").append(toIndentedString(qualityValue)).append("\n");
    sb.append("    charset: ").append(toIndentedString(charset)).append("\n");
    sb.append("    concrete: ").append(toIndentedString(concrete)).append("\n");
    sb.append("    wildcardType: ").append(toIndentedString(wildcardType)).append("\n");
    sb.append("    wildcardSubtype: ").append(toIndentedString(wildcardSubtype)).append("\n");
    sb.append("    subtypeSuffix: ").append(toIndentedString(subtypeSuffix)).append("\n");
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
