package com.trip.dao.review;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.trip.db.review.ReviewReadSqlMapConfig;
import com.trip.dto.review.RouteSelectDto;
import com.trip.dto.review.TeamDto;
import com.trip.dto.review.TeamMemberDto;

public class ReviewReadDaoImpl extends ReviewReadSqlMapConfig implements ReviewReadDao {

	String namespace = "com.trip.db.review.ReviewRead-mapper.";
	
	@Override
	public TeamDto teamSelect(int t_id) {
		// TODO Auto-generated method stub
		
		SqlSession session = getSqlSessionFactory().openSession();
		System.out.println("[t_id] " + t_id);
		TeamDto tDto = session.selectOne(namespace + "teamSelect", t_id);
		
		session.close();
		
		return tDto;
	}

	@Override
	public List<TeamMemberDto> teamMemberSelect(int tm_tid) {
		// TODO Auto-generated method stub
		
		SqlSession session = getSqlSessionFactory().openSession();
		
		List<TeamMemberDto> tmList = session.selectList(namespace+"teamMemberSelect", tm_tid);
		
		session.close();
		
		return tmList;
	}

	@Override
	public RouteSelectDto teamRouteSelect(int rs_tno, String rs_accdate) {
		// TODO Auto-generated method stub
		
		SqlSession session = getSqlSessionFactory().openSession();
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("rs_tno", rs_tno);
		params.put("rs_accdate", rs_accdate);
		
		RouteSelectDto rsDto = session.selectOne(namespace+"teamRouteSelect",params);
		
		return rsDto;
	}

	@Override
	public List<TeamDto> tripReviewWrite(String tm_uid) {
		// TODO Auto-generated method stub
		
		SqlSession session = getSqlSessionFactory().openSession();
		
		List<TeamDto> trwList = session.selectList(namespace+"team_Write_List", tm_uid);
		
		session.close();
		
		return trwList;
	}

	
	
}
