<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<script src="./js/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="js/util.js"></script>

<style type="text/css">
@import
url("css/ReviewView.css");
</style>

<script type="text/javascript" src="js/MainViewAjax.js"></script>
<script type="text/javascript">

$(function(){
	mainView(1, 8, '${keyword}','TripReviewView',0);
	mainView(1, 8, '${keyword}','RestaurantReviewView',1);
	mainView(1, 8, '${keyword}','RoomsReviewView',2);
	mainView(1, 8, '${keyword}','TouristReviewView',3);
	
	$(".searchIcon").each(function(i){
		$(this).on("click", function() {
			$(".searchDiv").eq(i).toggle("slow");
		});
		$(".searchBar").eq(i).keydown(function(key) {
			var keyword = $(this).val();
			if (key.keyCode == 13) {
				var urlEnd;
				if(i == 0){
					urlEnd = 'TripReviewList';
				} else if (i == 1){
					urlEnd = 'RestaurantReviewList';
				} else if (i == 2){
					urlEnd = 'RoomsReviewList';
				} else if (i == 3){
					urlEnd = 'TouristReviewList';
				}
				console.log(urlEnd);
				location.href = urlEnd + '?keyword=' + keyword;
			}
		});
	});
	
	
	$(".locationName").on("click", function() {
		location.href = urlEnd;
	})
	
	
	$('.writeIcon').each(function(i){
		if(i == 0){
			toolTipBoxOn('여행 후기 작성...',$(this));
		} else if(i == 1){
			toolTipBoxOn('맛집 후기 작성...',$(this));
		} else if(i == 2){
			toolTipBoxOn('숙소 후기 작성...',$(this));
		} else if(i == 3){
			toolTipBoxOn('명소 후기 작성...',$(this));
		}
	});

	$('.nowLocation').each(function(i){
		if(i == 0){
			toolTipBoxOn('여행 후기 더 보기...',$(this));
		} else if(i == 1){
			toolTipBoxOn('맛집 후기 더 보기...',$(this));
		} else if(i == 2){
			toolTipBoxOn('숙소 후기 더 보기...',$(this));
		} else if(i == 3){
			toolTipBoxOn('명소 후기 더 보기...',$(this));
		}
	});
	
	$(".searchIcon").each(function(i){
		if(i == 0){
			toolTipBoxOn('여행 후기 검색...',$(this));
		} else if(i == 1){
			toolTipBoxOn('맛집 후기 검색...',$(this));
		} else if(i == 2){
			toolTipBoxOn('숙소 후기 검색...',$(this));
		} else if(i == 3){
			toolTipBoxOn('명소 후기 검색...',$(this));
		}
	});
	
	$('.loading').remove();

});
</script>

<style type="text/css">
@import
url("css/bodyPosition.css");
@import
url("css/loading.css");

article.article_contents {
	margin-bottom : 40px;
}
</style>
<script type="text/javascript" src="js/haederLoad.js"></script>
</head>
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
		<article class="article_header">
			<div class="nowLocation">
					<a href="TripReviewList"><span class="locationName"> 여행 후기 </span></a>
				
			</div>
			<c:if test="${not empty user}">
			<a href="TripReviewWrite" class="writeIcon"></a>
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
		<article class="article_contents"></article>
		<article class="article_header">
			<div class="nowLocation">
					<a href="RestaurantReviewList"><span class="locationName"> 맛집 리뷰 </span></a>
			</div>
			<c:if test="${not empty user}">
			<a href="CategoryReviewWrite?category=RestaurantReviewView" class="writeIcon"></a>
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
		<article class="article_contents"></article>
		<article class="article_header">
			<div class="nowLocation">
					<a href="RoomsReivewList"><span class="locationName"> 숙소 리뷰 </span></a>
				
			</div>
			<c:if test="${not empty user}">
			<a href="CategoryReviewWrite?category=RoomsReviewView" class="writeIcon"></a>
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
		<article class="article_contents"></article>
		<article class="article_header">
			<div class="nowLocation">
					<a href="TouristReviewList"><span class="locationName"> 명소 리뷰 </span></a>
				
			</div>
			<c:if test="${not empty user}">
			<a href="CategoryReviewWrite?category=TouristReviewView" class="writeIcon"></a>
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
		<article class="article_contents"></article>
	</section>
</body>
</html>