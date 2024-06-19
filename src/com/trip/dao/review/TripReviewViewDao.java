package com.trip.dao.review;

import java.util.List;

import com.trip.dto.review.TripReviewViewDto;

public interface TripReviewViewDao {
	List<TripReviewViewDto> selectList(String start, String end, String keyword, String m_id);
	
}
