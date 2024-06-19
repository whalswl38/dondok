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
<%
AccountDto accountDto=(AccountDto)request.getAttribute("accountDto"); 
%>
<!-- 헤드 -->
<script type="text/javascript" src="js/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="js/util.js"></script>
<style type="text/css">
@import
url("css/bodyPosition.css");
@import
url("css/payment_leader.css");
@import
url('css/loading.css');
</style>
<script type="text/javascript" src="js/haederLoad.js"></script>

<script type="text/javascript">
$(function(){
	toolTipBoxOn('돌아가기...',$('#sr_home'));
	$('div.loading').remove();
});
</script>

<style>
.btn{
color:grey; background-color: skyblue;border:none; border-radius:10px;
}
.btn:hover{
color:skyblue; background-color: grey;
}
#home{
background:none;border:none;
}
</style>
<script type="text/javascript"
	src="https://code.jquery.com/jquery-3.4.1.js"></script>
<script type="text/javascript">
function chk(){
		if($("#acc_bank").val().length<1){
			$("#acc_bank").focus();
			failBox("은행명을 입력해주세요.");
			return false;
		}else if($("#acc_num").val().length<1){
			$("#acc_num").focus();
			failBox("계좌번호를 입력해주세요.");
			return false;
		}else if($("#acc_holder").val().length<1){
			$("#acc_holder").focus();
			failBox("예금주를 입력해주세요.");
			return false;
		}else if($("#acc_price").val().length<1){
			$("#acc_price").focus();
			failBox("금액을 입력해주세요.");
			return false;
		} else if($("#acc_price").val() <= <%=accountDto.getAcc_mileage()%>){
			$("#acc_price").focus();
			failBox("<%=accountDto.getAcc_mileage()%> 보다는 높은 금액을 입력해주세요.");
			return false;
		} else if($("#acc_price").val() > 100000){
			$("#acc_price").focus();
			failBox(100000 + "보다 많이 입력하지 마세요.");
			return false;
		}
		return true;
}
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
			<span class="locationName">5 단계 : 경비 책정 _ 리더</span>
			<div class="sr_homeBtn">
				<button id="sr_home" onclick="location.href='TeamMemberController?command=tripSearch'"></button>    
			</div>
		</div>
</article>
 <article class="pl_content">
<form action="TeamMemberController?command=updateacc" method="post" onsubmit="return chk();">
<table>
<tr><td>은행명</td><td><input type="text" id="acc_bank" name="acc_bank" value="<%=accountDto.getAcc_bank() %>"/></td></tr>
<tr><td>계좌번호</td><td><input type="text" id="acc_num" name="acc_num" value="<%=accountDto.getAcc_num() %>"/></td></tr>
<tr><td>예금주</td><td><input type="text" id="acc_holder" name="acc_holder" value="<%=accountDto.getAcc_holder() %>"/></td></tr>
<tr><td>입금할 금액</td><td><input type="number" id="acc_price" name="acc_price"/></td></tr>
</table>
<input class="btn" type="submit" value="저장"/>
</form>
</article>
</section>
</body>
</html>