package com.trip.dao.otherBoard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.trip.db.otherBoard.OtherBoardSqlMapConfig;
import com.trip.dto.otherBoard.FavoriteDto;

public class FavoriteDaoImpl extends OtherBoardSqlMapConfig implements FavoriteDao {

	String namespace = "com.trip.db.otherBoard.OtherBoard-mapper.";
	
	
	
	
	@Override
	public int favoriteCheck(String f_id, int f_pno, int f_cate) {
		// TODO Auto-generated method stub
		
		System.out.println("[sessionId]" + f_id);
		System.out.println("[f_pno]" + f_pno);	
		
		SqlSession session = getSqlSessionFactory().openSession();
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("f_id", f_id);
		params.put("f_pno", f_pno);
		params.put("f_cate", f_cate);
		
			
		int res = session.selectOne(namespace+"favoriteCheck",params);
		
		session.close();
		
		return res;
	}

	@Override
	public int favoriteInsert(FavoriteDto fDto) {
		// TODO Auto-generated method stub
		SqlSession session = getSqlSessionFactory().openSession();
		
		int res = session.insert(namespace+"favoriteInsert",fDto);
		
		if(res > 0) {
			session.commit();
		}
		session.close();
		
		return res;
	}

	@Override
	public int favoriteDelete(String f_id, int f_pno) {
		// TODO Auto-generated method stub
		SqlSession session = getSqlSessionFactory().openSession();
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("f_id", f_id);
		params.put("f_pno", f_pno);
		
		int res = session.delete(namespace+"favoriteDelete",params);
		
		if(res > 0) {
			session.commit();
		}
		session.close();
		
		return res;
	}

	@Override
	public List<FavoriteDto> favoriteList(String f_id) {
		// TODO Auto-generated method stub
		
		SqlSession session = getSqlSessionFactory().openSession();
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		List<FavoriteDto> list = session.selectList(namespace+"favoriteList",f_id);
		
		session.close();
		
		return list;
	}
	
}
