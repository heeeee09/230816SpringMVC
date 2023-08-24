package com.aug.spring.board.domain;

public class PageInfo {

	private int currentPage;
	private int totalCount;
	private int naviTotalCount;
	private int recordCountPerPage;
	private int naviVCountPerPage;
	private int startNavi;
	private int endNavi;
	
	public PageInfo() {
	}

	public PageInfo(int currentPage, int totalCount, int naviTotalCount, int recordCountPerPage, int naviVCountPerPage,
			int startNavi, int endNavi) {
		super();
		this.currentPage = currentPage;
		this.totalCount = totalCount;
		this.naviTotalCount = naviTotalCount;
		this.recordCountPerPage = recordCountPerPage;
		this.naviVCountPerPage = naviVCountPerPage;
		this.startNavi = startNavi;
		this.endNavi = endNavi;
	}

	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getNaviTotalCount() {
		return naviTotalCount;
	}
	public void setNaviTotalCount(int naviTotalCount) {
		this.naviTotalCount = naviTotalCount;
	}
	public int getRecordCountPerPage() {
		return recordCountPerPage;
	}
	public void setRecordCountPerPage(int recordCountPerPage) {
		this.recordCountPerPage = recordCountPerPage;
	}
	public int getNaviVCountPerPage() {
		return naviVCountPerPage;
	}
	public void setNaviVCountPerPage(int naviVCountPerPage) {
		this.naviVCountPerPage = naviVCountPerPage;
	}
	public int getStartNavi() {
		return startNavi;
	}
	public void setStartNavi(int startNavi) {
		this.startNavi = startNavi;
	}
	public int getEndNavi() {
		return endNavi;
	}
	public void setEndNavi(int endNavi) {
		this.endNavi = endNavi;
	}

	@Override
	public String toString() {
		return "PageInfo [현재페이지=" + currentPage + ", 총게시글수=" + totalCount + ", 네비총갯수="
				+ naviTotalCount + ", 한 페이지당 게시글수= " + recordCountPerPage + ", 한 페이지당 네비 갯수="
				+ naviVCountPerPage + ", 시작네비=" + startNavi + ", 종료네비=" + endNavi + "]";
	}
	
	
}
