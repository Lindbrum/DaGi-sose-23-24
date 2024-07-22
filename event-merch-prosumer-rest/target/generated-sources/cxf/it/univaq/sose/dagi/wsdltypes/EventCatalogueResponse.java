
package it.univaq.sose.dagi.wsdltypes;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="eventList"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="eventData" type="{http://wsdltypes.dagi.sose.univaq.it/}eventData" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
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
    "eventList"
})
@XmlRootElement(name = "eventCatalogueResponse")
public class EventCatalogueResponse {

    @XmlElement(required = true)
    protected EventCatalogueResponse.EventList eventList;

    /**
     * Recupera il valore della proprietà eventList.
     * 
     * @return
     *     possible object is
     *     {@link EventCatalogueResponse.EventList }
     *     
     */
    public EventCatalogueResponse.EventList getEventList() {
        return eventList;
    }

    /**
     * Imposta il valore della proprietà eventList.
     * 
     * @param value
     *     allowed object is
     *     {@link EventCatalogueResponse.EventList }
     *     
     */
    public void setEventList(EventCatalogueResponse.EventList value) {
        this.eventList = value;
    }


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
     *         &lt;element name="eventData" type="{http://wsdltypes.dagi.sose.univaq.it/}eventData" maxOccurs="unbounded" minOccurs="0"/&gt;
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
    public static class EventList {

        protected List<EventData> eventData;

        /**
         * Gets the value of the eventData property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the Jakarta XML Binding object.
         * This is why there is not a <CODE>set</CODE> method for the eventData property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getEventData().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link EventData }
         * 
         * 
         */
        public List<EventData> getEventData() {
            if (eventData == null) {
                eventData = new ArrayList<EventData>();
            }
            return this.eventData;
        }

    }

}
