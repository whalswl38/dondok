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

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.trip.util.Sha256Util;

/**
 * Servlet implementation class MemberLoginServlet
 */
@WebServlet("/chkOriPw")
public class MemberChkOriPwServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger(this.getClass());

    public MemberChkOriPwServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("[" + request.getServletPath() + "]=============[START]");
		
		String oripass = String.valueOf(request.getParameter("oripass"));
		String rtnPw = new String();
		if(!oripass.isEmpty()){
			rtnPw = Sha256Util.encSha256(oripass);
		}
		
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap.put("rtnPw", rtnPw);
		
		//JSON 데이터 출력
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		out.print(gson.toJson(rtnMap));  //객체 --> JSON 문자열로 바꿈
		out.flush();
		out.close();
				
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
