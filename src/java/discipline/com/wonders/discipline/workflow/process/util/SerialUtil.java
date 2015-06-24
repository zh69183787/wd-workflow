/**
 * 
 */
package com.wonders.discipline.workflow.process.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.wonders.util.DbUtil;

/** 
 * @ClassName: SerialUtil 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-25 下午8:30:53 
 *  
 */
@Component("discipline-serialUtil")
public class SerialUtil {

	private static DbUtil dbUtil;
	
	private static void saveSerialReserve(String prefix,String postfix,String typeId,String serial){
		String countSql = "select count(*) as count from t_serialno_reserve t where"+
				" t.prefix=? and  t.postfix = ? and t.typeid=? and t.serialno = ?";
		String insertSql = "insert into t_serialno_reserve " +
				" (prefix, postfix,typeid,serialno,removed) " +
				" values(?,?,?,?,'0') ";
		Object[] countObject = new Object[]{prefix,postfix,typeId,serial};
		Object[] insertObject = new Object[]{prefix,postfix,typeId,serial};
		int count = dbUtil.getJdbcTemplate().queryForInt(countSql,countObject);
		if(count == 0){
			dbUtil.getJdbcTemplate().update(insertSql, insertObject);
		}
	}
	
	private static void saveSerialReserve(String prefix,String postfix,String serial){
		String countSql = "select count(*) as count from t_serialno_reserve t where"+
				" t.prefix=? and  t.postfix = ? and t.serialno = ?";
		String insertSql = "insert into t_serialno_reserve " +
				" (prefix, postfix,serialno,removed) " +
				" values(?,?,?,'0') ";
		Object[] countObject = new Object[]{prefix,postfix,serial};
		Object[] insertObject = new Object[]{prefix,postfix,serial};
		int count = dbUtil.getJdbcTemplate().queryForInt(countSql,countObject);
		if(count == 0){
			dbUtil.getJdbcTemplate().update(insertSql, insertObject);
		}
	}
	
	public static void delSerialReserve(String prefix,String postfix,String typeId,String serial){
		String delSql = "update t_serialno_reserve t set t.removed='1' where "+
				" t.prefix=? and  t.postfix = ? and t.typeid=? and t.serialno = ?";
		Object[] delObject = new Object[]{prefix,postfix,typeId,serial};
		dbUtil.getJdbcTemplate().update(delSql,delObject);
	}
	
	public static void delSerialReserve(String prefix,String postfix,String serial){
		String delSql = "update t_serialno_reserve t set t.removed='1' where "+
				" t.prefix=? and  t.postfix = ? and t.serialno = ?";
		Object[] delObject = new Object[]{prefix,postfix,serial};
		dbUtil.getJdbcTemplate().update(delSql,delObject);
	}
	
	
	//获取序号
	public static int getSerial(String prefix,String postfix,String typeId){
		int serial = 1;
		String countSql = "select count(*) as count from t_serialno t where"+
				" t.prefix=? and  t.postfix = ? and t.typeid=? ";
		String updateSql = "update t_serialno set serialno = " +
				" (select max(serialno) + 1 from t_serialno t where"+
				" t.prefix=? and  t.postfix = ? and t.typeid=? ) where"+
				" prefix=? and  postfix = ? and typeid=?";
		String insertSql = "insert into t_serialno(prefix, serialno, postfix,typeid) " +
				" values(?,?,?,?) ";
		String querySql = "select max(serialno) from t_serialno t where"+
				" t.prefix=? and  t.postfix = ? and t.typeid=? ";
		Object[] countObject = new Object[]{prefix,postfix,typeId};
		Object[] updateObject = new Object[]{prefix,postfix,typeId,prefix,postfix,typeId};
		Object[] insertObject = new Object[]{prefix,serial,postfix,typeId};
		Object[] queryObject = new Object[]{prefix,postfix,typeId};
		int count = dbUtil.getJdbcTemplate().queryForInt(countSql,countObject);
		
		if(count > 0){
			dbUtil.getJdbcTemplate().update(updateSql, updateObject);
		}else{
			dbUtil.getJdbcTemplate().update(insertSql, insertObject);
		}
		
		try{
			serial = dbUtil.getJdbcTemplate().queryForInt(querySql, queryObject);
		}catch(Exception e){
			serial = 1;
		}		
		
		saveSerialReserve(prefix,postfix,typeId,serial+"");
		return serial;
	}
	
	//获取序号
		public static int getSerial(String prefix,String postfix){
			int serial = 1;
			String countSql = "select count(*) as count from t_serialno t where"+
					" t.prefix=? and  t.postfix = ?  ";
			String updateSql = "update t_serialno set serialno = " +
					" (select max(serialno) + 1 from t_serialno t where"+
					" t.prefix=? and  t.postfix = ? ) where"+
					" prefix=? and  postfix = ? ";
			String insertSql = "insert into t_serialno(prefix, serialno, postfix) " +
					" values(?,?,?) ";
			String querySql = "select max(serialno) from t_serialno t where"+
					" t.prefix=? and  t.postfix = ?";
			Object[] countObject = new Object[]{prefix,postfix};
			Object[] updateObject = new Object[]{prefix,postfix,prefix,postfix};
			Object[] insertObject = new Object[]{prefix,serial,postfix};
			Object[] queryObject = new Object[]{prefix,postfix};
			int count = dbUtil.getJdbcTemplate().queryForInt(countSql,countObject);
			
			if(count > 0){
				dbUtil.getJdbcTemplate().update(updateSql, updateObject);
			}else{
				dbUtil.getJdbcTemplate().update(insertSql, insertObject);
			}
			
			try{
				serial = dbUtil.getJdbcTemplate().queryForInt(querySql, queryObject);
			}catch(Exception e){
				serial = 1;
			}		
			
			saveSerialReserve(prefix,postfix,serial+"");
			return serial;
		}
	
	//选取序号
	public static List<Integer> selSerial(String prefix,String postfix,String typeId){
		String querySql = "select t.serialno from t_serialno_reserve t where"+
				" t.prefix=? and  t.postfix = ? and t.typeid=? and t.removed = '0' " +
				" order by to_number(t.serialno) ";
		Object[] queryObject = new Object[]{prefix,postfix,typeId};
		List<Integer> list = dbUtil.getJdbcTemplate().queryForList(querySql, Integer.class,queryObject);
		return list;
	}
	
	//选取序号
	public static List<Integer> selSerial(String prefix,String postfix){
		String querySql = "select t.serialno from t_serialno_reserve t where"+
				" t.prefix=? and  t.postfix = ?  and t.removed = '0' " +
				" order by to_number(t.serialno) ";
		Object[] queryObject = new Object[]{prefix,postfix};
		List<Integer> list = dbUtil.getJdbcTemplate().queryForList(querySql, Integer.class,queryObject);
		return list;
	}

	public static int updateSerial(String id,String prefix,String postfix,String serialno){
		int result = 0;
		String updateSql = "update t_receive_directive t " +
				" set t.xdept = ?, t.zdept = ? , t.deptid = ?" +
				" where t.id=? and t.removed = 0";
		Object[] updateObject = new Object[]{postfix,serialno,prefix+"["+postfix+"]"+serialno+"号",id};
		int count = dbUtil.getJdbcTemplate().update(updateSql,updateObject);
		if(count > 0){
			delSerialReserve(prefix,postfix,serialno);
			result = 1;
		}
		return result;
		
	}
	
	public static int prefillRecv(String id,String flag){
		int count = 0;
		String sql = "update t_doc_receive t set t.flag = ? where t.id = ? and t.removed = 0";
		Object[] param = new Object[]{flag,id};
		count = dbUtil.getJdbcTemplate().update(sql,param);
		return count;
	}
	
	public static int prefillRedv(String id,String flag){
		int count = 0;
		String sql = "update t_receive_directive t set t.flag = ? where t.id = ? and t.removed = 0";
		Object[] param = new Object[]{flag,id};
		count = dbUtil.getJdbcTemplate().update(sql,param);
		return count;
	}

	public static int hasSameDoc(String title,String unit,String zh){
		int count = 0;
		String sql = "select count(*) from t_doc_receive t where t.removed=0 " +
				" and t.title= ? and t.swunit= ? and t.filezh= ?";
		Object[] param = new Object[]{title,unit,zh};
		count = dbUtil.getJdbcTemplate().queryForInt(sql,param);
		return count;
	}
	
	public static DbUtil getDbUtil() {
		return dbUtil;
	}
	
	@Autowired(required=false)
	public void setDbUtil(@Qualifier("dbUtil")DbUtil dbUtil) {
		SerialUtil.dbUtil = dbUtil;
	}
	
}
