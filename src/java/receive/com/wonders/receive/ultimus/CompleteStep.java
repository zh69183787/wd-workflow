package com.wonders.receive.ultimus;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="strProcessName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strUserName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nIncidentNumber" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="strStepName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strSummary" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strMemo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="varList" type="{http://www.ultimus.com}ArrayOfVariable" minOccurs="0"/>
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
    "strProcessName",
    "strUserName",
    "nIncidentNumber",
    "strStepName",
    "strSummary",
    "strMemo",
    "varList"
})
@XmlRootElement(name = "CompleteStep")
public class CompleteStep {

    protected String strProcessName;
    protected String strUserName;
    protected int nIncidentNumber;
    protected String strStepName;
    protected String strSummary;
    protected String strMemo;
    protected ArrayOfVariable varList;

    /**
     * Gets the value of the strProcessName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrProcessName() {
        return strProcessName;
    }

    /**
     * Sets the value of the strProcessName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrProcessName(String value) {
        this.strProcessName = value;
    }

    /**
     * Gets the value of the strUserName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrUserName() {
        return strUserName;
    }

    /**
     * Sets the value of the strUserName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrUserName(String value) {
        this.strUserName = value;
    }

    /**
     * Gets the value of the nIncidentNumber property.
     * 
     */
    public int getNIncidentNumber() {
        return nIncidentNumber;
    }

    /**
     * Sets the value of the nIncidentNumber property.
     * 
     */
    public void setNIncidentNumber(int value) {
        this.nIncidentNumber = value;
    }

    /**
     * Gets the value of the strStepName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrStepName() {
        return strStepName;
    }

    /**
     * Sets the value of the strStepName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrStepName(String value) {
        this.strStepName = value;
    }

    /**
     * Gets the value of the strSummary property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrSummary() {
        return strSummary;
    }

    /**
     * Sets the value of the strSummary property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrSummary(String value) {
        this.strSummary = value;
    }

    /**
     * Gets the value of the strMemo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrMemo() {
        return strMemo;
    }

    /**
     * Sets the value of the strMemo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrMemo(String value) {
        this.strMemo = value;
    }

    /**
     * Gets the value of the varList property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfVariable }
     *     
     */
    public ArrayOfVariable getVarList() {
        return varList;
    }

    /**
     * Sets the value of the varList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfVariable }
     *     
     */
    public void setVarList(ArrayOfVariable value) {
        this.varList = value;
    }

}
