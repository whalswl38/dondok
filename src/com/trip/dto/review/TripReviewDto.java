package com.trip.dto.review;

import java.util.Date;

public class TripReviewDto {

	int tv_no, tv_teamid, tv_count;
	String tv_title, tv_delflag;
	Date  tv_date,tv_modifydate;
	public int getTv_no() {
		return tv_no;
	}
	public void setTv_no(int tv_no) {
		this.tv_no = tv_no;
	}
	public int getTv_teamid() {
		return tv_teamid;
	}
	public void setTv_teamid(int tv_teamid) {
		this.tv_teamid = tv_teamid;
	}
	public int getTv_count() {
		return tv_count;
	}
	public void setTv_count(int tv_count) {
		this.tv_count = tv_count;
	}
	public String getTv_title() {
		return tv_title;
	}
	public void setTv_title(String tv_title) {
		this.tv_title = tv_title;
	}
	public String getTv_delflag() {
		return tv_delflag;
	}
	public void setTv_delflag(String tv_delflag) {
		this.tv_delflag = tv_delflag;
	}
	public Date getTv_date() {
		return tv_date;
	}
	public void setTv_date(Date tv_date) {
		this.tv_date = tv_date;
	}
	public Date getTv_modifydate() {
		return tv_modifydate;
	}
	public void setTv_modifydate(Date tv_modifydate) {
		this.tv_modifydate = tv_modifydate;
	}
	@Override
	public String toString() {
		return "TripReviewDto [tv_no=" + tv_no + ", tv_teamid=" + tv_teamid + ", tv_count=" + tv_count + ", tv_title="
				+ tv_title + ", tv_delflag=" + tv_delflag + ", tv_date=" + tv_date + ", tv_modifydate=" + tv_modifydate
				+ "]";
	}
	public TripReviewDto(int tv_no, int tv_teamid, int tv_count, String tv_title, String tv_delflag, Date tv_date,
			Date tv_modifydate) {
		super();
		this.tv_no = tv_no;
		this.tv_teamid = tv_teamid;
		this.tv_count = tv_count;
		this.tv_title = tv_title;
		this.tv_delflag = tv_delflag;
		this.tv_date = tv_date;
		this.tv_modifydate = tv_modifydate;
	}
	public TripReviewDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
