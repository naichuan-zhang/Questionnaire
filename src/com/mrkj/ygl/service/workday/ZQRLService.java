package com.mrkj.ygl.service.workday;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service("zQRLService")
public class ZQRLService {
	
	JdbcTemplate sqlHelper;

	public Object getAll() {
		try {
			String sql = "select work_id as \"id\" , work_title as \"title\" ,work_start as \"start\", work_end as \"end\", work_color as \"color\" from work_day where work_scope='1'";
			return sqlHelper.queryForMap(sql);
		} catch (Exception e) {
            return null;
		}
	}

	public int add(String id,String title,String color, String start, String end) {
		String sql ="insert into sys_calendar(calendar_id,C_TITLE,C_COLOR,C_STARTDATE,C_ENDDATE,c_scope) values('"+id+"','"+title+"','"+color+"','"+start+"','"+end+"','1')";
		try {
			return sqlHelper.update(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return 0;
		}
	}

	public Object updateByParameters(String id, String title ,String start,String end) {
		String sql ="update sys_calendar set c_title ='"+title+"' ,C_STARTDATE ='"+start+"',C_ENDDATE='"+end+"' where calendar_id ='"+id+"'";
		try {
			return sqlHelper.update(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return 0;
		}
	}

	public Object delById(String id) {
		String sql ="delete from sys_calendar where calendar_id = '"+id+"'";
		try {
			return sqlHelper.update(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return 0;
		}
	}

	public Object getDataByParameters(String start, String end) {
		try {
			String sql = "select * from sys_calendar " +
						 "where to_char(to_date(c_startdate,'yyyy-mm-dd'),'mm')" +
						 "=to_char(to_date('"+start+"','yyyy-mm-dd'),'mm')"+
						 "and to_char(to_date(c_enddate,'yyyy-mm-dd'),'mm')"+
						 "=to_char(to_date('"+end+"','yyyy-mm-dd'),'mm') and c_scope ='1'" ;
			return sqlHelper.queryForMap(sql);
		} catch (Exception e) {
            return null;
		}
	}
}
