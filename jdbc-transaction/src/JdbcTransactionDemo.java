import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class JdbcTransactionDemo {
	
	//1. 테이블 만들기
	//   CREATE TABLE BANK_ACCOUNT
	//   (
	//         OWNER VARCHAR2(50) NOT NULL
	//       , BALANCE NUMBER NOT NULL
	//   )
	
	//2. 테스트 수행 전 필수 작업 - SQL Developer에서 수행
	//
	//   BANK_ACCOUNT 테이블의 모든 데이터 삭제
	//   - DELETE FROM BANK_ACCOUNT;
	//   BANK_ACCOUNT 테이블에 두 건의 데이터 삽입
	//   - INSERT INTO BANK_ACCOUNT VALUES ('장동건', 1000)
	//   - INSERT INTO BANK_ACCOUNT VALUES ('고소영', 1000)

	public static void main(String[] args) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conn = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/demo?serverTimezone=UTC", "devuser", "mysql");

			conn.setAutoCommit(false);
			
			String sql = 
				"UPDATE BANK_ACCOUNT SET BALANCE = BALANCE + ? WHERE OWNER = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, -500);
			pstmt.setString(2, "장동건");
			pstmt.executeUpdate(); //출금
			//default : 자동 commit 실행
			
			@SuppressWarnings("unused")
			int x = 10 / 0; //강제 예외 발생
			
			pstmt.setLong(1, 500);
			pstmt.setString(2, "고소영");
			pstmt.executeUpdate(); //입금
			//default : 자동 commit 실행
			
			conn.commit(); // 마지막 commit or rollback 이후에 실행된 모든 변경사항을 commit
			System.out.println("계좌 이체 성공");
			
		} catch (Exception ex) {
			try {conn.rollback();} catch (Exception ex2) {} // 마지막 commit or rollback 이후에 실행된 모든 병경사항을 rollback
				System.out.println("계좌 이체 실패");			
			
		} finally {
			try {conn.setAutoCommit(true);} catch (Exception ex3) {}
			try { pstmt.close(); } catch (Exception ex4) {}
			try { conn.close(); } catch (Exception ex5) {}
		}

	}
	
	

}





