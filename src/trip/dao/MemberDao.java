package trip.dao;

import static trip.db.JDBCTemplete.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import trip.dto.MemberDto;

public class MemberDao {

	public MemberDto selectOneMember(String m_id) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		ResultSet rs=null;
		MemberDto mdto=null;
		
		String sql=" SELECT * FROM MYMEMBER WHERE M_ID=? ";
		try {
			pstm=con.prepareStatement(sql);
			pstm.setString(1,m_id);
			rs=pstm.executeQuery();
			while(rs.next()) {
			mdto=new MemberDto(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8));	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs,pstm, con);
		}
		return mdto;
	}
}
