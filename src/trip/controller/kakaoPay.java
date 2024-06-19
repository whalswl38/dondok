package trip.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.trip.dto.member.MemberLoginDto;

import trip.biz.PlanBiz;
import trip.dao.AccountDao;
import trip.dto.AccountDto;
import trip.dao.TeamDao;

@WebServlet("/kakaoPay")
public class kakaoPay extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected MemberLoginDto sessionUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		return (MemberLoginDto)session.getAttribute("user");
	}
	
    public kakaoPay() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String command = request.getParameter("command");
		
		//AccountDao accountDao=new AccountDao();
		TeamDao teamDao=new TeamDao();
		PlanBiz planBiz = new PlanBiz();
		HttpSession session = request.getSession();
		
		if(command.equals("request")) {
		URL url=new URL("https://kapi.kakao.com/v1/payment/ready");
		HttpURLConnection conn=(HttpURLConnection)url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Authorization", "KakaoAK "+"7b16cc4b7b8b4bf2e9385a2cdea79a3c");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		conn.setDoInput(true);
		conn.setDoOutput(true);
		
		AccountDto accountDto=(AccountDto)request.getAttribute("accountDto");
		int total_price=Integer.parseInt(accountDto.getAcc_price())-Integer.parseInt(accountDto.getAcc_mileage());
		int price=total_price/planBiz.tamMemberCount((int)request.getAttribute("acc_tid"));
		
		
		request.setAttribute("total_price", total_price);
		request.setAttribute("price", price);
		
		String urlStr = new String(request.getRequestURL());
		String context = getServletContext().getContextPath();
		String resStr = urlStr.substring(0, urlStr.indexOf(context)+context.length());
		
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("cid","TC0ONETIME");
		params.put("partner_order_id",teamDao.selectOneTeam((int)request.getAttribute("acc_tid")).getT_name());
		params.put("partner_user_id",sessionUser(request).getM_id());
		params.put("item_name",accountDto.getAcc_holder());
		params.put("quantity",1);
		params.put("total_amount",price);
		params.put("tax_free_amount",0);
		params.put("approval_url", resStr + "/success.jsp");
		params.put("cancel_url",resStr + "/fail.jsp");
		params.put("fail_url", resStr + "/fail.jsp");
		System.out.println(params);
		String string_params=new String();
		for(Map.Entry<String, Object>elem:params.entrySet()) {
			string_params+=(elem.getKey()+"="+elem.getValue()+"&");
		}
		System.out.println(string_params);
		conn.getOutputStream().write(string_params.getBytes());
		
		BufferedReader in=new BufferedReader(new InputStreamReader(conn.getInputStream()));
		
		JSONParser parser=new JSONParser();
		try {
			JSONObject obj = (JSONObject) parser.parse(in);
			request.setAttribute("kakao", obj);
			String tid=(String) obj.get("tid");
			session.setAttribute("tid", tid);
			dispatch("payment.jsp",request,response);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}else if(command.equals("response")) {
		URL url=new URL("https://kapi.kakao.com/v1/payment/order");
		HttpURLConnection conn=(HttpURLConnection)url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Authorization", "KakaoAK "+"7b16cc4b7b8b4bf2e9385a2cdea79a3c");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		conn.setDoInput(true);
		conn.setDoOutput(true);
			
		Map<String, Object> params2=new HashMap<String, Object>();
		params2.put("cid","TC0ONETIME");
		params2.put("tid",(String)session.getAttribute("tid"));
		System.out.println("ddddd티id: "+(String)session.getAttribute("tid"));
		
		String string_params2=new String();
		for(Map.Entry<String, Object>elem:params2.entrySet()) {
			string_params2+=(elem.getKey()+"="+elem.getValue()+"&");
		}
		
		conn.getOutputStream().write(string_params2.getBytes());
		
		BufferedReader in2=new BufferedReader(new InputStreamReader(conn.getInputStream()));
		
		JSONParser parser2=new JSONParser();
		try {
			JSONObject obj = (JSONObject) parser2.parse(in2);
			request.setAttribute("kakao", obj);
			dispatch("response.jsp?command=update",request,response);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}

	}
	public void dispatch(String url, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatch = request.getRequestDispatcher(url);
		dispatch.forward(request, response);
	}
}