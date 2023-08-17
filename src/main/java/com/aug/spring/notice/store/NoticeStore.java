package com.aug.spring.notice.store;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.aug.spring.notice.domain.Notice;

public interface NoticeStore {

	/**
	 * 공지사항 등록
	 * @param session
	 * @param notice
	 * @return
	 */
	int insertNotice(SqlSession session, Notice notice);

	/**
	 * 공지사항 목록 조회
	 * @param session
	 * @return
	 */
	List<Notice> selectAllList(SqlSession session);

}
