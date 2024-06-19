package com.trip.dao.categoryreview;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.trip.db.categoryreview.CategoryReveiwSqlMapConfig;
import com.trip.dto.categoryreview.CategoryReviewDto;

public class CategoryReviewDaoImpl extends CategoryReveiwSqlMapConfig implements CategoryReviewDao{

	String namespace = "com.trip.db.categoryreview.CategoryReview-mapper.";
	
	@Override
	public List<CategoryReviewDto> selectList(String start, String end, String keyword, String category, String m_id) {

		SqlSession session = getSqlSessionFactory().openSession();
		
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("start", start);
		params.put("end", end);
		params.put("keyword", keyword);
		params.put("category", category);
		params.put("m_id",m_id);
		
		List<CategoryReviewDto> selectList = session.selectList(namespace + "selectList", params);
		session.close();
		return selectList;
	}

	@Override
	public CategoryReviewDto selectOne(int cr_no) {
		
		SqlSession session = getSqlSessionFactory().openSession();
		
		CategoryReviewDto selectOne = session.selectOne(namespace + "selectOne", cr_no);
		session.close();
		return selectOne;
	}

	@Override
	public int maxListNum() {

		SqlSession session = getSqlSessionFactory().openSession();
		int num = session.selectOne(namespace+"maxListNum");
		session.close();
		return num;
	}
	
	@Override
	public int insert(CategoryReviewDto dto) {
		SqlSession session = getSqlSessionFactory().openSession();
		int num = session.insert(namespace + "insert", dto);
		if(num == 1) {
			session.commit();
		} else {
			session.rollback();
		}
		session.close();
		return num;
	}

	@Override
	public int delete(int cr_no) {
		
		SqlSession session = getSqlSessionFactory().openSession();
		int num = session.update(namespace+"delete",cr_no);
		if(num == 1) {
			session.commit();
		} else {
			session.rollback();
		}
		session.close();
		return num;
	}

	@Override
	public int update(CategoryReviewDto dto) {

		SqlSession session = getSqlSessionFactory().openSession();
		int num = session.update(namespace+"update",dto);
		if(num == 1) {
			session.commit();
		} else {
			session.rollback();
		}
		session.close();
		return num;
	}

	@Override
	public int count(int cr_no) {
		
		SqlSession session = getSqlSessionFactory().openSession();
		
		int num = session.update(namespace+"count", cr_no);
		
		if(num == 1) {
			session.commit();
		} else {
			session.rollback();
		}
		session.close();	
		
		return num;
	}

	@Override
	public List<CategoryReviewDto> selectMyReview(String cr_id, String cr_placeid) {
		
		SqlSession session = getSqlSessionFactory().openSession();
		
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("cr_id", cr_id);
		params.put("cr_placeid", cr_placeid);
		
		List<CategoryReviewDto> selectList = session.selectList(namespace + "selectMyReview", params);
		session.close();
		return selectList;
		
	}
	
	
	
}
