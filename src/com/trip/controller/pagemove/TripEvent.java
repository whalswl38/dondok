package com.trip.controller.pagemove;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;

import net.sf.json.JSONObject;

@WebServlet({"/TripEvent.do"})
public class TripEvent extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public TripEvent() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        doPost(request,response);
        
//        response.setHeader("Access-Control-Allow-Origin", "*");						   //허용할 Origin(요청url) : *의 경우 모두 허용
//		response.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT");  //허용할 메소드
//		response.setHeader("Access-Control-Max-Age", "3600");     //브라우저 캐시 시간(단위: 초) : "3600" 이면 최소 1시간 안에는 서버로 재요청 되지 않음
//	    response.setHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, Authorization");    //요청 허용 헤더(ajax : X-Requested-With)
//	    
//		String url = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/searchFestival?ServiceKey=b1Ir0EWgotMKPp6adqfK4zOLPVG%2BXZf92q8%2FvYpydZ0Uw0DayW5Sl8VpR73jB4juYtG4SX%2BN3WDLwoXgjzTaeQ%3D%3D&MobileOS=ETC&MobileApp=AppTest&arrange=A&listYN=Y&eventStartDate=20200101";
//		
//		response.sendRedirect(url);
        //String startdate = request.getParameter("yyyymmdd");
        //String enddate = request.getParameter("yyyymmdd");
        
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        
        SimpleDateFormat format = new SimpleDateFormat( "yyyyMMdd" );
        String current = format.format(System.currentTimeMillis());
        
        System.out.println(current);
        
        URL url = new URL("http://api.visitkorea.or.kr/openapi/service/rest/KorService/searchFestival?ServiceKey=b1Ir0EWgotMKPp6adqfK4zOLPVG%2BXZf92q8%2FvYpydZ0Uw0DayW5Sl8VpR73jB4juYtG4SX%2BN3WDLwoXgjzTaeQ%3D%3D&MobileOS=ETC&MobileApp=AppTest&arrange=A&listYN=Y&eventStartDate="+current+"&_type=json");
     
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        InputStream in = con.getInputStream();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
          byte[] buf = new byte[1024 * 8];
          int length = 0;
          while ((length = in.read(buf)) != -1) {
                  out.write(buf, 0, length);
          }
          System.out.println(new String(out.toByteArray(), "UTF-8"));
          con.disconnect();
          response.getWriter().append(new String(out.toByteArray(), "UTF-8"));
  
        
	}

}
