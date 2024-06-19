<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="google-signin-client_id" content="566882666738-mj2b6q1ffar2lvqhqgkat4vh0kpcfarp.apps.googleusercontent.com">
<title>Insert title here</title>
<style type="text/css">
  @import url('https://fonts.googleapis.com/css?family=Noto+Sans+KR&display=swap');
   @import url('https://fonts.googleapis.com/css?family=Do+Hyeon&display=swap');
      * {
         font-family: 'Noto Sans KR', sans-serif;
      }
  
      h1{ 
         font-family: 'Do Hyeon', sans-serif;
         margin: 50;
         text-align: center;
      }
      
      h2{ 
        font-family: 'Do Hyeon', sans-serif;
        margin: 100;
        margin-left: 35px;
      }
      
      #id{
      	 border-radius:10px 10px 10px 10px;
      	 margin-left: 35px;
         width: 200pt;
         height: 28pt;
         background-color: #f3f3f3;
		 text-align: left;
   	  }
  
      #pass{
         border-radius:10px 10px 10px 10px;
         margin-left: 35px;
         width: 200pt;
         height: 28pt;
         background-color: #f3f3f3;
         text-align: left;
      }
      
      #find{
      	 margin-left: 220px; 
      }
      
      .loginbtn{
      	 margin: 50;
      	 margin-left: 35px;
         background-color: #3383c4;
         border-radius: 10px 10px 10px 10px;   
         width:200pt;
         height:26pt; 
         color:white;"
      }
      
      .loginbtn2{
         margin: 50;
      	 margin-left: 35px;
         background-color: #e41334;
         border-radius: 10px 10px 10px 10px;   
         width:200pt;
         height:26pt; 
         color:white;"
      }
      
     #kakao-login-btn{
      	 margin: 50;
      	 margin-left: 20px;
        
      }
     
	#naver_id_login{
		 margin: 50;
      	 margin-left: 35px;
	  }

	.g-signin2{
		 margin: 50;
      	 margin-left: 35px;
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
	
	$(function(){
		$("#register").click(function(){
			location.href = "page?page=join";
			
		});
	});
	
	function submit(param, data){
		if(param != "dondok"){
			$("#email").val(data);
		}else{
			if($("#id").val().trim().length < 1 || $("#pass").val().trim().length < 1){
				return alert("아이디와 비밀번호값을 입력해주세요.");
			}
		}
		$("#platform").val(param);
			
		$.ajax({
			url : "memberLogin",
			type : "post",
			dataType : "json",
			//data : $("#loginForm").serialize(),
			data : {
				"myId" : $("#id").val(),
				"myPw" : $("#pass").val(),
				"email" : $("#email").val(),
				"platform" : $("#platform").val()
			},
			success : function(data){
				console.log(data);
				if(!data.resultCode){
					//아이디 정보가 없을때
					alert('로그인에 실패했습니다.');
					window.location.href="/dondok/page?page";
				}else{
					//아이디 정보가 일치해서 존재할때
					window.opener.fnLoginSuccess(data.user);
					window.close();
				}
			},
			error : function(err){
				alert("에러가 발생했습니다.\n브라우저 콘솔의 내용을 확인하세요.");
				console.log(err);
			}
		});
	}
	
	function BtnGoogleClick(){
		googleClickFlag = true;
	}
	
	function fnEnterLogin(){
		if(event.keyCode == 13)
	     {
			submit('dondok');
	     }
	}
</script>
</head>

<body>

	<h1>WELCOME</h1>
	<h2>로그인<!-- <form id="loginForm" action="/memberLogin"> --></h2>
	
	
	<form id="loginForm" method="post">
		<input id="id" type="text" name="myId" placeholder="ID"></br>
	 	<input id="pass" type="password" name="myPw" placeholder="PW" onKeyDown="fnEnterLogin();"></br>
		<input id="email" type="hidden" name="email"></br>
		<input id="platform" type="hidden" name="platform">
	</form>
	
	<a href="page?page=findIdPw" id="find">ID/PW찾기</a><br/>

	 <input class="loginbtn" type="button" value="로그인" onclick="submit('dondok');"><br/>
	 <input class="loginbtn2" type="button" value="회원가입" id="register"><br/> 


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
					submit('kakao', res.kakao_account.email);
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
			submit('naver', email);
		}
		
		//구글
		function onSignIn(googleUser) {
			var profile = googleUser.getBasicProfile();
			if(googleClickFlag){
				submit('google', profile.getEmail());
			}
		}
		
	</script>
</body>
</html>