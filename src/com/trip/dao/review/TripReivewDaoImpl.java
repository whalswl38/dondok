package com.trip.dao.review;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.trip.db.review.TripReviewSqlMapConfig;
import com.trip.dto.review.TripReviewDto;

public class TripReivewDaoImpl extends TripReviewSqlMapConfig implements TripReviewDao {

	private String namespace = "com.trip.db.review.TripReview-mapper.";
	private int num;
	
	
	@Override
	public List<TripReviewDto> selectAll() {
		SqlSession session = getSqlSessionFactory().openSession();
		
		List<TripReviewDto> tripReviewList = session.selectList(namespace+"tripReview_List");
		session.close();
		
		return tripReviewList;
	}

	@Override
	public TripReviewDto select(int tv_no) {
		SqlSession session = getSqlSessionFactory().openSession();
		
		TripReviewDto tripReviewDto = session.selectOne(namespace+"tripReview_Select", tv_no);
		session.close();
		
		return tripReviewDto;
	}
	
	@Override
	public int insert(TripReviewDto tripReviewDto) {
		SqlSession session = getSqlSessionFactory().openSession();
		
		num = session.insert(namespace + "tripReview_Insert", tripReviewDto);
		if(num >0) {
			session.commit();
		}
		session.close();
		return num;
	}

	@Override
	public int modify(TripReviewDto tripReviewDto) {
		SqlSession session = getSqlSessionFactory().openSession();
		num = session.update(namespace+"tripReview_Modify", tripReviewDto);
		if(num >0) {
			session.commit();
		}
		session.close();
		return num;
	}

	@Override
	public int countUpdate(int tv_no) {
		SqlSession session = getSqlSessionFactory().openSession();
		num = session.update(namespace+"tripReview_countUpdate", tv_no);
		if(num >0) {
			session.commit();
		}
		session.close();
		return num;
	}

	@Override
	public int delete(int tv_no) {
		SqlSession session = getSqlSessionFactory().openSession();
		num = session.delete(namespace+"tripReview_Modify", tv_no);
		if(num >0) {
			session.commit();
		}
		session.close();
		return num;
	}

	@Override
	public TripReviewDto selectTeam(int tv_teamid) {
		// TODO Auto-generated method stub
		
		SqlSession session = getSqlSessionFactory().openSession();
		
		TripReviewDto tripReviewDto = session.selectOne(namespace+"tripReivew_SelectTeam", tv_teamid);
		session.close();
		
		return tripReviewDto;
	}

	@Override
	public int selectTeamCount(int tv_teamid) {
		// TODO Auto-generated method stub
		
		SqlSession session = getSqlSessionFactory().openSession();
		
		int res = session.selectOne(namespace+"tripReview_SelectTeamCount", tv_teamid);
		session.close();
		
		return res;
	}
}
