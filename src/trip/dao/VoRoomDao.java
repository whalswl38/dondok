package trip.dao;

import static trip.db.JDBCTemplete.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import trip.dto.VoRoomDto;

public class VoRoomDao {
	public int insertVoRoom(VoRoomDto voroomDto) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		int res=0;
		String sql = " INSERT INTO VOROOM VALUES(?,?,?,?) ";
		
		try {
			pstm=con.prepareStatement(sql);
			pstm.setInt(1, voroomDto.getVoroom_tid());
			pstm.setString(2, voroomDto.getVoroom_mid());
			pstm.setInt(3, voroomDto.getVoroom_id());
			pstm.setString(4, voroomDto.getVoroom_day());
			
			res = pstm.executeUpdate();
			
			if(res > 0) commit(con); else rollback(con);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstm, con);
		}
		return res;
	}
	
	public int selectVoRoom(VoRoomDto voroomDto) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		ResultSet rs=null;
		int res=0;
		
		String sql = " SELECT VOROOM_ID FROM VOROOM WHERE VOROOM_TID=? AND VOROOM_DAY=? AND VOROOM_MID=? ";
		
		try {
			pstm=con.prepareStatement(sql);
			pstm.setInt(1, voroomDto.getVoroom_tid());
			pstm.setString(2, voroomDto.getVoroom_day());
			pstm.setString(3, voroomDto.getVoroom_mid());
			
			rs=pstm.executeQuery();
			while(rs.next()) {
				res=rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs,pstm, con);
		}
		return res;
	}
	public int selectVoRoomCnt(VoRoomDto voroomDto) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		ResultSet rs=null;
		int res=0;
		
		String sql = " SELECT VOROOM_ID FROM VOROOM WHERE VOROOM_TID=? AND VOROOM_MID=? ";
		
		try {
			pstm=con.prepareStatement(sql);
			pstm.setInt(1, voroomDto.getVoroom_tid());
			pstm.setString(2, voroomDto.getVoroom_mid());
			
			rs=pstm.executeQuery();
			while(rs.next()) {
				res++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs,pstm, con);
		}
		return res;
	}
	public int updateVoRoom(VoRoomDto voroomDto) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		int res=0;
		
		String sql = " UPDATE VOROOM SET VOROOM_ID=? WHERE VOROOM_TID=? AND VOROOM_MID=? AND VOROOM_DAY=? ";
		
		try {
			pstm=con.prepareStatement(sql);
			pstm.setInt(1, voroomDto.getVoroom_id());
			pstm.setInt(2, voroomDto.getVoroom_tid());
			pstm.setString(3, voroomDto.getVoroom_mid());
			pstm.setString(4, voroomDto.getVoroom_day());
		
			res=pstm.executeUpdate();
			
			if(res > 0) commit(con); else rollback(con);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstm, con);
		}
		return res;
	}
	public int deleteVoRoom(VoRoomDto voroomDto) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		int res=0;
		
		String sql = " DELETE FROM VOROOM WHERE VOROOM_TID=? AND VOROOM_ID=? AND VOROOM_DAY=? ";
		
		try {
			pstm=con.prepareStatement(sql);
			pstm.setInt(1, voroomDto.getVoroom_tid());
			pstm.setInt(2, voroomDto.getVoroom_id());
			pstm.setString(3, voroomDto.getVoroom_day());
		
			res=pstm.executeUpdate();
			
			if(res > 0) commit(con); else rollback(con);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstm, con);
		}
		return res;
	}
	public List<VoRoomDto> resVoRoom(int rooms_tid) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		ResultSet rs=null;
		List<VoRoomDto> roomVoteList=new ArrayList<VoRoomDto>();
		
		String sql = " SELECT * " + 
				" FROM (" + 
				" SELECT VOROOM_ID, VOROOM_DAY, RANK() OVER(PARTITION BY VOROOM_DAY ORDER BY VOROOM_CNT DESC) AS RANK " + 
				" FROM(SELECT VOROOM_ID,VOROOM_DAY, COUNT(*) AS VOROOM_CNT FROM VOROOM WHERE VOROOM_TID=? GROUP BY VOROOM_ID,VOROOM_DAY)) " + 
				" WHERE RANK=1 ";
		
		try {
			pstm=con.prepareStatement(sql);
			pstm.setInt(1, rooms_tid);	
			rs=pstm.executeQuery();
			while(rs.next()) {
				VoRoomDto dto=new VoRoomDto();
				dto.setVoroom_id(rs.getInt(1));
				dto.setVoroom_day(rs.getString(2));
				dto.setVoroom_tid(rooms_tid);
				
				roomVoteList.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs, pstm, con);
		}
		return roomVoteList;
	}
	
}
