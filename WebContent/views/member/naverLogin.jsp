
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- 네이버 -->
<script type="text/javascript" src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.3.js" charset="utf-8"></script>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
<script type='text/javascript'>
	var naver_id_login = new naver_id_login("obaG5HszPWxOuj0r9ab5", "http://localhost:8888");
  
 	// 네이버 사용자 프로필 조회
    naver_id_login.get_naver_userprofile("naverSignInCallback()");
 
    // 네이버 사용자 프로필 조회 이후 프로필 정보를 처리할 callback function
    function naverSignInCallback() {
    	console.log(naver_id_login.getProfileData('email'));
    	console.log(naver_id_login.getProfileData('nickname'));
    	console.log(naver_id_login.getProfileData('age'));
    	
    	window.opener.response(naver_id_login.getProfileData('email'), naver_id_login.getProfileData('nickname'), naver_id_login.getProfileData('age'));
    	window.close();
    }
</script>
</head>
<body>


</body>

</html>