package com.aug.spring.board.store.logic;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.aug.spring.board.domain.Reply;
import com.aug.spring.board.store.ReplyStore;

@Repository
public class ReplyStoreLogic implements ReplyStore{

	@Override
	public int insertReply(SqlSession session, Reply reply) {
		int result = session.insert("ReplyMapper.insertReply", reply);
		return result;
	}

	@Override
	public int updateReply(SqlSession session, Reply reply) {
		int result = session.update("ReplyMapper.updateReply", reply);
		return result;
	}

	@Override
	public List<Reply> selecrReplyList(SqlSession session, int boardNo) {
		List<Reply> replyList = session.selectList("ReplyMapper.selectRepltList", boardNo);
		return replyList;
	}

}
