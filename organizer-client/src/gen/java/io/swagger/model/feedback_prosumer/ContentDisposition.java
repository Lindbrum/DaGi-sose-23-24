package io.swagger.model.feedback_prosumer;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

public class ContentDisposition   {
  
  @Schema(description = "")
  private String type = null;
  
  @Schema(description = "")
  private String name = null;
  
  @Schema(description = "")
  private String filename = null;
  
  @Schema(description = "")
  private ContentDispositionCharset charset = null;
  
  @Schema(description = "")
  private Long size = null;
  
  @Schema(description = "")
  private Date creationDate = null;
  
  @Schema(description = "")
  private Date modificationDate = null;
  
  @Schema(description = "")
  private Date readDate = null;
  
  @Schema(description = "")
  private Boolean inline = null;
  
  @Schema(description = "")
  private Boolean formData = null;
  
  @Schema(description = "")
  private Boolean attachment = null;
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

  public ContentDisposition type(String type) {
    this.type = type;
    return this;
  }

 /**
   * Get name
   * @return name
  **/
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ContentDisposition name(String name) {
    this.name = name;
    return this;
  }

 /**
   * Get filename
   * @return filename
  **/
  @JsonProperty("filename")
  public String getFilename() {
    return filename;
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }

  public ContentDisposition filename(String filename) {
    this.filename = filename;
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

  public ContentDisposition charset(ContentDispositionCharset charset) {
    this.charset = charset;
    return this;
  }

 /**
   * Get size
   * @return size
  **/
  @JsonProperty("size")
  public Long getSize() {
    return size;
  }

  public void setSize(Long size) {
    this.size = size;
  }

  public ContentDisposition size(Long size) {
    this.size = size;
    return this;
  }

 /**
   * Get creationDate
   * @return creationDate
  **/
  @JsonProperty("creationDate")
  public Date getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(Date creationDate) {
    this.creationDate = creationDate;
  }

  public ContentDisposition creationDate(Date creationDate) {
    this.creationDate = creationDate;
    return this;
  }

 /**
   * Get modificationDate
   * @return modificationDate
  **/
  @JsonProperty("modificationDate")
  public Date getModificationDate() {
    return modificationDate;
  }

  public void setModificationDate(Date modificationDate) {
    this.modificationDate = modificationDate;
  }

  public ContentDisposition modificationDate(Date modificationDate) {
    this.modificationDate = modificationDate;
    return this;
  }

 /**
   * Get readDate
   * @return readDate
  **/
  @JsonProperty("readDate")
  public Date getReadDate() {
    return readDate;
  }

  public void setReadDate(Date readDate) {
    this.readDate = readDate;
  }

  public ContentDisposition readDate(Date readDate) {
    this.readDate = readDate;
    return this;
  }

 /**
   * Get inline
   * @return inline
  **/
  @JsonProperty("inline")
  public Boolean isInline() {
    return inline;
  }

  public void setInline(Boolean inline) {
    this.inline = inline;
  }

  public ContentDisposition inline(Boolean inline) {
    this.inline = inline;
    return this;
  }

 /**
   * Get formData
   * @return formData
  **/
  @JsonProperty("formData")
  public Boolean isFormData() {
    return formData;
  }

  public void setFormData(Boolean formData) {
    this.formData = formData;
  }

  public ContentDisposition formData(Boolean formData) {
    this.formData = formData;
    return this;
  }

 /**
   * Get attachment
   * @return attachment
  **/
  @JsonProperty("attachment")
  public Boolean isAttachment() {
    return attachment;
  }

  public void setAttachment(Boolean attachment) {
    this.attachment = attachment;
  }

  public ContentDisposition attachment(Boolean attachment) {
    this.attachment = attachment;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ContentDisposition {\n");
    
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    filename: ").append(toIndentedString(filename)).append("\n");
    sb.append("    charset: ").append(toIndentedString(charset)).append("\n");
    sb.append("    size: ").append(toIndentedString(size)).append("\n");
    sb.append("    creationDate: ").append(toIndentedString(creationDate)).append("\n");
    sb.append("    modificationDate: ").append(toIndentedString(modificationDate)).append("\n");
    sb.append("    readDate: ").append(toIndentedString(readDate)).append("\n");
    sb.append("    inline: ").append(toIndentedString(inline)).append("\n");
    sb.append("    formData: ").append(toIndentedString(formData)).append("\n");
    sb.append("    attachment: ").append(toIndentedString(attachment)).append("\n");
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
