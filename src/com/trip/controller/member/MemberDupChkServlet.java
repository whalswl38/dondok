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
import com.trip.dao.member.MemberLoginDao;
import com.trip.dao.member.MemberLoginDaoImpl;

/**
 * Servlet implementation class MemberLoginServlet
 */
@WebServlet("/dupCheck")
public class MemberDupChkServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	MemberLoginDao memberLoginDao = new MemberLoginDaoImpl();
	Logger log = Logger.getLogger(this.getClass());

    public MemberDupChkServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("[" + request.getServletPath() + "]=============[START]");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> resMap = new HashMap<String, Object>();
		
		paramMap.put("param", String.valueOf(request.getParameter("param")));
		paramMap.put("paramValue", String.valueOf(request.getParameter("paramValue")));
		resMap = memberLoginDao.dupCheck(paramMap);
		
		if(resMap != null) {
			if(Integer.parseInt(String.valueOf(resMap.get("COUNT"))) > 0) {
				resMap.put("resCode", false);
			}else {
				resMap.put("resCode", true);
			}
		}
		
		//JSON 데이터 출력
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		out.print(gson.toJson(resMap));  //객체 --> JSON 문자열로 바꿈
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
