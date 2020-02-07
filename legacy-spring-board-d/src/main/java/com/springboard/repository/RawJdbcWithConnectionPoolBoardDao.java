package com.springboard.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.springboard.vo.BoardVO;

import lombok.Setter;

public class RawJdbcWithConnectionPoolBoardDao implements BoardDao {
	
//	private DataSource dataSource = 
//			new org.apache.commons.dbcp2.BasicDataSource();
	
	@Setter
	private DataSource dataSource;

	@Override
	public int insertBoard(BoardVO board) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {		
			conn = dataSource.getConnection();
			
			//3. 명령 만들기
			String sql = 
				"INSERT INTO tbl_board (bno, title, writer, content) " +
				"VALUES (seq_board.nextval, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getWriter());
			pstmt.setString(3, board.getContent());
			
			//4. 명령 실행
			pstmt.executeUpdate(); // for insert, update, delete
			
			//5. 결과 처리 (select인 경우)
		} catch (Exception ex) {
			
		} finally {
			//6. 연결 닫기
			try { pstmt.close(); } catch (Exception ex) {}
			try { conn.close(); } catch (Exception ex) {}
		}
		
		return 0;
	}

	@Override
	public List<BoardVO> selectBoard() {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<BoardVO> boards = new ArrayList<>(); //조회 결과 저장 변수
		
		try {
			//1. 커넥션풀에서 연결 가져오기
			conn = dataSource.getConnection();
			
			//3. 명령 만들기
			String sql = 
				"SELECT bno, title, writer, regdate, updatedate, deleted, readcount " +
				"FROM tbl_board  ";
			pstmt = conn.prepareStatement(sql);
			
			//4. 명령 실행
			rs = pstmt.executeQuery();
			
			//5. 결과 처리 (select인 경우)
			while (rs.next()) {
				// 한 행의 데이터를 읽어서 객체에 저장
				BoardVO board = new BoardVO();
				board.setBno(rs.getInt(1));
				board.setTitle(rs.getString(2));
				board.setWriter(rs.getString(3));
				board.setRegDate(rs.getDate(4));
				board.setUpdateDate(rs.getDate(5));
				board.setDeleted(rs.getBoolean(6));
				board.setReadCount(rs.getInt(7));
				
				boards.add(board); //한 행의 데이터를 결과 목록에 추가
				
			}
		} catch (Exception ex) {
			//ex.printStackTrace(); // 오류 메시지를 출력
		} finally {
			//6. 연결 닫기
			try { rs.close(); } catch (Exception ex) {}
			try { pstmt.close(); } catch (Exception ex) {}
			try { conn.close(); } catch (Exception ex) {}
		}
		
		return boards;
		
	}

}
















