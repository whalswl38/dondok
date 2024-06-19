package trip.dto;

public class RouteDto {
int r_tid;
String r_mid;
String r_day;
String r_route;
public RouteDto() {
	super();
}

public RouteDto(int r_tid, String r_mid) {
	super();
	this.r_tid = r_tid;
	this.r_mid = r_mid;
}

public RouteDto(int r_tid, String r_mid, String r_day) {
	super();
	this.r_tid = r_tid;
	this.r_mid = r_mid;
	this.r_day = r_day;
}

public RouteDto(int r_tid, String r_mid, String r_day, String r_route) {
	super();
	this.r_tid = r_tid;
	this.r_mid = r_mid;
	this.r_day = r_day;
	this.r_route = r_route;
}
public int getR_tid() {
	return r_tid;
}
public void setR_tid(int r_tid) {
	this.r_tid = r_tid;
}
public String getR_mid() {
	return r_mid;
}
public void setR_mid(String r_mid) {
	this.r_mid = r_mid;
}
public String getR_day() {
	return r_day;
}
public void setR_day(String r_day) {
	this.r_day = r_day;
}
public String getR_route() {
	return r_route;
}
public void setR_route(String r_route) {
	this.r_route = r_route;
}

}
