package com.trip.dao.comment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.trip.db.categoryreview.CategoryReveiwSqlMapConfig;
import com.trip.dto.comment.CategoryCommentDto;

public class CategoryCommentDaoImpl extends CategoryReveiwSqlMapConfig implements CategoryCommentDao{

	String namespace = "com.trip.db.categoryreview.CategoryReview-mapper.";
	
	@Override
	public List<CategoryCommentDto> selectList(int rv_crno, int page) {
		
		SqlSession session = getSqlSessionFactory().openSession();
		
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("rv_crno", ""+rv_crno);
		params.put("page", ""+page);
		
		List<CategoryCommentDto> list = session.selectList(namespace+"commentList",params);
		
		session.close();
		
		return list;
	}

	@Override
	public int insert(CategoryCommentDto dto) {
		
		SqlSession session = getSqlSessionFactory().openSession();
		
		int res = session.insert(namespace+"commentInsert", dto);
		
		if(res > 0) {
			session.commit();
		} else {
			session.rollback();
		}
		session.close();
		return res;
	}

	@Override
	public int update(CategoryCommentDto dto) {
		
		SqlSession session = getSqlSessionFactory().openSession();
		
		int res = session.update(namespace+"commentUpdate", dto);
		
		if(res > 0) {
			session.commit();
		} else {
			session.rollback();
		}
		session.close();
		return res;
	}

	@Override
	public int delete(int rv_no) {
		
		SqlSession session = getSqlSessionFactory().openSession();
		
		int res = session.update(namespace+"commentDelete", rv_no);
		
		if(res > 0) {
			session.commit();
		} else {
			session.rollback();
		}
		session.close();
		return res;
	}

	@Override
	public int commentCount(int rv_crno) {
		
		SqlSession session = getSqlSessionFactory().openSession();
		
		int res = session.selectOne(namespace+"commentCount", rv_crno);
		System.out.println("[commentCount]" + res);
		session.close();
		return res;
	}

	
	
	
}
