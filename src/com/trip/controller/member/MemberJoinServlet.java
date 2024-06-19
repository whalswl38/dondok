
package com.trip.controller.member;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.trip.dao.member.MemberLoginDao;
import com.trip.dao.member.MemberLoginDaoImpl;
import com.trip.dto.member.MemberLoginDto;

/**
 * Servlet implementation class MemberJoinServlet
 */
@WebServlet("/memberJoin")
public class MemberJoinServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	MemberLoginDao memberLoginDao = new MemberLoginDaoImpl();
	Logger log = Logger.getLogger(this.getClass());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberJoinServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("[" + request.getServletPath() + "]=============[START]");
		
		response.setContentType("text/html; charset=utf-8");
		//response.getWriter().append("업로드가 성공적으로 완료되었습니다.");
		
		String fullPath = new String();
		
		// enctype="multipart/form-data" 로 전송되었는지 확인
		if(ServletFileUpload.isMultipartContent(request)) {
			// 전송 파일 용량 제한 : 10Mbyte 제한한 경우
			int maxSize = 1024 * 1024 * 10;

			// 웹서버 컨테이너 경로 추출함
			String serverPath = getServletContext().getRealPath("/");
			String savePath = serverPath + "images/memberProfile/";
			File file = new File(savePath);
			if(!file.isDirectory()) {
				file.mkdirs();
			}

			// 파일 저장 경로(ex : web/board_uploadFiles/) 정함
			//String savePath = root + "board_uploadFiles/";

			try {
				MultipartRequest multiRequest = new MultipartRequest(request, savePath, maxSize, "UTF-8", new DefaultFileRenamePolicy());
				
				// 업로드한 파일명 추출
				String originalFileName = multiRequest.getFilesystemName("imgFile");
				String renameFileName = "";
	
				//전송된 파일이 있는 경우, 파일명 바꾸어 폴더에 기록하기
				if(originalFileName != null){
					
					String[] tailArr = originalFileName.split("\\.");
					String tail = tailArr[tailArr.length-1];
					
					if (!(tail.equals("jpg") || tail.equals("jpeg") || tail.equals("gif") || tail.equals("png") || tail.equals("bmp"))) {
						if (new File(savePath + "/" + originalFileName).delete() == true) {
							response.getWriter().append("<script>alert('이미지 파일만 업로드 가능합니다');</script>");
							return;
						}
					}
					
					//현재 서비스가 구동된 시간정보로 파일명 바꾸기
					//예 : 20200122121532.zip
					long current = System.currentTimeMillis();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
					//변경할 파일명 만들기
					renameFileName = tailArr[0] + "_" + sdf.format(new Date(current)) + "." + tailArr[1]; // 확장자 
	
					File originalFile = new File(savePath + originalFileName);
					File renameFile = new File(savePath + renameFileName);
	
					//원본 파일 객체 이름 바꾸기 
					//만약 이름이 바뀌지 않으면 직접 바꿈				
					if(!originalFile.renameTo(renameFile)) {
					//rename 이 되지 않았을 경우, 강제로 파일을 복사하고
					//원본 파일은 삭제함
	
						//클라이언트로 부터 업로드될 원본 파일을 읽어서, 
						//바꿀 파일명으로 고쳐서 지정 폴더에 기록 저장하기
						FileInputStream originalRead = new FileInputStream(originalFile);
						FileOutputStream renameCopy = new FileOutputStream(renameFile);
	
						byte[] readText = new byte[1024];
						int readResult = 0; // the total number of bytes read into the buffer
						while ((readResult = originalRead.read(readText, 0, readText.length)) != -1) {
							renameCopy.write(readText, 0, readResult);
							renameCopy.flush();
						}
						
						originalRead.close();
						renameCopy.close();
						originalFile.delete();
						
					}
					//파일에 대한 저장값 지정
					fullPath = "/images/memberProfile/" + renameFileName;
				} 
		
				// 다른 전송값들 추출하기
				if(fullPath.isEmpty()) {
					if(multiRequest.getParameter("profile") == null || "".equals(multiRequest.getParameter("profile"))) {
						fullPath = new String();
					}else {
						fullPath = multiRequest.getParameter("profile");
					}
				}
				
				MemberLoginDto dto = new MemberLoginDto();
				dto.setM_id(multiRequest.getParameter("myid"));
				dto.setM_nick(multiRequest.getParameter("mynick"));
				dto.setM_pass(multiRequest.getParameter("mypw"));
				dto.setM_name(multiRequest.getParameter("name"));
				dto.setM_email(multiRequest.getParameter("myemail"));
				dto.setM_phone(multiRequest.getParameter("phone"));
				dto.setM_flag("Y"); 
				dto.setM_grade("user");
				dto.setM_platform(multiRequest.getParameter("platform"));
				dto.setM_filepath(fullPath);
				dto.setM_addr1(multiRequest.getParameter("addr1"));
				dto.setM_addr2(multiRequest.getParameter("addr2"));
				
				int result = memberLoginDao.joinMember(dto);

				if(result < 1) { 
					response.setContentType("text/html; charset=utf-8");
					response.getWriter().append("<script>alert('회원가입에 실패했습니다.');" + "window.location.href='/dondok/page?page=login';</script>");
				}else {
					response.setContentType("text/html; charset=utf-8");
					response.getWriter().append("<script>alert('돈독의 멤버가 되신걸 환영합니다. "+ dto.getM_nick() + "님!');" + "window.location.href='/dondok/page?page=login';</script>");
				}
			} catch (Exception e) {
				e.printStackTrace();
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

