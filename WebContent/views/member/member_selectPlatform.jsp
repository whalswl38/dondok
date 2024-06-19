<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- 구글 api 클라이언트 아이디 입력 부분 -->
<meta name="google-signin-client_id" content="566882666738-mj2b6q1ffar2lvqhqgkat4vh0kpcfarp.apps.googleusercontent.com">

<title>Insert title here</title>
<style type="text/css">
#dondok{
         border-radius:10px 10px 10px 10px;
         width: 200pt;
         height: 28pt;
         background-color: #f3f3f3;
		 text-align: center;
   }
   
</style>

<script src="js/jquery-3.4.1.min.js"></script>
<!-- 카카오 -->
<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>
<!-- 구글 -->
<script src="https://apis.google.com/js/platform.js" async defer></script>
<!-- 네이버 -->
<script type="text/javascript" src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.3.js" charset="utf-8"></script>

<script type="text/javascript">
	var googleClickFlag = false; //구글 로그인 되어있을때 클릭해야지 서브밋 되게 수정
	
	function submit(param, data){
		if(param == "kakao"){
			$("#nickname").val(data.nickname);
			$("#profile").val(data.profile);
			$("#email").val(data.email);
		}else if(param == "naver"){
			$("#nickname").val(data.nickname);
			$("#email").val(data.email);
		}else if(param == "google"){
			$("#name").val(data.name);
			$("#profile").val(data.profile);
			$("#email").val(data.email);
		}
		
		$("#platform").val(param);
		$("#selectPlatform").submit();
	}
	
	function BtnGoogleClick(){
		googleClickFlag = true;
	}
</script>
</head>

<body>

	<h1>회원가입</h1>
	<h2>STEP 1 - 플랫폼 선택</h2>
	
	<!-- <form id="loginForm" action="/memberLogin"> -->
	<form id="selectPlatform" action="joinProc" method="post">
		<input id="platform" type="hidden" name="platform">
		<input id="name" type="hidden" name="name">
		<input id="nickname" type="hidden" name="nickname">
		<input id="profile" type="hidden" name="profile">
		<input id="email" type="hidden" name="email">
	</form>
	<br />
	<input id="dondok" type="button" value="돈독 계정으로 회원가입" onclick="submit('dondok');"><br>
	<a id="kakao-login-btn"></a>
	<div id="naver_id_login"></div>
	<div class="g-signin2" data-onsuccess="onSignIn" data-theme="dark" onclick="BtnGoogleClick();"></div>
	 
	<script type='text/javascript'>
		//<![CDATA[
		// 사용할 앱의 JavaScript 키를 설정해 주세요.
		Kakao.init('174a95d64854b089ce7bcd1d5eb41015');
		// 카카오 로그인 버튼을 생성합니다.
		Kakao.Auth.createLoginButton({
			container: '#kakao-login-btn',
			success: function(authObj) {
				// 로그인 성공시, API를 호출합니다.
				Kakao.API.request({
				url: '/v2/user/me',
				success: function(res) {
					//로그인처리
					var returnVal = new Object();
					returnVal = {
						nickname : res.kakao_account.profile.nickname,
						profile : res.kakao_account.profile.profile_image_url,
						email : res.kakao_account.email
					};
					
					submit('kakao', returnVal);
				},
					fail: function(error) {
					console.log(error);
					}
				});
			},
			fail: function(err) {
				console.log(err);
			}
		});
		function kout(){
			Kakao.Auth.logout();
		}
		//]]>
		
		//네이버
		var clientId = "HfNsjkYXUYG3ExPNXmA9";
		var callbackUrl = "http://qclass.iptime.org:8686/dondok/views/member/naverLogin.jsp";
		var naver_id_login = new naver_id_login(clientId, callbackUrl);
		var state = naver_id_login.getUniqState();
		naver_id_login.setButton("white", 3, 40);
		naver_id_login.setDomain("http://qclass.iptime.org:8686");
		naver_id_login.setState(state);
		naver_id_login.setPopup();
		naver_id_login.init_naver_id_login();
		
		function response(email, nickname, age){
			var returnVal = new Object();
			returnVal = {
				nickname : nickname,
				email : email
			};
			
			submit('naver', returnVal);
		}
		
		//구글
		function onSignIn(googleUser) {
			var profile = googleUser.getBasicProfile();
			var returnVal = new Object();
			returnVal = {
				name : profile.getName(),
				profile : profile.getImageUrl(),
				email : profile.getEmail()
			};
			
			if(googleClickFlag){
				submit('google', returnVal);
			}
		}
		
	</script>
</body>

</html>