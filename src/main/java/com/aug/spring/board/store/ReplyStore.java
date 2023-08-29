package com.aug.spring.board.store;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.aug.spring.board.domain.Reply;

public interface ReplyStore {

	/**
	 * 댓글 등록 service
	 * @param session
	 * @param reply
	 * @return
	 */
	int insertReply(SqlSession session, Reply reply);

	/**
	 * 댓글 수정 store
	 * @param session
	 * @param reply
	 * @return
	 */
	int updateReply(SqlSession session, Reply reply);

	/**
	 * 댓글 출력 store
	 * @param session
	 * @param boardNo
	 * @return
	 */
	List<Reply> selecrReplyList(SqlSession session, int boardNo);

	/**
	 * 댓글 삭제 store
	 * @param session
	 * @param reply
	 * @return
	 */
	int deleteReply(SqlSession session, Reply reply);

}
