<%@page import="trip.dto.TeamDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8");%>
<% response.setContentType("text/html; charset=UTF-8");%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
<script type="text/javascript" src="js/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="js/util.js"></script>
<style type="text/css">
@import
url("css/bodyPosition.css");
@import
url("css/setRegion.css");
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
			<span class="locationName">1 단계 : 지역선택</span>
			<button id="sr_nextBnt" disabled="disabled" onclick="location.href='TeamMemberController?command=upStage&stage=2'">▶</button>
			<div class="sr_homeBtn">
				<button id="sr_home" onclick="location.href='TeamMemberController?command=tripSearch'"></button>    
			</div>
		</div>
	</article>

<article class="sr_miniContent">
<div>
<select id="day">
<%
int tmp = Integer.parseInt(((TeamDto)request.getAttribute("team")).getT_days());
for(int i=1;i <= tmp ;i++){
%>	
<option><%=i %>일</option>
<%	
} 
%>
</select>
<form onsubmit="searchPlaces(); return false;">
<input type="text" id="keyword" size="15"> 
<button type="submit">검색</button> 
</form>
</div>
</article>
<article class="sr_map">
	<div id="map" style="width:100%;height:500px;display: inline-block;margin:5px 0;"></div>
</article>
<article class="sr_res">
<div id="res" style="display: inline-block;"></div>
</article>

<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=fea212ccfdfdea229404673b58166748&libraries=services"></script>
<script type="text/javascript">
var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
    mapOption = {
	center: new kakao.maps.LatLng(36.366826, 127.9786567), // 지도의 중심좌표
    level:13 
    };  

var map = new kakao.maps.Map(mapContainer, mapOption); 
var ps = new kakao.maps.services.Places(); 
var marker=new kakao.maps.Marker({map: map});
function searchPlaces() {
	marker.setMap(null);
    var keyword = document.getElementById('keyword').value+' 시청';
    if (!keyword.replace(/^\s+|\s+$/g, '')) {
        alert('키워드를 입력해주세요!');
        return false;
    }
    ps.keywordSearch( keyword, placesSearchCB); 
}

function placesSearchCB (data, status, pagination) {
	var day=$("#day option:selected").val();
	day=day.substr(0, day.length -1)
	var region=$("#keyword").val();
    if (status === kakao.maps.services.Status.OK) {
        displayMarker(data[0]);    
        if(parseFloat(data[0].y)<33.6808299){
        map.setCenter(new kakao.maps.LatLng(33.399926,126.5786637));
        map.setLevel(11);
        }else{
        map.setCenter(new kakao.maps.LatLng(36.366826, 127.9786567));
        map.setLevel(13);
            }
        $.ajax({
    		url : "TeamMemberController?command=addRegion",
    		type : "post",
    		data : {
    			day : day,
    			region: region
    		},
    		success : function(data) {
    			var jObj=JSON.parse(data);
    			$('#res').html('');
    			for(var i=0;i<(Object.keys(jObj).length-1);i++){
    				$('#res').append('<p><span>'+Object.keys(jObj)[i]+'일</span><span>'+Object.values(jObj)[i]+'</span></p>');
   				}
    			if((Object.values(jObj)[Object.keys(jObj).length-1])==(Object.keys(jObj).length-1)){
    				$("#sr_nextBnt").removeAttr("disabled");
    			}
    		},
    		error : function() {
    			alert("통신오류"); 
    		},beforeSend : function(){
				loadingBox($('article.sr_map'), {
					'background-color': '#ffffff80',
					'z-index': '98',
			    	'width': '100%',
					'height': '100%'
				});
			},complete : function(){
				$('#loadingBox').remove();
			}
    		
    		
    	});  
    } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
        alert('검색 결과가 존재하지 않습니다.');
        return;
    } else if (status === kakao.maps.services.Status.ERROR) {
        alert('검색 결과 중 오류가 발생했습니다.');
        return;
    }
}

function displayMarker(place) {
    marker = new kakao.maps.Marker({
        map: map,
        position: new kakao.maps.LatLng(place.y, place.x) 
    });
}
</script>
</section>
</body>
</html>