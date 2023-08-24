package com.aug.spring.board.service;

import java.util.List;

import com.aug.spring.board.domain.Board;
import com.aug.spring.board.domain.PageInfo;

public interface BoardService {

	/**
	 * 게시글 등록 service
	 * @param board
	 * @return
	 */
	int insertBoard(Board board);

	/**
	 * 전체 게시글수
	 * @return
	 */
	int getListCount();

	/**
	 * 게시글 전체 조회 서비스
	 * @param pInfo
	 * @return
	 */
	List<Board> selectBoardList(PageInfo pInfo);

	/**
	 * 게시글 상세조회
	 * @param boardNo
	 * @return
	 */
	Board selectOneBoardByNo(int boardNo);

}
