package com.wonders.util;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("dbUtil")
public class DbUtil {
	private static DataSource dataSource=null;
	private static JdbcTemplate jdbcTemplate=null;
	
	public JdbcTemplate getJdbcTemplate() {
		try{
			if(dataSource==null){
				throw new Exception("数据源为空！");
			}
			
			if(jdbcTemplate==null){
				jdbcTemplate = new JdbcTemplate(dataSource);
			}
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		return jdbcTemplate;
	}

	public static DataSource getDataSource() {
		return dataSource;
	}
	@Autowired(required = false)
	public void setDataSource(@Qualifier(value = "stptinc")DataSource dataSource) {
		DbUtil.dataSource = dataSource;
	}
}
