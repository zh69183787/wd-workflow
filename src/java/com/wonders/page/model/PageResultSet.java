/**
 * 
 */
package com.wonders.page.model;

/** 
 * @ClassName: PageResultSet 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun
 * @date 2012-3-11 下午12:44:37 
 *  
 */
import java.util.List;
/**
 *该类描述了一个分页数据集 list中是查询的数据集合 ,pageInfo则描述了附加的页相关的信息
*/
public class PageResultSet<T> {

	private List<T> list;  //当前页的数据信息
	private PageInfo pageInfo; //当前页的信息
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}

	public PageInfo getPageInfo() {
		return pageInfo;
	}
	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
}

