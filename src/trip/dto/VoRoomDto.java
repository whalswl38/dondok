package trip.dto;

public class VoRoomDto {
	int voroom_tid;
	String voroom_mid;
	int voroom_id;
	String voroom_day;
	
	public VoRoomDto() {
		super();
	}
	public VoRoomDto(int voroom_tid, String voroom_mid, String voroom_day) {
		super();
		this.voroom_tid = voroom_tid;
		this.voroom_mid = voroom_mid;
		this.voroom_day = voroom_day;
	}
	public VoRoomDto(int voroom_tid, int voroom_id, String voroom_day) {
		super();
		this.voroom_tid = voroom_tid;
		this.voroom_id = voroom_id;
		this.voroom_day = voroom_day;
	}
	public VoRoomDto(int voroom_tid, String voroom_mid, int voroom_id, String voroom_day) {
		super();
		this.voroom_tid = voroom_tid;
		this.voroom_mid = voroom_mid;
		this.voroom_id = voroom_id;
		this.voroom_day = voroom_day;
	}
	
	public int getVoroom_tid() {
		return voroom_tid;
	}
	public void setVoroom_tid(int voroom_tid) {
		this.voroom_tid = voroom_tid;
	}
	public String getVoroom_mid() {
		return voroom_mid;
	}
	public void setVoroom_mid(String voroom_mid) {
		this.voroom_mid = voroom_mid;
	}
	public int getVoroom_id() {
		return voroom_id;
	}
	public void setVoroom_id(int voroom_id) {
		this.voroom_id = voroom_id;
	}
	public String getVoroom_day() {
		return voroom_day;
	}
	public void setVoroom_day(String voroom_day) {
		this.voroom_day = voroom_day;
	}

	
}
