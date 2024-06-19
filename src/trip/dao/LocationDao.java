package trip.dao;

import static trip.db.JDBCTemplete.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import trip.dto.LocationDto;

public class LocationDao {
	public List<LocationDto> selectMemLoc(LocationDto locDto) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		ResultSet rs=null;
		List<LocationDto> loclist=new ArrayList<LocationDto>();
		
		String sql=" SELECT * FROM LOCATION WHERE LOC_TID=? and LOC_MID=? and LOC_DAY=? ";
		try {
			pstm=con.prepareStatement(sql);
			pstm.setInt(1,locDto.getLoc_tid());
			pstm.setString(2,locDto.getLoc_mid());
			pstm.setString(3,locDto.getLoc_day());
			
			rs=pstm.executeQuery();
			while(rs.next()) {
			loclist.add(new LocationDto(rs.getInt(1), rs.getInt(2),rs.getString(3),rs.getString(4),rs.getString(5),
					rs.getString(6),rs.getString(7),rs.getInt(8),rs.getString(9),rs.getString(10)));	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs,pstm, con);
		}
		return loclist;
	}
	
	public List<LocationDto> selectTeamLoc(LocationDto locDto) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		ResultSet rs=null;
		List<LocationDto> loclist=new ArrayList<LocationDto>();
		
		String sql=" SELECT * FROM LOCATION WHERE LOC_TID=? and LOC_DAY=? ";
		try {
			pstm=con.prepareStatement(sql);
			pstm.setInt(1,locDto.getLoc_tid());
			pstm.setString(2,locDto.getLoc_day());
			
			rs=pstm.executeQuery();
			while(rs.next()) {
			loclist.add(new LocationDto(rs.getInt(1), rs.getInt(2),rs.getString(3),rs.getString(4),rs.getString(5),
					rs.getString(6),rs.getString(7),rs.getInt(8),rs.getString(9),rs.getString(10)));	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs, pstm, con);
		}
		return loclist;
	}
	
	public LocationDto selectOneLoc(int loc_id) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		ResultSet rs=null;
		LocationDto lodto=null;
		
		String sql=" SELECT * FROM LOCATION WHERE LOC_ID=? ";
		try {
			pstm=con.prepareStatement(sql);
			pstm.setInt(1,loc_id);
			
			rs=pstm.executeQuery();
			while(rs.next()) {
			lodto=new LocationDto(rs.getInt(1), rs.getInt(2),rs.getString(3),rs.getString(4),rs.getString(5),
					rs.getString(6),rs.getString(7),rs.getInt(8),rs.getString(9),rs.getString(10));	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs,pstm, con);
		}
		return lodto;
	}
	
	public int insertPlace(LocationDto locDto) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		int res=0;
		String sql = " INSERT INTO LOCATION VALUES(?,?,?,?,?,?,?,?,?,?) ";
		
		try {
			pstm=con.prepareStatement(sql);
			pstm.setInt(1, locDto.getLoc_id());
			pstm.setInt(2, locDto.getLoc_cate());
			pstm.setString(3, locDto.getLoc_name());
			pstm.setString(4, locDto.getLoc_addr());
			pstm.setString(5, locDto.getLoc_x());
			pstm.setString(6, locDto.getLoc_y());
			pstm.setString(7, locDto.getLoc_url());
			pstm.setInt(8, locDto.getLoc_tid());
			pstm.setString(9, locDto.getLoc_mid());
			pstm.setString(10, locDto.getLoc_day());
			
			res = pstm.executeUpdate();
			
			if(res > 0) commit(con); else rollback(con);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstm, con);
		}
		return res;
	}
	
	public int deletePlace(LocationDto locDto) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		int res=0;
		String sql = " DELETE FROM LOCATION WHERE LOC_ID=? AND LOC_TID=? AND LOC_DAY=? ";
		
		try {
			pstm=con.prepareStatement(sql);
			pstm.setInt(1, locDto.getLoc_id());
			pstm.setInt(2, locDto.getLoc_tid());
			pstm.setString(3, locDto.getLoc_day());
			
			res = pstm.executeUpdate();
			
			if(res > 0) commit(con); else rollback(con);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstm, con);
		}
		return res;
	}
	
	public List<LocationDto> selectTeamRooms(int loc_tid,String loc_day) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		ResultSet rs=null;
		List<LocationDto> loclist=new ArrayList<LocationDto>();
		
		String sql=" SELECT * FROM LOCATION WHERE LOC_TID=? and LOC_DAY=? and LOC_CATE=1 ";
		try {
			pstm=con.prepareStatement(sql);
			pstm.setInt(1,loc_tid);
			pstm.setString(2,loc_day);
			
			rs=pstm.executeQuery();
			while(rs.next()) {
			loclist.add(new LocationDto(rs.getInt(1), rs.getInt(2),rs.getString(3),rs.getString(4),rs.getString(5),
					rs.getString(6),rs.getString(7),rs.getInt(8),rs.getString(9),rs.getString(10)));	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs,pstm, con);
		}
		return loclist;
	}
	
	public int locCount(int loc_tid, String loc_day) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		ResultSet rs=null;
		
		String sql=" SELECT count(*) FROM LOCATION WHERE LOC_TID=? and LOC_DAY=?";
		
		int res = 0;
		
		try {
			pstm = con.prepareStatement(sql);
			pstm.setInt(1,loc_tid);
			pstm.setString(2,loc_day);
			rs=pstm.executeQuery();
			
			if(rs.next()) {
				res = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(rs, pstm, con);
		}
		
		return res;
	}
}
