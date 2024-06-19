<%@page import="com.trip.dto.review.TripReviewDto"%>
<%@page import="trip.dto.TeamMemberDto"%>
<%@page import="trip.dao.TeamMemberDao"%>
<%@page import="trip.dto.AccountDto"%>
<%@page import="trip.dto.LocationDto"%>
<%@page import="java.util.List"%>
<%@page import="trip.dto.TeamDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
%>
<%
	response.setContentType("text/html; charset=UTF-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- 헤드 -->
<script type="text/javascript" src="js/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="js/util.js"></script>
<style type="text/css">
@import url("css/bodyPosition.css");
@import url("css/loading.css");
@import url("css/viewTeamInfo.css");
</style>
<script type="text/javascript" src="js/haederLoad.js"></script>
<script type="text/javascript">
	$(function(){
		toolTipBoxOn('일정 관리로...',$('div.nowLocation').children('a'));
		$('div.loading').remove();
	});
</script>


<%
	TeamDto teamDto = (TeamDto) request.getAttribute("teamInfo");
	List<TeamMemberDto> teamMemberList = (List<TeamMemberDto>) request.getAttribute("teammemberInfo");
	AccountDto accDto = (AccountDto) request.getAttribute("accInfo");
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
				<a href="TeamMemberController?command=tripSearch">일정 조회</a> <span></span> <span
					class="locationName"><%=teamDto.getT_name()%></span>
			</div>
		</article>

		<article class="al_leftBox">
			<div class="al_days">
				<ul id="menu_days">
					<li>일정</li>
					<%
						String days_c = (String) request.getAttribute("days");
						for (int i = 1; i <= Integer.parseInt(days_c); i++) {
					%>
					<li><span id="days"><input class="btn al_dayBtn"
							type="button" id="day_<%=i%>" value="<%=i%> 일자"
							onclick="change_day(this.value.substring(0,this.value.indexOf(' 일자')));" /></span></li>
					<%
						}
					%>
				</ul>
			</div>

			<div class="al_memberBox">
				<ul id="menu_teammember">
					<li><span>맴버</span>
					<%
						for (int i = 0; teamMemberList.size() > i; i++) {
					%>
					<li><a class="userRoute" href="#"
						onclick="return detail('<%=teamMemberList.get(i).getTm_uid()%>');"><%=teamMemberList.get(i).getTm_uid()%></a>
						<script>
							$(function() {
								toolTipBoxOn('<%=teamMemberList.get(i).getTm_uid()%>가 원하는 경로 보기',$('a.userRoute').eq(<%=i%>));
							});
						</script>
					</li>
					<%
						}
					%>
				</ul>
			</div>
		</article>
		<article class="al_rightBox">
			<div class="infoBox">
				<ul id="infoUl">
					<li><span>여행기간</span><span><%=teamDto.getT_days()%> 일</span></li>
					<%if(request.getAttribute("trDto") != null){ %>
					<li><span>여행후기</span><span><a href="TripReviewRead?tv_no=<%=((TripReviewDto)request.getAttribute("trDto")).getTv_no()%>"><%=((TripReviewDto)request.getAttribute("trDto")).getTv_title()%></a></span></li>
					<%} %>
					<li><span>예금주</span><span><%=accDto.getAcc_holder()%></span></li>
					<li><span>은행명</span><span><%=accDto.getAcc_bank()%></span></li>
					<li><span>계좌번호</span><span><%=accDto.getAcc_num()%></span></li>
					<li><span>총비용</span><span><%=accDto.getAcc_price()%></span></li>
					<li><span>마일리지</span><span><%=accDto.getAcc_mileage()%></span></li>
				</ul>
			</div>
		</article>
		<article class="vt_content" style="position: relative;">
			<div class="al_day">1일차</div>
			<div id="map" style="width: 100%; height: 500px;"></div>
			<div class="saveDelete">
				<button class="vt_save" onclick='save_root();'>저장</button>
				<button class="vt_delete" onclick='delcle_root();'>취소</button>
			</div>
		</article>
		<%
			String day = (String) request.getAttribute("days");
			int days = Integer.parseInt(day);
			List<LocationDto> loc_list = (List<LocationDto>) request.getAttribute("loc_list");
			LocationDto room_info = (LocationDto) request.getAttribute("room_info");
			String latlng = (String) request.getAttribute("latlng");
			List<String> clickId = (List<String>) request.getAttribute("clickId");
		%>


		<script type="text/javascript"
			src="https://dapi.kakao.com/v2/maps/sdk.js?appkey=546aea18ae9a6bd31c67526ce6764169"></script>
		<script>
			var mapContainer = document.getElementById('map'), // 지도를 표시할 div  
			mapOption = {
				center : new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
				level : 9
			// 지도의 확대 레벨
			};
			var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
			var bounds = new kakao.maps.LatLngBounds();

			var markers = [];
			//마커를 표시할 위치와 title 객체 배열입니다 
			var positions = [];
			/*
		<%for (int i = 0; i < loc_list.size(); i++) {%>
			var position= {
			 cate:
		<%=loc_list.get(i).getLoc_cate()%>
			,
			 id:
		<%=loc_list.get(i).getLoc_id()%>
			,
			 url: '
		<%=loc_list.get(i).getLoc_url()%>
			', 
			 title: '
		<%=loc_list.get(i).getLoc_name()%>
			', 
			 latlng: new kakao.maps.LatLng(
		<%=loc_list.get(i).getLoc_y()%>
			,
		<%=loc_list.get(i).getLoc_x()%>
			)
			 };
		<%if (loc_list.get(i).getLoc_cate() != 1) {%>
			positions.push(position);
			 bounds.extend(position.latlng);
		<%}
			}%>
			var position={
			 cate:
		<%=room_info.getLoc_cate()%>
			,
			 id:
		<%=room_info.getLoc_id()%>
			,
			 url: '
		<%=room_info.getLoc_url()%>
			', 
			 title: '
		<%=room_info.getLoc_name()%>
			', 
			 latlng: new kakao.maps.LatLng(
		<%=room_info.getLoc_y()%>
			,
		<%=room_info.getLoc_x()%>
			)
			 }
			 positions.push(position);
			 bounds.extend(position.latlng);
			 */

			// 마커 이미지의 이미지 주소입니다
			var res_imageSrc = "img/res.png";
			var att_imageSrc = "http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_red.png";
			var hotel_imageSrc = "img/hotel.png";
			var imageSize = new kakao.maps.Size(24, 35); // 마커 이미지의 이미지 크기 입니다

			var clickPosition = [];//폴리라인
			var clickPosition_id = '';//DB저장 id
			var r_day = 1;//디폴트 1일

			//저장된 id가 존재하면
		<%if (clickId != null) {
				for (int i = 0; i < clickId.size(); i++) {%>
			clickPosition_id += (
		<%=clickId.get(i)%>
			+ '|');
		<%}
			}%>
			//경로가 있다면

			var clickLine = new kakao.maps.Polyline({
				map : map, // 선을 표시할 지도입니다 
				path : [], // 선을 구성하는 좌표 배열입니다 클릭한 위치를 넣어줍니다
				strokeWeight : 3, // 선의 두께입니다 
				strokeColor : '#db4040', // 선의 색깔입니다
				strokeOpacity : 1, // 선의 불투명도입니다 0에서 1 사이값이며 0에 가까울수록 투명합니다
				strokeStyle : 'solid', // 선의 스타일입니다
			});

			//map.setBounds(bounds);
			//loading(1);
			//display(positions);

			function display(positions) {
				for (var i = 0; i < positions.length; i++) {
					var markerImage;
					if (positions[i].cate == 2) {
						markerImage = new kakao.maps.MarkerImage(res_imageSrc,
								imageSize);
					} else if (positions[i].cate == 3) {
						markerImage = new kakao.maps.MarkerImage(att_imageSrc,
								imageSize);
					} else if (positions[i].cate == 1) {
						markerImage = new kakao.maps.MarkerImage(
								hotel_imageSrc, imageSize);
					}
					// 마커를 생성합니다
					var marker = new kakao.maps.Marker({
						map : map, // 마커를 표시할 지도
						position : positions[i].latlng, // 마커를 표시할 위치
						title : positions[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
						image : markerImage
					// 마커 이미지 
					});
					markers.push(marker);

					var infowindow = new kakao.maps.InfoWindow({
						content : positions[i].title
					// 인포윈도우에 표시할 내용
					});
					kakao.maps.event.addListener(marker, 'mouseover',
							makeOverListener(map, marker, infowindow));
					kakao.maps.event.addListener(marker, 'mouseout',
							makeOutListener(infowindow));
					kakao.maps.event.addListener(marker, 'click',
							makeClickListener(positions[i]));
				}
			}
			function makeClickListener(positions) {
				return function() {
					clickPosition.push(positions.latlng);
					if (clickPosition[clickPosition.length - 1] == clickPosition[clickPosition.length - 2]) {
						clickPosition.pop();
					} else {
						clickPosition_id += (positions.id + '|');
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
			//인포윈도우를 닫는 클로저를 만드는 함수입니다 
			function makeOutListener(infowindow) {
				return function() {
					infowindow.close();
				};
			}
			function save_root() {
				$.ajax({
					url : "TeamMemberController?command=selectRoute_save",
					type : 'post',
					data : {
						'r_day' : r_day,
						'clickPosition_id' : clickPosition_id
					},
					dataType : "text",
					success : function(data) {
						failBox('저장');

					},
					error : function() {
						failBox("통신오류");
					}
				});
			}

			function delcle_root() {
				$.ajax({
					url : "TeamMemberController?command=selectRoute_clear",
					type : 'post',
					data : {
						'r_day' : r_day
					},
					dataType : "json",
					success : function(data) {
						removeMarker();

						var positions = [];
						var bounds = new kakao.maps.LatLngBounds();

						for (var i = 0; i < Object.keys(data).length - 1; i++) {
							var position = {
								cate : data['room' + i].cate,
								id : data['room' + i].id,
								traditional : true,
								title : data['room' + i].title,
								url : data['room' + i].url,
								latlng : new kakao.maps.LatLng(
										data['room' + i].y, data['room' + i].x)
							};
							if (position.cate != 1) {
								positions.push(position);
								bounds.extend(position.latlng);
							}
						}

						var position = {
							cate : data['hotel'].cate,
							id : data['hotel'].id,
							traditional : true,
							title : data['hotel'].title,
							url : data['hotel'].url,
							latlng : new kakao.maps.LatLng(data['hotel'].y,
									data['hotel'].x)
						};
						positions.push(position);
						bounds.extend(position.latlng);

						map.setBounds(bounds);

						display(positions);
						
						
					},
					error : function() {
						failBox("통신오류");
					}
				});
			}

			function change_day(day) {
				r_day = day;
				$.ajax({
					url : "TeamMemberController?command=viewTeamInfo_day",
					type : 'post',
					data : {
						'loc_day' : day
					},
					dataType : "json",
					success : function(data) {
						removeMarker();

						var positions = [];
						var bounds = new kakao.maps.LatLngBounds();
						for (var i = 0; i < Object.keys(data).length - 1; i++) {
							var position = {
								cate : data['room' + i].cate,
								id : data['room' + i].id,
								traditional : true,
								title : data['room' + i].title,
								url : data['room' + i].url,
								latlng : new kakao.maps.LatLng(
										data['room' + i].y, data['room' + i].x)
							};
							if (position.cate != 1) {
								positions.push(position);
								bounds.extend(position.latlng);
							}
						}

						var position = {
							cate : data['hotel'].cate,
							id : data['hotel'].id,
							traditional : true,
							title : data['hotel'].title,
							url : data['hotel'].url,
							latlng : new kakao.maps.LatLng(data['hotel'].y,
									data['hotel'].x)
						};
						positions.push(position);
						bounds.extend(position.latlng);

						map.setBounds(bounds);

						loading(day);
						display(positions);
						$('div.al_day').html(day+'일차');
					},
					error : function() {
						failBox("통신오류");
					},beforeSend : function(){
						loadingBox($('article.vt_content'), {
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

				clickPosition = [];
				clickPosition_id = '';

				if (clickLine) {
					clickLine.setPath(clickPosition);
				}
			}

			function loading(day) {
				
						$.ajax({
							url : "TeamMemberController?command=selectRoute_loading",
							type : 'post',
							data : {
								'loc_day' : day
							},
							async : false,
							dataType : "json",
							success : function(data) {
								if (Object.keys(data).length != 0) {
									for (var i = 0; i < Object.keys(data).length - 1; i++) {
										var latlng = new kakao.maps.LatLng(
												data['latlng' + i].y,
												data['latlng' + i].x);
										clickPosition.push(latlng);
									}
									clickLine.setPath(clickPosition);
									clickPosition_id = data['rootId'];
								} else {
									clickPosition = [];
									clickPosition_id = '';
								}
							},
							error : function() {
								failBox("로딩 통신오류");
							}
						});
			}
			function detail(memid) {
				window
						.open("TeamMemberController?command=memDetail&mid="
								+ memid, "",
								"width = 500, height = 500, top = 100, left = 200, location = no");
				return false;
			}

			$(function() {
				change_day(1);
			});
		</script>
	</section>
</body>
</html>