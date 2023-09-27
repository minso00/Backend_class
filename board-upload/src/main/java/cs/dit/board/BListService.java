package cs.dit.board;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BListService implements BoardService {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. BoardDao를 생성
		BoardDao dao = new BoardDao();
		int count = dao.recordCount();	// 전체 레코드의 갯수
		int numOfRecords = 10;			// 한 번에 가져올 레코드의 갯수 
		int numOfPages = 5;					// 한 화면에 표시될 페이지의 갯수
		
		// 전달되는 page 번호를 얻고 확인: null 값이거나 ""(빈문자열)이 아니라는 것을 확인
		String page = request.getParameter("p");
		int p = 1;									// 현재 페이지 번호
		
		if(page != null && !page.equals("")) {
			p = Integer.parseInt(page);
		}
		
		// 2. dao의 해당 메소드를 호출
		ArrayList<BoardDto> dtos = dao.list(p, numOfRecords);
		int startNum = p-((p-1)%numOfPages);
		int lastNum = (int)Math.ceil(count/10.0);
		
		// 3. 호출 결과 처리
		request.setAttribute("dtos", dtos);
		request.setAttribute("p", p);
		request.setAttribute("startNum", startNum);
		request.setAttribute("lastNum", lastNum);

		System.out.println("p :" + p);
		System.out.println("startNum:" + startNum);
		System.out.println("lastNum:" + lastNum);
		
	}

}
