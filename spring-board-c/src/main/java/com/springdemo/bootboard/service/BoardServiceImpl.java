package com.springdemo.bootboard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springdemo.bootboard.mapper.BoardMapper;
import com.springdemo.bootboard.vo.Board;

@Service("boardService")	// == @Component("boardService")
							// == <bean id="boardService" class="...BoardServiceImple">
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	BoardMapper boardMapper;

	@Transactional(rollbackFor = {Exception.class})
	@Override
	public void writeBoard(Board board) throws Exception {		
		boardMapper.insertBoard(board);
		throw new Exception();
	}

	@Override
	public List<Board> findBoardList() {
		
		List<Board> boards = boardMapper.selectBoardList();
		
		return boards;
	}

	@Override
	public Board findBoardByBoardIdx(int boardIdx) {

		boardMapper.increaseHitCount(boardIdx);
		Board board = boardMapper.selectBoardByBoardIdx(boardIdx);
		
		return board;
	}

	@Override
	public void updateBoardByBoardIdx(Board board) {
		boardMapper.updateBoardByBoardIdx(board);
		
	}

	@Override
	public void deleteBoardByBoardIdx(Board board) {
		boardMapper.deleteBoardByBoardIdx(board);
		
	}

}
