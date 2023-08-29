package com.aug.spring.board.service;

import java.util.List;

import com.aug.spring.board.domain.Reply;

public interface ReplyService {

	/**
	 * 게시글 등록 service
	 * @param reply
	 * @return
	 */
	int insertReply(Reply reply);

	/**
	 * 댓글 수정 service
	 * @param reply
	 * @return
	 */
	int updateReply(Reply reply);

	/**
	 * 댓글 출력 service
	 * @param boardNo
	 * @return
	 */
	List<Reply> selectReplyList(int boardNo);

	/**
	 * 댓글 삭제 service
	 * @param reply
	 * @return
	 */
	int deleteReply(Reply reply);

}
