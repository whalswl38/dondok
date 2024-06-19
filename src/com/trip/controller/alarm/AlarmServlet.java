package com.trip.controller.alarm;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.trip.dao.otherBoard.AlarmDao;
import com.trip.dao.otherBoard.AlarmDaoImpl;
import com.trip.dto.member.MemberLoginDto;
import com.trip.dto.otherBoard.AlarmDto;

import net.sf.json.JSONObject;


/**
 * Servlet implementation class AlarmServlet
 */
@WebServlet({"/AlarmCount", "/AlarmList", "/AlarmUpdate"})
public class AlarmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
		
		String uri = request.getRequestURI();

		AlarmDao dao = new AlarmDaoImpl();

		HttpSession session = request.getSession();
		MemberLoginDto userDto  = (MemberLoginDto)session.getAttribute("user");
		String myid = userDto.getM_id();
		
		if (uri.endsWith("AlarmCount")) {
			
			int alarmCount = dao.alarmCount(myid);
			
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put("alarmCount", alarmCount);
			
			// json-lib
			JSONObject obj = JSONObject.fromObject(map);
			PrintWriter out = response.getWriter();
			
			obj.write(out);
			
			
		} else if (uri.endsWith("AlarmList")) {
			List<AlarmDto> dtoList = dao.selectList(myid);
		    Map<String, List<AlarmDto>> map = new HashMap<String, List<AlarmDto>>();
			map.put("dtoList", dtoList);
			
			
			// json-lib
			JSONObject obj = JSONObject.fromObject(map);
			PrintWriter out = response.getWriter();
			
			obj.write(out);
			
		} else if (uri.endsWith("AlarmUpdate")) {

			int al_no = Integer.parseInt(request.getParameter("al_no"));
			int res = dao.viewChange(al_no);
		
		}
	}

}
