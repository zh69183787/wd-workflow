package com.wonders.receive.ultimus;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AbortIncidentResult" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="strError" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "abortIncidentResult",
    "strError"
})
@XmlRootElement(name = "AbortIncidentResponse")
public class AbortIncidentResponse {

    @XmlElement(name = "AbortIncidentResult")
    protected boolean abortIncidentResult;
    protected String strError;

    /**
     * Gets the value of the abortIncidentResult property.
     * 
     */
    public boolean isAbortIncidentResult() {
        return abortIncidentResult;
    }

    /**
     * Sets the value of the abortIncidentResult property.
     * 
     */
    public void setAbortIncidentResult(boolean value) {
        this.abortIncidentResult = value;
    }

    /**
     * Gets the value of the strError property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrError() {
        return strError;
    }

    /**
     * Sets the value of the strError property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrError(String value) {
        this.strError = value;
    }

}
