package io.swagger.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

public class HttpHeadersContentLanguage   {
  
  @Schema(description = "")
  private String language = null;
  
  @Schema(description = "")
  private String script = null;
  
  @Schema(description = "")
  private String country = null;
  
  @Schema(description = "")
  private String variant = null;
  
  @Schema(description = "")
  private List<String> unicodeLocaleAttributes = null;
  
  @Schema(description = "")
  private List<String> unicodeLocaleKeys = null;
  
  @Schema(description = "")
  private String displayLanguage = null;
  
  @Schema(description = "")
  private String displayScript = null;
  
  @Schema(description = "")
  private String displayCountry = null;
  
  @Schema(description = "")
  private String displayVariant = null;
  
  @Schema(description = "")
  private String displayName = null;
  
  @Schema(description = "")
  private List<String> extensionKeys = null;
  
  @Schema(description = "")
  private String iso3Language = null;
  
  @Schema(description = "")
  private String iso3Country = null;
 /**
   * Get language
   * @return language
  **/
  @JsonProperty("language")
  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public HttpHeadersContentLanguage language(String language) {
    this.language = language;
    return this;
  }

 /**
   * Get script
   * @return script
  **/
  @JsonProperty("script")
  public String getScript() {
    return script;
  }

  public void setScript(String script) {
    this.script = script;
  }

  public HttpHeadersContentLanguage script(String script) {
    this.script = script;
    return this;
  }

 /**
   * Get country
   * @return country
  **/
  @JsonProperty("country")
  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public HttpHeadersContentLanguage country(String country) {
    this.country = country;
    return this;
  }

 /**
   * Get variant
   * @return variant
  **/
  @JsonProperty("variant")
  public String getVariant() {
    return variant;
  }

  public void setVariant(String variant) {
    this.variant = variant;
  }

  public HttpHeadersContentLanguage variant(String variant) {
    this.variant = variant;
    return this;
  }

 /**
   * Get unicodeLocaleAttributes
   * @return unicodeLocaleAttributes
  **/
  @JsonProperty("unicodeLocaleAttributes")
  public List<String> getUnicodeLocaleAttributes() {
    return unicodeLocaleAttributes;
  }

  public void setUnicodeLocaleAttributes(List<String> unicodeLocaleAttributes) {
    this.unicodeLocaleAttributes = unicodeLocaleAttributes;
  }

  public HttpHeadersContentLanguage unicodeLocaleAttributes(List<String> unicodeLocaleAttributes) {
    this.unicodeLocaleAttributes = unicodeLocaleAttributes;
    return this;
  }

  public HttpHeadersContentLanguage addUnicodeLocaleAttributesItem(String unicodeLocaleAttributesItem) {
    this.unicodeLocaleAttributes.add(unicodeLocaleAttributesItem);
    return this;
  }

 /**
   * Get unicodeLocaleKeys
   * @return unicodeLocaleKeys
  **/
  @JsonProperty("unicodeLocaleKeys")
  public List<String> getUnicodeLocaleKeys() {
    return unicodeLocaleKeys;
  }

  public void setUnicodeLocaleKeys(List<String> unicodeLocaleKeys) {
    this.unicodeLocaleKeys = unicodeLocaleKeys;
  }

  public HttpHeadersContentLanguage unicodeLocaleKeys(List<String> unicodeLocaleKeys) {
    this.unicodeLocaleKeys = unicodeLocaleKeys;
    return this;
  }

  public HttpHeadersContentLanguage addUnicodeLocaleKeysItem(String unicodeLocaleKeysItem) {
    this.unicodeLocaleKeys.add(unicodeLocaleKeysItem);
    return this;
  }

 /**
   * Get displayLanguage
   * @return displayLanguage
  **/
  @JsonProperty("displayLanguage")
  public String getDisplayLanguage() {
    return displayLanguage;
  }

  public void setDisplayLanguage(String displayLanguage) {
    this.displayLanguage = displayLanguage;
  }

  public HttpHeadersContentLanguage displayLanguage(String displayLanguage) {
    this.displayLanguage = displayLanguage;
    return this;
  }

 /**
   * Get displayScript
   * @return displayScript
  **/
  @JsonProperty("displayScript")
  public String getDisplayScript() {
    return displayScript;
  }

  public void setDisplayScript(String displayScript) {
    this.displayScript = displayScript;
  }

  public HttpHeadersContentLanguage displayScript(String displayScript) {
    this.displayScript = displayScript;
    return this;
  }

 /**
   * Get displayCountry
   * @return displayCountry
  **/
  @JsonProperty("displayCountry")
  public String getDisplayCountry() {
    return displayCountry;
  }

  public void setDisplayCountry(String displayCountry) {
    this.displayCountry = displayCountry;
  }

  public HttpHeadersContentLanguage displayCountry(String displayCountry) {
    this.displayCountry = displayCountry;
    return this;
  }

 /**
   * Get displayVariant
   * @return displayVariant
  **/
  @JsonProperty("displayVariant")
  public String getDisplayVariant() {
    return displayVariant;
  }

  public void setDisplayVariant(String displayVariant) {
    this.displayVariant = displayVariant;
  }

  public HttpHeadersContentLanguage displayVariant(String displayVariant) {
    this.displayVariant = displayVariant;
    return this;
  }

 /**
   * Get displayName
   * @return displayName
  **/
  @JsonProperty("displayName")
  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public HttpHeadersContentLanguage displayName(String displayName) {
    this.displayName = displayName;
    return this;
  }

 /**
   * Get extensionKeys
   * @return extensionKeys
  **/
  @JsonProperty("extensionKeys")
  public List<String> getExtensionKeys() {
    return extensionKeys;
  }

  public void setExtensionKeys(List<String> extensionKeys) {
    this.extensionKeys = extensionKeys;
  }

  public HttpHeadersContentLanguage extensionKeys(List<String> extensionKeys) {
    this.extensionKeys = extensionKeys;
    return this;
  }

  public HttpHeadersContentLanguage addExtensionKeysItem(String extensionKeysItem) {
    this.extensionKeys.add(extensionKeysItem);
    return this;
  }

 /**
   * Get iso3Language
   * @return iso3Language
  **/
  @JsonProperty("iso3Language")
  public String getIso3Language() {
    return iso3Language;
  }

  public void setIso3Language(String iso3Language) {
    this.iso3Language = iso3Language;
  }

  public HttpHeadersContentLanguage iso3Language(String iso3Language) {
    this.iso3Language = iso3Language;
    return this;
  }

 /**
   * Get iso3Country
   * @return iso3Country
  **/
  @JsonProperty("iso3Country")
  public String getIso3Country() {
    return iso3Country;
  }

  public void setIso3Country(String iso3Country) {
    this.iso3Country = iso3Country;
  }

  public HttpHeadersContentLanguage iso3Country(String iso3Country) {
    this.iso3Country = iso3Country;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class HttpHeadersContentLanguage {\n");
    
    sb.append("    language: ").append(toIndentedString(language)).append("\n");
    sb.append("    script: ").append(toIndentedString(script)).append("\n");
    sb.append("    country: ").append(toIndentedString(country)).append("\n");
    sb.append("    variant: ").append(toIndentedString(variant)).append("\n");
    sb.append("    unicodeLocaleAttributes: ").append(toIndentedString(unicodeLocaleAttributes)).append("\n");
    sb.append("    unicodeLocaleKeys: ").append(toIndentedString(unicodeLocaleKeys)).append("\n");
    sb.append("    displayLanguage: ").append(toIndentedString(displayLanguage)).append("\n");
    sb.append("    displayScript: ").append(toIndentedString(displayScript)).append("\n");
    sb.append("    displayCountry: ").append(toIndentedString(displayCountry)).append("\n");
    sb.append("    displayVariant: ").append(toIndentedString(displayVariant)).append("\n");
    sb.append("    displayName: ").append(toIndentedString(displayName)).append("\n");
    sb.append("    extensionKeys: ").append(toIndentedString(extensionKeys)).append("\n");
    sb.append("    iso3Language: ").append(toIndentedString(iso3Language)).append("\n");
    sb.append("    iso3Country: ").append(toIndentedString(iso3Country)).append("\n");
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
