
package com.trip.controller.member;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.trip.dao.member.MemberLoginDao;
import com.trip.dao.member.MemberLoginDaoImpl;
import com.trip.dao.otherBoard.AlarmDao;
import com.trip.dao.otherBoard.AlarmDaoImpl;
import com.trip.dto.member.MemberLoginDto;

/**
 * Servlet implementation class MemberLoginServlet
 */
@WebServlet("/memberLogin")
public class MemberLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	MemberLoginDao memberLoginDao = new MemberLoginDaoImpl();
	Logger log = Logger.getLogger(this.getClass());
	AlarmDao alarmDao = new AlarmDaoImpl();
	
    public MemberLoginServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("[" + request.getServletPath() + "]=============[START]");
		HttpSession session = request.getSession();
		
		try {
			MemberLoginDto dto = new MemberLoginDto();
			dto.setM_id(String.valueOf(request.getParameter("myId")));
			dto.setM_pass(String.valueOf(request.getParameter("myPw")));
			dto.setM_email(String.valueOf(request.getParameter("email")));
			dto.setM_platform(String.valueOf(request.getParameter("platform")));
			
			//Member DB 통신
			MemberLoginDto user;
			if(request.getParameter("myId") == null && request.getParameter("myPw") == null && request.getParameter("email") == null) {
				user = new MemberLoginDto();
			}else {
				user = memberLoginDao.getList(dto);
			}
			
			//결과값 처리
			Map<String, Object> resMap = new HashMap<String, Object>();
			if(user == null) {
				resMap.put("resultCode", false);
			}else {
				session.setAttribute("user", user);
				int alarmCount = alarmDao.alarmCount(user.getM_id());
				session.setAttribute("alarmCount", alarmCount);
				resMap.put("resultCode", true);
				resMap.put("user", user);
			}
				
			//JSON 데이터 출력
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			Gson gson = new Gson();
			out.print(gson.toJson(resMap));  //객체 --> JSON 문자열로 바꿈
			out.flush();
			out.close();
				
		} catch (Exception e) {
			System.out.println("ERROR SERVLET");
			e.printStackTrace();
		}
		log.debug("[" + request.getServletPath() + "]=============[END]");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

