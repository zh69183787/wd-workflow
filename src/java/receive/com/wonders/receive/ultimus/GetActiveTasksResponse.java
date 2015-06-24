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
 *         &lt;element name="GetActiveTasksResult" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="taskList" type="{http://www.ultimus.com}ArrayOfCTaskInfo" minOccurs="0"/>
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
    "getActiveTasksResult",
    "taskList",
    "strError"
})
@XmlRootElement(name = "GetActiveTasksResponse")
public class GetActiveTasksResponse {

    @XmlElement(name = "GetActiveTasksResult")
    protected boolean getActiveTasksResult;
    protected ArrayOfCTaskInfo taskList;
    protected String strError;

    /**
     * Gets the value of the getActiveTasksResult property.
     * 
     */
    public boolean isGetActiveTasksResult() {
        return getActiveTasksResult;
    }

    /**
     * Sets the value of the getActiveTasksResult property.
     * 
     */
    public void setGetActiveTasksResult(boolean value) {
        this.getActiveTasksResult = value;
    }

    /**
     * Gets the value of the taskList property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfCTaskInfo }
     *     
     */
    public ArrayOfCTaskInfo getTaskList() {
        return taskList;
    }

    /**
     * Sets the value of the taskList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfCTaskInfo }
     *     
     */
    public void setTaskList(ArrayOfCTaskInfo value) {
        this.taskList = value;
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
