package trip.dto;

public class RouteSelectDto {
int rs_no;
int rs_tno;
String rs_route;
int rs_accdate;


public RouteSelectDto() {
   super();
}

public RouteSelectDto(int rs_tno, int rs_accdate) {
   super();
   this.rs_tno = rs_tno;
   this.rs_accdate = rs_accdate;
}

public RouteSelectDto(int rs_tno, String rs_route, int rs_accdate) {
   super();
   this.rs_tno = rs_tno;
   this.rs_route = rs_route;
   this.rs_accdate = rs_accdate;
}

public RouteSelectDto(int rs_no, int rs_tno, String rs_route, int rs_accdate) {
   super();
   this.rs_no = rs_no;
   this.rs_tno = rs_tno;
   this.rs_route = rs_route;
   this.rs_accdate = rs_accdate;
}
public int getRs_no() {
   return rs_no;
}
public void setRs_no(int rs_no) {
   this.rs_no = rs_no;
}
public int getRs_tno() {
   return rs_tno;
}
public void setRs_tno(int rs_tno) {
   this.rs_tno = rs_tno;
}
public String getRs_route() {
   return rs_route;
}
public void setRs_route(String rs_route) {
   this.rs_route = rs_route;
}
public int getRs_accdate() {
   return rs_accdate;
}
public void setRs_accdate(int rs_accdate) {
   this.rs_accdate = rs_accdate;
}
}