<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	<style type="text/css">
	
		.body{
			height:400px;
		}
	
		.review{
			border: 1px dotted black;
			width:20%;
			height:40%;
			float:left;
		}
	
	</style>
</head>
<body>

		<div class="body">
<%
		for(int i=0; i<8; i++){
			//동주 branch 테스트
%>
			<div class="review"></div>
<%
	}
%>
		</div>
	
</body>
</html>