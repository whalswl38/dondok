package com.trip.dao.member;

import java.util.Map;

import com.trip.dto.member.MemberLoginDto;

public interface MemberLoginDao {

	MemberLoginDto getList(MemberLoginDto dto);

	int joinMember(MemberLoginDto dto);

	Map<String, Object> dupCheck(Map<String, Object> paramMap);

	MemberLoginDto findIdPw(MemberLoginDto dto);

	int resetPw(MemberLoginDto dto);

	int updateMember(MemberLoginDto dto);

	int deleteMember(MemberLoginDto dto);
	
}

