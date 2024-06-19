<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Insert title here</title>
</head>
<script src="./js/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="js/util.js"></script>

<style type="text/css">
@import
url("css/ReviewView.css");
</style>

<style type="text/css">
@import
url("css/bodyPosition.css");
@import
url("css/loading.css");
</style>
<script type="text/javascript" src="js/haederLoad.js"></script>


<script type="text/javascript" src="js/ReviewViewAjax.js"></script>
<script type="text/javascript">
	$(function() {
		var urlArray = window.location.pathname.split('/');
		var urlEnd = urlArray[urlArray.length - 1];
		console.log(urlEnd);
		reviewView(start, end, '${keyword}', '${reviewLocation}');
		$(document).on(
				"mousewheel DOMMouseScroll",
				function(e) {
					var E = e.originalEvent;
					if (E.deltaY > 0) {
						var scrollVal = Math.floor($(window).scrollTop());
						var scrollValChk = Math.floor($(document).height()- $(window).height());
						if (scrollVal >= scrollValChk - 75) {
							start += 8;
							end += 8;
							reviewView(start, end, '${keyword}',
									'${reviewLocation}');
						}
					}
				});
		$(".searchIcon").on("click", function() {
			$(".searchDiv").toggle("slow");
		});
		$(".searchBar").keydown(function(key) {
			var keyword = $(".searchBar").val()
			if (key.keyCode == 13) {
				location.href = urlEnd + '?keyword=' + keyword;
			}
		});
		$(".locationName").on("click", function() {
			location.href = urlEnd;
		})
		
		toolTipBoxOn('검색 창',$('a.searchIcon'));
		
		toolTipBoxOn($('span.locationName').html() + " 메인 화면으로...",$('span.locationName'));
		
		toolTipBoxOn('리뷰 작성',$('a.writeIcon'));
		
		 $(".loading").remove();
		
	});
	
	
</script>
<body>
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
		<article id="article_header">
			<div class="nowLocation">
			<c:set var="category" value="${reviewLocation}"></c:set>
				<c:choose>
					<c:when test="${reviewLocation eq 'TripReviewView'}">
					<a href="TripReviewList"><span class="locationName"> 여행 후기 </span></a></c:when>
					<c:when test="${reviewLocation eq 'RestaurantReviewView'}">
					<a href="RestaurantReviewList"><span class="locationName"> 맛집 리뷰 </span></a>
					</c:when>
					<c:when test="${reviewLocation eq 'RoomsReviewView'}">
					<a href="RoomsReivewList"><span class="locationName">숙소 리뷰 </span></a>
					</c:when>
					<c:when test="${reviewLocation eq 'TouristReviewView'}">
					<a href="TouristReviewList"><span class="locationName"> 명소 리뷰 </span></a>
					</c:when>
				</c:choose>
				
			</div>
			<c:if test="${not empty user}">
			<a href="${reviewLocation ne 'TripReviewView'? 'CategoryReviewWrite?category=': 'TripReviewWrite'}${reviewLocation ne 'TripReviewView'? category : ''}" class="writeIcon"></a>
			</c:if>
			<c:if test="${empty user}">
			<a href="#" class="writeIcon"
				onclick="failBox('로그인을 한 후 시도해주세요.');"></a>
			</c:if>
			<a href="#"
				onclick="return false;" class="searchIcon"></a>
			<div class="searchDiv">
				<input name="keyword" type="text" class="searchBar"
					placeholder="검색하고자 하는 키워드를 입력하세요.">
			</div>
		</article>
		<article id="article_contents"></article>
	</section>
</body>
</html>