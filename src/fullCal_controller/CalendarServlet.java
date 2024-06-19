package fullCal_controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fullCal_dao.CalendarDao;
import fullCal_dto.CalendarDto;

@WebServlet("/cal.do")
public class CalendarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String command = request.getParameter("command");
		
		Map<String, CalendarDto> javaMap = new HashMap<String, CalendarDto>();
		 
		javaMap.put("evt1", new CalendarDto("babo", "title", "2020-01-21", "2020-01-22", "123"));
		
		if(command.equals("test")) {
			int res = 0;
			String dateStr = request.getParameter("dateStr");
			System.out.println(dateStr);
			
			CalendarDto dto = new CalendarDto("이종민지", "title", dateStr, "123123", "123");
			CalendarDao dao = new CalendarDao();
			res = dao.insert(dto);
			System.out.println(res);
			
			if(res > 0) {
				System.out.println("성공");
				response.sendRedirect("test.jsp");
			} else {
				System.out.println("실패");	
				response.sendRedirect("test.jsp");
			}
		
		} else if(command.equals("insert")) {
			
			//request.setAttribute(name, o);
			response.sendRedirect("insert.jsp");
		}
	}
}
