package fullCal_dto;

public class CalendarDto {

	// 유저 아이디
	private String id;
	
	// 일정 이름
	private String title;
	
	// 일정 시작 날짜
	private String startdate;
	
	// 일정 끝나는 날짜
	private String enddate;
	
	// 해당 일정에 대한 색
	private String allday;

	public CalendarDto() {
		super();
	}
	public CalendarDto(String startdate) {
		this.startdate = startdate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public String getAllday() {
		return allday;
	}

	public void setAllday(String allday) {
		this.allday = allday;
	}

	public CalendarDto(String id, String title, String startdate, String enddate, String allday) {
		super();
		this.id = id;
		this.title = title;
		this.startdate = startdate;
		this.enddate = enddate;
		this.allday = allday;
	}

	

}
