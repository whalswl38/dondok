package com.trip.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtil {

	/*
	 * title : 제목 content : 내용 receipant : 보낼사람 이메일 을 담은 Map<String, Object> 객체를 던지면 됨
	 */
	public static Map<String, Object> sendEmail(Map<String, Object> param) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		boolean returnCode = false;
		String recipient = (String)param.get("paramValue");
		String title = (String)param.get("title");
		String content = (String)param.get("content");
		
		Properties p = System.getProperties();
		p.put("mail.smtp.starttls.enable", "true"); 	// gmail은 무조건 true 고정
		p.put("mail.smtp.host", "smtp.gmail.com");		// smtp 서버 주소
		p.put("mail.smtp.auth","true");    				// gmail은 무조건 true 고정
		p.put("mail.smtp.port", "587"); 				// gmail 포트
		  
		Authenticator auth = new EmailAuth();
		
		//session 생성 및  MimeMessage생성
		Session session = Session.getDefaultInstance(p, auth);
		MimeMessage msg = new MimeMessage(session);
		
		try{
			//편지보낸시간
			msg.setSentDate(new Date());
			
			InternetAddress from = new InternetAddress() ;
			
			
			from = new InternetAddress("DonDok_Team<dondokteam@gmail.com>");
			
			// 이메일 발신자
			msg.setFrom(from);
			
			
			// 이메일 수신자
			InternetAddress to = new InternetAddress(recipient);
			msg.setRecipient(Message.RecipientType.TO, to);
			
			// 이메일 제목
			msg.setSubject(title, "UTF-8");
			
			// 이메일 내용 
			msg.setText(content, "UTF-8");
			
			// 이메일 헤더 
			msg.setHeader("content-Type", "text/html");
			
			//메일보내기
			javax.mail.Transport.send(msg);
			returnCode = true;
			
		}catch (AddressException addr_e) {
			addr_e.printStackTrace();
		}catch (MessagingException msg_e) {
			msg_e.printStackTrace();
		}

        returnMap.put("returnCode", returnCode);
		return returnMap;
	}
	
}
