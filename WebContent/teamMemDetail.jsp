<%@page import="java.util.List"%>
<%@page import="trip.dto.LocationDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8");%>
<% response.setContentType("text/html; charset=UTF-8");%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="js/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="js/util.js"></script>
<style type="text/css">
@import
url('css/teamMemDetail.css');
</style>
<%
String mid=(String)request.getAttribute("mid");
int days=Integer.parseInt((String)request.getAttribute("days"));
List<LocationDto> loc_list = (List<LocationDto>) request.getAttribute("loc_list");
String latlng=(String)request.getAttribute("latlng");
%>
</head>
<body>
	<article class="content">
		<div class="header"><span><%=mid %></span><span>님의 지도보기</span></div>
		<div class="nav">
			<%for(int i=1;i<=days;i++){
			%>   
			<button onclick="changeDay(<%=i%>);"><%=i %>일자</button>   
			<%
			} 
			%>
		</div>
		<div class="al_day">1일차</div>
		<div id="map" style="width: 100%; height: 100%;"></div>
	</article>
</body>
   <script type="text/javascript"
      src="https://dapi.kakao.com/v2/maps/sdk.js?appkey=546aea18ae9a6bd31c67526ce6764169"></script>
<script>
var mapContainer = document.getElementById('map'), // 지도를 표시할 div  
mapOption = { 
    center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
    level: 9 // 지도의 확대 레벨
};
var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
map.setDraggable(false);    
var bounds = new kakao.maps.LatLngBounds();

var markers = [];
//마커를 표시할 위치와 title 객체 배열입니다 
var positions=[];
<%for (int i = 0; i < loc_list.size(); i++) {%>
var position= {
      cate:<%=loc_list.get(i).getLoc_cate()%>,
         id: <%=loc_list.get(i).getLoc_id()%>,
          url: '<%=loc_list.get(i).getLoc_url()%>', 
          title: '<%=loc_list.get(i).getLoc_name()%>', 
          latlng: new kakao.maps.LatLng(<%=loc_list.get(i).getLoc_y()%>, <%=loc_list.get(i).getLoc_x()%>)
      };
   <%if (loc_list.get(i).getLoc_cate() != 1) {%>
   positions.push(position);
   bounds.extend(position.latlng);
      <%}
   }%>
var res_imageSrc = "img/res.png"; 
var att_imageSrc = "http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_red.png"; 
var imageSize = new kakao.maps.Size(24, 35);
var clickPosition=[];
var clickLine = new kakao.maps.Polyline({
    map: map, // 선을 표시할 지도입니다 
    path: [<%=latlng%>], // 선을 구성하는 좌표 배열입니다 클릭한 위치를 넣어줍니다
    strokeWeight: 3, // 선의 두께입니다 
    strokeColor: '#db4040', // 선의 색깔입니다
    strokeOpacity: 1, // 선의 불투명도입니다 0에서 1 사이값이며 0에 가까울수록 투명합니다
    strokeStyle: 'solid', // 선의 스타일입니다
});
var r_day=1;

display(positions);
map.setBounds(bounds);

function display(positions){
   for (var i = 0; i <positions.length; i++) {
       var markerImage;
       if(positions[i].cate==2){
           markerImage = new kakao.maps.MarkerImage(res_imageSrc, imageSize);    
       }else if(positions[i].cate==3){
           markerImage = new kakao.maps.MarkerImage(att_imageSrc, imageSize); 
       }
       // 마커를 생성합니다
       var marker = new kakao.maps.Marker({
           map: map, // 마커를 표시할 지도
           position: positions[i].latlng, // 마커를 표시할 위치
           title : positions[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
           image : markerImage // 마커 이미지 
       });
       markers.push(marker);
       
       var infowindow = new kakao.maps.InfoWindow({
          content: positions[i].title // 인포윈도우에 표시할 내용
       });
       kakao.maps.event.addListener(marker, 'mouseover', makeOverListener(map, marker, infowindow));
       kakao.maps.event.addListener(marker, 'mouseout', makeOutListener(infowindow));
    }
   }
function makeOverListener(map, marker, infowindow) {
    return function() {
        infowindow.open(map, marker);
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
function changeDay(day){
   r_day=day;
   $.ajax({
      url : "TeamMemberController?command=memDetail_day",
      type : 'post',
      data : {'loc_day':day},
      dataType: "json", 
      success : function(data) {
         removeMarker();
         
         var positions = [];
         var bounds = new kakao.maps.LatLngBounds();
         for(var i=0;i<Object.keys(data).length;i++){
         var position={
               cate:data['room'+i].cate,
                id:data['room'+i].id,
                traditional : true,
                   title:data['room'+i].title, 
                   url:data['room'+i].url, 
                   latlng: new kakao.maps.LatLng(data['room'+i].y, data['room'+i].x)
         };
            if(position.cate!=1){
            positions.push(position);
            bounds.extend(position.latlng);
         }}
         map.setBounds(bounds);
         
         loading(day);
         display(positions);
         
         $('div.al_day').html(day+'일차');
      },
      error : function() {
         alert("통신오류");
      }
   });
}
function removeMarker() {
   for (var i = 0; i < markers.length; i++) {
      markers[i].setMap(null);
   }
   markers = [];

   clickPosition=[];
   clickPosition_id='';

   if (clickLine) {
       clickLine.setPath(clickPosition);         
   }
   }
function loading(day){
   $.ajax({
      url : "TeamMemberController?command=memDetail_loading",
      type : 'post',
      data : {
         'loc_day':day,
         'mid':'<%=mid%>'
         },
      async: false,
      dataType: "json",
      success : function(data) {
         var i=0;
         for( var key in data ) {
            var latlng=new kakao.maps.LatLng( data['latlng'+i].y,data['latlng'+i].x );
            clickPosition.push(latlng);
              i++;
            }
          clickLine.setPath(clickPosition);
         },
      error : function() {
         alert("로딩 통신오류");
      }
   });
}
</script>
</html>