package com.wonders.receive.ultimus;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Variable complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Variable">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.ultimus.com}MarshalByRefObject">
 *       &lt;sequence>
 *         &lt;element name="strVariableName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="objVariableValue" type="{http://www.ultimus.com}ArrayOfAnyType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Variable", propOrder = {
    "strVariableName",
    "objVariableValue"
})
public class Variable
    extends MarshalByRefObject
{

    protected String strVariableName;
    protected ArrayOfAnyType objVariableValue;

    /**
     * Gets the value of the strVariableName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrVariableName() {
        return strVariableName;
    }

    /**
     * Sets the value of the strVariableName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrVariableName(String value) {
        this.strVariableName = value;
    }

    /**
     * Gets the value of the objVariableValue property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfAnyType }
     *     
     */
    public ArrayOfAnyType getObjVariableValue() {
        return objVariableValue;
    }

    /**
     * Sets the value of the objVariableValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfAnyType }
     *     
     */
    public void setObjVariableValue(ArrayOfAnyType value) {
        this.objVariableValue = value;
    }

}
