package com.wonders.contact.deptContact.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.wonders.contact.common.service.CommonService;
import com.wonders.contact.common.util.SimpleLogger;
import com.wonders.contact.deptContact.constant.DeptContactConstants;
import com.wonders.contact.deptContact.dao.DeptContactDao;
import com.wonders.contact.deptContact.model.bo.TDeptContactMain;
import com.wonders.contact.deptContact.model.bo.TDeptContactTree;

@Repository("contact-deptContactDao")
public class DeptContactDaoImpl implements DeptContactDao {

	SimpleLogger log = new SimpleLogger(this.getClass());
	
	private CommonService service;
	
	@SuppressWarnings("unchecked")
	@Override
	public TDeptContactMain getMainBo(String pname,String pincident){
		String hql = "from TDeptContactMain t where t.processname=? and t.incidentno=? and t.removed!=1";
		TDeptContactMain mainBo = new TDeptContactMain();
		
		List<TDeptContactMain> list = service.ListByHql(hql,new Object[]{pname,pincident});
		
		if(list.size()==0){
			log.warn("找不到符合条件的多级工作联系单！");
			return null;
		}else{

			mainBo = list.get(0);
		}
		
		return mainBo;
	}
	
	@Override
	public TDeptContactMain getMainBo(String Id){
		
		TDeptContactMain mainBo = null;
		
		if(Id.length()==0){
			log.warn("找不到符合条件的多级工作联系单！");
			return null;
		}
		
		mainBo = (TDeptContactMain) service.load(Id, TDeptContactMain.class);
		
		return mainBo;
	}
	
	/**根据流程名称实例号获得treeBo
	 * @param cname
	 * @param cincident
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public TDeptContactTree getTreeBo(String cname,String cincident){
		String hql = "from TDeptContactMain t where t.cname=? and t.cincident=? and t.removed!=1";
		TDeptContactTree treeBo = new TDeptContactTree();
		
		List<TDeptContactTree> list = service.ListByHql(hql,new Object[]{cname,cincident});
		
		if(list.size()==0){
			log.warn("找不到符合条件的多级工作联系单！");
			return null;
		}else{

			treeBo = list.get(0);
		}
		
		return treeBo;
	}
	
	@Override
	public TDeptContactTree getTreeBo(String key){
		TDeptContactTree treeBo = null;
		
		if(key.length()==0){
			log.warn("找不到符合的流程关联信息！");
			return null;
		}
		
		treeBo = (TDeptContactTree) service.load(key, TDeptContactTree.class);
		
		return treeBo;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public TDeptContactTree getTreeBoByMainBoId(String mainBoId){
		String hql = "from TDeptContactTree t where t.CId=? and t.removed!=1";
		TDeptContactTree treeBo = new TDeptContactTree();
		
		List<TDeptContactTree> list = service.ListByHql(hql,new Object[]{mainBoId});
		
		if(list.size()==0){
			log.warn("找不到符合条件的多级工作联系单tree信息！");
			return null;
		}else{
			treeBo = list.get(0);
		}
		
		return treeBo;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public TDeptContactTree getTreeBoByCInfo(String cname,String cincident){
		String hql = "from TDeptContactTree t where t.cname=? and t.cincident=? and t.removed!=1";
		TDeptContactTree treeBo = new TDeptContactTree();
		
		List<TDeptContactTree> list = service.ListByHql(hql,new Object[]{cname,cincident});
		
		if(list.size()==0){
			log.warn("找不到符合条件的多级工作联系单tree信息！");
			return null;
		}else{
			treeBo = list.get(0);
		}
		
		return treeBo;
	}
	
	public TDeptContactMain getParentMainBoByKey(String key){
		TDeptContactTree tree = this.getTreeBo(key);
		TDeptContactMain mainBo = null;
		if(tree!=null){
			String id = "";
			if(tree.getType()==DeptContactConstants.STATUS_MAIN){
				log.debug("主流程");
				id = tree.getCId();
			}else{
				id = tree.getPId();
			}
			log.debug("通过关联得到mainBo主键Id="+id);
			mainBo = this.getMainBo(id);
		}
		return mainBo;
	}
	
	public TDeptContactMain getSelfMainBoByKey(String key){
		TDeptContactTree tree = this.getTreeBo(key);
		TDeptContactMain mainBo = null;
		if(tree!=null){
			String id = tree.getCId();
			if(tree.getType()==DeptContactConstants.STATUS_MAIN){
				log.debug("主流程");
			}
			log.debug("通过关联得到mainBo主键Id="+id);
			mainBo = this.getMainBo(id);
		}
		return mainBo;
	}
	
	
	@Override
	public CommonService getService() {
		return service;
	}
	
	@Autowired(required=false)
	public void setService(@Qualifier(value="contact-commonService")CommonService service) {
		this.service = service;
	}
}
