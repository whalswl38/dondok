<%@page import="org.json.simple.JSONObject"%>
<%@page import="trip.dto.AccountDto"%>
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
@import
url("css/payment.css");
@import
url('css/loading.css');

</style>
<script type="text/javascript" src="js/haederLoad.js"></script>


<style>
.btn{
color:grey; background-color: skyblue;border:none; border-radius:10px;
}
.btn:hover{
color:skyblue; background-color: grey;
}
</style>
<%
AccountDto accountDto=(AccountDto)request.getAttribute("accountDto");
int total_price=(int)request.getAttribute("total_price");//총금액
int price=(int)request.getAttribute("price");//입금 금액

JSONObject kakao=(JSONObject)request.getAttribute("kakao");
%>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.4.1.js"></script>
<script type="text/javascript">
function kakaoPay(){
	var JSONobj=JSON.parse('<%=kakao%>');
	window.open(JSONobj.next_redirect_pc_url,'','width=420, height=480, menubar=no, status=no, toolbar=no,resizable=no');
}
$(function(){
	toolTipBoxOn('돌아가기...',$('#sr_home'));
	toolTipBoxOn('완료하기...',$('#sr_nextBnt'));
	$('div.loading').remove();
});
</script>
</head>
<body>

<!-- 로딩 -->
<div class="loading" style="z-index:9999;"><div class="sk-fading-circle">
		  <div class="sk-circle1 sk-circle"></div>
		  <div class="sk-circle2 sk-circle"></div>
		  <div class="sk-circle3 sk-circle"></div>
		  <div class="sk-circle4 sk-circle"></div>
		  <div class="sk-circle5 sk-circle"></div>
		  <div class="sk-circle6 sk-circle"></div>
		  <div class="sk-circle7 sk-circle"></div>
		  <div class="sk-circle8 sk-circle"></div>
		  <div class="sk-circle9 sk-circle"></div>
		  <div class="sk-circle10 sk-circle"></div>
		  <div class="sk-circle11 sk-circle"></div>
		  <div class="sk-circle12 sk-circle"></div>
	</div></div>

<section class="projectSection">
<article class="article_header">
		<div class="nowLocation">
			<a href="#" onclick="return false;">일정 등록</a> <span></span>
			<span class="locationName">5 단계 : 경비 입금</span>
			<button id="sr_nextBnt" onclick="location.href='TeamMemberController?command=upStage&stage=6';">▶</button>
		</div>
</article>
<article class="pay_content">
<table>
<tr><td>은행명</td><td colspan="2"><%=accountDto.getAcc_bank() %></td></tr>
<tr><td>계좌번호</td><td colspan="2"><%=accountDto.getAcc_num() %></td></tr>
<tr><td>예금주</td><td colspan="2"><%=accountDto.getAcc_holder() %></td></tr>
<tr><td>입금할 금액</td><td colspan="2"><%=price %></td><td><button class="btn" onclick="kakaoPay();">카카오페이로 결제하기</button></td><td id="res"></td></tr>
</table>
<table>
<tr><td>총 금액</td><td><%=total_price%></td></tr>
<tr><td>총 마일리지</td><td><%=accountDto.getAcc_mileage() %></td></tr>
</table>
</article>
</section>
</body>
</html>