package com.springdemo.bootboard.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.springdemo.bootboard.vo.Board;

@Mapper	// 현재 interface의 package name과 interface name이 일치하는 .xml 파일과 Mapping
public interface BoardMapper {

	void insertBoard(Board board);

	List<Board> selectBoardList();

	Board selectBoardByBoardIdx(int boardIdx);

	void increaseHitCount(int boardIdx);

	void updateBoardByBoardIdx(Board board);

	void deleteBoardByBoardIdx(Board board);
	
}
