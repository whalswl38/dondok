package trip.dao;

import static trip.db.JDBCTemplete.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import trip.dto.TeamDto;

public class TeamDao {
	public TeamDto selectOneTeam(int t_id) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		ResultSet rs=null;
		TeamDto tdto=null;
		
		String sql=" SELECT * FROM TEAM WHERE T_ID=? ";
		try {
			pstm=con.prepareStatement(sql);
			pstm.setInt(1,t_id);
			rs=pstm.executeQuery();
			while(rs.next()) {
			tdto=new TeamDto(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getDate("t_startdate"),rs.getDate("t_enddate"),rs.getInt("t_stage"),rs.getString("t_flag"));
			System.out.println(tdto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs,pstm, con);
		}
		return tdto;
	}
	
	public int selectTeamId(String leader) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		ResultSet rs=null;
		int res=0;
	
		String sql=" SELECT T_ID FROM (SELECT * FROM TEAM WHERE T_LEADERID=? ORDER BY T_ID DESC) WHERE ROWNUM=1 ";
		try {
			pstm=con.prepareStatement(sql);
			pstm.setString(1,leader);
			
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
	
	public int updateTeamName(String schedule_name, int t_id) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		int res=0;
		String sql=" UPDATE TEAM SET T_NAME=? WHERE T_ID=? ";
		
		try {
			pstm=con.prepareStatement(sql);
			pstm.setString(1, schedule_name);
			pstm.setInt(2, t_id);
			res = pstm.executeUpdate();
			
			if(res > 0) commit(con); else rollback(con);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstm, con);
		}
		return res;
	}
	
	public int upStage(int tid, int stage) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		int res = 0;
		String sql = " UPDATE TEAM SET T_STAGE=? WHERE T_ID=? ";
		try {
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, stage);
			pstm.setInt(2, tid);
			res = pstm.executeUpdate();
			
			if(res > 0) commit(con); else rollback(con);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstm, con);
		}
		return res;
	}

	public int updateFlag(int tid) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		int res = 0;
		String sql = " UPDATE TEAM SET T_FLAG='Y' WHERE T_ID=? ";
		try {
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, tid);
			res = pstm.executeUpdate();
			
			if(res > 0) commit(con); else rollback(con);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstm, con);
		}
		return res;
	}
	
	public int createTeam(String leaderId, String days) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		int res=0;
		String sql = " INSERT INTO TEAM(T_ID,T_LEADERID,T_DAYS,T_STAGE, T_FLAG) VALUES(TEAMSEQ.NEXTVAL,?,?,0,'N') ";
		
		try {
			pstm=con.prepareStatement(sql);
			pstm.setString(1, leaderId);
			pstm.setString(2, days);
			res = pstm.executeUpdate();
			
			if(res > 0) commit(con); else rollback(con);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstm, con);
		}
		return res;
	}
	
	public int deleteTeam(int tid) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		int res=0;
		String sql = " DELETE FROM TEAM WHERE T_ID=? ";
		
		try {
			pstm=con.prepareStatement(sql);
			pstm.setInt(1, tid);
			res = pstm.executeUpdate();
			
			if(res > 0) commit(con); else rollback(con);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstm, con);
		}
		return res;
	}
	
	public int deleteNull() {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		int res=0;
		String sql = " DELETE FROM TEAM WHERE T_NAME IS NULL ";
		
		try {
			pstm=con.prepareStatement(sql);
			res = pstm.executeUpdate();
			
			if(res > 0) commit(con); else rollback(con);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstm, con);
		}
		return res;
	}
	
	public int updateDays(int tid) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		int res = 0;
		String sql = " UPDATE TEAM SET t_days = (select t_enddate - t_startdate + 1 from team where t_id = ?)  WHERE T_ID=? ";
		try {
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, tid);
			pstm.setInt(2, tid);
			res = pstm.executeUpdate();
			
			if(res > 0) commit(con); else rollback(con);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstm, con);
		}
		return res;
	}
	
	public int updateDate(Date t_enddate, Date t_startdate, int tid) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		int res = 0;
		String sql = " UPDATE TEAM SET t_enddate = ?, t_startdate = ? WHERE T_ID=? ";
		try {
			pstm = con.prepareStatement(sql);
			pstm.setDate(1, new java.sql.Date(t_enddate.getTime()));
			pstm.setDate(2, new java.sql.Date(t_startdate.getTime()));
			pstm.setInt(3, tid);
			res = pstm.executeUpdate();
			
			if(res > 0) commit(con); else rollback(con);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			close(pstm, con);
		}
		
		return res;
	}
}
