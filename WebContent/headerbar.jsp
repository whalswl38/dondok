<%@page import="com.trip.dto.member.MemberLoginDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
	<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<link rel="stylesheet" type="text/css" href="css/loginheader.css">
<link rel="stylesheet" type="text/css" href="css/loginForm.css">
<style>
.toolTip {
	position : fixed;
	z-index:10010;
}
</style>
<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>
<script type="text/javascript" src="js/loginForm.js"></script>
<script type="text/javascript">

//회원정보 수정 페이지 연결을 위해 추가 [조민지]
function fnMemberModify(){
	var url = "memberModify"; //로그인 페이지 샘플 (보통은 /서블릿 액션명 을 넣는다)
	var popupName = "memberModify"; //popup에 고유한 이름을 부여한다. 이름이 같은 팝업이 열릴때는 브라우저가 인식해서 중복팝업이 발생 시키지 않게 한다.
	var popupSetting = "width=500, height=700, menubar=no, status=no, toolbar=no"; //팝업이 열리는 시점에 세팅을 하는 곳
	window.open(url, popupName, popupSetting);
	return false;
}


	function goToLoginForm(){
		var url = "/dondok/page?page=login"; //로그인 페이지 샘플 (보통은 /서블릿 액션명 을 넣는다)
		var popupName = "login"; //popup에 고유한 이름을 부여한다. 이름이 같은 팝업이 열릴때는 브라우저가 인식해서 중복팝업이 발생 시키지 않게 한다.
		var popupSetting = "width=500, height=700, menubar=no, status=no, toolbar=no"; //팝업이 열리는 시점에 세팅을 하는 곳
		window.open(url, popupName, popupSetting);
		return false;
	}
	
	//로그인 화면에서 유저정보 return
	function fnLoginSuccess(data){
		/*
		$('#loginFormBox').remove();
		$('#loginBak').remove();
		$("header").after(data);
		$('#usernotlogin').remove();
		$('#userlogin').show();
		$('#alarmbtn').show();
		$('#loginIdbar').text(data.m_nick);
		*/

		failBox(data.m_nick+'님, 환영합니다.');
		
		$('#failClose').on('click',function(){
			window.location.reload();
		});
	}
	
	function logout(){
		$.ajax({
			type:'post',
			url : 'logout',
			dataType : 'json',
			success : function(data){
				if(!data.result){
					return alert("로그인에 실패했습니다.");
				}
				failBox('로그아웃 되었습니다.');
				/*
				$('#userlogin').hide();
				$('#usernotlogin').show();
				$('#alarmbtn').hide();
				onClickidmenuBtn();
				var logBtn = `<div id="usernotlogin"  style="width : 45%; height : 40px; margin-left:10px; margin-top:9px; float:left;"><a href="#" onclick = "goToLoginForm();">로 그 인</a></div>`;
				$('div.loginimg').after(logBtn);
				*/
				$('#failClose').on('click',function(){
					location.href='MainList';
				});
			}
		})
	}


	var num = 1;
		$(function(){
			
			toolTipBoxOn('검색',$('a.img-button'));
			
			toolTipBoxOn('메뉴',$('#menuicon'));
			
			toolTipBoxOn('검색',$('a.img-button'));
			
			toolTipBoxOn('메인 화면으로...',$('#home'));
			
			toolTipBoxOn('알람',$('#alarmbtn img'));
			
			
			$('div.loginimg').hover(function(){
				
				$('div.sizeUpImg').css({
				    'background-repeat': 'no-repeat',
			    	'background-size': 'cover',
			    	'position': 'absolute',
			        'right': '50px',
			    	'z-index': '999',
			    	'display': 'block',
			    	'width': 'inherit',
			    	'height': 'inherit',
			    	'border-radius': '15px'
				});
				
			}, function(){
				
				$('div.sizeUpImg').css({
					'display' : 'none'
				});
				
			});
			
			/* 공공 데이터 문제로 잠시 정지
			var eventCon = function(){
					$.ajax({
					type : 'post',
					url : 'TripEvent.do',
					dataType : 'json',
					success : function(msg) {
						var eventObj = msg;
						console.log(eventObj);
						
						var selectEvent = (function(i){
							var len = eventObj.response.body.items.item.length;	
							if(i === len){
								num = 0;
								return;
							}
							
							
							function parse(str) {		
							    var y = str.substr(0, 4);
							    var m = str.substr(4, 2);
							    var d = str.substr(6, 2);
							    return new Date(y,m-1,d);
							}
							
							Date.prototype.format = function(f) {
								if (!this.valueOf()) return " ";

								var weekName = ["일", "월", "화", "수", "목", "금", "토"];
								var d = this;
								
								return f.replace(/(yyyy|MM|dd|E|)/gi, function($1) {
									switch ($1) {
										case "yyyy": return d.getFullYear();
										case "MM": return (d.getMonth() + 1).zf(2);
										case "dd": return d.getDate().zf(2);
										case "E": return weekName[d.getDay()];
										default: return $1;
									}
								});
							};

							String.prototype.string = function(len){var s = '', i = 0; while (i++ < len) { s += this; } return s;};
 							String.prototype.zf = function(len){return "0".string(len - this.length) + this;};
 							Number.prototype.zf = function(len){return this.toString().zf(len);};

							var eventstartdate = eventObj.response.body.items.item[i].eventstartdate+"";
							//console.log(parse(eventstartdate).format("yyyy.MM.dd(E)"));
							$("#eventstartdate").text(parse(eventstartdate).format("yyyy.MM.dd(E)"));
							
							var eventenddate = eventObj.response.body.items.item[i].eventenddate+"";
							
							$("#eventenddate").text(parse(eventenddate).format("yyyy.MM.dd(E)"));
							
							var addr1 = eventObj.response.body.items.item[i].addr1;
							var split = addr1.split(" ");
							$("#addr1").text(split[0]+" "+split[1]+" "+split[2]);
							
							
							var title = eventObj.response.body.items.item[i].title;
							var titleDiv = "<div class='titleCheck' style='margin-left:10px;display:inline-block;'>&#187;<div class='second' id='title'/>"+ title +"</div></div>";
							$("div.titleCheck").remove();
							$("#addr1").after(titleDiv);
							
							
						});
						selectEvent(0);
						
						
						
						setInterval(function(){
							selectEvent(num);
							num++;
						},3000);
					}
				});
			}();
			*/
			
			$('#search').keydown(function(key){
				if(key.keyCode == 13){
					var keyword = $("#search").val();
					location.href = 'SearchServlet?listpage=MainList&search='+keyword;
					
				}
			});
			
			$('.img-button').on('click',function(){
				var keyword = $("#search").val();
				location.href = 'SearchServlet?listpage=MainList&search='+keyword;
			});
		});
		
		var alarmBtnCheck = false; //알람리스트 show/hide key
//	 	카테고리면 알람 코멘트
		function cateFilter(cate,al_rno, al_rtitle){
			var str = "";
			var tmp = al_rtitle;
			if(al_rtitle){
				if(al_rtitle.length > 13){
					tmp = al_rtitle.substring(0,13)+"...";
				}
			}
			
			if(cate == 0 ){
				str = "시스템 메세지가 있습니다.";
			} else if(cate == 1){
				str = "새로운 이벤트가 있습니다.";
			} else if(cate == 2){
				str = "작성하신 리뷰 <a class='goPage' href='CategoryReviewRead?cr_no="+al_rno+"';>\"" + tmp + "\"</a>에 댓글이 달렸습니다.";
			} else if(cate == 3){
				str = "작성하신 여행 후기 <a class='goPage' href='TripReviewRead?tv_no="+al_rno+"';>\"" + tmp + "\"</a>에 댓글이 달렸습니다.";
			}
			return str;
		}
		
		function onClickAlarmListItem(item, al_no, al_cate, al_rno){		//클릭된 알람리스트 삭제 및 페이지이동
			$(item).closest("li").remove();		//item에서 제일 가까운 li 삭제
			$.ajax({
				type : 'get',
				data : { "al_no" :  al_no},		//업데이트 할 알람번호 전달
				url : 'AlarmUpdate',			//aflag N->Y 업데이트 
				success : function() {
					
					alarmList();
					alarmCount();
					
					if(al_cate == 3){
						
						location.href='TripReviewRead?tv_no='+al_rno;
					}
						
					if($("#alarmCount").text()==0){
						$("#alarmList").slideToggle();
					}
				},error:function(request,status,error){
					alert("code="+request.status+"message="+request.responseText+"error="+error);
				}
			});
			
		}	
		
		function dateFilter(date){			//알람기능 날짜 포맷
//	 		var today = new Date();
//	 		var dd = today.getDate();
//	 		var mm = today.getMonth()+1;
//	 		var yyyy = today.getFullYear();
		
			var year = date.year+1900;
			var month = "" + Number(date.month+1);
			month = (month.length == 1)? "0"+month : month;
			var day = "" + date.date;
			day = (day.length == 1)? "0"+day : day;
			
			var hours = date.hours;
			var min = date.minutes;
			var sec = date.seconds;
			
			return year+"."+month+"."+day;
			
			
		}

		
		function alarmList(){
			
			$.ajax({
				type : 'get',
				url : 'AlarmList',
				dataType:"json",
				async : false,
				success : function(msg) {
					var dtolist = new Array();
					var html = "";
					
					for(var i=0;i<msg.dtoList.length; i++){
						dtolist[i] = msg.dtoList[i];
						
						html += "<li class='alarmItem' id='alarmListItem"+i+"' onclick='onClickAlarmListItem(alarmListItem" + i + ", "+dtolist[i].al_no+","+dtolist[i].al_cate+","+dtolist[i].al_rno+");'>" 
								+"<span style='color:#777777;'>"+ dateFilter(dtolist[i].al_date) + "</span> "
								+ cateFilter(dtolist[i].al_cate,dtolist[i].al_rno, dtolist[i].al_rtitle ) + 
								"</a></li>"
								console.log(dtolist[i]);
					} 
					$("#ulList").html(html);	//알람 ul태그에 ajax에서 추가한 li태그를 담은 html 출력
					
					toolTipBoxOn('해당 페이지로 가기...', $('a.goPage'));
					$('li.alarmItem').hover(function(){
						$(this).css({
							'transition-duration': '0.3s',
							'background-color' : '#dcdcdc'
						});
					}, function(){
						$(this).css({
							'transition-duration': '0.3s',
							'background-color' : '#fff'
						});
					});
					
				},
				error:function(request,status,error){
					alert("code="+request.status+"message="+request.responseText+"error="+error);
				}
				
			});
		}
		
		function alarmCount(){
			$.ajax({
				type : 'get',
				url : 'AlarmCount',
				dataType:"json",
				async : false,
				success : function(msg) {
					var alarmcount = msg.alarmCount;
					$("#alarmCount").text(alarmcount);
					
				},error:function(request,status,error){
					alert("code="+request.status+"message="+request.responseText+"error="+error);
				}
			});
		}
		
		function onClickAlarmBtn() {
			if($("#alarmCount").text()!=0){	
				if(!alarmBtnCheck){
//	 				리스트 생성
					alarmList();
					alarmBtnCheck = true;
				
				} else {		
//					카운트 재호출(최신화) 및 alarmList hide
					alarmCount();
					alarmBtnCheck = false;
				}
				$("#alarmList").slideToggle();
			} else {
				failBox("추가된 알람이 없습니다.");
			}
		}
		
		var idmenuBtnCheck = false;
		function onClickidmenuBtn(){	//id메뉴바 show/hide
			if(!idmenuBtnCheck){
				$("#hide").show();
				idmenuBtnCheck = true;
			} else {
				$("#hide").hide();
				idmenuBtnCheck = false;
			}
		}
		
		var menuBarOpen = function(){
			$('div.menuBar').slideToggle(400);
		};
		
		var usermenuBarOpen = function(){
			$('div.userMenuBar').slideToggle(400);
			return false;
		}
		
</script>
<span class="toolTip"></span>
	<header>
		<div class="headerBox">
		<div class="firstline">
			<div class="firstparagraph1">
				<div class="inline" id="homeimg" style ="background-image: url('./images/logo.png'); background-size: 100% 100%;"></div>
				<div class="inline" id="home">	
					<a href="./">돈   독</a>
				</div>
				<div class="inline" id="menuicon" onclick="menuBarOpen();"></div>
 				<div class="menuBar">
 					<div class="barTrangle"></div>
					<ul id="menuUl">
								<c:if test="${not empty user}">
									<li class="subtitle"><a href="TeamMemberController?command=tripSearch">&nbsp;일정관리</a></li>
								</c:if>
								<c:if test="${empty user}">
									<li class="subtitle"><a href="javascript:failBox('로그인 후 사용해주세요.');">&nbsp;일정관리</a></li>
								</c:if>
								<!--
								<li class="subtitle">
									<ul class="subUl">
										<li><a href="PageMoveServlet?command=scheduleView">&nbsp;&nbsp;일정확정</a></li>
										<li><a href="TeamMemberController?command=createTeam">&nbsp;&nbsp;일정등록</a></li>
									</ul>
								</li>
								  -->
								<li class="subtitle"><a href="TripReviewList">&nbsp;여행후기</a></li>
								<li class="subtitle">
									<ul class="subUl">
										<li><a href="RestaurantReviewList">&nbsp;&nbsp;맛집후기</a></li>
										<li><a href="TouristReviewList">&nbsp;&nbsp;명소후기</a></li>
										<li><a href="RoomsReviewList">&nbsp;&nbsp;숙소후기</a></li>
									</ul></li>
								<!-- 
								<li class="subtitle"><a href="PageMoveServlet?command=shareAlbum">&nbsp;앨범공유</a></li>
								<li class="subtitle"><a href="PageMoveServlet?command=shareSchedule">&nbsp;일정공유</a></li>
								 -->
					</ul>
				</div>
			</div>
			<span class="linebar"></span>
			<div class="firstcen">
				<span class="minibar"></span>
				<div class="whatbtn">
					<a >
						<img alt="what" src="img/mainheader/what.png" style="width: 40px; height: 40px;">
					</a>
				</div>
				<input id = "search" type="text" name="search" placeholder="무엇을 찾으십니까?" />
			</div>
			<span class="linebar"></span>                        
			<div class="firstparagraph3">
				<div id="searchbtn">
 					<a class="img-button" href="#" onclick="return submit();"></a>
				</div>
				<div class="login">
						<c:if test="${not empty user}">
							<c:if test="${not empty user.m_filepath}">
							<c:set var="pathIndex" value="${fn:indexOf(user.m_filepath,'images/')}"></c:set>
							<c:set var="endIndex" value="${fn:length(user.m_filepath)}"></c:set>
							<c:set var="path" value="${fn:substring(user.m_filepath,pathIndex,endIndex)}"></c:set>
							<div class="loginimg" style="background-image: url('${path}'); background-size : 100% 100%;"></div>
							<div class="sizeUpImg" style="background-image: url('${path}'); display: none;"></div>
							</c:if>
							<c:if test="${empty user.m_filepath}">
							<div class="loginimg" style="background-image: url('images/default/user.svg'); background-size : 100% 100%;"></div>
							</c:if>
						</c:if>
						<c:if test="${empty user}">
						<script>
							toolTipBoxOn('로그인 창으로..',$('div.usertag'));
						</script>
						<div id="usernotlogin" class="usertag"><a href="#" onclick = "return goToLoginForm();">로 그 인</a></div>
						</c:if>
						<c:if test="${not empty user}">
						<script>
							alarmCount();
							toolTipBoxOn('유저 메뉴',$('div.usertag'));
						</script>
						<div id="usernotlogin" class="usertag"><a href="#" onclick = "return usermenuBarOpen();">${user.m_nick}</a></div>
						</c:if>	
						<div class="userMenuBar">
							<div class="barTrangle"></div>
							<ul id="hide">
								<c:if test="${not empty user}">
								<li><a href="#" onclick="return fnMemberModify();">개인 정보 수정</a></li>
								</c:if>
								<c:if test="${empty user }">
								<li><a href="#" onclick="return failBox('로그인 후 사용 가능합니다.');">개인 정보 수정</a></li>
								</c:if>
								<!-- 
								<li><a >작성 글 조회</a></li>
								<li><a>등록 스크랩 조회</a></li>
								<li><a>알림 조회</a></li>
								 -->
								<li><a href="#" onclick="return logout();">로그아웃</a></li>
							</ul>
						</div>
						</div>
				<c:if test="${not empty user}">
				<div id="alarmbtn" style= "position: absolute; display: inline-block;">
					<a href="javascript:onClickAlarmBtn();"><img alt="alarm" src="img/mainheader/alarm.png" style="width: 40px; height: 40px;">
						<span id="alarmCount" style="position: absolute;
    margin-left: -12.5px;
    margin-top: 22px;
    font-size: 10pt;
    color: #322;"></span>
					</a>
					<div id="alarmList">
						<div class="barTrangle" style="right: 72px;
    left: unset;"></div>
						<ul id="ulList">
						</ul>
					</div>
				</div>
				</c:if>
			</div>
		</div>
		<div class="secondline">
			 <div id = "secondcenter">
				[<div class="second" id="date">
					<span id= "eventstartdate"></span>~<span id="eventenddate"></span>
				</div>]  
				<div class="second" id="text_bar">|</div> 
				<div class="second" id="addr1" />공공데이터 포털에서 제공하는 api가 폭파돼서 바로 24시간 전까지 되던게 작동이 정지 되었습니다.</div>
			</div>
		</div>
		</div>
	</header>