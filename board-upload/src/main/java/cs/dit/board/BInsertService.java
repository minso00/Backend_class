package cs.dit.board;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

public class BInsertService implements BoardService {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");

		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		String writer = request.getParameter("writer");
		String fileName = null;
		String dir = null;
		
		String contentType = request.getContentType();
		System.out.println(contentType);
		if(contentType != null && contentType.toLowerCase().startsWith("multipart/")) {
			dir = request.getServletContext().getRealPath("/uploadfiles");
			System.out.println(dir);
		}
		
		//혹시 폴더가 만들어지지 않은 상태일 경우 실행
		File f = new File(dir);
		if(!f.exists()) {
			f.mkdir();
		}
		
		Collection<Part> parts = request.getParts();
		for(Part p:parts) {
			System.out.println("p name = " + p.getName());
			if(p.getHeader("Content-Disposition").contains("filename=")) {	// 이거는 네트워크에서 가져오는 거여서 filename으로 작성. 절대 DB이름으로 하면 안 됨
				if(p.getSize()>0) {
					fileName = p.getSubmittedFileName();
					String filePath = dir + File.separator + fileName;
					p.write(filePath);
					p.delete();
				}
			}
		}
		
		BoardDao dao = new BoardDao();
		BoardDto dto = new BoardDto(subject, content, writer, fileName);
		dao.insert(dto);
		

	}

}
