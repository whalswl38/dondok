package trip.dao;

import static trip.db.JDBCTemplete.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import trip.dto.RouteDto;
public class RouteDao {
	public int insertRoute(RouteDto dto) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		int res=0;
		String sql = " INSERT INTO ROUTE VALUES(?,?,?,?) ";		
		try {
			pstm=con.prepareStatement(sql);
			pstm.setInt(1, dto.getR_tid());
			pstm.setString(2, dto.getR_mid());
			pstm.setString(3, dto.getR_day());
			pstm.setString(4, dto.getR_route());
			
			res = pstm.executeUpdate();
			
			if(res > 0) commit(con); else rollback(con);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstm, con);
		}
		return res;
	}
	public int updateRoute(RouteDto dto) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		int res=0;
		String sql = " UPDATE ROUTE SET R_ROUTE=? WHERE R_TID=? AND R_MID=? AND R_DAY=? ";		
		try {
			pstm=con.prepareStatement(sql);
			pstm.setString(1, dto.getR_route());
			pstm.setInt(2, dto.getR_tid());
			pstm.setString(3, dto.getR_mid());
			pstm.setString(4, dto.getR_day());
			res = pstm.executeUpdate();
			
			if(res > 0) commit(con); else rollback(con);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstm, con);
		}
		return res;
	}
	public RouteDto selectOneRoute(RouteDto dto) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		ResultSet rs=null;
		RouteDto rdto=null;
		
		String sql=" SELECT * FROM ROUTE WHERE R_TID=? AND R_MID=? AND R_DAY=? ";	
		try {
			pstm=con.prepareStatement(sql);
			
			pstm.setInt(1, dto.getR_tid());
			pstm.setString(2, dto.getR_mid());
			pstm.setString(3, dto.getR_day());
			
			rs=pstm.executeQuery();
			while(rs.next()) {
				rdto=new RouteDto(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4));	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs,pstm, con);
		}
		return rdto;
	}
	public int deleteRoute(RouteDto dto) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		int res=0;
		String sql = " DELETE FROM ROUTE WHERE R_TID=? AND R_MID=? AND R_DAY=? ";		
		try {
			pstm=con.prepareStatement(sql);
			pstm.setInt(1, dto.getR_tid());
			pstm.setString(2, dto.getR_mid());
			pstm.setString(3, dto.getR_day());
			res = pstm.executeUpdate();
			
			if(res > 0) commit(con); else rollback(con);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstm, con);
		}
		return res;
	}
	
	public int selectRouteCnt(RouteDto dto) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		ResultSet rs=null;
		int res=0;
	
		String sql=" SELECT * FROM ROUTE WHERE R_TID=? AND R_MID=? ";	
		
		try {
			pstm=con.prepareStatement(sql);
			pstm.setInt(1, dto.getR_tid());
			pstm.setString(2, dto.getR_mid());
			
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
}
