package com.wonders.dept.workflow.process.pass.aspect;


import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.wonders.dept.workflow.process.pass.model.vo.PassMainParamVo;
import com.wonders.dept.workflow.process.pass.service.PassMainCheckService;
import com.wonders.dept.workflow.process.pass.service.PassMainService;

@Aspect
@Component("dept-passMainAspect")
public class PassMainAspect {
	private PassMainCheckService checkService;

	/**流程初始化
	 * @param joinPoint
	 * @throws Throwable
	 */
	@Before(value="execution(public * com.wonders.dept.workflow.process.pass.service.PassMainService.flowStep*(..))")
	public void beforeServiceFlowStep(JoinPoint joinPoint){
		initService(joinPoint);

		String stepCode = joinPoint.getSignature().getName();
		executeCheckMethod(stepCode);
    }
	
	
	/*
	 * 防止重复提交 及 错误人员签收
	 * */
	@Before(value="execution(public * com.wonders.dept.workflow.process.pass.service.PassMainService.flowStepRegister(..))")
	public void beforeRegister(JoinPoint joinPoint){
		try {
			Method checkMethod = checkService.getClass()
				.getMethod("checkAddSign",new Class[]{});
				checkMethod.invoke(checkService, new Object[]{});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**检查待办项是否存在
	 * @param joinPoint
	 */
	@After(value="execution(public * com.wonders.dept.workflow.process.pass.service.PassMainService.flowStepForward(..))")
	public void afterServiceForward(JoinPoint joinPoint){
		initService(joinPoint);
		checkService.checkFlowIsInProcess();
	}
	
	
	/**变量初始化
	 * @param joinPoint
	 */
	private void initService(JoinPoint joinPoint){
		Object[] args = joinPoint.getArgs();
		PassMainService target = (PassMainService) joinPoint.getTarget();
		PassMainParamVo params = (PassMainParamVo) args[0];
		//resultInfo = params.resultInfo;
		target.init(params);
		checkService.init(params);
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
	
	public PassMainCheckService getCheckService() {
		return checkService;
	}
	
	@Autowired
	public void setCheckService(@Qualifier("dept-passMainCheckService")PassMainCheckService checkService) {
		this.checkService = checkService;
	}
}
