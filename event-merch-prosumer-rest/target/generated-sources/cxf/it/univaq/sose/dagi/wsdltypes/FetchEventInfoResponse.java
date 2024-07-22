
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
 *         &lt;element name="eventData" type="{http://wsdltypes.dagi.sose.univaq.it/}eventData"/&gt;
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
    "eventData"
})
@XmlRootElement(name = "fetchEventInfoResponse")
public class FetchEventInfoResponse {

    @XmlElement(required = true)
    protected EventData eventData;

    /**
     * Recupera il valore della proprietà eventData.
     * 
     * @return
     *     possible object is
     *     {@link EventData }
     *     
     */
    public EventData getEventData() {
        return eventData;
    }

    /**
     * Imposta il valore della proprietà eventData.
     * 
     * @param value
     *     allowed object is
     *     {@link EventData }
     *     
     */
    public void setEventData(EventData value) {
        this.eventData = value;
    }

}
