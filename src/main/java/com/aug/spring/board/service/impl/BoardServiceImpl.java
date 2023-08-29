package com.aug.spring.board.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aug.spring.board.domain.Board;
import com.aug.spring.board.domain.PageInfo;
import com.aug.spring.board.service.BoardService;
import com.aug.spring.board.store.BoardStore;

@Service
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	private SqlSession session;
	@Autowired
	private BoardStore bStore;

	@Override
	public int insertBoard(Board board) {
		int result = bStore.insertBoard(session, board); 
		return result;
	}

	@Override
	public int getListCount() {
		int result = bStore.selectListCount(session);
		return result;
	}

	@Override
	public List<Board> selectBoardList(PageInfo pInfo) {
		List<Board> bList = bStore.selectBoardList(session, pInfo);
		return bList;
	}

	@Override
	public Board selectOneBoardByNo(int boardNo) {
		Board bPost = bStore.selectOneBoardByNo(session, boardNo);
		return bPost;
	}

	@Override
	public int deleteBoard(Board board) {
		int result = bStore.deleteBoard(session, board);
		return result;
	}

	@Override
	public int updateBoard(Board board) {
		int result = bStore.updateBoard(session, board);
		return result;
	}

}
