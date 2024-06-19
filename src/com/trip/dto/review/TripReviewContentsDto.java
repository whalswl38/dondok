package com.trip.dto.review;

import java.util.Date;

public class TripReviewContentsDto {

	String tvc_contents, tvc_path, tvc_delflag, tvc_userid, tvc_title;
	int tvc_no, tvc_day, tvc_tvno, tvc_reviewid, tvc_routeid ;
	Date tvc_date, tvc_modifydate;
	
	public TripReviewContentsDto() {
		super();
		// TODO Auto-generated constructor stub
	}


	
	


	@Override
	public String toString() {
		return "TripReviewContentsDto [tvc_contents=" + tvc_contents + ", tvc_path=" + tvc_path + ", tvc_delflag="
				+ tvc_delflag + ", tvc_userid=" + tvc_userid + ", tvc_title=" + tvc_title + ", tvc_no=" + tvc_no
				+ ", tvc_day=" + tvc_day + ", tvc_tvno=" + tvc_tvno + ", tvc_reviewid=" + tvc_reviewid
				+ ", tvc_routeid=" + tvc_routeid + ", tvc_date=" + tvc_date + ", tvc_modifydate=" + tvc_modifydate
				+ "]";
	}






	public String getTvc_title() {
		return tvc_title;
	}






	public void setTvc_title(String tvc_title) {
		this.tvc_title = tvc_title;
	}






	public String getTvc_userid() {
		return tvc_userid;
	}






	public void setTvc_userid(String tvc_userid) {
		this.tvc_userid = tvc_userid;
	}



	public String getTvc_delflag() {
		return tvc_delflag;
	}



	public void setTvc_delflag(String tvc_delflag) {
		this.tvc_delflag = tvc_delflag;
	}



	public String getTvc_contents() {
		return tvc_contents;
	}

	public void setTvc_contents(String tvc_contents) {
		this.tvc_contents = tvc_contents;
	}

	public String getTvc_path() {
		return tvc_path;
	}

	public void setTvc_path(String tvc_path) {
		this.tvc_path = tvc_path;
	}

	public int getTvc_no() {
		return tvc_no;
	}

	public void setTvc_no(int tvc_no) {
		this.tvc_no = tvc_no;
	}

	public int getTvc_day() {
		return tvc_day;
	}

	public void setTvc_day(int tvc_day) {
		this.tvc_day = tvc_day;
	}

	public int getTvc_tvno() {
		return tvc_tvno;
	}

	public void setTvc_tvno(int tvc_tvno) {
		this.tvc_tvno = tvc_tvno;
	}

	public int getTvc_reviewid() {
		return tvc_reviewid;
	}

	public void setTvc_reviewid(int tvc_reviewid) {
		this.tvc_reviewid = tvc_reviewid;
	}

	public int getTvc_routeid() {
		return tvc_routeid;
	}

	public void setTvc_routeid(int tvc_routeid) {
		this.tvc_routeid = tvc_routeid;
	}

	public Date getTvc_date() {
		return tvc_date;
	}

	public void setTvc_date(Date tvc_date) {
		this.tvc_date = tvc_date;
	}

	public Date getTvc_modifydate() {
		return tvc_modifydate;
	}

	public void setTvc_modifydate(Date tvc_modifydate) {
		this.tvc_modifydate = tvc_modifydate;
	}
	
	
}
