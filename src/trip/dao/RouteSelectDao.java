package trip.dao;

import static trip.db.JDBCTemplete.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import trip.dto.RouteSelectDto;

public class RouteSelectDao {
   public RouteSelectDto selectOneRoute(RouteSelectDto dto) {
      Connection con = getConnection();
      PreparedStatement pstm = null;
      ResultSet rs=null;
      RouteSelectDto rdto=null;
      
      String sql=" SELECT * FROM ROUTESELECT WHERE RS_TNO=? AND RS_ACCDATE=? ";   
      try {
         pstm=con.prepareStatement(sql);
         
         pstm.setInt(1, dto.getRs_tno());
         pstm.setInt(2, dto.getRs_accdate());
         
         rs=pstm.executeQuery();
         while(rs.next()) {
            rdto=new RouteSelectDto(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getInt(4));   
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }finally {
         close(rs,pstm, con);
      }
      return rdto;
   }
   public int insertRoute(RouteSelectDto dto) {
      Connection con = getConnection();
      PreparedStatement pstm = null;
      int res=0;
      String sql = " INSERT INTO ROUTESELECT VALUES(ROUTESELECT_SEQ.NEXTVAL,?,?,?) ";      
      try {
         pstm=con.prepareStatement(sql);
         pstm.setInt(1, dto.getRs_tno());
         pstm.setString(2, dto.getRs_route().substring(0,dto.getRs_route().length()-1));
         pstm.setInt(3, dto.getRs_accdate());
         
         res = pstm.executeUpdate();

         if(res > 0) commit(con); else rollback(con);
      
      } catch (SQLException e) {
         e.printStackTrace();
      }finally {
         close(pstm, con);
      }
      return res;
   }
   public int updateRoute(RouteSelectDto dto) {
      Connection con = getConnection();
      PreparedStatement pstm = null;
      int res=0;
      String sql = " UPDATE ROUTESELECT SET RS_ROUTE=? WHERE RS_TNO=? AND RS_ACCDATE=? ";      
      try {
         pstm=con.prepareStatement(sql);
         pstm.setString(1, dto.getRs_route().substring(0,dto.getRs_route().length()-1));
         pstm.setInt(2, dto.getRs_tno());
         pstm.setInt(3, dto.getRs_accdate());
         
         res = pstm.executeUpdate();
         
         if(res > 0) commit(con); else rollback(con);
         
      } catch (SQLException e) {
         e.printStackTrace();
      }finally {
         close(pstm, con);
      }
      return res;
   }
}