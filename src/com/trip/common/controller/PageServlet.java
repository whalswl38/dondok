
package com.trip.common.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class PageServlet
 */
@WebServlet("/page")
public class PageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    response.setContentType("text/html; charset=UTF-8");
		String pageParam = (String) request.getParameter("page");

		String returnUrl = "error.jsp";
		if(pageParam != null && !"".equals(pageParam)){
			switch(pageParam){
				case "login" :
					HttpSession session = request.getSession();
					if (session.getAttribute("user") == null) returnUrl = "views/member/member_login.jsp";
					else response.getWriter().append("<script>self.close();</script>");
					break;
					
				case "join" :
					returnUrl = "views/member/member_selectPlatform.jsp";
					break;
					
				case "findIdPw" :
					returnUrl = "views/member/member_findIdPw.jsp";
					break;
				
				case "main" :
					returnUrl = "mainheader.jsp";
				default :
					returnUrl = "views/member/member_main.jsp";
					break;
			}
		}
		// forward (파라미터까지 포함시켜서 페이지를 부르는 방법)
		RequestDispatcher dispatcher = request.getRequestDispatcher(returnUrl);
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

