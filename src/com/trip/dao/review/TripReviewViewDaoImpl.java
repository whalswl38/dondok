package com.trip.dao.review;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.trip.db.review.TripReviewSqlMapConfig;
import com.trip.dto.review.TripReviewViewDto;

public class TripReviewViewDaoImpl extends TripReviewSqlMapConfig implements TripReviewViewDao{

	private String namespace = "com.trip.db.review.TripReview-mapper.";
	private int num;
	
	@Override
	public List<TripReviewViewDto> selectList(String start, String end, String keyword, String m_id) {
		SqlSession session = getSqlSessionFactory().openSession();
		Map<String, String> params = new HashMap<String, String>();
		params.put("start", start);
		params.put("end", end);
		params.put("keyword", keyword);
		params.put("m_id", m_id);
		List<TripReviewViewDto> selectList = session.selectList(namespace+"tripReviewView_List",params);
		session.close();
		return selectList;
	}
	
}
