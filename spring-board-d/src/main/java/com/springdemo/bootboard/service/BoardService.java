package com.springdemo.bootboard.service;

import java.util.List;

import com.springdemo.bootboard.vo.Board;

public interface BoardService {

	void writeBoard(Board board) throws Exception;

	List<Board> findBoardList();

	Board findBoardByBoardIdx(int boardIdx);

	void updateBoardByBoardIdx(Board board);

	void deleteBoardByBoardIdx(Board board);
	
}
