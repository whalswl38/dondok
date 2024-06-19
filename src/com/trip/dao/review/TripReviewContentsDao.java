package com.trip.dao.review;

import java.util.List;

import com.trip.dto.review.TripReviewContentsDto;


public interface TripReviewContentsDao {

	List<TripReviewContentsDto> selectList(int tvc_tvno, int tvc_day, int tvc_routeid);
	TripReviewContentsDto select(int tvc_no);
	int insert(TripReviewContentsDto tripReviewContentsDto);
	int modify(TripReviewContentsDto tripReviewContentsDto);
	int delete(int tvc_no);
	
}
