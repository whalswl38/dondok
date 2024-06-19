package com.trip.dto.comment;

import java.util.Date;

public class CategoryCommentDto {

	int rv_no, rv_crno, rv_pno, level, isleaf;
	String rv_id, rv_content, rv_delflag;
	Date rv_date;
	
	
	
	
	
	public CategoryCommentDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CategoryCommentDto(int rv_no, int rv_crno, int rv_pno, int level, int isleaf, String rv_id,
			String rv_content, String rv_delflag, Date rv_date) {
		super();
		this.rv_no = rv_no;
		this.rv_crno = rv_crno;
		this.rv_pno = rv_pno;
		this.level = level;
		this.isleaf = isleaf;
		this.rv_id = rv_id;
		this.rv_content = rv_content;
		this.rv_delflag = rv_delflag;
		this.rv_date = rv_date;
	}
	public int getIsleaf() {
		return isleaf;
	}
	public void setIsleaf(int isleaf) {
		this.isleaf = isleaf;
	}
	
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getRv_no() {
		return rv_no;
	}
	public void setRv_no(int rv_no) {
		this.rv_no = rv_no;
	}
	public int getRv_crno() {
		return rv_crno;
	}
	public void setRv_crno(int rv_crno) {
		this.rv_crno = rv_crno;
	}
	public int getRv_pno() {
		return rv_pno;
	}
	public void setRv_pno(int rv_pno) {
		this.rv_pno = rv_pno;
	}
	public String getRv_id() {
		return rv_id;
	}
	public void setRv_id(String rv_id) {
		this.rv_id = rv_id;
	}
	public String getRv_content() {
		return rv_content;
	}
	public void setRv_content(String rv_content) {
		this.rv_content = rv_content;
	}
	public String getRv_delflag() {
		return rv_delflag;
	}
	public void setRv_delflag(String rv_delflag) {
		this.rv_delflag = rv_delflag;
	}
	public Date getRv_date() {
		return rv_date;
	}
	public void setRv_date(Date rv_date) {
		this.rv_date = rv_date;
	}

	
	
	
}
