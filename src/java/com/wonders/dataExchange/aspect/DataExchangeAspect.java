/**
 * 
 */
package com.wonders.dataExchange.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.wonders.dataExchange.model.common.vo.MainParamVo;
import com.wonders.dataExchange.service.DataExchangeCheckService;
import com.wonders.dataExchange.service.DataExchangeService;

/** 
 * @ClassName: DataExchangeAspect 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年3月13日 上午9:39:44 
 *  
 */
@Aspect
@Component("dataExchangeAspect")
public class DataExchangeAspect {
	private DataExchangeCheckService checkService;

	public DataExchangeCheckService getService() {
		return checkService;
	}
	@Autowired(required=false)
	public void setService(@Qualifier("dataExchangeCheckService")DataExchangeCheckService checkService) {
		this.checkService = checkService;
	}
	
	
	/**流程初始化
	 * @param joinPoint
	 * @throws Throwable
	 */
	@Before(value="execution(public * com.wonders.dataExchange.service.DataExchangeService.flowStep*(..))")
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
		DataExchangeService target = (DataExchangeService) joinPoint.getTarget();
		MainParamVo params = (MainParamVo) args[0];
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
}
