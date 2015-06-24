package com.wonders.receive.workflow.process.redv.aspect;


import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.wonders.receive.workflow.process.redv.model.vo.RedvMainParamVo;
import com.wonders.receive.workflow.process.redv.service.RedvMainCheckService;
import com.wonders.receive.workflow.process.redv.service.RedvMainService;

@Aspect
@Component("receive-redvMainAspect")
public class RedvMainAspect {
	private RedvMainCheckService checkService;
	
	
	/**流程初始化
	 * @param joinPoint
	 * @throws Throwable
	 */
	@Before(value="execution(public * com.wonders.receive.workflow.process.redv.service.RedvMainService.flowStep*(..))")
	public void beforeServiceFlowStep(JoinPoint joinPoint){
		initService(joinPoint);

		String stepCode = joinPoint.getSignature().getName();
		executeCheckMethod(stepCode);
    }
	
	/**检查待办项是否存在
	 * @param joinPoint
	 */
	@After(value="execution(public * com.wonders.receive.workflow.process.redv.service.RedvMainService.flowStepForward(..))")
	public void afterServiceForward(JoinPoint joinPoint){
		initService(joinPoint);
		checkService.checkFlowIsInProcess();
	}
	
	
	/**变量初始化
	 * @param joinPoint
	 */
	private void initService(JoinPoint joinPoint){
		Object[] args = joinPoint.getArgs();
		RedvMainService target = (RedvMainService) joinPoint.getTarget();
		RedvMainParamVo params = (RedvMainParamVo) args[0];
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
	
	public RedvMainCheckService getCheckService() {
		return checkService;
	}
	
	@Autowired
	public void setCheckService(@Qualifier("receive-redvMainCheckService")RedvMainCheckService checkService) {
		this.checkService = checkService;
	}
}
