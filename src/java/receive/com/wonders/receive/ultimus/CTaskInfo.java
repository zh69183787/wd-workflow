package com.wonders.receive.ultimus;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CTaskInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CTaskInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="m_nIncidentNum" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="m_strStepLabel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="m_strSummary" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CTaskInfo", propOrder = {
    "mnIncidentNum",
    "mStrStepLabel",
    "mStrSummary"
})
public class CTaskInfo {

    @XmlElement(name = "m_nIncidentNum")
    protected int mnIncidentNum;
    @XmlElement(name = "m_strStepLabel")
    protected String mStrStepLabel;
    @XmlElement(name = "m_strSummary")
    protected String mStrSummary;

    /**
     * Gets the value of the mnIncidentNum property.
     * 
     */
    public int getMNIncidentNum() {
        return mnIncidentNum;
    }

    /**
     * Sets the value of the mnIncidentNum property.
     * 
     */
    public void setMNIncidentNum(int value) {
        this.mnIncidentNum = value;
    }

    /**
     * Gets the value of the mStrStepLabel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMStrStepLabel() {
        return mStrStepLabel;
    }

    /**
     * Sets the value of the mStrStepLabel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMStrStepLabel(String value) {
        this.mStrStepLabel = value;
    }

    /**
     * Gets the value of the mStrSummary property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMStrSummary() {
        return mStrSummary;
    }

    /**
     * Sets the value of the mStrSummary property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMStrSummary(String value) {
        this.mStrSummary = value;
    }

}
