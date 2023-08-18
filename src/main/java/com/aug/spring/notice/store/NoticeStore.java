package com.aug.spring.notice.store;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.aug.spring.notice.domain.Notice;
import com.aug.spring.notice.domain.PageInfo;

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
	 * @param pInfo 
	 * @return
	 */
	List<Notice> selectAllList(SqlSession session, PageInfo pInfo);

	/**
	 * 공지사항 갯수 조회 store
	 * @param session
	 * @return
	 */
	int selectListCount(SqlSession session);

	/**
	 * 공지사항 전체 조회 service
	 * @param session
	 * @param searchKeyword
	 * @return
	 */
	List<Notice> selectNoticesByAll(SqlSession session, String searchKeyword);

	/**
	 * 공지사항 작성자로 조회 store
	 * @param session
	 * @param searchKeyword
	 * @return
	 */
	List<Notice> selectNoticesByWriter(SqlSession session, String searchKeyword);

	/**
	 * 공지사항 제목으로 조회 store
	 * @param session
	 * @param searchKeyword
	 * @return
	 */
	List<Notice> selectNoticesByTitle(SqlSession session, String searchKeyword);

	/**
	 * 공지사항 내용으로 조회 store
	 * @param session
	 * @param searchKeyword
	 * @return
	 */
	List<Notice> selectNoticesByContent(SqlSession session, String searchKeyword);

	/**
	 * 공지사항 조건에 따른 키워드로 검색 store
	 * @param session
	 * @param searchCondition
	 * @param searchKeyword
	 * @return
	 */
	List<Notice> searchNoticesByKeyword(SqlSession session, PageInfo pInfo, Map<String, String> paramMap);

	/**
	 * 공지사항 검색 게시물 전체 갯수 store
	 * @param session
	 * @param paramMap
	 * @return
	 */
	int selectListCount(SqlSession session, Map<String, String> paramMap);

}
