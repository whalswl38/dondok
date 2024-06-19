package com.trip.dto.categoryreview;

import java.util.Date;

public class CategoryReviewDto {

	String  cr_id, cr_title, cr_contents, cr_delflag, cr_category, cr_path, cr_placeid;
	int cr_no,  cr_count, cr_commentCount;
	Date cr_date;
	
	
	
	
	@Override
	public String toString() {
		return "CategoryReviewDto [cr_id=" + cr_id + ", cr_title=" + cr_title + ", cr_contents=" + cr_contents
				+ ", cr_delflag=" + cr_delflag + ", cr_category=" + cr_category + ", cr_path=" + cr_path
				+ ", cr_placeid=" + cr_placeid + ", cr_no=" + cr_no + ", cr_count=" + cr_count + ", cr_commentCount="
				+ cr_commentCount + ", cr_date=" + cr_date + "]";
	}
	public int getCr_commentCount() {
		return cr_commentCount;
	}
	public void setCr_commentCount(int cr_commentCount) {
		this.cr_commentCount = cr_commentCount;
	}
	public CategoryReviewDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getCr_id() {
		return cr_id;
	}
	public void setCr_id(String cr_id) {
		this.cr_id = cr_id;
	}
	public String getCr_title() {
		return cr_title;
	}
	public void setCr_title(String cr_title) {
		this.cr_title = cr_title;
	}
	public String getCr_contents() {
		return cr_contents;
	}
	public void setCr_contents(String cr_contents) {
		this.cr_contents = cr_contents;
	}
	public String getCr_delflag() {
		return cr_delflag;
	}
	public void setCr_delflag(String cr_delflag) {
		this.cr_delflag = cr_delflag;
	}
	public String getCr_category() {
		return cr_category;
	}
	public void setCr_category(String cr_category) {
		this.cr_category = cr_category;
	}
	public String getCr_path() {
		return cr_path;
	}
	public void setCr_path(String cr_path) {
		this.cr_path = cr_path;
	}
	public String getCr_placeid() {
		return cr_placeid;
	}
	public void setCr_placeid(String cr_placeid) {
		this.cr_placeid = cr_placeid;
	}
	public int getCr_no() {
		return cr_no;
	}
	public void setCr_no(int cr_no) {
		this.cr_no = cr_no;
	}
	public int getCr_count() {
		return cr_count;
	}
	public void setCr_count(int cr_count) {
		this.cr_count = cr_count;
	}
	public Date getCr_date() {
		return cr_date;
	}
	public void setCr_date(Date cr_date) {
		this.cr_date = cr_date;
	}
	
	
}
