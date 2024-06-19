<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8");%>
<% response.setContentType("text/html; charset=UTF-8");%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<!-- 헤드 -->
<script type="text/javascript" src="js/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="js/util.js"></script>
<style type="text/css">
@import
url("css/bodyPosition.css");
</style>
<script type="text/javascript" src="js/haederLoad.js"></script>

<style type="text/css">

section article {
	margin : 20px 0;
}

section div {
    padding: 15px;
    margin: 0px auto;
    font-size: 25pt;
    font-weight: 600;
    letter-spacing: 1pt;
	color : #415c6a;
	text-align: center;
}
section div:nth-child(3) {
    text-align: center;
}

section span:nth-child(1){
	font-size:16pt;
	font-weight: 600;
	color : #3383c4;
}

section span:nth-child(2){
	font-size:13pt;
	font-weight: 100;
	color:#6c797e;
	margin:0px 10px;
}

button {
    border: 0;
    padding: 10px 20px;
    font-size: 15pt;
    letter-spacing: 0.5pt;
    border-radius: 4px;
    box-shadow: -1px 1px 2px #00000080;
    color:#fff;
    background-color: #3383c4;
}

button:hover {
	transition-duration: 0.3s;
	background-color :#25ae70;
}

</style>
<script type="text/javascript">
$(function(){
	var num = 5;
	setInterval(function(){
		
		if(num >= 0){
			$('section div:nth-child(4)').children('span:nth-child(1)').html('');
			$('section div:nth-child(4)').children('span:nth-child(1)').html(num);
			num--;
		}
	}, 1000);
	setTimeout(function(){
		location.href='TeamMemberController?command=tripSearch';
	},6000);
});
</script>
</head>
<body>
<section class="projectSection">
	<article>
		<div>다른 사람이 모두 완료하지 않았습니다.</div>
		<div>잠시후 일정 조회 페이지로 이동합니다.</div>
		<div><img src="img/loading.gif"></div>
		<div><span>6</span><span>초 후에...</span></div>
		<div><button onclick='location.href="TeamMemberController?command=tripSearch"'>이동</button></div>
	</article>
</section>
</body>
</html>