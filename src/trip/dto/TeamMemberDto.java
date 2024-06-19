package trip.dto;

public class TeamMemberDto {
	int tm_tid;
	String tm_uid;
	String tm_depflag;//입금 여부
	int tm_stage;//진행 단계
	
	
	
	@Override
	public String toString() {
		return "TeamMemberDto [tm_tid=" + tm_tid + ", tm_uid=" + tm_uid + ", tm_depflag=" + tm_depflag + ", tm_stage="
				+ tm_stage + "]";
	}
	public TeamMemberDto() {
		
	}
	public TeamMemberDto(int tm_tid, String tm_uid) {
		super();
		this.tm_tid = tm_tid;
		this.tm_uid = tm_uid;
	}
	public TeamMemberDto(int tm_tid,String tm_uid, int tm_stage) {
		super();
		this.tm_tid = tm_tid;
		this.tm_uid = tm_uid;
		this.tm_stage = tm_stage;
	}
	public TeamMemberDto(int tm_tid, String tm_uid, String tm_depflag, int tm_stage) {
		super();
		this.tm_tid = tm_tid;
		this.tm_uid = tm_uid;
		this.tm_depflag = tm_depflag;
		this.tm_stage = tm_stage;
	}
	
	public int getTm_tid() {
		return tm_tid;
	}
	public void setTm_tid(int tm_tid) {
		this.tm_tid = tm_tid;
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
	public int getTm_stage() {
		return tm_stage;
	}
	public void setTm_stage(int tm_stage) {
		this.tm_stage = tm_stage;
	}
	
}
