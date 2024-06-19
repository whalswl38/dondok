package trip.dto;

public class RegionDto {
int r_tid;
String r_day;
String r_location;

public RegionDto() {
	super();
}

public RegionDto(int r_tid, String r_day) {
	super();
	this.r_tid = r_tid;
	this.r_day = r_day;
}

public RegionDto(int r_tid, String r_day, String r_location) {
	super();
	this.r_tid = r_tid;
	this.r_day = r_day;
	this.r_location = r_location;
}

public int getR_tid() {
	return r_tid;
}

public void setR_tid(int r_tid) {
	this.r_tid = r_tid;
}

public String getR_day() {
	return r_day;
}

public void setR_day(String r_day) {
	this.r_day = r_day;
}

public String getR_location() {
	return r_location;
}

public void setR_location(String r_location) {
	this.r_location = r_location;
}
}
