/**
 * 
 */
package com.wonders.discipline.workflow.approve.service;

import java.util.List;
import java.util.Map;

import com.wonders.receive.workflow.approve.model.bo.ApprovedInfoBo;

/** 
 * @ClassName: ApprovedInfoService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-8-1 上午9:25:30 
 *  
 */
public interface ApprovedInfoService {
	public void queryByRounds(String pname,String pincident,String[] stepname);
	public void queryByDeptRounds(String pname,String pincident,String[] stepname);
	public Map<Integer, List<ApprovedInfoBo>> getAppRounds();
	public Map<Integer, Map<String, List<ApprovedInfoBo>>> getAppDeptRounds();
	public Map<Integer, List<String>> getDeptRounds();
	public Map<String, String> getDeptMap();
}
