package com.trip.dto.review;

public class TeamMemberDto {

	int tm_tid,tm_stage;
	String tm_uid, tm_depflag;
	public TeamMemberDto(int tm_tid, int tm_stage, String tm_uid, String tm_depflag) {
		super();
		this.tm_tid = tm_tid;
		this.tm_stage = tm_stage;
		this.tm_uid = tm_uid;
		this.tm_depflag = tm_depflag;
	}
	public TeamMemberDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "TeamMemberDto [tm_tid=" + tm_tid + ", tm_stage=" + tm_stage + ", tm_uid=" + tm_uid + ", tm_depflag="
				+ tm_depflag + "]";
	}
	public int getTm_tid() {
		return tm_tid;
	}
	public void setTm_tid(int tm_tid) {
		this.tm_tid = tm_tid;
	}
	public int getTm_stage() {
		return tm_stage;
	}
	public void setTm_stage(int tm_stage) {
		this.tm_stage = tm_stage;
	}
	public String getTm_uid() {
		return tm_uid;
	}
	public void setTm_uid(String tm_uid) {
		this.tm_uid = tm_uid;
	}
	public String getTm_depflag() {
		return tm_depflag;
	}
	public void setTm_depflag(String tm_depflag) {
		this.tm_depflag = tm_depflag;
	}
	
	
	
	
}
