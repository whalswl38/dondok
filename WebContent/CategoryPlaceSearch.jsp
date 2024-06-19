<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<article id="searchBox">
	<div class="box">
		<div class="closeBtn"></div>
		<div class="Bar">
			<input
				type="text" name="searchKey" placeholder="${category}를 검색해주세요." />
			<input type="button" name="searchBtn" value="검색"
				onclick="searchFun();" />
		</div>
		<div class="list">
			<div class="listValue">
				<span class="id"></span>
				<div class="img">
					<img />
				</div>
				<div class="info">
					<p>
						<span class="infoTitle"></span>
					</p>
					<p>
						<span class="infoProp">주소</span><span class="infoContent"></span>
					</p>
					<p>
						<span class="infoProp">카테고리</span><span class="infoContent"></span>
					</p>
					<p>
						<span class="infoProp">연락처</span><span class="infoContent"></span>
					</p>
				</div>
				<div class="selectDiv">
					<div class="wish" onclick="wishSelect(0)"></div>
					<div class="kakaoBtn">
						<a><img src="./images/board_icon/kakao_ci_screen.png" /><img
							src="./images/board_icon/kakao_ci_screen_over.png" /></a>
					</div>
				</div>
			</div>
			<div class="listValue">
				<span class="id"></span>
				<div class="img">
					<img />
				</div>
				<div class="info">
					<p>
						<span class="infoTitle"></span>
					</p>
					<p>
						<span class="infoProp">주소</span><span class="infoContent"></span>
					</p>
					<p>
						<span class="infoProp">카테고리</span><span class="infoContent"></span>
					</p>
					<p>
						<span class="infoProp">연락처</span><span class="infoContent"></span>
					</p>
				</div>
				<div class="selectDiv">
					<div class="wish" onclick="wishSelect(1)"></div>
					<div class="kakaoBtn">
						<a><img src="./images/board_icon/kakao_ci_screen.png" /><img
							src="./images/board_icon/kakao_ci_screen_over.png" /></a>
					</div>
				</div>
			</div>
			<div class="listValue">
				<span class="id"></span>
				<div class="img">
					<img />
				</div>
				<div class="info">
					<p>
						<span class="infoTitle"></span>
					</p>
					<p>
						<span class="infoProp">주소</span><span class="infoContent"></span>
					</p>
					<p>
						<span class="infoProp">카테고리</span><span class="infoContent"></span>
					</p>
					<p>
						<span class="infoProp">연락처</span><span class="infoContent"></span>
					</p>
				</div>
				<div class="selectDiv">
					<div class="wish" onclick="wishSelect(2)"></div>
					<div class="kakaoBtn">
						<a><img src="./images/board_icon/kakao_ci_screen.png" /><img
							src="./images/board_icon/kakao_ci_screen_over.png" /></a>
					</div>
				</div>
			</div>
			<div class="listValue">
				<span class="id"></span>
				<div class="img">
					<img />
				</div>
				<div class="info">
					<p>
						<span class="infoTitle"></span>
					</p>
					<p>
						<span class="infoProp">주소</span><span class="infoContent"></span>
					</p>
					<p>
						<span class="infoProp">카테고리</span><span class="infoContent"></span>
					</p>
					<p>
						<span class="infoProp">연락처</span><span class="infoContent"></span>
					</p>
				</div>
				<div class="selectDiv">
					<div class="wish" onclick="wishSelect(3)"></div>
					<div class="kakaoBtn">
						<a><img src="./images/board_icon/kakao_ci_screen.png" /><img
							src="./images/board_icon/kakao_ci_screen_over.png" /></a>
					</div>
				</div>
			</div>

		</div>
		<div class="pagingDiv">
			<div class="pageGroup">
				<a><div class="prevBtn">
						<div class="prevBtnImg"></div>
					</div></a> <a><div class="numBtn">
						<span class="num"></span>
					</div></a> <a><div class="numBtn">
						<span class="num"></span>
					</div></a> <a><div class="numBtn">
						<span class="num"></span>
					</div></a> <a><div class="numBtn">
						<span class="num"></span>
					</div></a> <a><div class="numBtn">
						<span class="num"></span>
					</div></a> <a><div class="nextBtn">
						<div class="nextBtnImg"></div>
					</div></a>
			</div>
		</div>
	</div>
</article>
