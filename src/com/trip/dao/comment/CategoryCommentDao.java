package com.trip.dao.comment;

import java.util.List;

import com.trip.dto.comment.CategoryCommentDto;

public interface CategoryCommentDao {

	List<CategoryCommentDto> selectList(int rv_crno, int page);
	int insert(CategoryCommentDto dto);
	int update(CategoryCommentDto dto);
	int delete(int rv_no);
	int commentCount(int rv_crno);
	
}
