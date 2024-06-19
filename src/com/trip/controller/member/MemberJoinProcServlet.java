package com.trip.controller.member;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.trip.dao.member.MemberLoginDao;
import com.trip.dao.member.MemberLoginDaoImpl;

/**
 * Servlet implementation class MemberJoinServlet
 */
@WebServlet("/joinProc")
public class MemberJoinProcServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	MemberLoginDao memberLoginDao = new MemberLoginDaoImpl();
	Logger log = Logger.getLogger(this.getClass());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberJoinProcServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("[" + request.getServletPath() + "]=============[START]");
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("platform", String.valueOf(request.getParameter("platform")));
		resMap.put("name", String.valueOf(request.getParameter("name")));
		resMap.put("nickname", String.valueOf(request.getParameter("nickname")));
		resMap.put("profile", String.valueOf(request.getParameter("profile")));
		resMap.put("email", String.valueOf(request.getParameter("email")));
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		paramMap.put("param", "email");
		paramMap.put("paramValue", String.valueOf(request.getParameter("email")));
		resultMap = memberLoginDao.dupCheck(paramMap);
		
		if(resultMap != null) {
			if(Integer.parseInt(String.valueOf(resultMap.get("COUNT"))) > 0) {
				response.getWriter().append("<script>alert('이미 가입되어 있는 아이디가 있습니다.');" + "window.location.href='/dondok/page?page=login';</script>");
			}else {
				request.setAttribute("resMap", resMap);
				RequestDispatcher dispatcher = request.getRequestDispatcher("views/member/member_join.jsp");
				dispatcher.forward(request, response);
			}
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
