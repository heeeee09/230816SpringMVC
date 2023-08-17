package com.aug.spring.notice.service;

import java.util.List;

import com.aug.spring.notice.domain.Notice;

public interface NoticeService {

	/**
	 * 공지사항 등록 service
	 * @param notice
	 * @return
	 */
	int insertNotice(Notice notice);

	/**
	 * 공지사항 목록 조회
	 * @return
	 */
	List<Notice> selectNoticeList();

}
