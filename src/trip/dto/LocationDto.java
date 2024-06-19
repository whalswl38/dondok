package trip.dto;

public class LocationDto {
int loc_id;
int loc_cate;
String loc_name;
String loc_addr;
String loc_x;
String loc_y;
String loc_url;
int loc_tid;
String loc_mid;
String loc_day;
public LocationDto() {
	super();
}
public LocationDto(int loc_tid, String loc_day) {
	super();
	this.loc_tid = loc_tid;
	this.loc_day = loc_day;
}
public LocationDto(int loc_id, int loc_tid, String loc_day) {
	super();
	this.loc_id = loc_id;
	this.loc_tid = loc_tid;
	this.loc_day = loc_day;
}

public LocationDto(int loc_tid, String loc_mid, String loc_day) {
	super();
	this.loc_tid = loc_tid;
	this.loc_mid = loc_mid;
	this.loc_day = loc_day;
}

public LocationDto(int loc_id, int loc_cate, String loc_name, String loc_addr, String loc_x, String loc_y,
		String loc_url, String loc_day) {
	super();
	this.loc_id = loc_id;
	this.loc_cate = loc_cate;
	this.loc_name = loc_name;
	this.loc_addr = loc_addr;
	this.loc_x = loc_x;
	this.loc_y = loc_y;
	this.loc_url = loc_url;
	this.loc_day = loc_day;
}
public LocationDto(int loc_id, int loc_cate, String loc_name, String loc_addr, String loc_x, String loc_y,
		String loc_url, int loc_tid, String loc_mid, String loc_day) {
	super();
	this.loc_id = loc_id;
	this.loc_cate = loc_cate;
	this.loc_name = loc_name;
	this.loc_addr = loc_addr;
	this.loc_x = loc_x;
	this.loc_y = loc_y;
	this.loc_url = loc_url;
	this.loc_tid = loc_tid;
	this.loc_mid = loc_mid;
	this.loc_day = loc_day;
}
public int getLoc_id() {
	return loc_id;
}
public void setLoc_id(int loc_id) {
	this.loc_id = loc_id;
}
public int getLoc_cate() {
	return loc_cate;
}
public void setLoc_cate(int loc_cate) {
	this.loc_cate = loc_cate;
}
public String getLoc_name() {
	return loc_name;
}
public void setLoc_name(String loc_name) {
	this.loc_name = loc_name;
}
public String getLoc_addr() {
	return loc_addr;
}
public void setLoc_addr(String loc_addr) {
	this.loc_addr = loc_addr;
}
public String getLoc_x() {
	return loc_x;
}
public void setLoc_x(String loc_x) {
	this.loc_x = loc_x;
}
public String getLoc_y() {
	return loc_y;
}
public void setLoc_y(String loc_y) {
	this.loc_y = loc_y;
}
public String getLoc_url() {
	return loc_url;
}
public void setLoc_url(String loc_url) {
	this.loc_url = loc_url;
}
public int getLoc_tid() {
	return loc_tid;
}
public void setLoc_tid(int loc_tid) {
	this.loc_tid = loc_tid;
}
public String getLoc_mid() {
	return loc_mid;
}
public void setLoc_mid(String loc_mid) {
	this.loc_mid = loc_mid;
}
public String getLoc_day() {
	return loc_day;
}
public void setLoc_day(String loc_day) {
	this.loc_day = loc_day;
}

}
