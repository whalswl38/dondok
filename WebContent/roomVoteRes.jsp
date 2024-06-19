<%@page import="trip.dto.LocationDto"%>
<%@page import="trip.dto.VoRoomDto"%>
<%@page import="java.util.List"%>
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
List<VoRoomDto> roomVoteList=(List<VoRoomDto>)request.getAttribute("roomVoteList");
List<LocationDto> roomVoteList_info=(List<LocationDto>)request.getAttribute("roomVoteList_info");
List<String> n_uniqueList=(List<String>)request.getAttribute("n_uniqueList");
%>

<script type="text/javascript" src="js/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="js/util.js"></script>
<style type="text/css">
@import
url("css/bodyPosition.css");
@import
url("css/roomVoteRes.css");
@import
url("css/loading.css");
</style>
<script type="text/javascript" src="js/haederLoad.js"></script>
<script type="text/javascript">
$(function(){
	toolTipBoxOn('돌아가기...',$('#sr_home'));
	toolTipBoxOn('다음 단계로...',$('#sr_nextBnt'));
	$('div.loading').remove();
});
</script>


<script type="text/javascript">
function delRooms(id,day){
   $.ajax({
      url : "TeamMemberController?command=delRoom_vote_res",
      type : 'post',
      data : {
            "rooms_id":id,
            "rooms_day":day
            },
      success : function(data) {
         failBox('삭제 완료!');
         $('#failClose').on('click',function(){
        	 location.reload(); 
         });
      },
      error : function() {
    	 failBox('통신오류');
      }
   });
}
$(document).ready(function(){
   <%
   if(n_uniqueList.size()==0){
   %>
   $("#sr_nextBnt").removeAttr("disabled");
   <%
   }else{
   %>
   $("#sr_nextBnt").attr("disabled", "disabled");
   <%
   }
   %>
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
			<span class="locationName">3 단계 : 숙소 정하기 _ 리더</span>
			<button id="sr_nextBnt" onclick="location.href='TeamMemberController?command=upStage&stage=35'">▶</button>
			<div class="sr_homeBtn">
				<button id="sr_home" onclick="location.href='TeamMemberController?command=tripSearch'"></button>    
			</div>
		</div>
</article>

<article class="voteTable">
<table id="res">
<%for(int i=0;i<roomVoteList.size();i++){ %>
<tr>
<th style="width: 65px;">일차</th><td style="width: 25px;"><%=roomVoteList.get(i).getVoroom_day() %></td>
<th style="width: 90px;">숙소명</th>
<td>
<%=roomVoteList_info.get(i).getLoc_name()%>
<%
for(int j=0;j<n_uniqueList.size();j++){
   if(n_uniqueList.get(j).equals(roomVoteList.get(i).getVoroom_day())){
%>
<input 
type="button" 
onclick="delRooms(<%=roomVoteList.get(i).getVoroom_id() %>,<%=roomVoteList.get(i).getVoroom_day() %>);"
value="숙소 삭제"/>      
<input type="button" value="상세보기" onclick="window.open('<%=roomVoteList_info.get(i).getLoc_url()%>','','')" />
<%      
   }
} 
%>
</td>
</tr>
<%} %>
</table>
</article>

</section>
</body>
</html>