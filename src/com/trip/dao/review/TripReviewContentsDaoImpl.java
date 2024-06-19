package com.trip.dao.review;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.trip.db.review.TripReviewSqlMapConfig;
import com.trip.dto.review.TripReviewContentsDto;

public class TripReviewContentsDaoImpl extends TripReviewSqlMapConfig implements TripReviewContentsDao{

	private String namespace = "com.trip.db.review.TripReview-mapper.";
	private int num;
	
	@Override
	public List<TripReviewContentsDto> selectList(int tvc_tvno, int tvc_day, int tvc_routeid) {
		SqlSession session = getSqlSessionFactory().openSession();
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("tvc_tvno", tvc_tvno);
		params.put("tvc_day", tvc_day);
		params.put("tvc_routeid", tvc_routeid);
		
		List<TripReviewContentsDto> selectList = session.selectList(namespace+"tripReviewContents_List",params);
		session.close();
		return selectList;
	}

	@Override
	public TripReviewContentsDto select(int tvc_no) {
		SqlSession session = getSqlSessionFactory().openSession();
		TripReviewContentsDto tripReviewContentsDto = session.selectOne(namespace+"tripReviewContents_Select");
		session.close();
		return tripReviewContentsDto;
	}

	@Override
	public int insert(TripReviewContentsDto tripReviewContentsDto) {
		SqlSession session = getSqlSessionFactory().openSession();
		num = session.insert(namespace + "tripReviewContents_Insert", tripReviewContentsDto);
		System.out.println(num);
		if(num >0) {
			session.commit();
		}
		session.close();
		return num;
	}

	@Override
	public int modify(TripReviewContentsDto tripReviewContentsDto) {
		SqlSession session = getSqlSessionFactory().openSession();
		num = session.update(namespace + "tripReviewContents_Update", tripReviewContentsDto);
		if(num >0) {
			session.commit();
		}
		session.close();
		return num;
	}

	@Override
	public int delete(int tvc_no) {
		SqlSession session = getSqlSessionFactory().openSession();
		num = session.update(namespace + "tripReviewContents_Delete", tvc_no);
		if(num >0) {
			session.commit();
		}
		session.close();
		return num;
	}
	
}
