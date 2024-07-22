
package it.univaq.sose.dagi.wsdltypes;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="userId" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="eventId" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="rating" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="body" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "userId",
    "eventId",
    "rating",
    "body"
})
@XmlRootElement(name = "createFeedbackRequest")
public class CreateFeedbackRequest {

    protected long userId;
    protected long eventId;
    protected int rating;
    @XmlElement(required = true)
    protected String body;

    /**
     * Recupera il valore della proprietà userId.
     * 
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Imposta il valore della proprietà userId.
     * 
     */
    public void setUserId(long value) {
        this.userId = value;
    }

    /**
     * Recupera il valore della proprietà eventId.
     * 
     */
    public long getEventId() {
        return eventId;
    }

    /**
     * Imposta il valore della proprietà eventId.
     * 
     */
    public void setEventId(long value) {
        this.eventId = value;
    }

    /**
     * Recupera il valore della proprietà rating.
     * 
     */
    public int getRating() {
        return rating;
    }

    /**
     * Imposta il valore della proprietà rating.
     * 
     */
    public void setRating(int value) {
        this.rating = value;
    }

    /**
     * Recupera il valore della proprietà body.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBody() {
        return body;
    }

    /**
     * Imposta il valore della proprietà body.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBody(String value) {
        this.body = value;
    }

}
