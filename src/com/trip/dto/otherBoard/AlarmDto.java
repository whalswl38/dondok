package com.trip.dto.otherBoard;

import java.util.Date;

public class AlarmDto {

	int al_no, al_rno, al_cate;
	String al_id, al_aflag, alc_cateName, al_rtitle;
	Date al_date;
	@Override
	public String toString() {
		return "AlarmDto [al_no=" + al_no + ", al_rno=" + al_rno + ", al_cate=" + al_cate + ", al_id=" + al_id
				+ ", al_aflag=" + al_aflag + ", alc_cateName=" + alc_cateName + ", al_date=" + al_date + "]";
	}
	
	
	public String getAl_rtitle() {
		return al_rtitle;
	}


	public void setAl_rtitle(String al_rtitle) {
		this.al_rtitle = al_rtitle;
	}


	public int getAl_no() {
		return al_no;
	}
	public void setAl_no(int al_no) {
		this.al_no = al_no;
	}
	public int getAl_rno() {
		return al_rno;
	}
	public void setAl_rno(int al_rno) {
		this.al_rno = al_rno;
	}
	public int getAl_cate() {
		return al_cate;
	}
	public void setAl_cate(int al_cate) {
		this.al_cate = al_cate;
	}
	public String getAl_id() {
		return al_id;
	}
	public void setAl_id(String al_id) {
		this.al_id = al_id;
	}
	public String getAl_aflag() {
		return al_aflag;
	}
	public void setAl_aflag(String al_aflag) {
		this.al_aflag = al_aflag;
	}
	public String getAlc_cateName() {
		return alc_cateName;
	}
	public void setAlc_cateName(String alc_cateName) {
		this.alc_cateName = alc_cateName;
	}
	public Date getAl_date() {
		return al_date;
	}
	public void setAl_date(Date al_date) {
		this.al_date = al_date;
	}
	public AlarmDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AlarmDto(int al_no, int al_rno, int al_cate, String al_id, String al_aflag, String alc_cateName,
			Date al_date) {
		super();
		this.al_no = al_no;
		this.al_rno = al_rno;
		this.al_cate = al_cate;
		this.al_id = al_id;
		this.al_aflag = al_aflag;
		this.alc_cateName = alc_cateName;
		this.al_date = al_date;
	}
	
	
	
	
}
