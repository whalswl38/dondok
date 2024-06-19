<%@page import="trip.dto.TeamMemberDto"%>
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
<title>루트 정하기</title>

<script type="text/javascript" src="js/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="js/util.js"></script>
<style type="text/css">
@import
url("css/bodyPosition.css");
@import
url("css/setRoute.css");
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

<style type="text/css">
#layer_popup {display:none; border:2px solid #cccccc;margin:0;padding:5px;background-color:#ffffff;z-index:2000;}
#layer_popup .b-close {position:absolute;top:10px;right:15px;cursor:hand;}
#layer_popup .popupContent {margin:0;padding:0;text-align:center;border:0;width:400px;height:250px;}
#layer_popup .popupContent iframe {border:0;padding:0px;margin:0;z-index:10;}
</style>
   <%
   String day=(String)request.getAttribute("days");
   int days=Integer.parseInt(day);
   List<LocationDto> loc_list = (List<LocationDto>) request.getAttribute("loc_list");
   String latlng=(String)request.getAttribute("latlng");
   
   String l_name=(String)request.getAttribute("l_name");
   List<String> m_names=(List<String>)request.getAttribute("m_names");
   List<TeamMemberDto> teammember=(List<TeamMemberDto>)request.getAttribute("teammember");
   %>
   
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
			<span class="locationName">4 단계 : 원하는 경로 선택</span>
			<button id="sr_nextBnt" <%if(request.getAttribute("next").equals("true")){}else{%>disabled="disabled"<%}%> onclick="location.href='TeamMemberController?command=upStage&stage=45'">▶</button>
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
				for(int i=1;i<=days;i++){
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
<div class="al_day">1일차</div>
   <div id="map" style="width: 100%; height: 500px;"></div>
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
<div id="btn_group">
   <button id="complate" onclick='save_root();'>경로 저장</button>
   <button id="cancle" onclick='delcle_root();'>경로 취소</button>
</div>
      <script src="./jquery.bpopup.min.js"></script>
   <script type="text/javascript"
      src="https://dapi.kakao.com/v2/maps/sdk.js?appkey=fea212ccfdfdea229404673b58166748"></script>
   <script>

var mapContainer = document.getElementById('map'), // 지도를 표시할 div  
    mapOption = { 
        center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
        level: 9 // 지도의 확대 레벨
    };
var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
var bounds = new kakao.maps.LatLngBounds();

var markers = [];
// 마커를 표시할 위치와 title 객체 배열입니다 
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
   // 마커 이미지의 이미지 주소입니다
var res_imageSrc = "img/res.png"; 
var att_imageSrc = "http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_red.png"; 
var imageSize = new kakao.maps.Size(24, 35); // 마커 이미지의 이미지 크기 입니다

var clickPosition=[];
if(<%=latlng%>==null){
   var clickLine = new kakao.maps.Polyline({
       map: map, // 선을 표시할 지도입니다 
       path: [], // 선을 구성하는 좌표 배열입니다 클릭한 위치를 넣어줍니다
       strokeWeight: 3, // 선의 두께입니다 
       strokeColor: '#db4040', // 선의 색깔입니다
       strokeOpacity: 1, // 선의 불투명도입니다 0에서 1 사이값이며 0에 가까울수록 투명합니다
       strokeStyle: 'solid', // 선의 스타일입니다
   });   
}else{
var clickLine = new kakao.maps.Polyline({
    map: map, // 선을 표시할 지도입니다 
    path: [<%=latlng%>], // 선을 구성하는 좌표 배열입니다 클릭한 위치를 넣어줍니다
    strokeWeight: 3, // 선의 두께입니다 
    strokeColor: '#db4040', // 선의 색깔입니다
    strokeOpacity: 1, // 선의 불투명도입니다 0에서 1 사이값이며 0에 가까울수록 투명합니다
    strokeStyle: 'solid', // 선의 스타일입니다
});
}
var clickPosition_id='';
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
    kakao.maps.event.addListener(marker, 'click',makeClickListener(positions[i]));
}
}
function makeClickListener(positions) {
    return function() {
       clickPosition.push(positions.latlng);
       if(clickPosition[clickPosition.length-1]==clickPosition[clickPosition.length-2]){
          clickPosition.pop();
      }else{
         clickPosition_id+=(positions.id+'|');
      }
       //if(clickPosition.length>1)
        clickLine.setPath(clickPosition);
    };
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
function save_root(){
   $.ajax({
      url : "TeamMemberController?command=setRoute_save",
      type : 'post',
      data : {
         'r_day':r_day, 
         'clickPosition_id':clickPosition_id
         },
      dataType: "text",
      success : function(data) {
    	  failBox('저장');
         if(data==='next'){
            $("#sr_nextBnt").removeAttr("disabled");
         }
      },
      error : function() {
    	  failBox("통신오류");
      }
   });
}

function delcle_root(){
   var url="TeamMemberController?command=setRoute_clear";
   $.ajax({
      url : url,
      type : 'post',
      data : {'r_day':r_day},
      dataType: "json",
      success : function(data) {
         removeMarker();
         
         var positions = [];
         
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
         }}
         display(positions,r_day);
      },
      error : function() {
    	  failBox("통신오류");
      }
   });
}

function change_day(day){
   r_day=day;
   $.ajax({
      url : "TeamMemberController?command=setRoute_bnt",
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

//지도 위에 표시되고 있는 마커를 모두 제거합니다
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
      url : "TeamMemberController?command=setRoute_loading",
      type : 'post',
      data : {'loc_day':day},
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
    	  failBox("로딩 통신오류");
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
         url : "TeamMemberController?command=add_mem&stage=2",
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
</article>
</section>
</body>
</html>