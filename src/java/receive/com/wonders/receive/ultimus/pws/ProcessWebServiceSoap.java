package com.wonders.receive.ultimus.pws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.Holder;

import com.wonders.receive.ultimus.ArrayOfCTaskInfo;
import com.wonders.receive.ultimus.ArrayOfVariable;


@WebService(name = "ProcessWebServiceSoap", targetNamespace = "http://www.ultimus.com")
@SOAPBinding(use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface ProcessWebServiceSoap {

	@WebMethod(operationName = "GetActiveTasks", action = "http://www.ultimus.com/GetActiveTasks")
	@WebResult(name = "GetActiveTasksResult", targetNamespace = "http://www.ultimus.com")
	public boolean getActiveTasks(
			@WebParam(name = "strProcessName", targetNamespace = "http://www.ultimus.com")
			String strProcessName,
			@WebParam(name = "strUserName", targetNamespace = "http://www.ultimus.com")
			String strUserName,
			@WebParam(name = "taskList", targetNamespace = "http://www.ultimus.com", mode = WebParam.Mode.OUT)
			Holder<ArrayOfCTaskInfo> taskList,
			@WebParam(name = "strError", targetNamespace = "http://www.ultimus.com", mode = WebParam.Mode.OUT)
			Holder<String> strError);

	@WebMethod(operationName = "ReturnStep", action = "http://www.ultimus.com/ReturnStep")
	@WebResult(name = "ReturnStepResult", targetNamespace = "http://www.ultimus.com")
	public boolean returnStep(
			@WebParam(name = "strProcessName", targetNamespace = "http://www.ultimus.com")
			String strProcessName,
			@WebParam(name = "strUserName", targetNamespace = "http://www.ultimus.com")
			String strUserName,
			@WebParam(name = "nIncidentNumber", targetNamespace = "http://www.ultimus.com")
			int nIncidentNumber,
			@WebParam(name = "strStepName", targetNamespace = "http://www.ultimus.com")
			String strStepName,
			@WebParam(name = "strSummary", targetNamespace = "http://www.ultimus.com")
			String strSummary,
			@WebParam(name = "strMemo", targetNamespace = "http://www.ultimus.com")
			String strMemo,
			@WebParam(name = "varList", targetNamespace = "http://www.ultimus.com")
			ArrayOfVariable varList,
			@WebParam(name = "strError", targetNamespace = "http://www.ultimus.com", mode = WebParam.Mode.OUT)
			Holder<String> strError);

	@WebMethod(operationName = "CompleteStep", action = "http://www.ultimus.com/CompleteStep")
	@WebResult(name = "CompleteStepResult", targetNamespace = "http://www.ultimus.com")
	public boolean completeStep(
			@WebParam(name = "strProcessName", targetNamespace = "http://www.ultimus.com")
			String strProcessName,
			@WebParam(name = "strUserName", targetNamespace = "http://www.ultimus.com")
			String strUserName,
			@WebParam(name = "nIncidentNumber", targetNamespace = "http://www.ultimus.com")
			int nIncidentNumber,
			@WebParam(name = "strStepName", targetNamespace = "http://www.ultimus.com")
			String strStepName,
			@WebParam(name = "strSummary", targetNamespace = "http://www.ultimus.com")
			String strSummary,
			@WebParam(name = "strMemo", targetNamespace = "http://www.ultimus.com")
			String strMemo,
			@WebParam(name = "varList", targetNamespace = "http://www.ultimus.com")
			ArrayOfVariable varList,
			@WebParam(name = "strError", targetNamespace = "http://www.ultimus.com", mode = WebParam.Mode.OUT)
			Holder<String> strError);

	@WebMethod(operationName = "GetLaunchInformation", action = "http://www.ultimus.com/GetLaunchInformation")
	@WebResult(name = "GetLaunchInformationResult", targetNamespace = "http://www.ultimus.com")
	public boolean getLaunchInformation(
			@WebParam(name = "strProcessName", targetNamespace = "http://www.ultimus.com")
			String strProcessName,
			@WebParam(name = "strUserName", targetNamespace = "http://www.ultimus.com")
			String strUserName,
			@WebParam(name = "varList", targetNamespace = "http://www.ultimus.com", mode = WebParam.Mode.OUT)
			Holder<ArrayOfVariable> varList,
			@WebParam(name = "strError", targetNamespace = "http://www.ultimus.com", mode = WebParam.Mode.OUT)
			Holder<String> strError);

	@WebMethod(operationName = "LaunchIncident", action = "http://www.ultimus.com/LaunchIncident")
	@WebResult(name = "LaunchIncidentResult", targetNamespace = "http://www.ultimus.com")
	public boolean launchIncident(
			@WebParam(name = "strProcessName", targetNamespace = "http://www.ultimus.com")
			String strProcessName,
			@WebParam(name = "strUserName", targetNamespace = "http://www.ultimus.com")
			String strUserName,
			@WebParam(name = "strSummary", targetNamespace = "http://www.ultimus.com")
			String strSummary,
			@WebParam(name = "varList", targetNamespace = "http://www.ultimus.com")
			ArrayOfVariable varList,
			@WebParam(name = "nIncidentNo", targetNamespace = "http://www.ultimus.com")
			int nIncidentNo,
			@WebParam(name = "nIncidentNo", targetNamespace = "http://www.ultimus.com", mode = WebParam.Mode.OUT)
			Holder<Integer> nIncidentNo2,
			@WebParam(name = "strError", targetNamespace = "http://www.ultimus.com", mode = WebParam.Mode.OUT)
			Holder<String> strError);

	@WebMethod(operationName = "GetTaskInformation", action = "http://www.ultimus.com/GetTaskInformation")
	@WebResult(name = "GetTaskInformationResult", targetNamespace = "http://www.ultimus.com")
	public boolean getTaskInformation(
			@WebParam(name = "strProcessName", targetNamespace = "http://www.ultimus.com")
			String strProcessName,
			@WebParam(name = "strUserName", targetNamespace = "http://www.ultimus.com")
			String strUserName,
			@WebParam(name = "nIncidentNumber", targetNamespace = "http://www.ultimus.com")
			int nIncidentNumber,
			@WebParam(name = "strStepName", targetNamespace = "http://www.ultimus.com")
			String strStepName,
			@WebParam(name = "varList", targetNamespace = "http://www.ultimus.com", mode = WebParam.Mode.OUT)
			Holder<ArrayOfVariable> varList,
			@WebParam(name = "strError", targetNamespace = "http://www.ultimus.com", mode = WebParam.Mode.OUT)
			Holder<String> strError);

	@WebMethod(operationName = "AbortIncident", action = "http://www.ultimus.com/AbortIncident")
	@WebResult(name = "AbortIncidentResult", targetNamespace = "http://www.ultimus.com")
	public boolean abortIncident(
			@WebParam(name = "strProcessName", targetNamespace = "http://www.ultimus.com")
			String strProcessName,
			@WebParam(name = "strUserName", targetNamespace = "http://www.ultimus.com")
			String strUserName,
			@WebParam(name = "nIncidentNumber", targetNamespace = "http://www.ultimus.com")
			int nIncidentNumber,
			@WebParam(name = "strSummary", targetNamespace = "http://www.ultimus.com")
			String strSummary,
			@WebParam(name = "strError", targetNamespace = "http://www.ultimus.com", mode = WebParam.Mode.OUT)
			Holder<String> strError);

}
