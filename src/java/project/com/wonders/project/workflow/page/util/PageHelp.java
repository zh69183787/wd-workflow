package com.wonders.project.workflow.page.util;

import javax.servlet.http.HttpServletRequest;

public class PageHelp {
	public static Page getPager(HttpServletRequest request, int totalRows) {

		Page pager = new Page(totalRows);

		String currentPage = request.getParameter("currentPage");
		int cPageNo = 1;
		try{
			cPageNo = Integer.parseInt(currentPage);
			if(cPageNo<=0) cPageNo=1;
		}catch(Exception e){}
		pager.refresh(cPageNo);
		
		String pagerMethod = request.getParameter("pageMethod");

		if (pagerMethod != null) {
			if (pagerMethod.equals("first")) {
				pager.first();
			} else if (pagerMethod.equals("previous")) {
				pager.previous();
			} else if (pagerMethod.equals("next")) {
				pager.next();
			} else if (pagerMethod.equals("last")) {
				pager.last();
			} 
			/*else if (pagerMethod.equals("go")) {
				String pageCnt = request.getParameter("pageCnt");
				int pageNo = 1;
				try{
					pageNo = Integer.parseInt(pageCnt);
					if(pageNo<=0) pageNo=1;
				}catch(Exception e){}
				pager.refresh(pageNo);
			}*/
		}
		return pager;
	}

	public static Page getPager(int totalRows) {

		Page pager = new Page(totalRows);

		return pager;
	}
}
