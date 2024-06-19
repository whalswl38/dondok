package com.trip.biz.comment;

import java.util.List;

import com.trip.dto.comment.TripCommentDto;

public interface TripCommentBiz {

	List<TripCommentDto> selectList(int rv_crno, int page);
	int insert(TripCommentDto dto);
	int update(TripCommentDto dto);
	int delete(int rv_no);
	int commentCount(int rv_crno);

}
