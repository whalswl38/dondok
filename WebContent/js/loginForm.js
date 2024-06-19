/**
 * 
 */

function closeBox(){
	
	$('#loginFormBox').remove();
	$('#loginBak').remove();
	
}

function submit(param, data){
	if(param == "kakao"){
		$("#email").val(data.kakao_account.email);
	} 
	//네이버,구글 else if 추가해서 넣기
	$("#platform").val(param);
	/* $("#loginForm").attr("action", "/memberLogin");
	$("#loginForm").submit(); */
	
	var obj = {
			
			url : "memberLogin2",
			type : "post",
			dataType : "json",
			/* data : {
				aaa : 123,
				bbb : "hi"
			}, */
			data : $("#loginForm").serialize(),
			success : function(data){
				alert(data.m_name+"님 반갑습니다.");
				console.log(data);
				$('#loginFormBox').hide();
				$('#loginBak').hide();
				
			},
			error : function(err){
				alert("에러가 발생했습니다.\n브라우저 콘솔의 내용을 확인하세요.");
				console.log(err);
			}
	};
	
	$.ajax(obj);
	
}
