package trip.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static trip.db.JDBCTemplete.*;
import trip.dto.TeamMemberDto;

public class TeamMemberDao {
	public List<TeamMemberDto> teamMemberList(int tm_tid) {
		List<TeamMemberDto> list = new ArrayList<TeamMemberDto>();

		Connection con = getConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = " SELECT * FROM TEAMMEMBER WHERE TM_TID=? ";

		try {
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, tm_tid);

			rs = pstm.executeQuery();

			while (rs.next()) {
				TeamMemberDto dto = new TeamMemberDto();

				dto.setTm_tid(rs.getInt(1));
				dto.setTm_uid(rs.getString(2));
				dto.setTm_depflag(rs.getString(3));
				dto.setTm_stage(rs.getInt(4));
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, pstm, con);
		}
		return list;
	}

	public List<TeamMemberDto> teamList(String tm_uid) {
		List<TeamMemberDto> list = new ArrayList<TeamMemberDto>();

		Connection con = getConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = " SELECT * FROM TEAMMEMBER WHERE TM_UID=? ";

		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, tm_uid);

			rs = pstm.executeQuery();

			while (rs.next()) {
				TeamMemberDto dto = new TeamMemberDto();

				dto.setTm_tid(rs.getInt(1));
				dto.setTm_uid(rs.getString(2));
				dto.setTm_depflag(rs.getString(3));
				dto.setTm_stage(rs.getInt(4));
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, pstm, con);
		}
		return list;
	}

	public String idChk(String id) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = " SELECT M_ID FROM MYMEMBER WHERE M_ID = ? AND M_FLAG='Y' ";
		String res = null;

		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, id);

			rs = pstm.executeQuery();
			rs.next();
			res = rs.getString(1);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, pstm, con);
		}

		return res;
	}

	public int insertMember(String userId, int teamId) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		int res = 0;
		String sql = " INSERT INTO TEAMMEMBER VALUES(?,?,'N',1) ";

		try {
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, teamId);
			pstm.setString(2, userId);

			res = pstm.executeUpdate();
			
			if(res > 0) commit(con); else rollback(con);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstm, con);
		}
		return res;
	}
	public int insertAddMember(TeamMemberDto dto) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		int res = 0;
		String sql = " INSERT INTO TEAMMEMBER VALUES(?,?,'N',?) ";

		try {
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, dto.getTm_tid());
			pstm.setString(2, dto.getTm_uid());
			pstm.setInt(3, dto.getTm_stage());

			res = pstm.executeUpdate();
			
			if(res > 0) commit(con); else rollback(con);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstm, con);
		}
		return res;
	}

	public int deleteMember(TeamMemberDto dto) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		int res = 0;
		String sql = " DELETE FROM TEAMMEMBER WHERE TM_TID=? AND TM_UID=? ";

		try {
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, dto.getTm_tid());
			pstm.setString(2, dto.getTm_uid());
			res = pstm.executeUpdate();
			
			if(res > 0) commit(con); else rollback(con);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstm, con);
		}
		return res;
	}

	public int updatePayment(TeamMemberDto dto) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		int res = 0;
		String sql = " UPDATE TEAMMEMBER SET TM_DEPFLAG='Y' WHERE TM_TID=? AND TM_UID=? ";

		try {
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, dto.getTm_tid());
			pstm.setString(2, dto.getTm_uid());
			res = pstm.executeUpdate();
			
			if(res > 0) commit(con); else rollback(con);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstm, con);
		}
		return res;
	}
	public int upStage(TeamMemberDto dto) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		int res = 0;
		String sql = " UPDATE TEAMMEMBER SET TM_STAGE=? WHERE TM_TID=? AND TM_UID=? ";

		try {
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, dto.getTm_stage());
			pstm.setInt(2, dto.getTm_tid());
			pstm.setString(3, dto.getTm_uid());
			res = pstm.executeUpdate();
			
			if(res > 0) commit(con); else rollback(con);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstm, con);
		}
		return res;
	}
	public TeamMemberDto selectOneMember(int tm_tid, String tm_uid) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = " SELECT * FROM TEAMMEMBER WHERE TM_TID=? AND TM_UID=? ";
		TeamMemberDto dto = new TeamMemberDto();
		
		try {
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, tm_tid);
			pstm.setString(2, tm_uid);
			rs = pstm.executeQuery();

			while (rs.next()) {
				dto.setTm_tid(rs.getInt(1));
				dto.setTm_uid(rs.getString(2));
				dto.setTm_depflag(rs.getString(3));
				dto.setTm_stage(rs.getInt(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, pstm, con);
		}
		return dto;
	}
}
