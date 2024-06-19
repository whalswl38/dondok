package com.trip.biz.review;

import java.util.List;
import java.util.Map;

import com.trip.dao.review.TripReivewDaoImpl;
import com.trip.dao.review.TripReviewDao;
import com.trip.dto.review.TripReviewDto;

public class TripReviewBizImpl implements TripReviewBiz{

	TripReviewDao tripReviewDao = new TripReivewDaoImpl();
	
	@Override
	public List<TripReviewDto> selectAll() {

		return tripReviewDao.selectAll();
	}
	
	@Override
	public TripReviewDto select(int tv_no) {
		// TODO Auto-generated method stub
		return tripReviewDao.select(tv_no);
	}

	@Override
	public int insert(TripReviewDto tripReviewDto) {
		// TODO Auto-generated method stub
		return tripReviewDao.insert(tripReviewDto);
	}

	@Override
	public int modify(TripReviewDto tripReviewDto) {
		// TODO Auto-generated method stub
		return tripReviewDao.modify(tripReviewDto);
	}

	@Override
	public int countUpdate(int tv_no) {
		return tripReviewDao.countUpdate(tv_no);
	}

	@Override
	public int delete(int tv_no) {
		return tripReviewDao.delete(tv_no);
	}

	@Override
	public TripReviewDto selectTeam(int tv_teamid) {
		// TODO Auto-generated method stub
		return tripReviewDao.selectTeam(tv_teamid);
	}

	@Override
	public int selectTeamCount(int tv_teamid) {
		// TODO Auto-generated method stub
		return tripReviewDao.selectTeamCount(tv_teamid);
	}

	
	
}
