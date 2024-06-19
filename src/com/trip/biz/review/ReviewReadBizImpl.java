package com.trip.biz.review;

import java.util.List;

import com.trip.dao.review.ReviewReadDao;
import com.trip.dao.review.ReviewReadDaoImpl;
import com.trip.dto.review.RouteSelectDto;
import com.trip.dto.review.TeamDto;
import com.trip.dto.review.TeamMemberDto;

public class ReviewReadBizImpl implements ReviewReadBiz{

	ReviewReadDao dao = new ReviewReadDaoImpl();
	
	@Override
	public TeamDto teamSelect(int t_id) {
		// TODO Auto-generated method stub
		return dao.teamSelect(t_id);
	}

	@Override
	public List<TeamMemberDto> teamMemberSelect(int tm_tid) {
		
		return dao.teamMemberSelect(tm_tid);
	}

	@Override
	public RouteSelectDto teamRouteSelect(int rs_tno, String rs_accdate) {
		// TODO Auto-generated method stub
		return dao.teamRouteSelect(rs_tno, rs_accdate);
	}

	@Override
	public List<TeamDto> tripReviewWrite(String tm_uid) {
		// TODO Auto-generated method stub
		return dao.tripReviewWrite(tm_uid);
	}
	
	
	
}
