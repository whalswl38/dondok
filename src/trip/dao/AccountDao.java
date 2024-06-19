package trip.dao;

import static trip.db.JDBCTemplete.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import trip.dto.AccountDto;

public class AccountDao {
	public int insertAcc(AccountDto dto) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		int res=0;
		String sql = " INSERT INTO ACCOUNT VALUES(?,?,?,?,'0','0') ";		
		try {
			pstm=con.prepareStatement(sql);
			pstm.setInt(1, dto.getAcc_tid());
			pstm.setString(2, dto.getAcc_holder());
			pstm.setString(3, dto.getAcc_bank());
			pstm.setString(4, dto.getAcc_num());
			
			res = pstm.executeUpdate();
			if(res > 0) commit(con); else rollback(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstm, con);
		}
		return res;
	}
	public int updateAcc(AccountDto dto) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		int res=0;
		String sql = " UPDATE ACCOUNT SET ACC_HOLDER=?,ACC_BANK=?, ACC_NUM=?, ACC_PRICE=? WHERE ACC_TID=? ";
		try {
			pstm=con.prepareStatement(sql);
			pstm.setString(1, dto.getAcc_holder());
			pstm.setString(2, dto.getAcc_bank());
			pstm.setString(3, dto.getAcc_num());
			pstm.setString(4, dto.getAcc_price());
			pstm.setInt(5, dto.getAcc_tid());
			
			res = pstm.executeUpdate();
			if(res > 0) commit(con); else rollback(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstm, con);
		}
		return res;
	}
	public AccountDto selectAcc(int acc_tid) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		ResultSet rs=null;
		AccountDto adto=null;
		
		String sql=" SELECT * FROM ACCOUNT WHERE ACC_TID=? ";
		try {
			pstm=con.prepareStatement(sql);
			pstm.setInt(1,acc_tid);
			rs=pstm.executeQuery();
			while(rs.next()) {
			adto=new AccountDto(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6));	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs,pstm, con);
		}
		return adto;
	}
	
	public int upMileage(String mileage,int tid){
		Connection con = getConnection();
		PreparedStatement pstm = null;
		int res=0;
		String sql=" UPDATE ACCOUNT SET ACC_MILEAGE=? WHERE ACC_TID=? ";
		
		try {
			pstm=con.prepareStatement(sql);
			pstm.setString(1, mileage);
			pstm.setInt(2,tid);
			
			res = pstm.executeUpdate();
			commit(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstm, con);
		}
		return res;
	}
}
