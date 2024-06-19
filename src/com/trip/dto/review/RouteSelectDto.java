package com.trip.dto.review;

public class RouteSelectDto {

	int rs_no, rs_tno;
	String rs_route, rs_accdate;
	@Override
	public String toString() {
		return "{\"RouteSelectDto\" : {\"rs_no\" :" + rs_no + ", \"rs_tno\" :" + rs_tno + ", \"rs_route\" : \"" + rs_route + "\", \"rs_accdate\" : "
				+ rs_accdate + "}}";
	}
	public RouteSelectDto(int rs_no, int rs_tno, String rs_route, String rs_accdate) {
		super();
		this.rs_no = rs_no;
		this.rs_tno = rs_tno;
		this.rs_route = rs_route;
		this.rs_accdate = rs_accdate;
	}
	public RouteSelectDto() {
		super();
		// TODO Auto-generated constructor stub
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
	public String getRs_accdate() {
		return rs_accdate;
	}
	public void setRs_accdate(String rs_accdate) {
		this.rs_accdate = rs_accdate;
	}
	
	
	
}
