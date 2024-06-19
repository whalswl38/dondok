<%@page import="com.trip.dto.member.MemberLoginDto"%>
<%@page import="trip.dto.MemberDto"%>
<%@page import="trip.dto.TeamDto"%>
<%@page import="java.util.List"%>
<%@page import="trip.dto.TeamMemberDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8");
	response.setContentType("text/html; charset=UTF-8");%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%
	List<TeamDto> c_list=(List<TeamDto>)request.getAttribute("c_list");
	List<TeamDto> u_list=(List<TeamDto>)request.getAttribute("u_list");
%>
<style type="text/css">
@import url('https://fonts.googleapis.com/css?family=Varela+Round&display=swap');

@import
url("css/checkTrip.css");

body {
font-family: 'Varela Round', sans-serif;
}

</style>

<!-- 헤드 삽입 -->
<script type="text/javascript" src="js/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="js/util.js"></script>
<style type="text/css">
@import
url("css/bodyPosition.css");
@import
url('css/loading.css');
</style>
<script type="text/javascript" src="js/haederLoad.js"></script>	

<script type="text/javascript">
	$(function(){
		toolTipBoxOn('일정 만들기',$('input.ct_btn'));
		$('.loading').remove();
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
			<span class="locationName">일정 관리</span>
		</div>
		<div class="createTripDiv">
			<input class="ct_btn" type="button" onclick="location.href='TeamMemberController?command=createTeam'" value=" "/>
		</div>
	</article>



<article class="complete_trip">
	<div class="ct_header"><p>완료된 일정</p></div>
	<div class="ct_content">
<%for(int i=0;i<c_list.size();i++){
%>
		<div class="ct_item"><input class="btn" type="button" onclick="location.href='TeamMemberController?command=viewTeamInfo&tid=<%=c_list.get(i).getT_id()%>';" value="<%=c_list.get(i).getT_name()%>"/></div>
		<script type="text/javascript">
			$(function(){
				toolTipBoxOn('일정 경로 보기...',$('input.btn').eq(<%=i%>));
			});
		</script>
<%	
} %>
	</div>
</article>

<article class="tripCreating">
	<div class="tc_header"><p>미완료 일정</p></div>
	<div class="tc_content">
<%for(int i=0;i<u_list.size();i++){
%>
		<div class="tc_item"><input class="btn" type="button" 
								onclick="location.href='TeamMemberController?command=go&tid=<%=u_list.get(i).getT_id() %>&stage=<%=u_list.get(i).getT_stage() %>'" 
								value="<%=(u_list.get(i).getT_name().length() > 13)? u_list.get(i).getT_name().substring(0, 12)+"..." : u_list.get(i).getT_name()%>"/>

<script>
	toolTipBoxOn("<%=u_list.get(i).getT_stage() %> 단계 진행중...",$('div.tc_item > input.btn:eq(<%=i%>)'));
</script>
<%if(u_list.get(i).getT_leaderId().equals((String)session.getAttribute("my_id"))){
%>
							<button class="tc_deleteBtn" onclick="location.href='TeamMemberController?command=deleteTeam&tid=<%=u_list.get(i).getT_id()%>'">일정 삭제</button>
<%	
}else{ %>
							<button class="tc_deleteBtn" onclick="location.href='TeamMemberController?command=deleteTeamMember&tid=<%=u_list.get(i).getT_id()%>'">일정 탈퇴</button>
<%
}
%>
		</div>
<%
}%>
</div>	
</article>
</section>
</body>
</html>