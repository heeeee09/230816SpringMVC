package com.aug.spring.notice.domain;

// 페이징 처리 관련 VO
public class PageInfo {
	private int currentPage;
	private int recordnaiCountPage;
	private int naviCountPerPage;
	private int startNavi;
	private int endNavi;
	private int totalCount;
	private int naviTotalCount;
	
	public PageInfo() {
		// TODO Auto-generated constructor stub
	}

	public PageInfo(int currentPage, int recordnaiCountPage, int naviCountPerPage, int startNavi, int endNavi,
			int totalCount, int naviTotalCount) {
		super();
		this.currentPage = currentPage;
		this.recordnaiCountPage = recordnaiCountPage;
		this.naviCountPerPage = naviCountPerPage;
		this.startNavi = startNavi;
		this.endNavi = endNavi;
		this.totalCount = totalCount;
		this.naviTotalCount = naviTotalCount;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getRecordnaiCountPage() {
		return recordnaiCountPage;
	}

	public void setRecordnaiCountPage(int recordnaiCountPage) {
		this.recordnaiCountPage = recordnaiCountPage;
	}

	public int getNaviCountPerPage() {
		return naviCountPerPage;
	}

	public void setNaviCountPerPage(int naviCountPerPage) {
		this.naviCountPerPage = naviCountPerPage;
	}

	public int getstartNavi() {
		return startNavi;
	}

	public void setstartNavi(int startNavi) {
		this.startNavi = startNavi;
	}

	public int getEndNavi() {
		return endNavi;
	}

	public void setEndNavi(int endNavi) {
		this.endNavi = endNavi;
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

	@Override
	public String toString() {
		return "PageInfo [currentPage=" + currentPage + ", recordnaiCountPage=" + recordnaiCountPage
				+ ", naviCountPerPage=" + naviCountPerPage + ", startNavi=" + startNavi + ", endNavi=" + endNavi
				+ ", totalCount=" + totalCount + ", naviTotalCount=" + naviTotalCount + "]";
	}
	
}
