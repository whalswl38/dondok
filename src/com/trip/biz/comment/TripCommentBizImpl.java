package com.trip.biz.comment;

import java.util.List;

import com.trip.dao.comment.TripCommentDao;
import com.trip.dao.comment.TripCommentDaoImpl;
import com.trip.dto.comment.TripCommentDto;

public class TripCommentBizImpl implements TripCommentBiz {

	TripCommentDao dao = new TripCommentDaoImpl();

	@Override
	public List<TripCommentDto> selectList(int rv_crno, int page) {
		// TODO Auto-generated method stub
		return dao.selectList(rv_crno, page);
	}

	@Override
	public int insert(TripCommentDto dto) {
		// TODO Auto-generated method stub
		return dao.insert(dto);
	}

	@Override
	public int update(TripCommentDto dto) {
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
