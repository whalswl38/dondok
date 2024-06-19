package com.trip.biz.comment;

import java.util.List;

import com.trip.dao.comment.CategoryCommentDao;
import com.trip.dao.comment.CategoryCommentDaoImpl;
import com.trip.dto.comment.CategoryCommentDto;

public class CategoryCommentBizImpl implements CategoryCommentBiz{

	CategoryCommentDao dao = new CategoryCommentDaoImpl();
	
	@Override
	public List<CategoryCommentDto> selectList(int rv_crno, int page) {
		// TODO Auto-generated method stub
		return dao.selectList(rv_crno, page);
	}

	@Override
	public int insert(CategoryCommentDto dto) {
		// TODO Auto-generated method stub
		return dao.insert(dto);
	}

	@Override
	public int update(CategoryCommentDto dto) {
		// TODO Auto-generated method stub
		return dao.update(dto);
	}

	@Override
	public int delete(int rv_no) {
		// TODO Auto-generated method stub
		return dao.delete(rv_no);
	}

	@Override
	public int commentCount(int rv_crno) {
		// TODO Auto-generated method stub
		return dao.commentCount(rv_crno);
	}

	
	
}
