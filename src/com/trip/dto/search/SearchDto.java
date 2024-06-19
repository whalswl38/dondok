package com.trip.dto.search;

public class SearchDto {
	private int myno;
	private String myid;
	private String search;
	
	public SearchDto() {}
	
	public SearchDto(int myno, String myid, String search) {
		this.myno=myno;
		this.myid=myid;
		this.search=search;
	}
	
	public SearchDto(String myid, String search) {
		this.myid=myid;
		this.search=search;
	}
	
	

	public String getMyid() {
		return myid;
	}

	public void setMyid(String myid) {
		this.myid = myid;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}
	
	

}
