package com.aug.spring.notice.store.logic;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.aug.spring.notice.domain.Notice;
import com.aug.spring.notice.store.NoticeStore;

@Repository
public class NoticeStoreLogic implements NoticeStore{

	@Override
	public int insertNotice(SqlSession session, Notice notice) {
		int result = session.insert("NoticeMapper.insertNotice", notice);
		return result;
	}

	/**
	 *
	 */
	@Override
	public List<Notice> selectAllList(SqlSession session) {
		List<Notice> nList = session.selectList("NoticeMapper.selectNoticeList");
		return nList;
	}

}
