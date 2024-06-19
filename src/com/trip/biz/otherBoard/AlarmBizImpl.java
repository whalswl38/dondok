package com.trip.biz.otherBoard;

import java.util.List;

import com.trip.dao.otherBoard.AlarmDao;
import com.trip.dao.otherBoard.AlarmDaoImpl;
import com.trip.dto.otherBoard.AlarmDto;

public class AlarmBizImpl implements AlarmBiz {

	AlarmDao dao = new AlarmDaoImpl();
	
	@Override
	public List<AlarmDto> selectList(String al_id) {
		// TODO Auto-generated method stub
		return dao.selectList(al_id);
	}

	@Override
	public int viewChange(int al_no) {
		// TODO Auto-generated method stub
		return dao.viewChange(al_no);
	}

	@Override
	public int delete(int al_no) {
		// TODO Auto-generated method stub
		return dao.delete(al_no);
	}

	@Override
	public int insert(AlarmDto dto) {
		// TODO Auto-generated method stub
		return dao.insert(dto);
	}

	
	
}
