package com.wonders.project.workflow.page.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class Page implements Serializable{
	private int totalRows; 		
	private int pageSize=15;
	private int currentPage; 
	private int totalPages; 
	private int startRow; 
	public boolean hasNext=true;
	public boolean hasPrevious=false;
	
	public List<String[]> result = new ArrayList<String[]>();
	
	public Page(){
		super();
	}
	
	public Page(int _totalRows) {
		totalRows = _totalRows;
		totalPages = totalRows / pageSize;
		int mod = totalRows % pageSize;
		if (mod > 0) {
			totalPages++;
		}
		if(pageSize >= totalRows){
			hasNext=false;
			hasPrevious=false;
		}
		currentPage = 1;
		startRow = 0;
	}
	public Page(int _totalRows,int pageSize) {
		totalRows = _totalRows;
		this.pageSize = pageSize;
		totalPages = totalRows / pageSize;
		int mod = totalRows % pageSize;
		if (mod > 0) {
			totalPages++;
		}
		if(pageSize >= totalRows){
			hasNext=false;
			hasPrevious=false;
		}
		currentPage = 1;
		startRow = 0;
	}
	public int getStartRow() {
		return startRow;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void first() {
		
		currentPage = 1;
		startRow = 0;
		this.hasNext=true;
		this.hasPrevious=false;
		if(pageSize >= totalRows){
			hasNext=false;
			hasPrevious=false;
		}
	}

	public void previous() {
		if(currentPage>1){
			this.hasNext=true;
			this.hasPrevious=false;
			currentPage--;
		}else{
			this.hasNext=true;
			this.hasPrevious=true;
		}
		startRow = (currentPage - 1) * pageSize;
		if(startRow<0) startRow = 0;
	}

	public void next() {
		if(currentPage==(totalPages-1)){
			this.hasNext=false;
			this.hasPrevious=true;
		}else{
			this.hasNext=true;
			this.hasPrevious =true;
		}
		if (currentPage < totalPages) {
			currentPage++;
		}
		
		startRow = (currentPage -1) * pageSize;
	}

	public void last() {		currentPage = totalPages;
		this.hasNext=false;
		this.hasPrevious=true;
		startRow = (currentPage - 1) * pageSize;
		if(startRow<0) startRow = 0;
		if(pageSize >= totalRows){
			hasNext=false;
			hasPrevious=false;
		}
	}

	public void refresh(int _currentPage) {
		currentPage = _currentPage;
		if(currentPage==1){
			this.hasNext=true;
			this.hasPrevious=false;
		}else if(currentPage==totalPages){
			this.hasNext=false;
			this.hasPrevious=true;
		}else{
			this.hasNext=this.hasPrevious=true;
		}
		startRow = (currentPage - 1) * pageSize;
		if(startRow<0) startRow = 0;
		if (currentPage > totalPages) {
			last();
		}
	}

	public boolean isHasNext() {
		return hasNext;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

	public boolean isHasPrevious() {
		return hasPrevious;
	}

	public void setHasPrevious(boolean hasPrevious) {
		this.hasPrevious = hasPrevious;
	}
}
