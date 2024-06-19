package com.trip.dao.otherBoard;

import java.util.List;

import com.trip.dto.otherBoard.AlarmDto;

public interface AlarmDao {

	List<AlarmDto> selectList(String al_id);
	int viewChange(int al_no);
	int delete(int al_no);
	int insert(AlarmDto dto);
	int alarmCount(String al_id); 
}
