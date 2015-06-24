package com.wonders.contact.deptSubProcess.aspect;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.wonders.contact.common.action.AbstractParamAction;
import com.wonders.contact.common.model.vo.ParamVo;
import com.wonders.contact.common.util.SimpleLogger;
import com.wonders.contact.deptSubProcess.action.DeptSubProcessAction;
import com.wonders.contact.deptSubProcess.model.vo.DeptSubProcessParamVo;
import com.wonders.contact.deptSubProcess.service.DeptSubProcessCheckService;
import com.wonders.contact.deptSubProcess.service.DeptSubProcessService;
import com.wonders.contact.deptSubProcess.util.DeptSubProcessParamsUtil;

/**部门内部子流程AOP
 * @author XFZ
 * @version 1.0 2012-6-12
 */
@Aspect
@Component("contact-deptSubProcessAspect")
public class DeptSubProcessAspect {
	private DeptSubProcessCheckService checkService;
	SimpleLogger log = new SimpleLogger(this.getClass());
	private DeptSubProcessParamsUtil paramsUtil = new DeptSubProcessParamsUtil();
	
	/**DeptSubProcessAction初始化
	 * @param joinPoint
	 */
	@Before(value="execution(public * com.wonders.contact.deptSubProcess.action.DeptSubProcessAction.step*(..))")
	public void beforeActionStep(JoinPoint joinPoint){
		DeptSubProcessAction target = (DeptSubProcessAction) joinPoint.getTarget();
		String stepCode = joinPoint.getSignature().getName();
		executePrepareMethod(stepCode,target);
	}
	
	/**DeptSubProcessService流程初始化
	 * @param joinPoint
	 * @throws Throwable
	 */
	@Before(value="execution(public * com.wonders.contact.deptSubProcess.service.DeptSubProcessService.flowStep*(..))")
	public void beforeServiceFlowStep(JoinPoint joinPoint){
		initService(joinPoint);
		String stepCode = joinPoint.getSignature().getName();
		executeCheckMethod(stepCode);
	}
		
	
	/**DeptSubProcessService变量初始化
	 * @param joinPoint
	 */
	private void initService(JoinPoint joinPoint){
		Object[] args = joinPoint.getArgs();
		DeptSubProcessService target = (DeptSubProcessService) joinPoint.getTarget();
		DeptSubProcessParamVo params = (DeptSubProcessParamVo) args[0];
		//resultInfo = params.resultInfo;
		target.init(params);
		checkService.init(params);
	}
	
	/**执行对应prepare方法
	 * @param stepCode
	 * @param target
	 */
	private void executePrepareMethod(String stepCode,AbstractParamAction target){
		if(stepCode.length()==0) return;
		
		stepCode = stepCode.substring("step".length());
//log.debug("prepare:"+stepCode);
		
		try {
			Method prepareMethod = paramsUtil.getClass()
				.getMethod("prepare"+stepCode,new Class[]{HttpServletRequest.class,ParamVo.class});
				prepareMethod.invoke(paramsUtil, new Object[]{target.request,target.getParams()});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**执行对应check方法
	 * 
	 */
	private void executeCheckMethod(String stepCode){
		if(stepCode.length()==0) return;
		
		stepCode = stepCode.substring("stepFlow".length());
//log.debug("check:"+stepCode);
		
		try {
			Method checkMethod = checkService.getClass()
				.getMethod("check"+stepCode,new Class[]{});
				checkMethod.invoke(checkService, new Object[]{});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	public DeptSubProcessCheckService getCheckService() {
		return checkService;
	}

	@Autowired
	public void setCheckService(@Qualifier(value="contact-deptSubProcessCheckService")DeptSubProcessCheckService checkService) {
		this.checkService = checkService;
	}
}
