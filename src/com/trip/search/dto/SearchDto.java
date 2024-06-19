package com.trip.search.dto;

public class SearchDto {
	private String myid;
	private String search;
	
	public SearchDto() {}
	
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
