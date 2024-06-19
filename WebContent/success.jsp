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
window.onload=locat();
function locat(){
	location.href='kakaoPay?command=response&tid=<%=(String)session.getAttribute("tid")%>';
}
</script>
</head>
<body>
</body>
</html>