package com.trip.search.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.trip.db.SqlMapConfig;

public class SearchDaoImpl extends SqlMapConfig implements SearchDao{
	
	private String namespace = "memberMapper.";
	
	public int insertSearch(String myid, String search) {
		SqlSession session=null;
		int res = 0;
		
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("myid", myid);
		params.put("search", search);
		
		try {
			session = getSqlSessionFactory().openSession();
			res = session.insert(namespace+"searchInsert",params);
			
			if(res>0) {
				session.commit();
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return res;
	}

}
