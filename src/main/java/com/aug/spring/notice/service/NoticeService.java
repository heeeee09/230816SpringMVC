package com.aug.spring.notice.service;

import java.util.List;
import java.util.Map;

import com.aug.spring.notice.domain.Notice;
import com.aug.spring.notice.domain.PageInfo;

public interface NoticeService {

	/**
	 * 공지사항 등록 service
	 * @param notice
	 * @return
	 */
	int insertNotice(Notice notice);

	/**
	 * 공지사항 목록 조회
	 * @param pInfo 
	 * @return
	 */
	List<Notice> selectNoticeList(PageInfo pInfo);

	/**
	 * 공지사항 갯수 조회 service
	 * @return
	 */
	int getListCount();

	/**
	 * 공지사항 전체 검색 service
	 * @param searchKeyword
	 * @return
	 */
	List<Notice> searchNoticeByAll(String searchKeyword);

	/**
	 * 공지사항 작성자로 조회 service
	 * @param searchKeyword
	 * @return
	 */
	List<Notice> searchNoticeByWriter(String searchKeyword);

	/**
	 * 공지사항 제목으로 조회 service
	 * @param searchKeyword
	 * @return
	 */
	List<Notice> searchNoticeByTitle(String searchKeyword);

	/**
	 * 공지사항 내용으로 검색 service
	 * @param searchKeyword
	 * @return
	 */
	List<Notice> searchNoticeByContent(String searchKeyword);

	/**
	 * 공지사항 조건에 따른 키워드로 검색 service
	 * @param pInfo 
	 * @param searchCondition
	 * @param searchKeyword
	 * @return
	 */
	List<Notice> searchNoticesByKeyword(PageInfo pInfo, Map<String, String> paramMap);

	/**
	 * 공지사항 검색 게시물 전체 갯수 service
	 * @param paramMap
	 * @return
	 */
	int getListCount(Map<String, String> paramMap);

}
