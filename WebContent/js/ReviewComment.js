$(function(){
	
	
	var rv_crno = $('input[name=rv_crno]').val();
	var selectComment = $('input[name=selectComment]').val();
	var maxComment;
	
	function commentReLoad(rv_crno,selectComment,page){
		
		commentLoad(rv_crno, selectComment, page);
		
		var y1 = $("article.footer").height();
		var y2 = $("article#article_header").height();
		var y3 = ($("article.contents").height());
		
		$('html').animate({scrollTop : y3 }, 400);
	}
	
	
	var commentCountLoad = function(cr_no,selectComment) {
		
		$.ajax({
			url : "CommentCount",
			data : {
				"rv_crno" : cr_no,
				"selectComment": selectComment
			},
			method : "post",
			dataType : "text",
			success : (data)=>{
				console.log("[Count] " + data + typeof(data));
				maxComment = data;
				$(".commentBtn > p > span:nth-child(3)").eq(0).html(maxComment);
			}
		});
	}

	var commentLoad = function (rv_crno, selectComment) {
		
		var page = arguments[2] ? arguments[2] : 1;
		
		var chk = arguments[2];
		
		$.ajax({
			url : "CommentList",
			data : {
				"rv_crno" : rv_crno,
				"selectComment": selectComment,
				"page" : page,
			},
			method : "post",
			dataType : "html",
			success : function(data){
				$('div.commentList').remove();
				$('div.commentPage').before(data);
				if(chk){
					$('div.commentList').css("display","block");
					$('div.commentPage').css("display","block");
				}
				
				console.log(page);
				pageFun(page, rv_crno, selectComment);
				
				// 댓글 커맨드 생성
				
				commentBtnOn();
			}
		});
	}

	
	// 댓글 커맨드
	
	var commentDelete = function(){
		var selectComment = $('input[name=selectComment]').val();
		$.ajax({
			
			url : "CommentDelete",
			data : {
				"selectComment" : selectComment,
				"rv_crno" : rv_crno,
				"rv_no" : arguments[0]
			},
			success : function(data){
				var data = Number(data);
				
				maxComment = data;
						
				var btnCount= maxComment%8 == 0? Math.floor(maxComment/8) : Math.floor(maxComment/8+1);
						
				console.log("[InsertBtnCount] : "+btnCount);
				
				var nowPage = $('span.num').eq(2).html();
				
				commentReLoad(rv_crno,selectComment,nowPage);
				$(".commentBtn > p > span:nth-child(3)").eq(0).html(maxComment);

			}
		});
	};


	var commentModify = function(){
		var selectComment = $('input[name=selectComment]').val();
		$.ajax({
			
			url : "CommentModify",
			data : {
				"selectComment" : selectComment,
				"rv_content" : arguments[1],
				"rv_crno" : rv_crno,
				"rv_no" : arguments[0],
			},
			success : function(data){
				var data = Number(data);
				
				maxComment = data;
						
				var btnCount= maxComment%8 == 0? Math.floor(maxComment/8) : Math.floor(maxComment/8+1);
						
				console.log("[InsertBtnCount] : "+btnCount);
				
				var nowPage = $('span.num').eq(2).html();
				
				commentReLoad(rv_crno,selectComment,nowPage);
				$(".commentBtn > p > span:nth-child(3)").eq(0).html(maxComment);

			}
		});
	};


	var commentWrite = function(val){
		
		var commentValue = val;
		var rv_crno = $('input[name=rv_crno]').val();
		var selectComment = $('input[name=selectComment]').val();
		var al_id = $('span.id').html();
		var rv_pno = arguments[1];
		var commentIndex = arguments[2];
		
		console.log(rv_pno, commentValue, rv_crno, selectComment);
		if(val){
		$.ajax({
			
			url : "CommentWrite",
			data : {
				"rv_crno" : rv_crno,
				"rv_pno" : rv_pno,
				"selectComment" : selectComment,
				"rv_content" : commentValue,
				"al_id" : al_id
			},
			dataType : "text",
			method : "post",
			success : function(data){
				var data = Number(data);
				
				maxComment = data;
				var btnCount= maxComment%8 == 0? Math.floor(maxComment/8) : Math.floor(maxComment/8+1);
				
				if(!rv_pno){
					
					commentLoad(rv_crno,selectComment,btnCount);
					
					console.log("[InsertBtnCount] : "+btnCount);

					$(".commentWrite").focus();
					
					$(".commentBtn > p > span:nth-child(3)").eq(0).html(maxComment);
					
					
				} else {
					
					var nowPage = $('span.num').eq(2).html();
					
					if(commentIndex !=7){
						commentLoad(rv_crno,selectComment,nowPage);
						$('div.commentValue').eq(commentIndex).focus();
					} else {
						nowPage = Number(nowPage)+1;
						commentReLoad(rv_crno,selectComment,nowPage);
					}
					
					
					
					$(".commentBtn > p > span:nth-child(3)").eq(0).html(maxComment);
				
				}
			}
		});
		} else {
			failBox('내용을 입력해주세요.');
		}
	};
	
	
	
	var pageFun = function(page, rv_crno, selectComment){
		
		var page = Number(page);
		
		var btnCount= maxComment%8 == 0? Math.floor(maxComment/8) : Math.floor(maxComment/8+1);

		console.log("MaxCount : " + btnCount);
	
		var pageNumFun = function(index, page){
		
			console.log("check : " + page);
		
			var page = (page <= 0 || page > btnCount)? "" : page;

			console.log("numPage" + page);
		
			$('span.num').eq(index-1).html(page);
			$('div.pageGroup > a').eq(index).off("click");
		
			if(page<=0 || page > btnCount){
			
			
				$('div.pageGroup > a').eq(index).removeAttr("href");
				$('.pageGroup > a').eq(index).unbind('mouseenter mouseleave');
				$('.pageGroup > a > div').eq(index).removeAttr("style")
				$('span.num').eq(index-1).removeAttr("style");
			
			} else {
			
				if(!arguments[2]){
					$('div.pageGroup > a').eq(index).attr("href","#");
					
					// 코멘트 리로드 커맨드
					$('div.pageGroup > a').eq(index).on("click",function(){
						commentReLoad(rv_crno,selectComment,page);
					});
					
					$('.pageGroup > a').eq(index).hover(function(){
				
						$('.pageGroup > a > div').eq(index).css({
							"transition-duration": "0.3s",
							"background-color": "#FFFFFF"
						});
						$('span.num').eq(index-1).css({
							"transition-duration": "0.3s",
							"color": "#DB362C",
							"font-weight": "bold"
						});
				
					}, ()=>{
					
						$('.pageGroup > a > div').eq(index).removeAttr("style");
						$('span.num').eq(index-1).removeAttr("style");
				
					});
				} else {
					$('div.pageGroup > a').eq(index).on("click",function(){
						commentReLoad(rv_crno,selectComment,page);
					});
					$('.pageGroup > a > div').eq(index).css({
						"transition-duration": "0.3s",
						"background-color": "#FFFFFF"
					});
					$('span.num').eq(index-1).css({
						"transition-duration": "0.3s",
						"color": "#DB362C",
		    			"font-weight": "bold"
					});
				}
			}
		}

		var pageBtnFun = function(index, page, img){
		
			$('div.pageGroup > a').eq(index).off("click");
		
			if(page<=0 || page > btnCount) { 
				
				$('div.pageGroup > a').eq(index).removeAttr("href");
				$('.pageGroup > a').eq(index).unbind('mouseenter mouseleave');
				$('.pageGroup > a > div').eq(index).removeAttr("style")
				if(img === "prevBtnImg"){
					$('div.prevBtnImg').removeAttr("style");
				} else {
					$('div.nextBtnImg').removeAttr("style");
				}
				
			} else {
			
				$('div.pageGroup > a').eq(index).attr("href","#");
			
				// 코멘트 리로드 커맨드
				$('div.pageGroup > a').eq(index).on("click",function(){
					commentReLoad(rv_crno,selectComment,page);
				});
				
				$('.pageGroup > a').eq(index).hover(function(){
					$('.pageGroup > a > div').eq(index).css({
						"transition-duration": "0.3s",
						"background-color": "#FFFFFF"
					});
						if(img === "prevBtnImg"){
							$('div.prevBtnImg').css({
								"transition-duration": "0.3s",
								"margin-right": "11px",
								"border-top": "7px solid transparent",
								"border-bottom": "7px solid transparent",
								"border-left": "0px solid transparent",
								"border-right": "12px solid #DB362C"
							});
						} else {
							$('div.nextBtnImg').css({
								"transition-duration": "0.3s",
								"border-top": "7px solid transparent",
								"border-bottom": "7px solid transparent",
								"border-right": "0px solid transparent",
								"border-left": "12px solid #DB362C"
							});
						}
				},function(){
					
					$('.pageGroup > a > div').eq(index).removeAttr("style");
					
					if(img === "prevBtnImg"){
						
						$('div.prevBtnImg').removeAttr("style");
					} else {
						$('div.nextBtnImg').removeAttr("style");
					}
				});
			}
		}

		if(page!==1){
			pageBtnFun(0,page-3,'prevBtnImg');
			pageNumFun(1,page-2);
			pageNumFun(2,page-1);
			pageNumFun(3, page, "select");
			pageNumFun(4,page+1);
			pageNumFun(5,page+2);
			pageBtnFun(6,page+3,'nextBtnImg');
		
		} else {
			pageBtnFun(0, 0,'prevBtnImg');
			pageNumFun(1, 0);
			pageNumFun(2, 0);
			pageNumFun(3, 1, "select");
			pageNumFun(4, 2);
			pageNumFun(5, 3);
			pageBtnFun(6, 4,'nextBtnImg');
		}

	}
	
	
	function commentContextFun(textarea){
		textarea.on("keyup",function(){
			var value = $(this).val();
			var length = value.length;
			var countSpan = $(this).parent('div.commentTextArea').next('p.commentCount').children('span');
			countSpan.html(length);
			if(length > 1500){
				countSpan.css({
					"color" : "#DB362C",
					"font-weight" : "400"
				});
			} else {
				countSpan.css({
					"color" : "#707C7F",
					"font-weight" : "100"
				});
			}
		});
	}
	
	
	
	// commentBox 삭제
	
	function commentBoxRemove(){
		
		if($('div.commentModifyArea')){
			var rv_content = $('textarea.oldCommentContent').val();
			var content = `
				<p class="commentValMiddle">${rv_content}</p>
			`;
			$('div.commentModifyArea').parent().children('p.commentValHead').after(content);
			$('div.commentModifyArea').parent().children('p.commentValFoot').children('a.commentModify').html("수정");
			$('div.commentModifyArea').remove();
		}
		
		if($('div.commentPlus')){
			$('div.commentPlus').prev().children('p.commentValFoot').children('a.commentPlus').html("답글");
			$('div.commentPlus').remove();
		}
		
	}
	
	// comment버튼 작동
	
	function commentBtnOn(){
		
		$('a.commentPlus').on("click",function(){
			
			var rv_no = $(this).parent('p.commentValFoot').children('input[name=rv_no]').val();
			var commentIndex = $(this).parent('p.commentValFoot').children('input[name=commentIndex]').val();
			
			if($(this).html() === "답글"){
			
				commentBoxRemove();
				
				var sessionUser = $('input[name=user]').val();
				
				var commentPlusDiv = `
				<div class="commentWrite commentPlus" style="padding-top:10px; padding-bottom:10px;">
					<div>
						<div class="commentWriteBac">
							<p class="commentMyId">${sessionUser}</p>
							<div class="commentTextArea commentPlusArea">
								<textarea class="commentContext commentPlusContext"></textarea>
								<button class="commentSummit commentPlusSummit">등록</button>
							</div>
							<p class="commentCount"><span>0</span>/1500</p>
						</div>
					</div>
				</div>
				`;
				
				$(this).parent().parent().after(commentPlusDiv);
				
				// count 기능 탑재
				commentContextFun($('textarea.commentPlusContext'));
				
				// 글작성 기능
				$('button.commentPlusSummit').on("click",function(){
					var val = $(this).prev().val();
					$(this).prev().val('');
					var rv_pno = Number(rv_no);
					console.log(rv_pno);
					commentWrite(val,rv_pno,commentIndex);
					
				});
				
				$(this).html("취소");
				
			} else {
				commentBoxRemove();
				$(this).html("답글");
			}
		});
		
		$('a.commentModify').on("click",function(){
			
			var rv_no = $(this).parent('p.commentValFoot').children('input[name=rv_no]').val();
			
			if($(this).html() === "수정"){
				
				commentBoxRemove();
				
				var commentValMiddle = $(this).parent().parent().children('p.commentValMiddle');
				var rv_content = commentValMiddle.html();
				
				var textarea = `
				<div class="commentTextArea commentModifyArea">
					<textarea style="display:none;" class="oldCommentContent">${rv_content}</textarea>
					<textarea class="commentContext commentModifyContent">${rv_content}</textarea>
					<button class="commentSummit commentModifySummit">등록</button>
				</div>
				`;
			
				$(this).parent().parent().children('p.commentValMiddle').remove();
				$(this).parent().parent().children('p.commentValHead').after(textarea);
				$(this).html("취소");
				
				var button = $('button.commentModifySummit');
				
				button.on("click",function(){
					var modify_rv_content = $('textarea.commentModifyContent').val();
					commentModify(rv_no,modify_rv_content);
				});
				
			} else {
				var rv_content = $('textarea.oldCommentContent').val();
				var content = `
					<p class="commentValMiddle">${rv_content}</p>
				`;
				$('div.commentModifyArea').remove();
				$(this).parent().parent().children('p.commentValHead').after(content);
				$(this).html("수정");
			}
		
		});
		
		$('a.commentDelete').on("click",function(){
			var rv_no = $(this).parent('p.commentValFoot').children('input[name=rv_no]').val();
			commentDelete(rv_no);
			
		});
	}
	
	
	
	
	// 페이지 첫 렌더링 시 부여
	commentContextFun($('textarea.commentContext'));
	
	// commentWrite
	
	$('button.commentSummit:nth-last-child(1)').on("click",function(){
		if($('p.commentMyId').html()){
			var val = $(this).prev().val();
			commentWrite(val);
			$(this).prev().val("");
		}
	});
	
	// 페이지 처음 로딩
	
	commentCountLoad(rv_crno,selectComment);
	
	commentLoad(rv_crno, selectComment);
	
	console.log("[maxValue]" + maxComment);
	
	// 스크롤링
	
	$("div.commentBtn").on("click",function(){
		if(maxComment != 0){
			$("div.commentList").slideToggle();
			$("div.commentPage").slideToggle();
			var btn = $(".commentBtn > p > span:nth-last-child(1)").css("border-bottom");
			var px = btn.split(" ")[0];
			if(px === "12px"){
				$(".commentBtn > p > span:nth-last-child(1)").css({
					"transition-duration": "0.3s",
		    		"border-bottom": "0px solid #bbb",
		    		"border-top": "12px solid #bbb"
				});
			} else {
				$(".commentBtn > p > span:nth-last-child(1)").css({
					"transition-duration": "0.3s",
		    		"border-bottom": "12px solid #bbb",
		    		"border-top": "0px solid #bbb"
				});
			}
		}
	});
	
});
	
	


