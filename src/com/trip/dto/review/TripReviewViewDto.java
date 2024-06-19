package com.trip.dto.review;

import java.util.Date;

public class TripReviewViewDto {

	String tv_title, tv_count, tvc_no, tvc_path, cr_path, default_path, tv_tname;
	Date tv_date;
	int tv_no,tv_teamid;
	@Override
	public String toString() {
		return "TripReviewViewDto [tv_title=" + tv_title + ", tv_count=" + tv_count + ", tvc_no=" + tvc_no
				+ ", tvc_path=" + tvc_path + ", cr_path=" + cr_path + ", default_path=" + default_path + ", tv_date="
				+ tv_date + ", tv_no=" + tv_no + ", tv_teamid=" + tv_teamid + "]";
	}
	
	
	public String getTv_tname() {
		return tv_tname;
	}


	public void setTv_tname(String tv_tname) {
		this.tv_tname = tv_tname;
	}


	public String getTv_title() {
		return tv_title;
	}
	public void setTv_title(String tv_title) {
		this.tv_title = tv_title;
	}
	public String getTv_count() {
		return tv_count;
	}
	public void setTv_count(String tv_count) {
		this.tv_count = tv_count;
	}
	public String getTvc_no() {
		return tvc_no;
	}
	public void setTvc_no(String tvc_no) {
		this.tvc_no = tvc_no;
	}
	public String getTvc_path() {
		return tvc_path;
	}
	public void setTvc_path(String tvc_path) {
		this.tvc_path = tvc_path;
	}
	public String getCr_path() {
		return cr_path;
	}
	public void setCr_path(String cr_path) {
		this.cr_path = cr_path;
	}
	public String getDefault_path() {
		return default_path;
	}
	public void setDefault_path(String default_path) {
		this.default_path = default_path;
	}
	public Date getTv_date() {
		return tv_date;
	}
	public void setTv_date(Date tv_date) {
		this.tv_date = tv_date;
	}
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
	public TripReviewViewDto(String tv_title, String tv_count, String tvc_no, String tvc_path, String cr_path,
			String default_path, Date tv_date, int tv_no, int tv_teamid) {
		super();
		this.tv_title = tv_title;
		this.tv_count = tv_count;
		this.tvc_no = tvc_no;
		this.tvc_path = tvc_path;
		this.cr_path = cr_path;
		this.default_path = default_path;
		this.tv_date = tv_date;
		this.tv_no = tv_no;
		this.tv_teamid = tv_teamid;
	}
	public TripReviewViewDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
