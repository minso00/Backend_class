package cs.dit.board;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@MultipartConfig(
		maxFileSize = 1024 * 1024 * 5,
		maxRequestSize = 1024 * 1024 * 50
)

@WebServlet(description = "게시판 컨트롤러", urlPatterns = { "*.do" }) //urlPatterns은 호출할 때 사용하는 이름이라고 생각하면 된다. /만 입력할 경우 모든 요청이 들어올 수 있다.
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		String viewPage = null;
		//System.out.println(uri.lastIndexOf("/"));
		//System.out.println(uri.lastIndexOf(".do"));
		
		String com = uri.substring(uri.lastIndexOf("/")+1, uri.lastIndexOf(".do"));
		//System.out.println(com);
		
		if(com != null && com.trim().equals("list")) {
			BoardService service = new BListService(); //BoardService로 묶으면 나중에 수정할 때 편함
			service.execute(request, response);
			viewPage = "/WEB-INF/view/list.jsp";
			
		} else if(com != null && com.trim().equals("insertForm")) {
			viewPage = "/WEB-INF/view/insertForm.jsp";
		} 
		else if(com != null && com.trim().equals("insert")) {
			BoardService service = new BInsertService(); //BoardService로 묶으면 나중에 수정할 때 편함
			service.execute(request, response);
			viewPage = "/WEB-INF/view/list.do";
		}
		else if(com != null && com.trim().equals("updateForm")) {
			BoardService service = new BSelectOneService(); //BoardService로 묶으면 나중에 수정할 때 편함
			service.execute(request, response);
			viewPage = "/WEB-INF/view/updateForm.jsp";
		}
		else if(com != null && com.trim().equals("update")) {
			BoardService service = new BUpdateService(); //BoardService로 묶으면 나중에 수정할 때 편함
			service.execute(request, response);
			viewPage = "/WEB-INF/view/list.do";
		}
		else if(com != null && com.trim().equals("delete")) {
			BoardService service = new BDeleteService(); //BoardService로 묶으면 나중에 수정할 때 편함
			service.execute(request, response);
			viewPage = "/WEB-INF/view/list.do";
		}
		else if(com != null && com.trim().equals("index")) {
			viewPage = "/WEB-INF/view/index.jsp";
		}
		RequestDispatcher rd = request.getRequestDispatcher(viewPage);
		rd.forward(request, response);

	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
