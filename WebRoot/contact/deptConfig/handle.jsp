<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<div id="handleZone" title="handle">
   <p class="validateTips">Config user</p>

	<form id="handleForm">
	<input type="hidden" name="flag" id="flag"/>
	<input type="hidden" name="configDeptName" id="configDeptName"/>
	<fieldset>
		<label>ConfigId</label>
		<input type="text" name="configId" id="configId"/>
		<label>Dept</label>
		<select name="configDeptId" id="configDeptId">
		</select>
		<label>LoginName</label>
		<input type="text" id="configLoginName" name="configLoginName"/>
		<label>UserName</label>
		<input type="text" id="configUserName" name="configUserName"/>
	</fieldset>
	</form>
</div>

    