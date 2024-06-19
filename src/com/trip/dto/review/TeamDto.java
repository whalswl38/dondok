package com.trip.dto.review;

import java.util.Date;

public class TeamDto {

	int t_id, t_stage;
	String t_name, t_leaderid, t_days,ft_deadline, t_flag;
	Date  t_startdate, t_enddate;
	public int getT_id() {
		return t_id;
	}
	public void setT_id(int t_id) {
		this.t_id = t_id;
	}
	public int getT_stage() {
		return t_stage;
	}
	public void setT_stage(int t_stage) {
		this.t_stage = t_stage;
	}
	public String getT_name() {
		return t_name;
	}
	public void setT_name(String t_name) {
		this.t_name = t_name;
	}
	public String getT_leaderid() {
		return t_leaderid;
	}
	public void setT_leaderid(String t_leaderid) {
		this.t_leaderid = t_leaderid;
	}
	public String getT_days() {
		return t_days;
	}
	public void setT_days(String t_days) {
		this.t_days = t_days;
	}
	public String getFt_deadline() {
		return ft_deadline;
	}
	public void setFt_deadline(String ft_deadline) {
		this.ft_deadline = ft_deadline;
	}
	public String getT_flag() {
		return t_flag;
	}
	public void setT_flag(String t_flag) {
		this.t_flag = t_flag;
	}
	public Date getT_startdate() {
		return t_startdate;
	}
	public void setT_startdate(Date t_startdate) {
		this.t_startdate = t_startdate;
	}
	public Date getT_enddate() {
		return t_enddate;
	}
	public void setT_enddate(Date t_enddate) {
		this.t_enddate = t_enddate;
	}
	@Override
	public String toString() {
		return "TeamDto [t_id=" + t_id + ", t_stage=" + t_stage + ", t_name=" + t_name + ", t_leaderid=" + t_leaderid
				+ ", t_days=" + t_days + ", ft_deadline=" + ft_deadline + ", t_flag=" + t_flag + ", t_startdate="
				+ t_startdate + ", t_enddate=" + t_enddate + "]";
	}
	public TeamDto(int t_id, int t_stage, String t_name, String t_leaderid, String t_days, String ft_deadline,
			String t_flag, Date t_startdate, Date t_enddate) {
		super();
		this.t_id = t_id;
		this.t_stage = t_stage;
		this.t_name = t_name;
		this.t_leaderid = t_leaderid;
		this.t_days = t_days;
		this.ft_deadline = ft_deadline;
		this.t_flag = t_flag;
		this.t_startdate = t_startdate;
		this.t_enddate = t_enddate;
	}
	public TeamDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
