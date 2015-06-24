package com.wonders.project.workflow.process.discard.aspect;


import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.wonders.project.workflow.process.discard.model.vo.DiscardMainParamVo;
import com.wonders.project.workflow.process.discard.service.DiscardMainCheckService;
import com.wonders.project.workflow.process.discard.service.DiscardMainService;

@Aspect
@Component("project-discardMainAspect")
public class DiscardMainAspect {
	private DiscardMainCheckService checkService;
	
	
	/**流程初始化
	 * @param joinPoint
	 * @throws Throwable
	 */
	@Before(value="execution(public * com.wonders.project.workflow.process.discard.service.DiscardMainService.flowStep*(..))")
	public void beforeServiceFlowStep(JoinPoint joinPoint){
		initService(joinPoint);

		String stepCode = joinPoint.getSignature().getName();
		executeCheckMethod(stepCode);
    }
	
	/**检查待办项是否存在
	 * @param joinPoint
	 */
	@After(value="execution(public * com.wonders.project.workflow.process.discard.service.DiscardMainService.flowStepForward(..))")
	public void afterServiceForward(JoinPoint joinPoint){
		initService(joinPoint);
		checkService.checkFlowIsInProcess();
	}
	
	
	/**变量初始化
	 * @param joinPoint
	 */
	private void initService(JoinPoint joinPoint){
		Object[] args = joinPoint.getArgs();
		DiscardMainService target = (DiscardMainService) joinPoint.getTarget();
		DiscardMainParamVo params = (DiscardMainParamVo) args[0];
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
	
	public DiscardMainCheckService getCheckService() {
		return checkService;
	}
	
	@Autowired
	public void setCheckService(@Qualifier("project-discardMainCheckService")DiscardMainCheckService checkService) {
		this.checkService = checkService;
	}
}
