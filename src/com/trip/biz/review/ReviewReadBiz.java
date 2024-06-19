package com.trip.biz.review;

import java.util.List;

import com.trip.dto.review.RouteSelectDto;
import com.trip.dto.review.TeamDto;
import com.trip.dto.review.TeamMemberDto;

public interface ReviewReadBiz {

	TeamDto teamSelect(int t_id);
	
	List<TeamMemberDto> teamMemberSelect(int tm_tid);
	
	RouteSelectDto teamRouteSelect(int rs_tno, String rs_accdate);

	List<TeamDto> tripReviewWrite(String tm_uid);
}
