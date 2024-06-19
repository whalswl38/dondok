package com.trip.controller.member;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.trip.dao.member.MemberLoginDao;
import com.trip.dao.member.MemberLoginDaoImpl;
import com.trip.util.EmailUtil;

/**
 * Servlet implementation class MemberLoginServlet
 */
@WebServlet("/emailValidation")
public class MemberEmailValidationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	MemberLoginDao memberLoginDao = new MemberLoginDaoImpl();
	Logger log = Logger.getLogger(this.getClass());

    public MemberEmailValidationServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("[" + request.getServletPath() + "]=============[START]");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> resMap = new HashMap<String, Object>();
		String emailValidationCode = "";
		
		String recipient = String.valueOf(request.getParameter("email"));
		paramMap.put("param", "email");
		paramMap.put("paramValue", recipient);
		resMap = memberLoginDao.dupCheck(paramMap);
		
		//이메일 중복이지 않을때
		Random random = new Random(System.currentTimeMillis());
		int pwdLength = 7; //코드 길이 설정
		char[] codeTable =  { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};
		
		int tablelength = codeTable.length;
		
		StringBuffer buf = new StringBuffer();
		for(int i = 0; i < pwdLength; i++) {
			buf.append(codeTable[random.nextInt(tablelength)]);
		}
		// 이메일 코드 변수 생성
		emailValidationCode = buf.toString(); 
		
		String content = "";
		String title = "";
		if(request.getParameter("param") == null) {
			content = "<h2>이메일 인증을 시작합니다.</h2>" +
					 "<h1>인증번호 : <span style='color:blue;'>" + emailValidationCode + "</span></h1>" +
					 "<p>위 인증번호를 회원가입 페이지에 입력해주세요.</p><br/>" +
					 "<h2>DonDok Team</h2>";
			
			title = "[DonDok] 이메일 인증";
		}else{
			content = "<h2>이메일 인증을 시작합니다.</h2>" +
					 "<h1>인증번호 : <span style='color:blue;'>" + emailValidationCode + "</span></h1>" +
					 "<p>위 인증번호를 비밀번호 찾기 페이지에 입력해주세요.</p><br/>" +
					 "<h2>DonDok Team</h2>";
			
			title = "[DonDok] 비밀번호 찾기 이메일 인증";
		}
		
		
		paramMap.put("title", title);
		paramMap.put("content", content);
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		if(request.getParameter("param") == null) {
			if(Integer.parseInt(String.valueOf(resMap.get("COUNT"))) < 1) {
				result = EmailUtil.sendEmail(paramMap);
				
				if((boolean)result.get("returnCode")) {
					resMap.put("resultCode", "1000"); //정상
					resMap.put("emailValidationCode", emailValidationCode);
				}else {
					resMap.put("resultCode", "1001");//발신중에러
				}
				
			}else {
				resMap.put("resultCode", "1002");//가입되어있음
			}
		}else {
			if(Integer.parseInt(String.valueOf(resMap.get("COUNT"))) > 0) {
				result = EmailUtil.sendEmail(paramMap);
				
				if((boolean)result.get("returnCode")) {
					resMap.put("resultCode", "1002"); //정상
					resMap.put("emailValidationCode", emailValidationCode);
				}else {
					resMap.put("resultCode", "1001");//발신중에러
				}
				
			}else {
				resMap.put("resultCode", "1000");//가입되어있음
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
