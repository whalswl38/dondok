<%@page import="com.trip.dto.member.MemberLoginDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<link rel="stylesheet" type="text/css" href="css/loginheader.css">
<link rel="stylesheet" type="text/css" href="css/loginForm.css">

<script type="text/javascript"
	src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>
<script type="text/javascript" src="js/loginForm.js"></script>	
<script type="text/javascript">

	function submit(){
		$("#searchForm").attr("action", "UserSearchServlet");
		$("#searchForm").submit();
	}
	
	function goToLoginForm(){
		var url = "page?page=login"; //로그인 페이지 샘플 (보통은 /서블릿 액션명 을 넣는다)
		var popupName = "login"; //popup에 고유한 이름을 부여한다. 이름이 같은 팝업이 열릴때는 브라우저가 인식해서 중복팝업이 발생 시키지 않게 한다.
		var popupSetting = "width=500, height=700, menubar=no, status=no, toolbar=no"; //팝업이 열리는 시점에 세팅을 하는 곳
		window.open(url, popupName, popupSetting);
	}
	
	//로그인 화면에서 유저정보 return
	function fnLoginSuccess(data){
		$('#loginFormBox').remove();
		$('#loginBak').remove();
		$("header").after(data);
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
				alert("로그아웃 되었습니다.");
				location.href="page?page=main";
			}
		})
	}


	var num = 1;
		$(function(){
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
							var eventstartdate = eventObj.response.body.items.item[i].eventstartdate;
 							$("#eventstartdate").text(eventstartdate);
							var eventenddate = eventObj.response.body.items.item[i].eventenddate;
							$("#eventenddate").text(eventenddate);
							var title = eventObj.response.body.items.item[i].title;
							$("#title").text(title);
							var addr1 = eventObj.response.body.items.item[i].addr1;
							
							
							var split = addr1.split(" ");
							
// 							var yyyymmdd = (function(j){
// 								j.substring(0,3)+"."+j.substring(4,5)+"."+j.substring(6,7);
// 							})
// 							$("#eventstartdate").text(yyyymmdd(eventstartdate));
// 							$("#eventenddate").text(yyyymmdd(eventenddate));
						
							
							$("#addr1").text(split[0]+" "+split[1]+" "+split[2]);
							
						});
						selectEvent(0);
						
						setInterval(function(){
							selectEvent(num);
							num++;
							  
						},3000);
					}
				});
			}();
		});
	var alarmBtnCheck = false; //알람리스트 show/hide key
// 	카테고리면 알람 코멘트
	function cateFilter(cate){
		var str = "";
		if(cate == 0 ){
			str = "시스템 메세지가 있습니다.";
		} else if(cate == 1){
			str = "새로운 이벤트가 있습니다.";
		} else if(cate == 2){
			str = "작성하신 리뷰에 댓글이 달렸습니다.";
		} else if(cate == 3){
			str = "작성하신 여행 후기에 댓글이 달렸습니다.";
		}
		return str;
	}
	
	function onClickAlarmListItem(item, al_no){		//클릭된 알람리스트 삭제 및 페이지이동
		$(item).closest("li").remove();		//item에서 제일 가까운 li 삭제
		$.ajax({
			type : 'get',
			data : { "al_no" :  al_no},		//업데이트 할 알람번호 전달
			url : 'AlarmUpdate',			//aflag N->Y 업데이트 
			success : function() {			
				onClickAlarmBtn();
			},error:function(request,status,error){
				alert("code="+request.status+"message="+request.responseText+"error="+error);
			}
		});
		
	}	
	
	function dateFilter(date){			//날짜 포맷
// 		var today = new Date();
// 		var dd = today.getDate();
// 		var mm = today.getMonth()+1;
// 		var yyyy = today.getFullYear();
	
		var year = date.year+1900;
		var month = date.month+1;
		var day = date.date;
		
		var hours = date.hours;
		var min = date.minutes;
		var sec = date.seconds;
		
		return year+"."+month+"."+day;
		
		
	}

	function onClickAlarmBtn() {
		if(!alarmBtnCheck){
// 			리스트 생성
			$.ajax({
				type : 'get',
				url : 'AlarmList',
				dataType:"json",
				success : function(msg) {
					var dtolist = new Array();
					var html = "";
					
					for(var i=0;i<msg.dtoList.length; i++){
						dtolist[i] = msg.dtoList[i];
						
						html += "<li id='alarmListItem"+i+"'>" 
								+ dateFilter(dtolist[i].al_date) +
								" <a href='#'onclick='onClickAlarmListItem(alarmListItem" + i + ", "+dtolist[i].al_no+");'>" + cateFilter(dtolist[i].al_cate) + 
								"</a></li>"
								
								//console.log(dtolist[i]);
					} 
					$("#ulList").html(html);	//알람 ul태그에 ajax에서 추가한 li태그를 담은 html 출력
					
				},
				error:function(request,status,error){
					alert("code="+request.status+"message="+request.responseText+"error="+error);
				}
			});
			$("#alarmList").show();
			alarmBtnCheck = true;
			
		} else {		
//			카운트 재호출(최신화) 및 alarmList hide
			$.ajax({
				type : 'get',
				url : 'AlarmCount',
				dataType:"json",
				success : function(msg) {
					var alarmcount = msg.alarmCount;
					$("#alarmCount").text(alarmcount);
					
				},error:function(request,status,error){
					alert("code="+request.status+"message="+request.responseText+"error="+error);
				}
			});
			$("#alarmList").hide();
			alarmBtnCheck = false;
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
		
</script>

	<%MemberLoginDto user = (MemberLoginDto)session.getAttribute("user"); %>
	<%int alarmCount = (int)session.getAttribute("alarmCount"); %>
	
</head>
<body>



	<header>
		<form id="searchForm" method="post">
			<input  type="hidden" name="myid" value="<%=user.getM_id()%>"/>
		
		<div class="firstline">
			<div class="firstparagraph1">
				<div class="inline" id="homeimg">
					
				</div>
				<div class="inline" id="home">	
					<a href="TripReviewView">돈   독</a>
				</div>
				<div class="inline" id="menubar">
					<ul>
						<li id="menubarimg"><a><img alt="menu" src="img/mainheader/menubar.jpg"
								style="width: 40px; height: 40px;"></a>
							<ul>
								<li><a href="PageMoveServlet?command=schedule">일정관리</a>
									<ul>
										<li><a href="PageMoveServlet?command=scheduleCheck">일정조회</a></li>
										<li><a href="PageMoveServlet?command=scheduleView">일정보기</a></li>
										<li><a href="PageMoveServlet?command=scheduleRegister">일정등록</a></li>
									</ul>
								</li>
								<li><a href="PageMoveServlet?command=review">여행후기</a>
									<ul>
										<li><a href="">맛집후기</a></li>
										<li><a href="">명소후기</a></li>
										<li><a href="">숙소후기</a></li>
									</ul></li>
								<li><a href="PageMoveServlet?command=shareAlbum">앨범공유</a></li>
								<li><a href="PageMoveServlet?command=shareSchedule">일정공유</a></li>
							</ul></li>
					</ul>
				</div>
				
			</div>
			<span class="linebar">  |</span>
			<div class="firstcen">
				<div class="whatbtn">
					<a >
						<img alt="what" src="img/mainheader/what.png" style="width: 40px; height: 40px;">
					</a>
				</div>
				<span class="minibar">|</span>
				<input id = "search" type="text" name="search" placeholder="무엇을 찾으십니까?" />
			</div>                             
			<div class="firstparagraph3">
				<div id="searchbtn">
 					<a href = "" class="img-button" onclick="submit();" title="검색"><img alt="search" src="img/mainheader/search.jpg" style="width: 40px; height: 40px;">
 					</a>
				</div>
				<div class="login">
					<span class="linebar"> | </span>
						<div class="loginimg"></div>
						<div class="innerlogin">
							<ul>
								<li class="idmenu">
									<a href="javascript:onClickidmenuBtn();"> <%=user.getM_nick() %> </a>
										<ul id="hide" style="display:none; margin-top: 19px; position: relative; z-index: 99; margin-left: -105px; background-color:white; width:150px; font-size:medium;">
											<li><a href="#">개인 정보 수정</a></li>
											<li><a >작성 글 조회</a></li>
											<li><a>등록 스크랩 조회</a></li>
											<li><a>알림 조회</a></li>
											<li><a>로그아웃</a></li>
										</ul>
								</li>
							</ul>
						</div>
					<span class="linebar"> | </span>
				</div>
				<div id="alarmbtn" style= "position: absolute;">
					
					<a href="javascript:onClickAlarmBtn();" title="알림"><img alt="alarm" src="img/mainheader/alarm.png" style="width: 40px; height: 40px;">
						<span id="alarmCount" style="position: absolute; margin-left: -13px; margin-top: 20px;"><%=alarmCount %></span>
					</a>
					<div id="alarmList" style="display: none; width: 300px; position: absolute; margin-top: 5px; margin-left: -200px; z-index: 80; background-color: white; font-size:smaller;">
						<ul id="ulList">
						</ul>
					</div>
				</div>
			</div>
		</div>
		</form>
		
		<div class="secondline">
			
			 <div id = "secondcenter">
				[<div class="second" id="date">
					<span id= "eventstartdate"></span>~<span id="eventenddate"></span>
				</div>]  
				<div class="second" id="text_bar">|</div> 
				<div class="second" id="addr1" /></div>> 
				<div class="second" id="title" /></div> 
			</div>
		</div>
		
	</header>

</body>
</html>