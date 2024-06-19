package com.trip.biz.review;

import java.util.List;

import com.trip.dao.review.TripReviewContentsDao;
import com.trip.dao.review.TripReviewContentsDaoImpl;
import com.trip.dto.review.TripReviewContentsDto;

public class TripReviewContentsBizImpl implements TripReviewContentsBiz {

	TripReviewContentsDao tripReviewContentsDao = new TripReviewContentsDaoImpl();
	
	@Override
	public List<TripReviewContentsDto> selectList(int tvc_tvno, int tvc_day, int tvc_routeid) {
		// TODO Auto-generated method stub
		return tripReviewContentsDao.selectList(tvc_tvno, tvc_day, tvc_routeid);
	}

	@Override
	public TripReviewContentsDto select(int tvc_no) {
		// TODO Auto-generated method stub
		return tripReviewContentsDao.select(tvc_no);
	}

	@Override
	public int insert(TripReviewContentsDto tripReviewContentsDto) {
		// TODO Auto-generated method stub
		return tripReviewContentsDao.insert(tripReviewContentsDto);
	}

	@Override
	public int modify(TripReviewContentsDto tripReviewContentsDto) {
		// TODO Auto-generated method stub
		return tripReviewContentsDao.modify(tripReviewContentsDto);
	}

	@Override
	public int delete(int tvc_no) {
		// TODO Auto-generated method stub
		return tripReviewContentsDao.delete(tvc_no);
	}

	
	
}
