package cs.dit.board;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BoardDao {

	/**======================================================================
	 * 패키지명 : cs.dit.board
	 * 파일명   : BoardDao.java
	 * 작성자  : 
	 * 변경이력 : 
	 *   2022-9-11
	 * 프로그램 설명 : board 테이블의 내용과 연동하여 게시글 관리
	*======================================================================*/

	private Connection getConnection() throws Exception{
		
		InitialContext intCtv = new InitialContext();
		
		DataSource ds = (DataSource) intCtv.lookup("java:comp/env/jdbc/mingu");
		
		Connection con = ds.getConnection();
		
		return con;
	}
	
	public void insert(BoardDto dto) {
		String sql = "INSERT INTO board2(SUBJECT, CONTENT, WRITER, FILENAME) VALUES(?, ?, ?, ?)";
		
		try (
			Connection con = getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
		)
		{   
			pstmt.setString(1,  dto.getSubject());
			pstmt.setString(2,  dto.getContent());
			pstmt.setString(3,  dto.getWriter());
			pstmt.setString(4, dto.getFileName());
			pstmt.executeUpdate();
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<BoardDto> list(int p, int numOfRecords){
		String sql = "SELECT BCODE, SUBJECT, CONTENT, WRITER, REGDATE, FILENAME FROM board2 ORDER BY BCODE DESC LIMIT ?, ?";
		ArrayList<BoardDto> dtos = new ArrayList<BoardDto>();
		
		try (	Connection con = getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
			)
			{
				pstmt.setInt(1, (p-1)*numOfRecords);
				pstmt.setInt(2, numOfRecords);
				
				ResultSet rs = pstmt.executeQuery();
				while(rs.next()) {
					
					int bcode = rs.getInt("bcode");
					String subject = rs.getString("subject");
					String content = rs.getString("content");
					String writer = rs.getString("writer");
					Date regDate = rs.getDate("regDate");
					String fileName = rs.getString("fileName");
					
					//2. 위에서 만들어진 dto를 ArrayList 인 dtos에 차례로 입력하세요.
					BoardDto dto = new BoardDto(bcode, subject, content, writer, regDate,fileName);
					dtos.add(dto);	
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		return dtos;
	}
	
	public BoardDto selectOne(int bcode) {
		
		//3. 전달받은 bcode를 가진 레코드를 검색하는 select 문을 아래에 작성하세요.
		String sql = "select * from board2 where bcode=?";
		
		
		BoardDto dto = new BoardDto();
		
		try (	Connection con = getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				)
		{	pstmt.setInt(1, bcode);
		
			try(ResultSet rs = pstmt.executeQuery();)
			{
				rs.next();
				
				dto.setBcode(bcode);
				dto.setSubject(rs.getString("subject"));
				dto.setContent(rs.getString("content"));
				dto.setWriter(rs.getString("writer"));
				dto.setRegDate(rs.getDate("regDate"));
				dto.setFileName(rs.getString("fileName"));
				
			}catch (Exception e) {
				e.printStackTrace();
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}
	
	public BoardDto update(BoardDto dto) {
		String sql = "UPDATE board2 SET subject = ?, content = ?, writer = ?, fileName = ? WHERE bcode =?";
		
		try (
			Connection con = getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
		)
		{
			pstmt.setString(1,  dto.getSubject());
			pstmt.setString(2,  dto.getContent());
			pstmt.setString(3,  dto.getWriter());
			pstmt.setString(4, dto.getFileName());
			pstmt.setInt(5, dto.getBcode());
			
			pstmt.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();
		}		
		return dto;
	}
	
	public void delete(int bcode) {
		String sql = "DELETE FROM board2 WHERE bcode =?";
		
		try (
			Connection con = getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
		)
		{
			pstmt.setInt(1, bcode);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public int recordCount() {
		int count = 0;	
		
		String sql = "select count(bcode)  from board2";
		
		try( Connection con = getConnection();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				) {
				if(rs.next())
					count = rs.getInt(1);
				System.out.println(count);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		return count;
		
	}
	
}
