<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8");%>
<% response.setContentType("text/html; charset=UTF-8");%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
<script type="text/javascript">
window.onload=auto_close();
function auto_close(){
	setTimeout('closed()',1100);
}
function closed(){
	self.close();
}
</script>
</head>
<body>
<b>결제 실패</b> 더치페이 화면으로 돌아갑니다.
</body>
</html>