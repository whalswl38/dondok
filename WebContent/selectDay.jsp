<%@page import="com.trip.dto.member.MemberLoginDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<% request.setCharacterEncoding("UTF-8");
   response.setContentType("text/html; charset=UTF-8");%>
<!DOCTYPE html>
<html lang="ko">



<head>
    <meta charset="utf-8" />
    <title>FullCalendar Example</title>

    <link rel="stylesheet" href="vendor/css/fullcalendar.min.css" />
    <link rel="stylesheet" href='vendor/css/select2.min.css' />
    <link rel="stylesheet" href='vendor/css/bootstrap-datetimepicker.min.css' />

    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Open+Sans:400,500,600">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">

    <link rel="stylesheet" href="css/main.css">

<script type="text/javascript" src="js/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="js/util.js"></script>
<style type="text/css">
@import
url("css/bodyPosition.css");
@import
url("css/selectDay.css");
@import
url('css/loading.css');
</style>
<script type="text/javascript" src="js/haederLoad.js"></script>
<script type="text/javascript">
$(function(){
	toolTipBoxOn('돌아가기...',$('#sr_home'));
	toolTipBoxOn('다음 단계로...',$('#sr_nextBnt'));
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
<section class="projectSection" style="width: 1150px;">
<article class="article_header">
		<div class="nowLocation">
			<a href="#" onclick="return false;">일정 등록</a> <span></span>
			<span class="locationName">1 단계 : 일정 선택 _ 리더</span>
			<c:if test="${team.t_days eq '0'}">
				<button id="sr_nextBnt" disabled="disabled" onclick="location.href='TeamMemberController?command=upStage&stage=15'">▶</button>
			</c:if>
			<c:if test="${team.t_days ne '0'}">
				<button id="sr_nextBnt" onclick="location.href='TeamMemberController?command=upStage&stage=15'">▶</button>
			</c:if>
			<div class="sr_homeBtn">
				<button id="sr_home" onclick="location.href='TeamMemberController?command=tripSearch'"></button>    
			</div>
		</div>
</article>
<article class="dateContent" style="position:relative;">
<c:if test="${team.t_leaderId eq user.m_id}">
	<div style="position: absolute;left: 50px;top: 18px;"><button class="insertDate">저장</button></div>
	</c:if>
    <div class="container">
        <div id="wrapper">
            <div id="loading"></div>
            <div id="calendar" data-tname="${team.t_name}"data-tid="${team_id}" data-startDate="${team.t_startDate}" data-endDate="${team.t_endDate}"></div>
        </div>

   
        <!-- /.filter panel -->
    </div>
    <!-- /.container -->
    </article>
</section>
    <script src="vendor/js/jquery.min.js"></script>
    <script src="vendor/js/bootstrap.min.js"></script>
    <script src="vendor/js/moment.min.js"></script>
    <script src="vendor/js/fullcalendar.min.js"></script>
    <script src="vendor/js/ko.js"></script>
    <script src="vendor/js/select2.min.js"></script>
    <script src="vendor/js/bootstrap-datetimepicker.min.js"></script>
    <script src="js/main.js"></script>
    <script src="js/addEvent.js"></script>
    <script src="js/editEvent.js"></script>
    <script src="js/etcSetting.js"></script>
    
    <c:if test="${team.t_leaderId ne user.m_id}">
	<script type="text/javascript">
	$(function(){
		delete objCal.select;
		var calendar = $('#calendar').fullCalendar(objCal);
	});
	</script>
	</c:if>


	<c:if test="${team.t_leaderId eq user.m_id}">
	<script type="text/javascript">
	$(function(){
		var calendar = $('#calendar').fullCalendar(objCal);		
	});
	</script>
	</c:if>
	<c:if test="${team.t_days ne '0'}">
	<script type="text/javascript">
	$(function(){
		$('#calendar').fullCalendar('renderEvent', {
			  id:'date',
      	  title: '${team.t_name}',
          start: moment($('#calendar').attr('data-startdate')),
      	  end: moment($('#calendar').attr('data-enddate')).add(1,'days').format('YYYY-MM-DD'),
      	  allDay: true
		});	
	});
	</script>
	</c:if>
</body>

</html>


