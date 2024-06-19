package com.trip.biz.categoryreview;

import java.util.List;


import com.trip.dto.categoryreview.CategoryReviewDto;

public interface CategoryReviewBiz {

	List<CategoryReviewDto> selectList(String start, String end, String keyword, String category, String m_id);
	CategoryReviewDto selectOne(int cr_no);
	int maxListNum();
	int insert(CategoryReviewDto dto, String cr_path);
	int delete(int cr_no);
	int update(CategoryReviewDto dto, String cr_path, String cr_tmp_path, String serverPath);
	int count(int cr_no);
	List<CategoryReviewDto> selectMyReview(String cr_id, String cr_placeid);
}
