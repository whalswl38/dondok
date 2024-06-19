<%@page import="org.json.simple.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8");%>
<% response.setContentType("text/html; charset=UTF-8");%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
var command='<%=request.getParameter("command")%>';
window.onload=start(command);

function start(command){
if(command==='close'){
	auto_close();
}else if(command==='update'){
	update_payment();
}
}

function update_payment(){
location.href="TeamMemberController?command=paymentfinished";
}

function auto_close(){
	setTimeout('closed()',2200);
}
function closed(){
		self.close();
}
</script>
</head>
<body>
<b>결제 완료</b> 자동으로 창이 꺼집니다.
</body>
</html>


