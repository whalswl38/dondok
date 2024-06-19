package trip.dto;

import java.util.Date;

public class TeamDto {
int t_id;
String t_name;
String t_leaderId;
String t_days;
Date t_startDate;
Date t_endDate;
int t_stage;
String t_flag;

public TeamDto() {
	super();
}



@Override
public String toString() {
	return "TeamDto [t_id=" + t_id + ", t_name=" + t_name + ", t_leaderId=" + t_leaderId + ", t_days=" + t_days
			+ ", t_startDate=" + t_startDate + ", t_endDate=" + t_endDate + ", t_stage=" + t_stage + ", t_flag="
			+ t_flag + "]";
}



public TeamDto(int t_id, String t_name, String t_leaderId, String t_days, Date t_startDate, Date t_endDate, int t_stage,
		String t_flag) {
	super();
	this.t_id = t_id;
	this.t_name = t_name;
	this.t_leaderId = t_leaderId;
	this.t_days = t_days;
	this.t_startDate = t_startDate;
	this.t_endDate = t_endDate;
	this.t_stage = t_stage;
	this.t_flag = t_flag;
}
public int getT_id() {
	return t_id;
}
public void setT_id(int t_id) {
	this.t_id = t_id;
}
public String getT_name() {
	return t_name;
}
public void setT_name(String t_name) {
	this.t_name = t_name;
}
public String getT_leaderId() {
	return t_leaderId;
}
public void setT_leaderId(String t_leaderId) {
	this.t_leaderId = t_leaderId;
}
public String getT_days() {
	return t_days;
}
public void setT_days(String t_days) {
	this.t_days = t_days;
}
public Date getT_startDate() {
	return t_startDate;
}
public void setT_startDate(Date t_endDate) {
	this.t_startDate = t_startDate;
}
public Date getT_endDate() {
	return t_endDate;
}
public void setT_endDate(Date t_endDate) {
	this.t_endDate = t_endDate;
}
public int getT_stage() {
	return t_stage;
}
public void setT_stage(int t_stage) {
	this.t_stage = t_stage;
}
public String getT_flag() {
	return t_flag;
}
public void setT_flag(String t_flag) {
	this.t_flag = t_flag;
}

}
