<%@page import="trip.dto.TeamMemberDto"%>
<%@page import="java.util.List"%>
<%@page import="trip.dao.TeamMemberDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	response.setContentType("text/html; charset=UTF-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>


<script type="text/javascript" src="js/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="js/util.js"></script>
<style type="text/css">
@import
url("css/bodyPosition.css");
@import
url("css/invitation.form.css");
@import
url("css/loading.css");
</style>
<script type="text/javascript" src="js/haederLoad.js"></script>	

	
<script type="text/javascript">

	function invite() {
    $("#invite").click(function() {
  	  console.log("으악");
       $.ajax({
          url : "TeamMemberController?command=idCheck",
          type : "post",
          data : {
             id : $("#invitation_search").val()
          },
          dataType : "text",
          success : function(data) {
             if(data===$("#invitation_search").val()){
             $("#res").html("초대되었습니다.");
             $('#add_list').append('<div class="if_item_id">'+data+'<input type="button" value="×" onclick="delmem(\''+data+'\');"/></div>');
             }else if(data==='비회원'){
                $("#res").html("탈퇴 또는 존재하지 않는 아이디입니다.");
             }else if(data==='이미초대'){
                $("#res").html("이미 초대된 회원입니다.");
             }else if(data==='비입력'){
                $("#res").html("아이디를 입력하세요.");
             }
          },
          error : function() {
          }
       });
       
    	});
	   }
	
	function delmem(mem){
    $.ajax({
         url : "TeamMemberController?command=del_mem",
         async :false,
         type : "post",
         data : {
            id : mem
         },
         dataType : "text",
         success : function(data) {
            var jObj=JSON.parse(data);
            $('#res').html('삭제되었습니다.');
            $('#add_list').html('');
            $('#add_list').html(`
            	<div>
    				<input type="text" id="invitation_search">
    				<button id="invite" class="btn">+</button>
    				<span id="res"></span>
    			</div>
            		`);
            invite();
            for(var i=0;i<Object.keys(jObj).length;i++){
               var id='\''+Object.values(jObj)[i].id+'\'';
               $('#add_list').append('<div class="if_item_id">'+Object.values(jObj)[i].id+'<input type="button" value="×" onclick="delmem(\''+Object.values(jObj)[i].id+'\');"/></div>');
               }
         },
         error : function() {
            failBox("통신오류"); 
         }
      });}
	
	
   $(function() {
	  
	  invite();
	 
      $("#cancle").click(function() {
         location.href="TeamMemberController?command=deleteTeam";
      });
      $("#complate").click(function() {
         var schedule_name=$("#schedule_name").val();
         var acc_holder=$("#acc_holder").val();
         var acc_bank=$("#acc_bank").val();
         var acc_num=$("#acc_num").val();
         
         if(schedule_name.length<1){
        	 failBox('일정명을 입력해주세요.');
            $("#schedule_name").focus();
         }else if(acc_holder.length<1){
        	 failBox('예금주를 입력해주세요.');
            $("#acc_holder").focus();
         }else if(acc_bank.length<1){
        	 failBox('은행명을 입력해주세요.');
            $("#acc_bank").focus();
         }else if(acc_num.length<1){
        	 failBox('계좌번호를 입력해주세요.');
            $("#acc_num").focus();
         }else{
         $.ajax({
            
            
            url : "TeamMemberController?command=complete_0",
            type : "post",
            data : {
               schedule_name : schedule_name,
               acc_holder : acc_holder,
               acc_bank :acc_bank,
               acc_num : acc_num
            },
            dataType : "text",
            success : function(data) {
               location.href = "TeamMemberController?command=upStage&stage=1";
            },
            error : function() {
            	failBox("오류!!");
            },beforeSend : function(){
				loadingBox($('section'), {
					'background-color': '#ffffff80',
					'z-index': '999',
			    	'width': '100%',
					'height': '100%'
				});
			},complete : function(){
				$('#loadingBox').remove();
			}
         });
      }
      });
		
      toolTipBoxOn('인원 추가', $('button#invite'));
  	
  		$('div.loading').remove();
      
  	  $('#acc_num').keydown(function(e){
  		 
  		  if(e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)){
  			  failBox('오직 숫자만 입력 가능합니다.');
  			  $('#acc_num').attr('disabled','disabled');
  			  $('#failClose').on('click',function(){
  				$('#acc_num').removeAttr('disabled'); 
  			  });
  			  return false;
  		  }
  		  
  	  });
  		
   });
	
</script>
</head>
<body>

<!-- 로딩 -->
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
			<a href="#" onclick="return false;">일정 등록</a> <span></span>
			<span class="locationName">0 단계 : 일정 만들기</span>
		</div>
	</article>

	<div>

		<div class="if_header" id="trip_name">일정명</div>
		<div>
			<input type="text" id="schedule_name" placeholder="일정명 입력하세요!" />
		</div>
		
		<div class="if_header">인원</div>
		
		<div id="add_list">
			<div>
				<input type="text" id="invitation_search" />
				<button id="invite" class="btn">+</button>
				<span id="res"></span>
			</div>
		</div>
		
		<div  class="if_header" id="acc_registor">계좌 등록</div>
		<div class="if_footer">
			<span>예금주</span><input type="text" id="acc_holder"/><br/>
			<span>은행명</span><input type="text" id="acc_bank"/><br/>
			<span>계좌 번호</span><input type="text" id="acc_num"/><span></span><br/>
		</div>
		
		<div id="btn_group">
			<button id="complate">확인</button>
			<button id="cancle">취소</button>
		</div>

	</div>
	</section>
</body>
</html>