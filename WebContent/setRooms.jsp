<%@page import="trip.dto.TeamMemberDto"%>
<%@page import="trip.dto.LocationDto"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8");%>
<% response.setContentType("text/html; charset=UTF-8");%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>여러개 마커에 이벤트 등록하기2</title>
    
<script type="text/javascript" src="js/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="js/util.js"></script>
<style type="text/css">
@import
url("css/bodyPosition.css");
@import
url("css/setRooms.css");
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
<style>
#layer_popup {display:none; border:2px solid #cccccc;margin:0;padding:5px;background-color:#ffffff;z-index:2000;}
#layer_popup .b-close {position:absolute;top:10px;right:15px;cursor:hand;}
#layer_popup .popupContent {margin:0;padding:0;text-align:center;border:0;width:400px;height:250px;}
#layer_popup .popupContent iframe {border:0;padding:0px;margin:0;z-index:10;}


</style>     
</head>
<body>
<%String l_name=(String)request.getAttribute("l_name");
List<String> m_names=(List<String>)request.getAttribute("m_names");
List<TeamMemberDto> teammember=(List<TeamMemberDto>)request.getAttribute("teammember");
List<LocationDto> loc_rooms_list=(List<LocationDto>)request.getAttribute("loc_rooms_list");
%>
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
			<span class="locationName">3 단계 : 숙소 정하기</span>
			<button id="sr_nextBnt" <%if(request.getAttribute("next").equals("true")){}else{%>disabled="disabled"<%}%>onclick="location.href='TeamMemberController?command=upStage&stage=35'">▶</button>
			<div class="sr_homeBtn">
				<button id="sr_home" onclick="location.href='TeamMemberController?command=tripSearch'"></button>    
			</div>
		</div>
</article>
<article class="al_leftBox">
	<div class ="al_days">
		<ul id="menu_days">
				<li>일정</li>
				<%
				String days=(String)request.getAttribute("days");
				for(int i=1;i<=Integer.parseInt(days);i++){
				%>
				<li><span id="days"><input class="btn al_dayBtn" type="button" id="day_<%=i %>" value="<%=i %> 일자" onclick="change_day(this.value.substring(0,this.value.indexOf(' 일자')));"/></span></li>
				<%
				}
				%>
		</ul>
	</div>
	<div class="al_mileage">
		<p id="menu_mileage"><span>마일리지</span></p>
		<p><span id="mileage"><%=request.getAttribute("mileage") %>원</span></p>
	</div>
	<div class="al_memberBox">
		<ul id="menu_teammember">
			<li><span>참여인원</span><button class="btn" onclick="add_mem();">+</button></li>
			<li><%=l_name%></li>
				<%
				for(int i=0;m_names.size()>i;i++){
				%>
				<li><%=m_names.get(i) %>
				<%
				if(request.getAttribute("l_id").equals((String)session.getAttribute("my_id"))){
				%>
			<span id="teammember"><button class="btn" id="mem<%=i %>_bnt" value="<%=teammember.get(i)%>" onclick="del_mem(this.value);">X</button></span>
				<%
				}
				%>
				</li>
				<%
				}
				%>
		</ul>
	</div>
</article>
<article class="al_content" style="position: relative;">
<div id="map" style="width:100%;height:500px;"></div>
<script src="./jquery.bpopup.min.js"></script>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=fea212ccfdfdea229404673b58166748"></script>
<script>
var markers = [];
var mapContainer = document.getElementById('map'), // 지도를 표시할 div
    mapOption = { 
        center: new kakao.maps.LatLng(37.5401065310004, 127.213179314442), // 지도의 중심좌표
        level: 5 // 지도의 확대 레벨
    };

var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

var positions = [
	 <%
	 	days=(String)request.getAttribute("days");
		
	   for(int i=0;i<loc_rooms_list.size();i++){
	   %>
	   {
		   id: '<%=loc_rooms_list.get(i).getLoc_id()%>',
	       title: '<%=loc_rooms_list.get(i).getLoc_name()%>', 
	       url:'<%=loc_rooms_list.get(i).getLoc_url()%>', 
	       latlng: new kakao.maps.LatLng(<%=loc_rooms_list.get(i).getLoc_y()%>, <%=loc_rooms_list.get(i).getLoc_x()%>)
	   },
	   <%
	   }
	   %>
];
display(positions);
// 지도 위에 마커를 표시합니다
function display(positions){
	var bounds = new kakao.maps.LatLngBounds();
for (var i = 0;i < positions.length; i++) {
 // 마커 이미지의 이미지 크기 입니다
	var imageSize = new kakao.maps.Size(24, 35); 
	// 마커 이미지를 생성합니다    
	var normalImage = new kakao.maps.MarkerImage("http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png", imageSize);  

    // 마커를 생성하고 이미지는 기본 마커 이미지를 사용합니다
    var marker = new kakao.maps.Marker({
        map: map,
        position: positions[i].latlng,
        image: normalImage
    });
    markers.push(marker); 
    
    var infowindow = new kakao.maps.InfoWindow({
    	content: positions[i].title // 인포윈도우에 표시할 내용
    });
    bounds.extend(positions[i].latlng);
    
    kakao.maps.event.addListener(marker, 'mouseover', makeOverListener(map, marker, infowindow));
    kakao.maps.event.addListener(marker, 'mouseout', makeOutListener(infowindow));
    kakao.maps.event.addListener(marker, 'click',makeClickistener(positions[i].url));
}
map.setBounds(bounds);
}

function makeClickistener(url) {
    return function() {
    	 window.open(url);
    };
}
// 인포윈도우를 표시하는 클로저를 만드는 함수입니다 
function makeOverListener(map, marker, infowindow) {
    return function() {
        infowindow.open(map, marker);
    };
}
// 인포윈도우를 닫는 클로저를 만드는 함수입니다 
function makeOutListener(infowindow) {
    return function() {
        infowindow.close();
    };
}


function change_day(day){
	$.ajax({
		url : "TeamMemberController?command=setRooms_bnt",
		type : 'post',
		data : {"loc_day":day},
		dataType: "json",
		success : function(data) {
			removeMarker();
			
			var positions = [];
			for(var i=0;i<Object.keys(data).length;i++){
			var room={
					 id:data['room'+i].id,
				       title:data['room'+i].title, 
				       url:data['room'+i].url, 
				       latlng: new kakao.maps.LatLng(data['room'+i].y, data['room'+i].x)
				  
			};
			positions.push(room);
			}
			
			display(positions);
			
			 var poll = document.querySelector('#poll');
			 
			 html = '';
		        for (var i = 0; i < Object.keys(data).length; i++) {
		            html += '<tr><td>';
		            if(data['room'+i].id==data['room'+i].vote){
		            html+='<input type="radio"  checked="checked" onchange="handleChange('+day+',this.value);" name="poll_'+day+'" value="'+data['room'+i].id+'">';
		            }else{
		            html+='<input type="radio" onchange="handleChange('+day+',this.value);" name="poll_'+day+'" value="'+data['room'+i].id+'">';    	
		            }
		            html+=data['room'+i].title+
		            '</td><td>';
		        }       
		        poll.innerHTML = html;
		},
		error : function() {
			failBox("통신오류");
		}, beforeSend : function(){
			loadingBox($('article.al_content'), {
				'background-color': '#ffffff80',
				'z-index': '98',
		    	'width': '100%',
				'height': '100%'
			});
		},complete : function(){
			$('#loadingBox').remove();
		}
	});
}


// 지도 위에 표시되고 있는 마커를 모두 제거합니다
function removeMarker() {
   for (var i = 0; i < markers.length; i++) {
      markers[i].setMap(null);
   }
   markers = [];
}
function handleChange(day,id){
	$.ajax({
		url : "TeamMemberController?command=setRooms_vote",
		type : 'post',
		data : {"loc_day":day,
				"loc_id":id,
				"loc_tid":1
				},
		dataType:'text',
		success : function(data) {
			if(data==='next'){
				$("#sr_nextBnt").removeAttr("disabled");
			}	
			failBox("투표 완료");
		},
		error : function() {
			failBox("통신오류");
		}
	});
}
function del_mem(memid){
	 $.ajax({
			url : "TeamMemberController?command=del_mem",
			async :false,
			type : "post",
			data : {
				id : memid
			},
			dataType : "text",
			success : function(data) {
				var jObj=JSON.parse(data);
				$("#res").html("초대 되었습니다.");
				var jObj=JSON.parse(data);
				$('#menu_teammember').html('');
				$('#menu_teammember').html(`
						<li><span>참여인원</span><button class="btn" onclick="add_mem();">+</button></li>
						<li class="memberleader"><%=l_name%></li>
						`);
				for(var i=0;i<Object.keys(jObj).length;i++){
					//$('#menu_teammember').append(Object.values(jObj)[i].name);
					var id='\''+Object.values(jObj)[i].id+'\'';
					$('#menu_teammember').append('<li>' + Object.values(jObj)[i].name + '<%if(request.getAttribute("l_id").equals((String)session.getAttribute("my_id"))){%><span id="teammember"><button class="btn" onclick="del_mem('+id+');">x</button></span><%}%></li>'); 
				}
				
			},
			error : function() {
				
				failBox("통신오류"); 
			}
		});
}
function add_mem(){
	 $("#layer_popup").bPopup();	 	
}
function idChk(){
		$.ajax({
			url : "TeamMemberController?command=add_mem&stage=3",
			type : "post",
			data : {
				id : $("#al_search").val()
			},
			dataType : "text",
			success : function(data) {
				if(data==='비회원'){
					$("#res").html("탈퇴 또는 존재하지 않는 아이디입니다.");
				}else if(data==='이미초대'){
					$("#res").html("이미 초대된 회원입니다.");
				}else if(data==='비입력'){
					$("#res").html("아이디를 입력하세요.");
				}else{
					$("#res").html("초대 되었습니다.");
					var jObj=JSON.parse(data);
    				$('#menu_teammember').html('');
    				$('#menu_teammember').html(`
    						<li><span>참여인원</span><button class="btn" onclick="add_mem();">+</button></li>
    						<li class="memberleader"><%=l_name%></li>
    						`);
    				for(var i=0;i<Object.keys(jObj).length;i++){
    					//$('#menu_teammember').append(Object.values(jObj)[i].name);
    					var id='\''+Object.values(jObj)[i].id+'\'';
    					$('#menu_teammember').append('<li>' + Object.values(jObj)[i].name + '<%if(request.getAttribute("l_id").equals((String)session.getAttribute("my_id"))){%><span id="teammember"><button class="btn" onclick="del_mem('+id+');">x</button></span><%}%></li>'); 
    				}   	
				}
				$("#al_search").val('');
			},
			error : function() {
			}
		});
	}
</script>

<%
		for (int i = 1; i <= Integer.parseInt(days); i++) {
	%>
	<!-- 
	<button class="btn" id="bnt_<%=i %>" onclick='change_day(this.value);' value=<%=i %>>
	<%=i%>일차
	</button>
	 -->
	<%
		}
	%>

	<div class="voteRooms">
		<table  id="poll">
			<%
			int vote_id=(int)request.getAttribute("vote_id");
				for (int i = 0; i < loc_rooms_list.size(); i++) {
			%>
			<tr>
				<td>
				<%
				if(vote_id==loc_rooms_list.get(i).getLoc_id()){ 
					%>
					<input type="radio" checked="checked" onchange="handleChange(1,this.value);" name="poll_1" value="<%=loc_rooms_list.get(i).getLoc_id()%>">
					<%=loc_rooms_list.get(i).getLoc_name()%>
					<%
					}else{
					%>
					<input type="radio" onchange="handleChange(1,this.value);" name="poll_1" value="<%=loc_rooms_list.get(i).getLoc_id()%>">
					<%=loc_rooms_list.get(i).getLoc_name()%>	
					<%	
					}
					%>
				</td>
			</tr>
			<%
				}
			%>
		</table>
	</div>
		
<!-- 
	<span id="menu_mileage">
				<b>마일리지</b><span id="mileage">
				<%=request.getAttribute("mileage") %>원</span>
			</span>
			<span id="menu_teammember">
				<b>참여인원</b><button class="btn" onclick="add_mem();">+</button><%=l_name%>
				<span id="teammember">
				<%
				for(int i=0;m_names.size()>i;i++){
				%>
				<%=m_names.get(i) %>
				<%
				if(request.getAttribute("l_id").equals((String)session.getAttribute("my_id"))){
				%>
				<button class="btn" id="mem<%=i %>_bnt" value="<%=teammember.get(i)%>" onclick="del_mem(this.value);">x</button>
				<%
				}}
				%>
				</span>
			</span>
 -->
 
</article>			
<div id="layer_popup" style="display:none; ">
    <span>    	
        <div class="b-close">X</div>
    </span>
    <div class="popupContent">
    <h2>인원 추가</h2>
	<input id="al_search" type="text"/><button class="btn" onclick="idChk();">추가</button>
<div id="res"></div>
	</div>
</div>
</section>		
</body>
</html>
