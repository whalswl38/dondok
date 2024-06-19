
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
function loginpopup(){
	var url = "page?page=login";
	var popupName = "login"; 
	var popupSetting = "width=300, height=500, menubar=no, status=no, toolbar=no";
	window.open(url, popupName, popupSetting);
};

</script>
</head>
<body>
	<a onclick="loginpopup();" href="javascript:;">로그인</a>
	<a href="page?page=join">회원가입</a>
	<a href="page?page=findIdPw">아이디 패스워드 찾기</a>
</body>

</html>