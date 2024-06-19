package com.trip.biz.review;

import java.util.List;

import com.trip.dto.review.TripReviewViewDto;

public interface TripReviewViewBiz {
	List<TripReviewViewDto> selectList(String start, String end, String keyword, String m_id);
}
