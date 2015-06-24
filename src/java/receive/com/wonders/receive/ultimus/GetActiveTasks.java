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
    "strUserName"
})
@XmlRootElement(name = "GetActiveTasks")
public class GetActiveTasks {

    protected String strProcessName;
    protected String strUserName;

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

}
