package com.trip.dto.review.festival;

import java.util.Date;

public class FestivalDto {
	private Date StartDate;
	private Date EndDate;
	private String address;
	private String title;
	
	public FestivalDto(){
	}
	
	

	public FestivalDto(Date startDate, Date endDate) {
		super();
		StartDate = startDate;
		EndDate = endDate;
	}



	public FestivalDto(Date startDate, Date endDate, String address, String title) {
		StartDate = startDate;
		EndDate = endDate;
		this.address = address;
		this.title = title;
	}

	public Date getStartDate() {
		return StartDate;
	}

	public void setStartDate(Date startDate) {
		StartDate = startDate;
	}

	public Date getEndDate() {
		return EndDate;
	}

	public void setEndDate(Date endDate) {
		EndDate = endDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	
}
