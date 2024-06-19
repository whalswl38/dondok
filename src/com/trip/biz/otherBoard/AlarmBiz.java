package com.trip.biz.otherBoard;

import java.util.List;

import com.trip.dto.otherBoard.AlarmDto;

public interface AlarmBiz {

	List<AlarmDto> selectList(String al_id);
	int viewChange(int al_no);
	int delete(int al_no);
	int insert(AlarmDto dto);	
	
}
