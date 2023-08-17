package com.aug.spring.notice.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aug.spring.notice.domain.Notice;
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
	public List<Notice> selectNoticeList() {
		List<Notice> nList = nStore.selectAllList(session);
		return nList;
	}

}
