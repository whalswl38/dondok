<%@page import="trip.dto.LocationDto"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%
   request.setCharacterEncoding("UTF-8");
   response.setContentType("text/html; charset=UTF-8");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>여러개 마커 표시하기</title>

<!-- 헤드 -->
<script type="text/javascript" src="js/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="js/util.js"></script>
<style type="text/css">
@import
url("css/bodyPosition.css");
</style>
<script type="text/javascript" src="js/haederLoad.js"></script>

    
</head>

<body>
<section class="projectSection" style="width: 975px;">
<h1>일정 등록</h1>
   <h2>4단계 : 루트 정하기</h2><button onclick="location.href='TeamMemberController?command=setRoute&day=1'">입금하기▶</button>
   
<div id="map" style="width:100%;height:350px;"></div>

<script type="text/javascript"
	src="https://code.jquery.com/jquery-3.4.1.js"></script>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=546aea18ae9a6bd31c67526ce6764169"></script>
<script>
var mapContainer = document.getElementById('map'), // 지도를 표시할 div  
    mapOption = { 
        center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
        level: 9 // 지도의 확대 레벨
    };

var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
<%
List<LocationDto> loc_list=(List<LocationDto>)request.getAttribute("loc_list");
%>
// 마커를 표시할 위치와 title 객체 배열입니다 
var res_positions = [
	 <%
	   for(int i=0;i<loc_list.size();i++){
		   if(loc_list.get(i).getLoc_cate()==2){
	   %>
	   {
		   id: '<%=loc_list.get(i).getLoc_id()%>',
	       url: '<%=loc_list.get(i).getLoc_url()%>', 
	       title: '<%=loc_list.get(i).getLoc_name()%>', 
	       latlng: new kakao.maps.LatLng(<%=loc_list.get(i).getLoc_y()%>, <%=loc_list.get(i).getLoc_x()%>)
	   },
	   <%
	   }
		   }
	   %>
];
var att_positions = [
	 <%
	   for(int i=0;i<loc_list.size();i++){
		   if(loc_list.get(i).getLoc_cate()==3){
	   %>
	   {
		   id: '<%=loc_list.get(i).getLoc_id()%>',
	       url: '<%=loc_list.get(i).getLoc_url()%>', 
	       title: '<%=loc_list.get(i).getLoc_name()%>', 
	       latlng: new kakao.maps.LatLng(<%=loc_list.get(i).getLoc_y()%>, <%=loc_list.get(i).getLoc_x()%>)
	   },
	   <%
	   }
		   }
	   %>
];

// 마커 이미지의 이미지 주소입니다
var res_imageSrc = "http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png"; 
var att_imageSrc = "http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_red.png"; 
var selected_latlng=[];
for (var i = 0; i < res_positions.length; i ++) {
    // 마커 이미지의 이미지 크기 입니다
    var imageSize = new kakao.maps.Size(24, 35);  
    // 마커 이미지를 생성합니다    
    var markerImage = new kakao.maps.MarkerImage(res_imageSrc, imageSize);  
    // 마커를 생성합니다
    var marker = new kakao.maps.Marker({
        map: map, // 마커를 표시할 지도
        position: res_positions[i].latlng, // 마커를 표시할 위치
        title : res_positions[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
        image : markerImage // 마커 이미지 
    });
    
    // 마커에 표시할 인포윈도우를 생성합니다 
    var infowindow = new kakao.maps.InfoWindow({
    	content: res_positions[i].title // 인포윈도우에 표시할 내용
    });
    kakao.maps.event.addListener(marker, 'mouseover', makeOverListener(map, marker, infowindow));
    kakao.maps.event.addListener(marker, 'mouseout', makeOutListener(infowindow));
    kakao.maps.event.addListener(marker, 'click', makeClickListener(res_positions[i]));
}
for (var i = 0; i < att_positions.length; i ++) {
    
    // 마커 이미지의 이미지 크기 입니다
    var imageSize = new kakao.maps.Size(24, 35); 
    
    // 마커 이미지를 생성합니다    
    var markerImage = new kakao.maps.MarkerImage(att_imageSrc, imageSize); 
    
    // 마커를 생성합니다
    var marker = new kakao.maps.Marker({
        map: map, // 마커를 표시할 지도
        position: att_positions[i].latlng, // 마커를 표시할 위치
        title : att_positions[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
        image : markerImage // 마커 이미지 
    });
    
    var infowindow = new kakao.maps.InfoWindow({
    	content: att_positions[i].title // 인포윈도우에 표시할 내용
    });
    kakao.maps.event.addListener(marker, 'mouseover', makeOverListener(map, marker, infowindow));
    kakao.maps.event.addListener(marker, 'mouseout', makeOutListener(infowindow));
    kakao.maps.event.addListener(marker, 'click', makeClickListener(att_positions[i]));
}

function makeClickListener(position) {
	return function() {
		selected_latlng.push(position.latlng);
		console.log('선택된 위치 배열='+selected_latlng);
   };
}
//인포윈도우를 표시하는 클로저를 만드는 함수입니다 
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
</script>


<%
//int days=Integer.parseInt(request.getParameter("days"));
for(int i=1;i<=3;i++){
%>	
	<button onclick='location.href="TeamMemberController?command=setRoute&day="+<%=i%>'><%=i%> 일차</button>
<%	
}
%>
</section>
</body>
</html>