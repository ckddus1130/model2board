package com.naver.ccy6451.domain;

public class SearchCriteria extends Criteria {
	// 검색 옵션을 저장할 변수를 저장
	private String searchType;
	// 검색어를 저장할 변수
	private String keyword;
	
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	@Override
	public String toString() {
		return "SearchCriteria [searchType=" + searchType + ", keyword=" + keyword + "]";
	}
	
}
