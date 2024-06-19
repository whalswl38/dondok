<%@page import="trip.dto.RegionDto"%>
<%@page import="java.util.ArrayList"%>
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
<title>키워드로 장소검색하고 목록으로 표출하기</title>

<!-- 헤드 -->
<script type="text/javascript" src="js/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="js/util.js"></script>
<style type="text/css">
@import
url("css/bodyPosition.css");

@import
url("css/addlist.css");

</style>
<script type="text/javascript" src="js/haederLoad.js"></script>
<script type="text/javascript">
$(function(){
	toolTipBoxOn('돌아가기...',$('#sr_home'));
	toolTipBoxOn('다음 단계로...',$('#sr_nextBnt'));
	updateList(<%=request.getParameter("loc_day") %>);
	$('div.loading').remove();
});
</script>
<style>
#layer_popup {display:none; border:2px solid #cccccc;margin:0;padding:5px;background-color:#ffffff;z-index:2000;}
#layer_popup .b-close {position:absolute;top:10px;right:15px;cursor:hand;}
#layer_popup .popupContent {margin:0;padding:0;text-align:center;border:0;width:400px;height:250px;}
#layer_popup .popupContent iframe {border:0;padding:0px;margin:0;z-index:10;}

.map_wrap, .map_wrap * {
   margin: 0;
   padding: 0;
   font-family: 'Malgun Gothic', dotum, '돋움', sans-serif;
   font-size: 12px;
}

.map_wrap a, .map_wrap a:hover, .map_wrap a:active {
   color: #000;
   text-decoration: none;
}

.map_wrap{
   position: relative;
   width: 100%;
   height: 500px;
   overflow: hidden;
}

#menu_wrap2 {
   display: none;
}

.bg_white {
   background: #fff;
}

#menu_wrap hr {
   display: block;
   height: 1px;
   border: 0;
   border-top: 2px solid #5F5F5F;
   margin: 3px 0;
}

#menu_wrap .option {
   text-align: center;
}

#menu_wrap .option p {
   margin: 10px 0;
}

#menu_wrap .option button {
   margin-left: 5px;
}

#placesList li {
   list-style: none;
}

#placesList .item {
   position: relative;
   border-bottom: 1px solid #888;
   overflow: hidden;
   cursor: pointer;
   min-height: 65px;
}

#placesList .item span {
   display: block;
   margin-top: 4px;
}

#placesList .item h5, #placesList .item .info {
   text-overflow: ellipsis;
   overflow: hidden;
   white-space: nowrap;
}

#placesList .item .info {
   padding: 10px 0 10px 55px;
}

#placesList .info .gray {
   color: #8a8a8a;
}

#placesList .info .jibun {
   padding-left: 26px;
   background:
      url(http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_jibun.png)
      no-repeat;
}

#placesList .info .tel {
   color: #009900;
}

#placesList .item .markerbg {
   float: left;
   position: absolute;
   width: 36px;
   height: 37px;
   margin: 10px 0 0 10px;
   background:
      url(http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png)
      no-repeat;
}

#placesList .item .marker_1 {
   background-position: 0 -10px;
}

#placesList .item .marker_2 {
   background-position: 0 -56px;
}

#placesList .item .marker_3 {
   background-position: 0 -102px
}

#placesList .item .marker_4 {
   background-position: 0 -148px;
}

#placesList .item .marker_5 {
   background-position: 0 -194px;
}

#placesList .item .marker_6 {
   background-position: 0 -240px;
}

#placesList .item .marker_7 {
   background-position: 0 -286px;
}

#placesList .item .marker_8 {
   background-position: 0 -332px;
}

#placesList .item .marker_9 {
   background-position: 0 -378px;
}

#placesList .item .marker_10 {
   background-position: 0 -423px;
}

#placesList .item .marker_11 {
   background-position: 0 -470px;
}

#placesList .item .marker_12 {
   background-position: 0 -516px;
}

#placesList .item .marker_13 {
   background-position: 0 -562px;
}

#placesList .item .marker_14 {
   background-position: 0 -608px;
}

#placesList .item .marker_15 {
   background-position: 0 -654px;
}

#pagination {
   margin: 10px auto;
   text-align: center;
}

#pagination a {
   display: inline-block;
   margin-right: 10px;
}

#pagination .on {
   font-weight: bold;
   cursor: default;
   color: #777;
}

@import
url('css/loading.css');
</style>
<%
String l_name=(String)request.getAttribute("l_name");
List<String> m_names=(List<String>)request.getAttribute("m_names");
List<TeamMemberDto> teammember=(List<TeamMemberDto>)request.getAttribute("teammember");
RegionDto rDto=(RegionDto)request.getAttribute("region");
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
			<span class="locationName">2 단계 : 위시 리스트</span>
			<button id="sr_nextBnt" disabled="disabled" onclick="location.href='TeamMemberController?command=upStage&stage=3'">▶</button>
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
				<li><span id="days"><input class="btn al_dayBtn" type="button" id="day_<%=i %>" value="<%=i %> 일자" onclick="location.href='TeamMemberController?command=addlist&loc_day=<%=i %>'"/></span></li>
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
			<li class="memberleader"><%=l_name%></li>
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
<article class="al_content">
   <div class="al_day"><%=request.getParameter("loc_day") %>일차 : <%=rDto.getR_location() %></div>
   <div class="map_wrap">
      <div id="map" style="width: 100%; height: 100%; position: relative; overflow: hidden;"></div>
      <div id="btn_group">
    		<button id="addList">장소 추가</button><button id="showList">장소 보기</button>
   	  </div>
      <div id="menu_wrap" class="bg_white">
         <div class="option">
            <div>
                  	<select id="cate" onchange  ="c_search('<%=rDto.getR_location()%>');">
                     <option value="AD5">숙소</option>
                     <option value="FD6">음식점</option>
                     <option value="AT4">명소</option>
                  </select> : <input type="text" value="" id="keyword" size="15">
                  <!-- 디폴트 -->
                  <button type="button" onclick="search('<%=rDto.getR_location()%>');">검색하기</button>
            </div>
         </div>

         <ul id="placesList"></ul>
         <div id="pagination"></div>
      </div>

    <div id="menu_wrap2">
   	</div>

</article>
 <div id="layer_popup" style="display:none; ">
    <span>    	
        <div class="b-close">X</div>
    </span>
    <div class="popupContent">
    <h2>인원 추가</h2>
	<input id="al_search" type="text"/><button onclick="idChk();">추가</button>
	<div id="res"></div>
	</div>
</div>  

      <script src="./jquery.bpopup.min.js"></script>
   <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=fea212ccfdfdea229404673b58166748&libraries=services"></script>
   <script>
      
      // 마커를 담을 배열입니다
      var markers = [];

      var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
      mapOption = {
         center : new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
         level : 3
      // 지도의 확대 레벨
      };

      // 지도를 생성합니다    
      var map = new kakao.maps.Map(mapContainer, mapOption);

      // 장소 검색 객체를 생성합니다
      var ps = new kakao.maps.services.Places(map);

      // 검색 결과 목록이나 마커를 클릭했을 때 장소명을 표출할 인포윈도우를 생성합니다
      var infowindow = new kakao.maps.InfoWindow({
         zIndex : 1
      });

      // 키워드로 장소를 검색합니다
      searchPlaces('<%=rDto.getR_location()%>', 'AD5');
      
      
      function search(city){
    	  var cate=$("#cate option:selected").val();
    	 
    	  searchPlaces(city, cate);
      }
      
      function c_search(city){
    	  var cate=$("#cate option:selected").val();
    	  document.getElementById('keyword').value="";
    	  searchPlaces(city, cate);
      }
      
      // 키워드 검색을 요청하는 함수입니다
      function searchPlaces(city, cate) {
         var keyword = city + document.getElementById('keyword').value;//지역+키워드
         var cate = cate;
         
         ps.keywordSearch(keyword, placesSearchCB, {
            category_group_code : cate
         });
      }

      // 장소검색이 완료됐을 때 호출되는 콜백함수 입니다
      function placesSearchCB(data, status, pagination) {
         if (status === kakao.maps.services.Status.OK) {

            // 정상적으로 검색이 완료됐으면
            // 검색 목록과 마커를 표출합니다
            displayPlaces(data);

            // 페이지 번호를 표출합니다
            displayPagination(pagination);

         } else if (status === kakao.maps.services.Status.ZERO_RESULT) {

            failBox('검색 결과가 존재하지 않습니다.');
            return;

         } else if (status === kakao.maps.services.Status.ERROR) {

            failBox('검색 결과 중 오류가 발생했습니다.');
            return;

         }
      }

      // 검색 결과 목록과 마커를 표출하는 함수입니다
      function displayPlaces(places) {

         var listEl = document.getElementById('placesList'), menuEl = document
               .getElementById('menu_wrap'), fragment = document
               .createDocumentFragment(), bounds = new kakao.maps.LatLngBounds(), listStr = '';

         // 검색 결과 목록에 추가된 항목들을 제거합니다
         removeAllChildNods(listEl);

         // 지도에 표시되고 있는 마커를 제거합니다
         removeMarker();

         for (var i = 0; i < places.length; i++) {

            // 마커를 생성하고 지도에 표시합니다
            var placePosition = new kakao.maps.LatLng(places[i].y,
                  places[i].x), marker = addMarker(placePosition, i), itemEl = getListItem(
                  i, places[i],<%=request.getParameter("loc_day")%>); // 검색 결과 항목 Element를 생성합니다

            //console.log(places[i].category_group_code);//category_group_code

            // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
            // LatLngBounds 객체에 좌표를 추가합니다
            bounds.extend(placePosition);

            // 마커와 검색결과 항목에 mouseover 했을때
            // 해당 장소에 인포윈도우에 장소명을 표시합니다
            // mouseout 했을 때는 인포윈도우를 닫습니다
            (function(marker, title) {
               kakao.maps.event.addListener(marker, 'mouseover',
                     function() {
                        displayInfowindow(marker, title);
                     });

               kakao.maps.event.addListener(marker, 'mouseout',
                     function() {
                      infowindow.close();
                     });
               
               itemEl.onmouseover = function() {
                  displayInfowindow(marker, title);
               };

               itemEl.onmouseout = function() {
                  infowindow.close();
               };
            })(marker, places[i].place_name);

            fragment.appendChild(itemEl);
         }

         // 검색결과 항목들을 검색결과 목록 Elemnet에 추가합니다
         listEl.appendChild(fragment);
         menuEl.scrollTop = 0;

         // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
         map.setBounds(bounds);
      }
      
      
      // 검색결과 항목을 Element로 반환하는 함수입니다
      function getListItem(index, places, day) {

         var el = document.createElement('li'), itemStr = '<span class="markerbg marker_'
               + (index + 1)
               + '"></span>'
               + '<div class="info">'
               + '   <h5>' + places.place_name + '</h5>';

         if (places.road_address_name) {
            itemStr += ' <span>' + places.road_address_name + '</span>'
                  + '   <span class="jibun gray">' + places.address_name
                  + '</span>';
         } else {
            itemStr += ' <span>' + places.address_name + '</span>';
         }

         itemStr += '  <span class="tel">' + places.phone + '</span>'
               + '</div>';

         itemStr += '<div class="itemBtn"><button><a href=' + places.place_url+' target="_blank">상세보기</a></button>';
         
         itemStr += '<input type="button" onclick="addplace(\''+places.id+',\'+\''+places.category_group_code+',\'+\''+places.place_name+',\'+\''+places.address_name+',\'+\''+places.x+',\'+\''+places.y+',\'+\''+places.place_url+',\'+\''+day+'\');" value="담기"/></div>';

         el.innerHTML = itemStr;  
         el.className = 'item';
         
         return el;
      }

      // 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
      function addMarker(position, idx, title) {
         var imageSrc = 'http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png', // 마커 이미지 url, 스프라이트 이미지를 씁니다
         imageSize = new kakao.maps.Size(36, 37),// 마커 이미지의 크기
         imgOptions = {
            spriteSize : new kakao.maps.Size(36, 691),// 스프라이트 이미지의 크기
            spriteOrigin : new kakao.maps.Point(0, (idx * 46) + 10),// 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
            offset : new kakao.maps.Point(13, 37)
         // 마커 좌표에 일치시킬 이미지 내에서의 좌표
         }, markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize,
               imgOptions), marker = new kakao.maps.Marker({
            position : position, // 마커의 위치
            image : markerImage
         });

         marker.setMap(map); // 지도 위에 마커를 표출합니다
         markers.push(marker); // 배열에 생성된 마커를 추가합니다

         return marker;
      }

      // 지도 위에 표시되고 있는 마커를 모두 제거합니다
      function removeMarker() {
         for (var i = 0; i < markers.length; i++) {
            markers[i].setMap(null);
         }
         markers = [];
      }

      // 검색결과 목록 하단에 페이지번호를 표시는 함수입니다
      function displayPagination(pagination) {
         var paginationEl = document.getElementById('pagination'), fragment = document
               .createDocumentFragment(), i;

         // 기존에 추가된 페이지번호를 삭제합니다
         while (paginationEl.hasChildNodes()) {
            paginationEl.removeChild(paginationEl.lastChild);
         }

         for (i = 1; i <= pagination.last; i++) {
            var el = document.createElement('a');
            el.href = "#";
            el.innerHTML = i;

            if (i === pagination.current) {
               el.className = 'on';
            } else {
               el.onclick = (function(i) {
                  return function() {
                     pagination.gotoPage(i);
                  }
               })(i);
            }

            fragment.appendChild(el);
         }
         paginationEl.appendChild(fragment);
      }

      // 검색결과 목록 또는 마커를 클릭했을 때 호출되는 함수입니다
      // 인포윈도우에 장소명을 표시합니다
      function displayInfowindow(marker, title) {
         var content = '<div style="padding:5px;z-index:1;">' + title
               + '</div>';

         infowindow.setContent(content);
         infowindow.open(map, marker);
      }

      // 검색결과 목록의 자식 Element를 제거하는 함수입니다
      function removeAllChildNods(el) {
         while (el.hasChildNodes()) {
            el.removeChild(el.lastChild);
         }
      }
      $(function() {
          $('#addList').click(function() {
             $('#menu_wrap2').hide();
             $('#menu_wrap').show();
          });
          $('#showList').click(function() {
             $('#menu_wrap2').show();
             $('#menu_wrap').hide();
          });
       });
     function delplace(id,day){
    	  $.ajax({
    			url : "TeamMemberController?command=delplace",
    			type : "post",
    			data : {
    				loc_id:id,
    				loc_day:day
    			},
    			dataType : "json",
    			success : function(data) {
    				failBox("삭제성공");		
    				
    				$('#menu_wrap2').html('<div class="menu_day">'+day+'일차</div>');
    				
    				if(Object.keys(data).length!=0){
    				for(var i=0;i<Object.keys(data).length;i++){
    					var m_id=Object.values(data)[i].m_id;	
    					var loc_id=Object.values(data)[i].loc_id;	
    					var loc_name=Object.values(data)[i].loc_name;
    					var loc_day=Object.values(data)[0].loc_day;
    					var loc_url=Object.values(data)[i].loc_url;	
    					var loc_cate=Object.values(data)[i].loc_cate;	
    					var loc_addr=Object.values(data)[i].loc_addr;
    					
    					
    					$('#menu_wrap2').append('<div class="wishItem"></div>');
    					
    					$('#menu_wrap2').children('div.wishItem').eq(i).append('<p>'+loc_name+'</p>');
    					$('#menu_wrap2').children('div.wishItem').eq(i).append('<p><span>카테고리</span><span>'+loc_cate+'</span></p>');
    					$('#menu_wrap2').children('div.wishItem').eq(i).append('<p><span>주소</span><span>'+loc_addr+'</span></p>');
    					$('#menu_wrap2').children('div.wishItem').eq(i).append('<p><button><a href="'+loc_url+'" target="_blank">상세보기</a></button></p>');	
    					if(m_id==='<%=(String)session.getAttribute("my_id")%>')
        					$('#menu_wrap2').children('div.wishItem').eq(i).children('p:nth-last-child(1)').append('<button onclick="delplace('+loc_id+','+loc_day+');">삭제</button>');
    				}  
    				}else{
    					$('#menu_wrap2').append('<br/>--리스트가 비였습니다. 추가해주세요!--');
    				}
    				
    				if($('#menu_wrap2').children('div.wishItem').length > 2){
    					$('#sr_nextBnt').removeAttr('disabled');
    				} else {
    					$('#sr_nextBnt').attr('disabled','disabled');
    				}
    				
    			},
    			error : function() {
    				failBox("통신오류"); 
    			}
    		});	 
     }
      function addplace(placedata){
    	  $.ajax({
  			url : "TeamMemberController?command=addplace",
  			type : "post",
  			data : {
  				data : placedata
  			},
  			dataType : "json",
  			success : function(data) {
  				failBox("담기성공");		
  				
  				var loc_day=Object.values(data)[0].loc_day;
  				$('#menu_wrap2').html('<div class="menu_day">'+loc_day+'일차</div>');
				for(var i=0;i<Object.keys(data).length;i++){
					var m_id=Object.values(data)[i].m_id;	
					var loc_id=Object.values(data)[i].loc_id;	
					var loc_name=Object.values(data)[i].loc_name;	
					var loc_url=Object.values(data)[i].loc_url;	
					var loc_cate=Object.values(data)[i].loc_cate;	
					var loc_addr=Object.values(data)[i].loc_addr;
					
					$('#menu_wrap2').append('<div class="wishItem"></div>');
					
					$('#menu_wrap2').children('div.wishItem').eq(i).append('<p>'+loc_name+'</p>');
					$('#menu_wrap2').children('div.wishItem').eq(i).append('<p><span>카테고리</span><span>'+loc_cate+'</span></p>');
					$('#menu_wrap2').children('div.wishItem').eq(i).append('<p><span>주소</span><span>'+loc_addr+'</span></p>');
					$('#menu_wrap2').children('div.wishItem').eq(i).append('<p><button><a href="'+loc_url+'" target="_blank">상세보기</a></button></p>');	
					if(m_id==='<%=(String)session.getAttribute("my_id")%>')
    					$('#menu_wrap2').children('div.wishItem').eq(i).children('p:nth-last-child(1)').append('<button onclick="delplace('+loc_id+','+loc_day+');">삭제</button>');
				}
				
				if($('#menu_wrap2').children('div.wishItem').length > 2){
					$('#sr_nextBnt').removeAttr('disabled');
				} else {
					$('#sr_nextBnt').attr('disabled','disabled');
				}
				
  			},
  			error : function() {
  				failBox("통신오류"); 
  			}
  		});
      }
      function updateList(day){
    	  $.ajax({
  			url : "TeamMemberController?command=updateList",
  			type : "post",
  			data : {
  				loc_day : day
  			},
  			dataType : "json",
  			success : function(data) {
  				$('#menu_wrap2').html('<div class="menu_day">'+day+'일차</div>');
				
				if(Object.keys(data).length!=0){
				for(var i=0;i<Object.keys(data).length;i++){
					var m_id=Object.values(data)[i].m_id;	
					var loc_id=Object.values(data)[i].loc_id;	
					var loc_name=Object.values(data)[i].loc_name;
					var loc_day=Object.values(data)[0].loc_day;
					var loc_url=Object.values(data)[i].loc_url;	
					var loc_cate=Object.values(data)[i].loc_cate;	
					var loc_addr=Object.values(data)[i].loc_addr;
					
					$('#menu_wrap2').append('<div class="wishItem"></div>');
					
					$('#menu_wrap2').children('div.wishItem').eq(i).append('<p>'+loc_name+'</p>');
					$('#menu_wrap2').children('div.wishItem').eq(i).append('<p><span>카테고리</span><span>'+loc_cate+'</span></p>');
					$('#menu_wrap2').children('div.wishItem').eq(i).append('<p><span>주소</span><span>'+loc_addr+'</span></p>');
					$('#menu_wrap2').children('div.wishItem').eq(i).append('<p><button><a href="'+loc_url+'" target="_blank">상세보기</a></button></p>');	
					if(m_id==='<%=(String)session.getAttribute("my_id")%>')
    					$('#menu_wrap2').children('div.wishItem').eq(i).children('p:nth-last-child(1)').append('<button onclick="delplace('+loc_id+','+loc_day+');">삭제</button>');
				}  
				
				}else{
					$('#menu_wrap2').append('<br/>--리스트가 비였습니다. 추가해주세요!--');
				}
				
				if($('#menu_wrap2').children('div.wishItem').length > 2){
					$('#sr_nextBnt').removeAttr('disabled');
				} else {
					$('#sr_nextBnt').attr('disabled','disabled');
				}
				console.log('리스트 새로고침 완료');
  			},
  			error : function() {
  				failBox("통신오류"); 
  			}
  		});
      }
      
     window.setInterval("updateList(<%=request.getParameter("loc_day") %>)", 15000);
      
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
</section>  
</body>
</html>