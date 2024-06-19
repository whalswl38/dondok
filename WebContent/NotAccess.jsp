<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script src="./js/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="js/util.js"></script>

<!-- 헤드 삽입 -->
<style type="text/css">
@import
url("css/bodyPosition.css");

section div {
    padding: 30px;
    margin: 30px auto;
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

</style>
<script type="text/javascript" src="js/haederLoad.js"></script>	
<script type="text/javascript">
	$(function(){
		var num = 5;
		setInterval(function(){
			
			if(num >= 0){
				$('section div:nth-child(3)').children('span:nth-child(1)').html('');
				$('section div:nth-child(3)').children('span:nth-child(1)').html(num);
				num--;
			}
		}, 1000);
		setTimeout(function(){
			location.href='./';
		},6000);
	});
</script>
<body>
<section class="projectSection">
	<article>
		<div>부적절한 접근입니다.</div>
		<div>잠시후 메인 페이지로 이동합니다.</div>
		<div><span>6</span><span>초 후에...</span></div>
	</article>
</section>
</body>
</html>