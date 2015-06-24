/**
 * 
 */
package com.wonders.discipline.workflow.approve.model.comparator;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
/** 
 * @ClassName: ApprovedInfoComparator 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-8-1 上午9:39:00 
 *  
 */
public class ApprovedInfoComparator implements Comparator<Object>{

	 @Override  
	 public int compare(Object o1, Object o2) {  
		 // TODO Auto-generated method stub  
		 Integer a = (Integer)o1;  
		 Integer b = (Integer)o2;  
		 if(a == 0 && b == 0){ return 0;}
		 if(a == 0){ return -1;}
		 if(b == 0){ return 1;}		 
		 return b.compareTo(a);  
	 }
	 
	 public static void main(String[] args){
	 	Map<Integer,Integer> map = new TreeMap<Integer,Integer>(new ApprovedInfoComparator());
	 	
	 	map.put(1, 1);
	 	map.put(3, 3);
	 	map.put(2, 2);
	 	map.put(1 ,0);
	 	map.put(6, 10);
	 	System.out.println("2".compareTo("8"));
	 	for(Map.Entry<Integer,Integer> entry : map.entrySet()){
	 		System.out.println(entry.getKey() + "-" + entry.getValue());
	 	}
	 }
}
