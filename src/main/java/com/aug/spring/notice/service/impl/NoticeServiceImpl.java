package com.aug.spring.notice.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aug.spring.notice.domain.Notice;
import com.aug.spring.notice.domain.PageInfo;
import com.aug.spring.notice.service.NoticeService;
import com.aug.spring.notice.store.NoticeStore;

@Service
public class NoticeServiceImpl implements NoticeService{
	
	@Autowired
	private NoticeStore nStore;
	
	@Autowired
	private SqlSession session;

	@Override
	public int insertNotice(Notice notice) {
		int result = nStore.insertNotice(session, notice);
		return result;
	}

	@Override
	public int updateNotice(Notice notice) {
		int result = nStore.updateNotice(session, notice);
		return result;
	}

	@Override
	public int getListCount() {
		int result = nStore.selectListCount(session);
		return result;
	}

	@Override
	public List<Notice> selectNoticeList(PageInfo pInfo) {
		List<Notice> nList = nStore.selectAllList(session, pInfo);
		return nList;
	}

	@Override
	public List<Notice> searchNoticeByAll(String searchKeyword) {
		List<Notice> searchList = nStore.selectNoticesByAll(session, searchKeyword);
		return searchList;
	}

	@Override
	public List<Notice> searchNoticeByWriter(String searchKeyword) {
		List<Notice> searchList = nStore.selectNoticesByWriter(session, searchKeyword);
		return searchList;
	}

	@Override
	public List<Notice> searchNoticeByTitle(String searchKeyword) {
		List<Notice> searchList = nStore.selectNoticesByTitle(session, searchKeyword);
		return searchList;
	}

	@Override
	public List<Notice> searchNoticeByContent(String searchKeyword) {
		List<Notice> searchList = nStore.selectNoticesByContent(session, searchKeyword);
		return searchList;
	}

	@Override
	public List<Notice> searchNoticesByKeyword(PageInfo pInfo, Map<String, String> paramMap) {
		List<Notice> searchList = nStore.searchNoticesByKeyword(session, pInfo, paramMap);
		return searchList;
	}

	@Override
	public int getListCount(Map<String, String> paramMap) {
		int result = nStore.selectListCount(session, paramMap);
		return result;
	}

	@Override
	public Notice selectNoticeByNo(Integer noticeNo) {
		Notice noticeOne = nStore.selecrNoticeByNo(session, noticeNo);
		return noticeOne;
	}

}
