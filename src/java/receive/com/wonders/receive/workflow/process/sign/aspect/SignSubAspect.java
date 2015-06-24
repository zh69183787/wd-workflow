package com.wonders.receive.workflow.process.sign.aspect;


import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.wonders.receive.workflow.process.sign.model.vo.SignSubParamVo;
import com.wonders.receive.workflow.process.sign.service.SignSubCheckService;
import com.wonders.receive.workflow.process.sign.service.SignSubService;

@Aspect
@Component("receive-signSubAspect")
public class SignSubAspect {
	private SignSubCheckService checkService;
	
	
	/**流程初始化
	 * @param joinPoint
	 * @throws Throwable
	 */
	@Before(value="execution(public * com.wonders.receive.workflow.process.sign.service.SignSubService.flowStep*(..))")
	public void beforeServiceFlowStep(JoinPoint joinPoint){
		initService(joinPoint);

		String stepCode = joinPoint.getSignature().getName();
		executeCheckMethod(stepCode);
    }
	
	
	/**变量初始化
	 * @param joinPoint
	 */
	private void initService(JoinPoint joinPoint){
		Object[] args = joinPoint.getArgs();
		SignSubService target = (SignSubService) joinPoint.getTarget();
		SignSubParamVo params = (SignSubParamVo) args[0];
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
	
	public SignSubCheckService getCheckService() {
		return checkService;
	}
	
	@Autowired
	public void setCheckService(@Qualifier("receive-signSubCheckService")SignSubCheckService checkService) {
		this.checkService = checkService;
	}
}
