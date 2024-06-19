package trip.dao;

import static trip.db.JDBCTemplete.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import trip.dto.RegionDto;

public class RegionDao {
	public List<RegionDto> selectRegion(int t_id){
		Connection con = getConnection();
		PreparedStatement pstm = null;
		ResultSet rs=null;
		List<RegionDto> list=new ArrayList<RegionDto>();
		
		String sql=" SELECT * FROM REGION WHERE R_TID=? ORDER BY R_DAY ";
		try {
			pstm=con.prepareStatement(sql);
			
			pstm.setInt(1, t_id);
			
			rs=pstm.executeQuery();
			while(rs.next()) {
				RegionDto rdto=new RegionDto(rs.getInt(1),rs.getString(2),rs.getString(3));	
				list.add(rdto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs,pstm, con);
		}
		return list;
	}
	
	public RegionDto selectOneRegion(RegionDto dto) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		ResultSet rs=null;
		RegionDto rdto=null;
		
		String sql=" SELECT * FROM REGION WHERE R_TID=? AND R_DAY=? ";
		try {
			pstm=con.prepareStatement(sql);
			
			pstm.setInt(1,dto.getR_tid());
			pstm.setString(2,dto.getR_day());
			
			rs=pstm.executeQuery();
			while(rs.next()) {
				rdto=new RegionDto(rs.getInt(1),rs.getString(2),rs.getString(3));	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs,pstm, con);
		}
		return rdto;
	}
	
	public int insertRegion(RegionDto dto) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		int res=0;
		
		String sql=" INSERT INTO REGION VALUES(?,?,?) ";
		try {
			pstm=con.prepareStatement(sql);
			
			pstm.setInt(1,dto.getR_tid());
			pstm.setString(2,dto.getR_day());
			pstm.setString(3,dto.getR_location());
			
			res=pstm.executeUpdate();
			
			if(res > 0) commit(con); else rollback(con);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstm, con);
		}
		return res;
	}
	
	public int updateRegion(RegionDto dto) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		int res=0;
		
		String sql=" UPDATE REGION SET R_LOCATION=? WHERE R_TID=? AND R_DAY=? ";
		try {
			pstm=con.prepareStatement(sql);
			
			pstm.setString(1,dto.getR_location());
			pstm.setInt(2,dto.getR_tid());
			pstm.setString(3,dto.getR_day());
			
			res=pstm.executeUpdate();
			
			if(res > 0) commit(con); else rollback(con);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstm, con);
		}
		return res;
	}
}
