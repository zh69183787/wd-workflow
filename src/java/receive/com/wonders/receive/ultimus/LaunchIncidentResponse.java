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
 *         &lt;element name="LaunchIncidentResult" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="nIncidentNo" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "launchIncidentResult",
    "nIncidentNo",
    "strError"
})
@XmlRootElement(name = "LaunchIncidentResponse")
public class LaunchIncidentResponse {

    @XmlElement(name = "LaunchIncidentResult")
    protected boolean launchIncidentResult;
    protected int nIncidentNo;
    protected String strError;

    /**
     * Gets the value of the launchIncidentResult property.
     * 
     */
    public boolean isLaunchIncidentResult() {
        return launchIncidentResult;
    }

    /**
     * Sets the value of the launchIncidentResult property.
     * 
     */
    public void setLaunchIncidentResult(boolean value) {
        this.launchIncidentResult = value;
    }

    /**
     * Gets the value of the nIncidentNo property.
     * 
     */
    public int getNIncidentNo() {
        return nIncidentNo;
    }

    /**
     * Sets the value of the nIncidentNo property.
     * 
     */
    public void setNIncidentNo(int value) {
        this.nIncidentNo = value;
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
