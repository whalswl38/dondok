package com.trip.dto.otherBoard;

import java.util.Date;

public class FavoriteDto {

	int f_no, f_pno, f_cate;
	String f_id;
	Date f_date;
	@Override
	public String toString() {
		return "favoriteDto [f_no=" + f_no + ", f_pno=" + f_pno + ", f_cate=" + f_cate + ", f_id=" + f_id + ", f_date="
				+ f_date + "]";
	}
	public FavoriteDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public FavoriteDto(int f_no, int f_pno, int f_cate, String f_id, Date f_date) {
		super();
		this.f_no = f_no;
		this.f_pno = f_pno;
		this.f_cate = f_cate;
		this.f_id = f_id;
		this.f_date = f_date;
	}
	public int getF_no() {
		return f_no;
	}
	public void setF_no(int f_no) {
		this.f_no = f_no;
	}
	public int getF_pno() {
		return f_pno;
	}
	public void setF_pno(int f_pno) {
		this.f_pno = f_pno;
	}
	public int getF_cate() {
		return f_cate;
	}
	public void setF_cate(int f_cate) {
		this.f_cate = f_cate;
	}
	public String getF_id() {
		return f_id;
	}
	public void setF_id(String f_id) {
		this.f_id = f_id;
	}
	public Date getF_date() {
		return f_date;
	}
	public void setF_date(Date f_date) {
		this.f_date = f_date;
	}
	
	
	
}
