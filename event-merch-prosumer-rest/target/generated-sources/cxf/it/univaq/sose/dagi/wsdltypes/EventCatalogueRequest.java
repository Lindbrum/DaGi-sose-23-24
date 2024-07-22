
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
 *         &lt;element name="page" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="sortBy" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
    "page",
    "sortBy"
})
@XmlRootElement(name = "eventCatalogueRequest")
public class EventCatalogueRequest {

    protected int page;
    @XmlElement(required = true)
    protected String sortBy;

    /**
     * Recupera il valore della proprietà page.
     * 
     */
    public int getPage() {
        return page;
    }

    /**
     * Imposta il valore della proprietà page.
     * 
     */
    public void setPage(int value) {
        this.page = value;
    }

    /**
     * Recupera il valore della proprietà sortBy.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSortBy() {
        return sortBy;
    }

    /**
     * Imposta il valore della proprietà sortBy.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSortBy(String value) {
        this.sortBy = value;
    }

}
