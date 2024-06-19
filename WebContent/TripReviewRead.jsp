<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script src="./js/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="js/util.js"></script>

<style type="text/css">
@import url("css/article_header.css");

@import url("css/TripReviewRead.css");

@import url("css/comment.css");

</style>


<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=fea212ccfdfdea229404673b58166748"></script>
<script type="text/javascript" src="js/TripReviewRead.js"></script>
<script type="text/javascript" src="js/ReviewComment.js"></script>
<script type="text/javascript">

	$(function(){
		$('div.loading').remove();
	});

</script>
<!-- 헤드 삽입 -->
<style type="text/css">
@import
url("css/bodyPosition.css");
@import
url("css/loading.css");
</style>
<script type="text/javascript" src="js/haederLoad.js"></script>	

<body>

<!-- 로딩 -->
<div class="loading" style="z-index:9999;top:0; height: 100%;background-color: #fff; opacity: 1; position: fixed;"><div class="sk-fading-circle">
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
<!-- 

tmList : 맴버 객체

	int tm_tid(팀 SEQ), tm_stage(진행 단계);
	String tm_uid(유저 아이디), tm_depflag(완료 여부);

trDto : 일정 후기 정보 객체

	int tv_no(여행 후기 SEQ), tv_teamid(팀 SEQ), tv_count(조회수);
	String tv_title(여행 후기 제목), tv_delflag(여행 후기 삭제 여부);
	Date  tv_date(작성일),tv_modifydate(수정일);

tDto : 팀 정보 객체
	int t_id(팀 SEQ);
	String t_name(팀 명), t_leaderid(리더명), t_days(일자), t_stage(진행단계), t_deadline(기한), t_flag(완료 여부);
	Date  t_startdate(시작날), t_enddate(끝날);


 -->
 
 <!-- 코멘트용 정보 -->
<input name = "selectComment" type="hidden" value="trip"/>
<input name = "rv_crno" type="hidden" value="${trDto.tv_no}"/>
<input name = "user" type="hidden" value="${user.m_id}"/>

<!-- Content Load 용 -->
<input type="hidden" name="tv_teamid" value="${trDto.tv_teamid}"/>

<section class="projectSection">
		<article id="article_header">
			<div class="nowLocation">
				<a href="TripReviewList">여행 후기</a><span></span>
				<span class="locationName">${trDto.tv_title}</span>
				<c:forEach var="memberList"	items="${tmList}">
					<c:if test="${user.m_id eq memberList.tm_uid}">
						<a href="#" onclick="return false;" class="modify">수정</a>
					</c:if>
				</c:forEach>
				<c:if test="${not empty favoriteCheck}">
					<c:if test="${favoriteCheck eq 1}">
						<a href="#" class="wishBtn wishDel" onclick="return favoriteDelete(1);">★</a>
					</c:if>
					<c:if test="${favoriteCheck ne 1}">
						<a href="#" class="wishBtn wishAdd" onclick="return favoriteInsert(1);">★</a>
					</c:if>
				</c:if>
			</div>
		</article>
		<article class="contents">
			<div class="contents_head">
				<p class="head">
					<span class="id" data-t_id="${tDto.t_id}">${tDto.t_name}</span> <span class="date"><fmt:formatDate
							value="${trDto.tv_date}" pattern="yyyy/MM/dd"/></span> <span>조회수</span>
					<span class="count">${trDto.tv_count}</span>
				</p>
				<ul class="memberList">
					<c:forEach var="member" items="${tmList}">
						<li class="member"><div></div>${member.tm_uid}</li>
					</c:forEach>
				</ul>
				<div class="locationMap">
					<div id="map" style="position:relative;width:100%;height:100%;"></div>
				</div>
				<div class="dayPage" data-days="${tDto.t_days}">
					<a><div class="DayPrevBtnImg"></div></a>
					<div><a></a></div>
					<div><a></a></div>
					<div><a></a></div>
					<div><a></a></div>
					<div><a></a></div>
					<a><div class="DayNextBtnImg"></div></a>
				</div>
			</div>
			<div class="contents_body">
				
			</div>
			<div class="contents_footer">
				<a href="#" onclick="return false;"><div class="commentBtn">
						<p>
							<img src="./images/board_icon/comment_icon.png" /> <span>댓글</span>
							<span></span> <span></span>
						</p>
					</div></a>
			<c:forEach var="memberList"	items="${tmList}">
				<c:if test="${user.m_id eq memberList.tm_uid}">
					<a href="TripReviewRead?cr_no=${trDto.tv_no}" class="delete">삭제</a>
				</c:if>
			</c:forEach>
			<a href="#" onclick="history.back(-1);" class="list">목록</a>
			</div>
		</article>
		<article class="footer">
		<!-- 코멘트 -->
			<div class="commentDiv">
				<div class="commentPage">
				<!-- 페이징 모듈 -->
					<div class="pageGroup">
						<a onclick="return false;"><div class="prevBtn">
								<div class="prevBtnImg"></div>
							</div></a> <a onclick="return false;"><div class="numBtn">
								<span class="num"></span>
							</div></a> <a onclick="return false;"><div class="numBtn">
								<span class="num"></span>
							</div></a> <a onclick="return false;"><div class="numBtn">
								<span class="num"></span>
							</div></a> <a onclick="return false;"><div class="numBtn">
								<span class="num"></span>
							</div></a> <a onclick="return false;"><div class="numBtn">
								<span class="num"></span>
							</div></a> <a onclick="return false;"><div class="nextBtn">
								<div class="nextBtnImg"></div>
							</div></a>
					</div>
				</div>
				<div class="commentWrite">
					<div>
						<div class="commentWriteBac">
							<p class="commentMyId">${user.m_id}</p>
							<div class="commentTextArea">
								<c:choose>
									<c:when test="${empty user}">
										<textarea class="commentContext" placeholder="해당 가능은 로그인이 있어야 사용 가능 합니다." readonly="readonly"></textarea>
									</c:when>
									<c:otherwise>
										<textarea class="commentContext"></textarea>
									</c:otherwise>
								</c:choose>
								<button class="commentSummit">등록</button>
							</div>
							<p class="commentCount"><span>0</span>/1500</p>
						</div>
					</div>
				</div>
			
			</div>
		</article>
</section>

</body>
</html>