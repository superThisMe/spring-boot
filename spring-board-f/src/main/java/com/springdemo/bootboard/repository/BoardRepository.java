package com.springdemo.bootboard.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.springdemo.bootboard.entity.BoardEntity;
import com.springdemo.bootboard.entity.BoardFileEntity;

// ORM이 지정딘 Entity(Table)에 대해 작성한 Interface에 따라 필요한 작업(CRUD)을 구현하는 Class 자동 생성
public interface BoardRepository extends CrudRepository<BoardEntity, Integer> {

	// find : SELECT
	// AllBy : all data
	// OrderByBoardIdxDecs : ORDER BY board_idx DESC
	List<BoardEntity> findAllByOrderByBoardIdxDesc();
	
	@Query("SELECT file FROM BoardFileEntity file WHERE file_idx = :idx")
	BoardFileEntity findBoardFileByIdx(@Param("idx")int fileIdx);
	
	@Transactional
	@Modifying
	@Query("UPDATE BoardEntity b SET b.title = :#{#board.title}, b.contents = :#{#board.contents}, b.updatedDatetime = :#{#board.updatedDatetime} WHERE b.boardIdx = :#{#board.boardIdx}")
	void updateBoardOnly(@Param("board")BoardEntity board);
	
}
