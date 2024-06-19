package com.trip.dao.otherBoard;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.trip.db.otherBoard.OtherBoardSqlMapConfig;
import com.trip.dto.otherBoard.AlarmDto;

public class AlarmDaoImpl extends OtherBoardSqlMapConfig implements AlarmDao{

	String namespace = "com.trip.db.otherBoard.OtherBoard-mapper.";
	
	@Override
	public List<AlarmDto> selectList(String al_id) {
		// TODO Auto-generated method stub
		
		SqlSession session = getSqlSessionFactory().openSession();
		
		List<AlarmDto> list = session.selectList(namespace+"alarmlist", al_id);
		
		session.close();
		
		return list;
	}

	@Override
	public int viewChange(int al_no) {		
		
		SqlSession session = getSqlSessionFactory().openSession();
		
		int res = session.update(namespace+"alarmchange", al_no);
		
		if(res > 0) {
			session.commit();
		}
		session.close();
		
		return res;
	}

	@Override
	public int delete(int al_no) {
		// TODO Auto-generated method stub
		
		SqlSession session = getSqlSessionFactory().openSession();
		
		int res = session.delete(namespace+"alarmdelete", al_no);
		
		if(res > 0) {
			session.commit();
		}
		session.close();
		
		return res;
		
	}

	@Override
	public int insert(AlarmDto dto) {
		// TODO Auto-generated method stub
		SqlSession session = getSqlSessionFactory().openSession();
		
		int res = session.delete(namespace+"alarminsert", dto);
		
		if(res > 0) {
			session.commit();
		}
		session.close();
		
		return res;
	}

	@Override
	public int alarmCount(String al_id) {
		// TODO Auto-generated method stub
		SqlSession session = getSqlSessionFactory().openSession();
		
		int count = 0;
		try {
			count = session.selectOne(namespace+"alarmcount", al_id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		
		session.close();
		
		return count;
	}
	
}
