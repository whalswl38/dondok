package com.trip.controller.pagemove;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



@WebServlet({"/PageMoveServlet"})
public class PageMoveServlet extends HttpServlet {
	
	
	public PageMoveServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = 1L;
	HttpSession session;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		
		
		String command = request.getParameter("command");
		
		System.out.println("<"+command+">");
		
		if(command.equals("login")) {
			session = request.getSession();
			String id = request.getParameter("id");
			String pw = request.getParameter("pw");
			
					response.sendRedirect("loginheader.jsp");
				jsResponse("존재하지 않는 아이디입니다", "index.jsp", response);
			
		} else if(command.equals("loginform")) {
			response.sendRedirect("views/member/member_login.jsp");
			
		} else if(command.equals("schedule")) {
			
			
			HttpSession session = request.getSession();
			
			if(session.equals("")) {				//비회원일 경우
				
				//로그인alert, 로그인폼으로 이동
			} else {								//회원일 경우
				//일정관리 페이지 이동
				System.out.println("서블릿 스케줄 입성");
				response.sendRedirect("schedule_maintenance.jsp");
			}
		} else if(command.equals("scheduleCheck")) {
			HttpSession session = request.getSession();
			if(session.equals("")) {				//비회원일 경우
				
				//로그인alert, 로그인폼으로 이동
			} else {			
				response.sendRedirect("schedule_check.jsp");
			}
		} else if(command.equals("scheduleView")) {
			HttpSession session = request.getSession();
			if(session.equals("")) {				//비회원일 경우
				
				//로그인alert, 로그인폼으로 이동
			} else {				
				response.sendRedirect("schedule_view.jsp");
			}
		} else if(command.equals("schduleRegister")) {
			HttpSession session = request.getSession();
			if(session.equals("")) {				//비회원일 경우
				
				//로그인alert, 로그인폼으로 이동
			} else {			
				response.sendRedirect("schedule_register.jsp");
			}
		} else if(command.equals("review")) {
			response.sendRedirect("all_reviews");
		} else if(command.equals("shareAlbum")) {
			HttpSession session = request.getSession();
			if(session.equals("")) {				//비회원일 경우
				
				//로그인alert, 로그인폼으로 이동
			} else {			
				response.sendRedirect("share_album.jsp");
			}
		} else if(command.equals("shareSchedule")) {
			HttpSession session = request.getSession();
			if(session.equals("")) {				//비회원일 경우
				
				//로그인alert, 로그인폼으로 이동
			} else {			
				response.sendRedirect("share_schedule.jsp");
			}
		}
		
	}
	
	public void jsResponse(String msg, String url, HttpServletResponse response) throws IOException {
		String s = "<script type='text/javascript'>"
				 + "alert('"+ msg + "');"
				 + "location.href='"+url+"';"
				 + "</script>";
		PrintWriter out = response.getWriter();
		out.println(s);
	}

}
