<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>

<script src="./js/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="./js/util.js"></script>
<link href="./api/summernote/summernote-lite.min.css" rel="stylesheet">
<script src="./api/summernote/summernote-lite.min.js"></script>
<script type="text/javascript" src="./js/CategoryReviewModify.js"></script>
<script type="text/javascript" src="./js/PlaceSearch.js"></script>
<script type="text/javascript">
	placeNameSelect('${dto.cr_placeid}');
	$(function(){
		$('.loading').remove();
	});
</script>
<style type="text/css">
@import
url("css/CategoryReviewWrite.css");

@import
url("css/article_header.css");

@import
url("css/CategoryPlaceSearch.css")
</style>

<!-- 헤드 삽입 -->
<style type="text/css">
@import
url("css/bodyPosition.css");
</style>
<script type="text/javascript" src="js/haederLoad.js"></script>	


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
				<span class="locationName" style="margin-top: 5px;">${dto.cr_title}</span>
			</div>
		</article>
		<article id="writeSection">
			<div class="writeDiv">
				<form action="CategoryReviewModifyRes" method="POST">
					<input type="hidden" name="cr_no" value="${dto.cr_no}"/>
					<input type="hidden" name="cr_placeid" value="${dto.cr_placeid}"/>
					<input type="hidden" name="category" value="${dto.cr_category}" />
					<input type="hidden" name="cr_path" value="${dto.cr_path}">
					<div class="writeHead">
						<div class="titleForm">
							<p class="titleP">
								<span>제목</span><input type="text" name="cr_title" value="${dto.cr_title}"/>
							</p>
						</div>
						<div class="placeForm">
							<p class="placeP">
								<span>장소</span><img src="images/board_icon/location_icon.png"/><span><span></span></span><a href="#" onclick="return false;" class="searchIcon"></a>
							</p>
						</div>
					</div>
					<div class="writeMiddle">
						<textarea id="summernote" name="cr_contents">${dto.cr_contents}</textarea>
					</div>
					<div class="writeFoot">
						<input type="submit" value="작성" />
						<input type="button" value="목록"/>
					</div>
				</form>
			</div>
		</article>
	</section>
</body>
</html>