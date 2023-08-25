package com.aug.spring.board.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aug.spring.board.domain.Reply;
import com.aug.spring.board.service.ReplyService;
import com.aug.spring.board.store.ReplyStore;

@Service
public class ReplyServiceImpl implements ReplyService{
	
	@Autowired
	private ReplyStore rStore;
	@Autowired
	private SqlSession session;

	@Override
	public int insertReply(Reply reply) {
		int result = rStore.insertReply(session, reply);
		return result;
	}

	@Override
	public int updateReply(Reply reply) {
		int result = rStore.updateReply(session, reply);
		return result;
	}

	@Override
	public List<Reply> selectReplyList(int boardNo) {
		List<Reply> replyList = rStore.selecrReplyList(session, boardNo);
		return replyList;
	}

}
