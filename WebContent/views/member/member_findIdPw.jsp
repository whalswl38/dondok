<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
#name{
 border-radius:10px 10px 10px 10px;
         width: 200pt;
         height: 28pt;
         background-color: #f3f3f3;
         text-align: left;
}

#email{
 border-radius:10px 10px 10px 10px;
         width: 200pt;
         height: 28pt;
         background-color: #f3f3f3;
         text-align: left;
}

#id{
 border-radius:10px 10px 10px 10px;
         width: 200pt;
         height: 28pt;
         background-color: #f3f3f3;
         text-align: left;
}

#name2{
 border-radius:10px 10px 10px 10px;
         width: 200pt;
         height: 28pt;
         background-color: #f3f3f3;
         text-align: left;
}

#pass{
 		 border-radius:10px 10px 10px 10px;
         width: 200pt;
         height: 28pt;
         background-color: #f3f3f3;
         text-align: left;
}

#pass2{
  		 border-radius:10px 10px 10px 10px;
         width: 200pt;
         height: 28pt;
         background-color: #f3f3f3;
         text-align: left;
}

#a{    margin-left: 10px;
         background-color: #3383c4;
         border-radius: 10px 10px 10px 10px;   
         width:120pt;
         height:26pt; 
         color:white;"
      }
      
#b{
      margin-left: 10px;
         background-color: #e41334;
         border-radius: 10px 10px 10px 10px;   
         width:57pt;
         height:26pt; 
         color:white;"
   }
   
   .c{    margin-left: 10px;
         background-color: #3383c4;
         border-radius: 10px 10px 10px 10px;   
         width:120pt;
         height:26pt; 
         color:white;"
      }
      
      #d{
      	 margin-left: 10px;
         background-color: #e41334;
         border-radius: 10px 10px 10px 10px;   
         width:57pt;
         height:26pt; 
         color:white;"
   }
   
   .btn{
   		background-color: #3383c4;
		margin-left: 0px;
		border-radius: 10px 10px 10px 10px;
		width: 40pt; 
		height: 26pt; 
		color: white; 
   }
   
   .btn2{
  		background-color: #3383c4;
		margin-left: 0px;
		border-radius: 10px 10px 10px 10px;
		width: 40pt;
		height: 26pt; 
		color: white;
   }
   
   .email{
    	border-radius: 10px 10px 10px 10px;
		width: 150pt;
		height: 28pt;
		background-color: #f3f3f3;
		text-align: left;
   }
   
   .emailchk{
  		border-radius: 10px 10px 10px 10px;
		width: 150pt;
		height: 28pt;
		background-color: #f3f3f3;
		text-align: left;
   }
</style>
<script src="js/jquery-3.4.1.min.js"></script>
<script type="text/javascript">
function fnCancel(){
	window.location.href = "/dondok/page?page=login";
}

function fnSubmit(param){
	var obj = new Object();
	if(param == "findId"){
		//밸리데이션넣는곳
		
		obj = {
				url : "findIdPw",
				type : "post",
				data : {
					"param" : param,
					"name" : $("#name").val(),
					"email" : $("#email").val()
				},
				dataType : "json",
				success : function(data){
					if(data == null){
						return alert("해당 정보와 일치하는 아이디가 없습니다.");
					}else{
						alert("찾으시는 아이디는 : [" + data.m_id + "] 입니다.");
					}
				},
				error : function(err){
					alert("에러가 발생했습니다.\n브라우저 콘솔의 내용을 확인하세요.");
					console.log(err);
				}
					
			};
	}else if(param == "findPw"){
		//밸리데이션넣는곳
		
		
		obj = {
				url : "findIdPw",
				type : "post",
				data : {
					"param" : param,
					"id" : $("#id").val(),
					"name" : $("#name2").val(),
					"email" : $("#email2").val()
				},
				dataType : "json",
				success : function(data){
					if(data == null){
						return alert("해당 정보와 일치하는 회원정보가 없습니다.");
					}else{
						$("#resetPw").show();
						$("#findPw").hide();
						$("#resPw").show();
					}
				},
				error : function(err){
					alert("에러가 발생했습니다.\n브라우저 콘솔의 내용을 확인하세요.");
					console.log(err);
				}
					
			};
	}else if(param == "resetPw"){
		//밸리데이션넣는곳
		var regExpPass = /^[A-Za-z0-9+]{8,16}$/; //영문 숫자 8자~16자
		
		obj = {
				url : "findIdPw",
				type : "post",
				data : {
					"param" : param,
					"id" : $("#id").val(),
					"name" : $("#name2").val(),
					"email" : $("#email2").val(),
					"pass" : $("#pass").val()
				},
				dataType : "json",
				success : function(data){
					if ($("#pass, #pass2").val().trim().length < 1) {
						$("#pass, #pass2").val("");
						return alert("새로운 비밀번호를 입력해 주세요.");
					}

					if (!regExpPass.test($("#pass").val())) {
						$("#pass").val("");
						return alert("비밀번호 형식이 올바르지 않습니다.\n8~16글자수의 영문(대,소문자) 와 숫자 조합으로 만들 수 있습니다.");
					}

					if ($("#pass").val() != $("#pass2").val()) {
						return alert("비밀번호가 일치하지 않습니다.");
					}
					
					if(data == 1){
						alert("비밀번호 변경에 성공했습니다.");
						window.close();
					}else{
						return alert("비밀번호 변경에 실패했습니다.");
					}
				},
				error : function(err){
					alert("에러가 발생했습니다.\n브라우저 콘솔의 내용을 확인하세요.");
					console.log(err);
				}
					
			};
	}
	
	if(param == "findPw"){
		if ($("#emailRes").val() == "N") {
			return alert("이메일 인증을 해주시기 바랍니다.");
		}
	}
	
	$.ajax(obj);
}

var emailCode = "";
function fnEmailValidation() {
	if ($("#email2").val().trim().length < 1) {
		return;
	}

	$.ajax({
		url : "emailValidation",
		type : "post",
		dataType : "json",
		data : {
			"param" : "findPw",
			"email" : $("#email2").val()
		},
		success : function(data) {
			console.log(data);
			if (data.resultCode == "1002") {
				emailCode = data.emailValidationCode;
				alert("메일이 발송 되었습니다.\n메일에 포함된 인증코드를 입력 후 인증 버튼을 눌러주세요.");
			} else if (data.resultCode == "1001") {
				return alert("메일 발송중 에러가 발생했습니다.\n메일을 발송하지 못했습니다.");
			}else{
				return alert("이메일 정보가 없습니다.");
			}

		},
		error : function(err) {
			alert("에러가 발생했습니다.\n브라우저 콘솔의 내용을 확인하세요.");
			console.log(err);
		}
	});
}

function chkEmailCode() {
	if ($("#num").val() == emailCode) {
		alert("인증되었습니다.");
		$("#emailRes").val("Y");
		$("#resetPw").show();
		$("#findPw").hide();
		$("#resPw").show();
	} else {
		return alert("인증번호가 일치하지 않습니다.");
	}
}
</script>
</head>
<body>
<h1>회원 계정 찾기</h1>

<h2>아이디 찾기</h2>
<input id="name" type="text" class="form-control" name="name" placeholder="이름"/></br>
<input id="email" type="text" class="form-control" name="email" placeholder="email"/></br>
<button id="a" type="button"  onclick="fnSubmit('findId');">아이디 찾기</button>
<button id="b" type="button"  onclick="fnCancel();">취소</button></br>
    
<h2>비밀번호 찾기</h2>
<input id="id" type="text" class="form-control" name="userId" placeholder="아이디" required="" autofocus="" /></br>
<input id="name2" type="text" class="form-control" name="name" placeholder="이름" required="" autofocus="" /></br>
<input id="email2"  type="text" class="email" name="email" placeholder="email" required=""/>
<button type="button"  class="btn" onclick="fnEmailValidation();">전송</button></br>
<input id="num" type="text" class="emailchk" placeholder="인증번호 입력" required=""/>
<input id="emailRes" type="hidden" value="N" />
<button type="button" class="btn2" onclick="chkEmailCode();">인증</button></br>

<div id="resetPw" style="display:none;">
</br>
<input id="pass" type="password" class="form-control" name="pass" placeholder="패스워드 재설정"/></br>
<input id="pass2" type="password" class="form-control" placeholder="패스워드 확인"/></br>
</div>

<button id="findPw" class="c" type="button"  onclick="fnSubmit('findPw');">비밀번호 찾기</button>
<button id="resPw" class="c" type="button"  onclick="fnSubmit('resetPw');" style="display:none;">비밀번호 재설정</button>

<button id="d" type="button"  onclick="fnCancel();">취소</button></br>

</body>

</html>