package com.trip.controller.member;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.trip.dao.member.MemberLoginDao;
import com.trip.dao.member.MemberLoginDaoImpl;
import com.trip.dto.member.MemberLoginDto;

/**
 * Servlet implementation class MemberFindIdPwServlet
 */
@WebServlet("/findIdPw")
public class MemberFindIdPwServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	MemberLoginDao memberLoginDao = new MemberLoginDaoImpl();
	Logger log = Logger.getLogger(this.getClass());
  
    public MemberFindIdPwServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("[" + request.getServletPath() + "]=============[START]");
		try {
			MemberLoginDto dto = new MemberLoginDto();
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String param = request.getParameter("param");
			dto.setM_platform(param);
			dto.setM_name(name);
			dto.setM_email(email);
			dto.setM_id(id);
			
			//JSON 데이터 출력
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			Gson gson = new Gson();
			
			if("resetPw".equals(param)) {
				String pass = request.getParameter("pass");
				dto.setM_pass(pass);
				int result = memberLoginDao.resetPw(dto);
				out.print(gson.toJson(result));  //객체 --> JSON 문자열로 바꿈
			}else{
				MemberLoginDto result = memberLoginDao.findIdPw(dto);
				out.print(gson.toJson(result));  //객체 --> JSON 문자열로 바꿈
			}
			
			out.flush();
			out.close();
				
		} catch (Exception e) {
			System.out.println("ERROR SERVLET");
			e.printStackTrace();
		}
		log.debug("[" + request.getServletPath() + "]=============[END]");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
