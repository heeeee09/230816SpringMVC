package com.aug.spring.board.store;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.aug.spring.board.domain.Board;
import com.aug.spring.board.domain.PageInfo;

public interface BoardStore {

	/**
	 * 게시글 등록 store
	 * @param session
	 * @param board
	 * @return
	 */
	int insertBoard(SqlSession session, Board board);

	/**
	 * 총 게시글수
	 * @param session
	 * @return
	 */
	int selectListCount(SqlSession session);

	/**
	 * 게시글 전체 조회 store
	 * @param session
	 * @param pInfo
	 * @return
	 */
	List<Board> selectBoardList(SqlSession session, PageInfo pInfo);

	/**
	 * 게시글 상세 조회 store
	 * @param session
	 * @param boardNo
	 * @return
	 */
	Board selectOneBoardByNo(SqlSession session, int boardNo);

}
