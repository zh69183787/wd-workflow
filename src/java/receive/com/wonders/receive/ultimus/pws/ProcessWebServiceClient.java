package com.wonders.receive.ultimus.pws;

import java.net.MalformedURLException;
import java.util.Collection;
import java.util.HashMap;

import javax.xml.namespace.QName;

import org.codehaus.xfire.XFireRuntimeException;
import org.codehaus.xfire.aegis.AegisBindingProvider;
import org.codehaus.xfire.annotations.AnnotationServiceFactory;
import org.codehaus.xfire.annotations.jsr181.Jsr181WebAnnotations;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.jaxb2.JaxbTypeRegistry;
import org.codehaus.xfire.service.Endpoint;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.soap.AbstractSoapBinding;
import org.codehaus.xfire.transport.TransportManager;

import com.wonders.util.PWSProperties;


public class ProcessWebServiceClient {

    private static XFireProxyFactory proxyFactory = new XFireProxyFactory();
    @SuppressWarnings("rawtypes")
	private HashMap endpoints = new HashMap();
    private Service service0;

    @SuppressWarnings("unchecked")
	public ProcessWebServiceClient() {
        create0();
        Endpoint ProcessWebServiceSoapEP = service0 .addEndpoint(new QName("http://www.ultimus.com", "ProcessWebServiceSoap"), new QName("http://www.ultimus.com", "ProcessWebServiceSoap"), "http://"+PWSProperties.getValueByKey("pws_server_ip")+"/PLWebServices/ProcessWebService.asmx");
        endpoints.put(new QName("http://www.ultimus.com", "ProcessWebServiceSoap"), ProcessWebServiceSoapEP);
        Endpoint ProcessWebServiceSoapLocalEndpointEP = service0 .addEndpoint(new QName("http://www.ultimus.com", "ProcessWebServiceSoapLocalEndpoint"), new QName("http://www.ultimus.com", "ProcessWebServiceSoapLocalBinding"), "xfire.local://ProcessWebService");
        endpoints.put(new QName("http://www.ultimus.com", "ProcessWebServiceSoapLocalEndpoint"), ProcessWebServiceSoapLocalEndpointEP);
    }

    public Object getEndpoint(Endpoint endpoint) {
        try {
            return proxyFactory.create((endpoint).getBinding(), (endpoint).getUrl());
        } catch (MalformedURLException e) {
            throw new XFireRuntimeException("Invalid URL", e);
        }
    }

    public Object getEndpoint(QName name) {
        Endpoint endpoint = ((Endpoint) endpoints.get((name)));
        if ((endpoint) == null) {
            throw new IllegalStateException("No such endpoint!");
        }
        return getEndpoint((endpoint));
    }

    @SuppressWarnings("rawtypes")
	public Collection getEndpoints() {
        return endpoints.values();
    }

    @SuppressWarnings({"rawtypes", "unchecked" })
	private void create0() {
        TransportManager tm = (org.codehaus.xfire.XFireFactory.newInstance().getXFire().getTransportManager());
        HashMap props = new HashMap();
        props.put("annotations.allow.interface", true);
        AnnotationServiceFactory asf = new AnnotationServiceFactory(new Jsr181WebAnnotations(), tm, new AegisBindingProvider(new JaxbTypeRegistry()));
        asf.setBindingCreationEnabled(false);
        service0 = asf.create((ProcessWebServiceSoap.class), props);
        {
            @SuppressWarnings("unused")
			AbstractSoapBinding soapBinding = asf.createSoap11Binding(service0, new QName("http://www.ultimus.com", "ProcessWebServiceSoap"), "http://schemas.xmlsoap.org/soap/http");
        }
        {
            @SuppressWarnings("unused")
			AbstractSoapBinding soapBinding = asf.createSoap11Binding(service0, new QName("http://www.ultimus.com", "ProcessWebServiceSoapLocalBinding"), "urn:xfire:transport:local");
        }
    }

    public ProcessWebServiceSoap getProcessWebServiceSoap() {
        return ((ProcessWebServiceSoap)(this).getEndpoint(new QName("http://www.ultimus.com", "ProcessWebServiceSoap")));
    }

    public ProcessWebServiceSoap getProcessWebServiceSoap(String url) {
        ProcessWebServiceSoap var = getProcessWebServiceSoap();
        org.codehaus.xfire.client.Client.getInstance(var).setUrl(url);
        return var;
    }

    public ProcessWebServiceSoap getProcessWebServiceSoapLocalEndpoint() {
        return ((ProcessWebServiceSoap)(this).getEndpoint(new QName("http://www.ultimus.com", "ProcessWebServiceSoapLocalEndpoint")));
    }

    public ProcessWebServiceSoap getProcessWebServiceSoapLocalEndpoint(String url) {
        ProcessWebServiceSoap var = getProcessWebServiceSoapLocalEndpoint();
        org.codehaus.xfire.client.Client.getInstance(var).setUrl(url);
        return var;
    }

    public static void main(String[] args) {
    	/*
    	System.out.println("java.endorsed.dirs===="+System.getProperty("java.endorsed.dirs")); 
    	ProcessWebServiceClient client = new ProcessWebServiceClient();
        
		//create a default service endpoint
        ProcessWebServiceSoap service = client.getProcessWebServiceSoap();
        
        ArrayOfVariable varList = new ArrayOfVariable();
        Variable val = new Variable();
        val.setStrVariableName("发文字号2");
        ArrayOfAnyType value = new ArrayOfAnyType();
        value.getAnyType().add("陆luyj6222");
        val.setObjVariableValue(value);
        varList.getVariable().add(val);
        int nIncidentNo=0;
        Holder nIncidentNo2 = new Holder();
        Holder strError = new Holder();
        String strProcessName = "合同审批流程";
        String strUserName = "ST/G00100000038";
        String strSummary = "陆luyj11";
        
        boolean r = service.launchIncident(strProcessName, strUserName, strSummary, varList, nIncidentNo, nIncidentNo2, strError);
        
        		//
        		//service.yourServiceOperationHere();
        System.out.println("result==="+r);
		System.out.println("test client completed");
        		System.exit(0);
        */
    }

}
