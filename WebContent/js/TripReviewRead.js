/**
 * http://usejsdoc.org/
 */
$(function(){
	
	// 맴버 체크
	var teamMemberChk = function(){
		
		var teamID = $('span.id').attr('data-t_id');
		var userChk = $('input[name=user]').val();
		var check = false;
		
		$.ajax({
			url : "TeamMember",
			data : {
				"tm_tid" : teamID
			},
			dataType:'json',
			async : false,
			success : function(data){
				var users = data['user'];
				users.forEach(function(val){
					if(val.tm_uid === userChk){
						check = true;
						console.log("check");
					}
				});
			}
		});
		return check;
		
	};
	
	
	var memberCheck = teamMemberChk();  // 맴버 체크
	
	console.log(memberCheck);
	
	function fnMove(select){
	        var offset = $(select).offset();
	        $('html, body').animate({scrollTop : offset.top}, 400);
	}
	
	var mapLoad = function(){
		
		var routeX = $('#map').attr("data-routeX");
		var routeY = $('#map').attr("data-routeY");
		var place_name = $('#map').attr("data-place_name");
		var category_code = $('#map').attr("data-category");
		
		console.log("[data-routeX] " + routeX);
		console.log("[data-routeY] " + routeY);
		console.log("[data-place_name] " + place_name);
		
		var routeXArr = routeX.split(",");
		var routeYArr = routeY.split(",");
		var place_nameArr = place_name.split(",");
		var category_codeArr = category_code.split(",");
		
		var routeMiddle = Math.floor(routeXArr.length/2); 
		
		var markerTrackerArray = new Array(); // 마커 추적 배열
		
		
		var tourMarker = {
				src : './images/board_icon/tour_icon.svg',
				size : new kakao.maps.Size(44, 49),
				option : {offset: new kakao.maps.Point(23, 52)}
		}
		
		var roomsMarker = {
				src : './images/board_icon/rooms_icon.svg',
				size : new kakao.maps.Size(44, 49),
				option : {offset: new kakao.maps.Point(23, 52)}
		}	      
		
		var resMarker = {
				src : './images/board_icon/res_icon.svg',
				size : new kakao.maps.Size(44, 49),
				option : {offset: new kakao.maps.Point(23, 53)}
		}
		
		
		
		
	
		
		
		// 지도 생성
		var container = document.getElementById('map');
		var options = {
			center: new kakao.maps.LatLng(routeYArr[routeMiddle],routeXArr[routeMiddle]),
			level: 6
		};

		var map = new kakao.maps.Map(container, options);

		var kakaoRoute = new Array();
		
		var kakaoMarker = new Array();
		
		routeXArr.forEach(function(val, index){
			
			console.log(val);
			console.log(routeYArr[index]);
			
			kakaoRoute.push(new kakao.maps.LatLng(routeYArr[index],val));
			
			var markerImage;
			
			if(category_codeArr[index] === 'FD6'){ // 맛집
				markerImage = resMarker;
			} else if (category_codeArr[index] === 'AT4') { // 명소
				markerImage = tourMarker;
			} else if (category_codeArr[index] === 'AD5') { // 숙소
				markerImage = roomsMarker;
			}
			
			var count = Number(index)+1;
			
			kakaoMarker.push({
				content : `<div class='mapInfo' style='
							position: absolute;
							top: -44px;
							left: -22px;
							' onmouseover="markerInfoOver('${place_nameArr[index]}',this);" onmouseout="markerInfoOut();">
								<div style="background-image:url('${markerImage.src}');
								background-size: 100% 100%;
								position: relative;
								width: 44px;
								height: 44px;
								">
									<span style='
									display: inline-block;
									text-align: center;
									width: 100%;
									padding: 5px 0px 5px 0px;
									color: #400909;
									'>${count}</span>
								</div>
							</div>
							`,
				latlng: new kakao.maps.LatLng(routeYArr[index],val)
			});
		});
		
		
		
		var polyline = new kakao.maps.Polyline({
		    path: kakaoRoute, // 선을 구성하는 좌표배열 입니다
		    strokeWeight: 3, // 선의 두께 입니다
		    strokeColor: '#db4040', // 선의 색깔입니다
		    strokeOpacity: 1, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
		    strokeStyle: 'solid' // 선의 스타일입니다
		});
		
		polyline.setMap(map);
		
		
		
		
		
		routeXArr.forEach(function(val, index){
			
			var circle = new kakao.maps.Circle({
				center : new kakao.maps.LatLng(routeYArr[index],val),
		    	radius: 25, // 미터 단위의 원의 반지름입니다
		    	strokeWeight: 5, // 선의 두께입니다
		    	strokeColor: '#db4040', // 선의 색깔입니다
		    	strokeOpacity: 1, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
		    	strokeStyle: 'solid', // 선의 스타일 입니다
		    	fillColor: '#fff', // 채우기 색깔입니다
		    	fillOpacity: 1  // 채우기 불투명도 입니다
			});

			
			var customOverlay = new kakao.maps.CustomOverlay({
				map : map,
				position: kakaoMarker[index].latlng,
			    content : kakaoMarker[index].content,
			    yAnchor : 1
			});
			
			circle.setMap(map);
			customOverlay.setMap(map);
			
			
			// map event
			
			
		});
		
		
	};
	
	
	
	// 자신의 리뷰 조회
	var selectMyReviewList = function(routeid){
		$.ajax({
			
			url : "SelectMyReviewList",
			data : {
				'cr_placeid' : routeid
			},
			dataType : 'json',
			async : false,
			success : function(json){
				var cList = json.cList;
				$('div[data-placeid='+routeid+']').children('p').remove();
				if(cList.length > 0){
					cList.forEach(function(val){
						
						var pTag = `<p data-cr_no="${val.cr_no}"><a style="color:#404040;"href="CategoryReviewRead?cr_no=${val.cr_no}">${val.cr_title}</a><img src="./images/board_icon/comment_icon.png"/><span>${val.cr_commentCount}</span> <button >추가</button></p>`;
						
						$('div[data-placeid='+routeid+']').append(pTag);
						
					});
				} else {
					var pTag = `<p>작성한 리뷰가 없습니다.</p>`;
					$('div[data-placeid='+routeid+']').append(pTag);
				}
			},beforeSend : function(){
				loadingBox($('body'), {
					'background-color': '#ffffff80',
					'z-index': '5',
			    	'width': '100%',
					'height': '100%'
				});
			},complete : function(){
				$('#loadingBox').remove();
			}
		});
	};
	
	function memoContextFun(textarea){
		textarea.on("keyup",function(){
			var value = $(this).val();
			var length = value.length;
			var countSpan = $(this).parent('div.memoTextArea').next('p.memoCount').children('span');
			countSpan.html(length);
			if(length > 1500){
				
				$(this).parent().children('button.memoSummit').prop("disabled", true);
				countSpan.css({
					"color" : "#DB362C",
					"font-weight" : "400"
				});
			} else {
				$(this).parent().children('button.memoSummit').prop("disabled", false);
				countSpan.css({
					"color" : "#707C7F",
					"font-weight" : "100"
				});
			}
		});
	}
	
	
	var RouteIDs; // 장소 식별 코드
	var RouteXs;
	var RouteYs;
	var RoutePlace_name;
	var RouteCategory;
	
	// 장소 정보 함수
	var routeInfoLoad = function(routeid){
		$.ajax({
			
			url : "RouteInfo",
			method : "get",
			data : {
				"routeid" : routeid
			},
			dataType : "json",
			async : false,
			success : function(data){
				console.log(data);
				console.log("[XY] " + data.x + "|" + data.y);
				RouteXs.push(data.x);
				RouteYs.push(data.y);
				RoutePlace_name.push(data.place_name);
				RouteCategory.push(data.category_group_code);
				$('#map').attr('data-routeX',RouteXs.toString());
				$('#map').attr('data-routeY',RouteYs.toString());
				$('#map').attr('data-place_name',RoutePlace_name.toString());
				$('#map').attr('data-category',RouteCategory.toString());
				
				$.ajax({
					
					url : "kakaoPlaceMainImgSearch",
					data : {
						"place_name" : data.place_name
					},
					async : false,
					success : function(path){
						// 추가시킬 장소 상세 정보 보기
						
						var markerPath;
						
						if(data.category_group_name === '음식점'){
							markerPath = "background-image: url(./images/board_icon/res_icon.svg);";
							markerPoint = "background-color : #be904d;";
						} else if(data.category_group_name === '관광명소'){
							markerPath = "background-image: url(./images/board_icon/tour_icon.svg);";
							markerPoint = "background-color : #b24747;";
						} else {
							markerPath = "background-image: url(./images/board_icon/rooms_icon.svg);";
							markerPoint = "background-color : #4AB648;";
						}
						
						var placeVar = `	
						<div class="dayPlaceInfo">
							<div class="dayLeft">
								<div class="markerImg" style="${markerPath}"><span></span></div>
								<div style="${markerPoint}"></div>
							</div>
							<div class="line"></div>
							<div class="dayInfoBar">
								<div class="dayInfoBox">
									<div class="dayInfoHeader">
										<a>
											<span><span style="font-weight:400;font-size:10pt;color:#bbb;padding-right: 10px;">${data.category_group_name}</span>${data.place_name}</span>
											<span></span>
										</a>
									</div>
									<div class="dayInfoContents">
										<div>
											<div class="placeInfoImg"><img style="width:100%; height:100%;" src="${path}"/></div>
											<div>
												<p><span class="placeCate">주소</span><span>${data.address_name} ( ${data.road_address_name} )</span></p>
												<p><span class="placeCate">분류</span><span>${data.category_name}</span></p>
												<p><span class="placeCate">전화번호</span><span>${data.phone}</span></p>
											</div>
										</div>
									</div>
								</div>
								<div><img src="./images/board_icon/trangle.svg"/></div>
							</div>
						</div>`;
						$('div.contents_body').append(placeVar);
						if(memberCheck) {
						var conPlus = `
						<div class="conPlus">
							<div class="dayLeft">
								<div></div>
							</div>
							<div class="line"></div>
							<div class="dayInfoBar" style="display:none;">
								<div class="dayInfoBox">
									<div class="dayInfoHeader">
										<ul>
											<li style="border-radius:8px 0px 0px 8px;"><a>리뷰 추가</a></li>
											<li><a>메모 추가</a></li>
										</ul>
									</div>
									<div class="dayInfoContents">
										<div class="reviewPlus" style="padding:0; display:none;">
											<div>
												<p>내가 작성한 <span>${data.place_name}</span> 리뷰 글</p>
											</div>
											<div>
												<div class="reviewList" data-placeid="${routeid}"></div>
												<div class="reviewFoot">
													<div class="reviewPlusPage"></div>
												</div>
											</div>
										</div>
										<div class="memoPlus" style = "padding:0;display:none;padding: 0;height: auto;min-height: 125px;">
											<div class="memoWriteBac">
												<div class="memoTextArea">
													<textarea class="memoContext"></textarea>
													<button class="memoSummit">등록</button>
												</div>
												<p class="memoCount"><span>0</span>/1500</p>
											</div>
										</div>
									</div>
								</div>
								<div><img src="./images/board_icon/trangle.svg"/></div>
							</div>
						</div>
						`;
						$('div.contents_body').append(conPlus);
						
						selectMyReviewList(routeid);
						}
						
					},beforeSend : function(){
						loadingBox($('body'), {
							'background-color': '#ffffff80',
							'z-index': '5',
					    	'width': '100%',
							'height': '100%'
						});
					},complete : function(){
						$('#loadingBox').remove();
					}
					
				});
				
			}
			
		});
		
	}
	
	
	// 리뷰 콘텐츠 함수
	
	var tripReviewContentLoad = function(tvc_day, tvc_tvno, tvc_routeid){
		console.log(tvc_day, tvc_tvno, tvc_routeid);
		$.ajax({
			
			url : "TripReviewContent",
			method : "post",
			data : {
				"tvc_day" : tvc_day,
				"tvc_tvno" : tvc_tvno,
				"tvc_routeid" : tvc_routeid
			},
			dataType : "html",
			async: false,
			success : function(data){
				
				var num = Number(tvc_routeid)-1
				
				$('div.dayPlaceInfo').eq(num).after(data);
				
				// 왼쪽 점 찍기
				
				var pointColor = $('div.dayPlaceInfo').eq(num).children('div.dayLeft').children('div:nth-last-child(1)').css('background-color');
				$('div.dayPlaceInfo:eq('+num+')+div').children('div.dayRead').children('div.dayLeft').children('div.markerImg').remove();
				$('div.dayPlaceInfo:eq('+num+')+div').children('div.dayRead').children('div.dayLeft').children('div').css({
					'background-color': '#fff',
					'top': '14px',
					'left' : '12px',
			    	'border': '4px solid ' + pointColor
				});
				
				// contents 이벤트 컨트롤
				var placeid = $('div.reviewList').eq(num).attr('data-placeid');
				var allCon = $('div.dayContentList').eq(num);
				var conArray = $(allCon).children('div.dayRead');
				for(var i = 0 ; i < conArray.length ; i ++){
					var aTag = conArray.eq(i).children('div.dayInfoBar').children('div.dayInfoBox').children('div.dayInfoHeader').children('a');
					var aDayDelete = conArray.eq(i).children('div.dayInfoBar').children('div.dayInfoBox').children('div.dayInfoContents').children('div').children('p').children('a');
					console.log(aDayDelete);
					var tvc_no = conArray.eq(i).attr('data-tvc_no');
					
					// 삭제 이벤트
					if(memberCheck){
					aDayDelete.on('click',function(){
						$.ajax({
							url : 'ContentsDelete',
							data : {
								'tvc_no' : tvc_no
							},
							success : function(data){
								if(data > 0){
									$('div.dayContentList').eq(num).remove();
									tripReviewContentLoad(tvc_day, tvc_tvno, tvc_routeid);
									selectMyReviewList(placeid);
									
									if(memberCheck){
										var button = $('div[data-placeid='+id+'] button');
										for(var i = 0 ; i < button.length ; i++){
											
											// 리뷰 추가
											button.eq(i).off('click');
											button.eq(i).click(function(){
												var cr_no = $('div[data-placeid='+id+'] p').attr('data-cr_no');
												$.ajax({
													url : "ContentsInsert",
													data : {
														"tvc_reviewid" : cr_no,
														"tvc_tvno" : tvc_tvno,
														"tvc_day" : tvc_day,
														"tvc_routeid" : tvc_routeid
													},
													dataType : "text",
													success : function(data){
														if(data > 0){
															var index = Number(num)-1
															$('div.dayContentList').eq(index).remove();
															tripReviewContentLoad(rs_accdate, tv_no, num);
															selectMyReviewList(id);
														}
													}
												});
											});
											
										}
									
									}
								}
							}
						})
					});
					}
					
					aTag.on("click",function(){
						$(this).parent().parent().children('div.dayInfoContents').slideToggle(400);
					});
				}
				
			},beforeSend : function(){
				loadingBox($('body'), {
					'background-color': '#ffffff80',
					'z-index': '5',
			    	'width': '100%',
					'height': '100%'
				});
			},complete : function(){
				$('#loadingBox').remove();
			}
		});
		
	}
	
	
	
	// 일자 함수
	var routeListLoad = function(){
		var rs_accdate = arguments[0]? arguments[0] : 1; // 일차
		var rs_tno = $("input[name=tv_teamid").val(); // 팀 ID
		var tv_no = $("input[name=rv_crno]").val();
		console.log(rs_accdate);
		$.ajax({
			
			url : "RouteSelect",
			mehtod : "post",
			data : {
				
				"rs_accdate" : rs_accdate,
				"rs_tno" : rs_tno
				
			},
			dataType:"json",
			success : function(data){
				RouteIDs = data.RouteSelectDto.rs_route.split('|'); // 장소 식별 코드
				console.log("[RS_ACCDATE]" + rs_accdate);
				
				RouteXs = new Array(); // x 좌표 배열 초기화
				RouteYs = new Array(); // y 좌표 배열 초기화
				RoutePlace_name = new Array(); // 장소 이름 배열 초기화
				RouteCategory = new Array();
				
				RouteIDs.forEach(function(id,index){
					var num = Number(index) + 1;
					
					console.log("[ROUTE_ID]" + id + "[INDEX 순서] " + num);
					
					routeInfoLoad(id);
					
					$('div.dayPlaceInfo div.markerImg > span').eq(index).html(num); // 순서
																					// 찍기
					
					
					// 장소 상세 보기 이벤트 컨트롤
					$('div.dayPlaceInfo div.dayInfoHeader > a').eq(index).click(function(){
						$('div.dayPlaceInfo div.dayInfoContents').eq(index).slideToggle(400);
					});
					
					
					// 리뷰 추가 이벤트 컨트롤
					if(memberCheck){
					var button = $('div[data-placeid='+id+'] button');
					for(var i = 0 ; i < button.length ; i++){
						
						// 리뷰 추가
						button.eq(i).click(function(){
							var cr_no = $('div[data-placeid='+id+'] p').attr('data-cr_no');
							$.ajax({
								url : "ContentsInsert",
								data : {
									"tvc_reviewid" : cr_no,
									"tvc_tvno" : tv_no,
									"tvc_day" : rs_accdate,
									"tvc_routeid" : num
								},
								dataType : "text",
								success : function(data){
									if(data > 0){
										var index = Number(num)-1
										$('div.dayContentList').eq(index).remove();
										tripReviewContentLoad(rs_accdate, tv_no, num);
										selectMyReviewList(id);
									}
								}
							});
						});
						
					}
					
					// 메모 추가 이벤트
					var memoWriteBtn = $('button.memoSummit:eq('+index+')');
					console.log(memoWriteBtn);
					memoWriteBtn.on('click',function(){
						var val = $(this).parent().children('textarea.memoContext').val();
						$(this).parent().children('textarea.memoContext').val('');
						
						$.ajax({
							url : "ContentsInsert",
							data : {
								"tvc_tvno" : tv_no,
								"tvc_day" : rs_accdate,
								"tvc_routeid" : num,
								"tvc_contents" : val
							},
							method : "post",
							dataType : "text",
							success : function(data){
								if(data > 0){
									var index = Number(num)-1
									$('div.dayContentList').eq(index).remove();
									tripReviewContentLoad(rs_accdate, tv_no, num);
								}
							}
						});
					});
					
					}
					
					tripReviewContentLoad(rs_accdate, tv_no, num);	// 일차 장소별 내용
																	// Load
					
				});
				
				// conPlus 마우스 이벤트
				if(memberCheck){
				var conPlus = $('div.conPlus div.dayInfoHeader ul li a');
				console.log(conPlus);
				for(var i = 0 ; i < conPlus.length ; i++){
					conPlus.eq(i).hover(function(){
						$(this).parent().css({
							'transition-duration': '0.3s',
							'background-color':'#57BEEC',
							'color':'#fff'
						});
					},function(){
						$(this).parent().css({
							'transition-duration': '0.3s',
							'background-color':'#fff',
							'color':'#454c4f'
						});
					});
					
					if(conPlus[i].text === '리뷰 추가'){
						conPlus.eq(i).on("click",function(){
							var memoPlus = $(this).parent().parent().parent().parent().children('div.dayInfoContents').children('div.memoPlus');
							var reviewPlus = $(this).parent().parent().parent().parent().children('div.dayInfoContents').children('div.reviewPlus');
							var con =  $(this).parent().parent().parent().parent().children('div.dayInfoContents');
							if(con.css('display') === 'none'){
								reviewPlus.css('display','block');
								con.slideToggle(400);
							} else {
								if(memoPlus.css('display') === 'block'){
									con.slideToggle(400,function(){
										memoPlus.css('display','none');
										reviewPlus.css('display','block');
									});
									con.slideToggle(400);
								} else {
									con.slideToggle(400,function(){
										reviewPlus.css('display','none');	
									});
									
								}
							}
							
						});
					} else {
						conPlus.eq(i).on("click",function(){
							var memoPlus = $(this).parent().parent().parent().parent().children('div.dayInfoContents').children('div.memoPlus');
							var reviewPlus = $(this).parent().parent().parent().parent().children('div.dayInfoContents').children('div.reviewPlus');
							var con =  $(this).parent().parent().parent().parent().children('div.dayInfoContents');
							if(con.css('display') === 'none'){
								memoPlus.css('display','block');
								con.slideToggle(400);
							} else {
								if(reviewPlus.css('display') === 'block'){
									con.slideToggle(400,function(){
										reviewPlus.css('display','none');
										memoPlus.css('display','block');
									});
									con.slideToggle(400);
								} else {
									con.slideToggle(400,function(){
										memoPlus.css('display','none');
									});
								}
							}
						});
					}
					
					
					// 추가 버튼 on/off 버튼
					
					var plusBtn = $('div.conPlus').eq(i).children('div.dayLeft').children('div')
					
					plusBtn.on('click',function(){
						$(this).parent().parent().children('div.dayInfoBar').toggle(200);
					});
					
					
					memoContextFun($('textarea.memoContext').eq(i));
				}
				}
				
				mapLoad(); // 맵 로드
				
			},
			beforeSend : function(){
				var loading = `<div id="loadingBox"><div class="sk-fading-circle">
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
</div></div>`;
				$('article.contents').prepend(loading);
			}, complete : function(){
				$('article.contents').children('div#loadingBox').remove();
			}
		});
	}
	
	
	// dayPage 이벤트
	
	var dayPageClickFun = function(){
		var days = $('div.dayPage').attr('data-days');
		var pageBtn = $('div.dayPage').children('div').children('a');
		var nextBtn = $('div.dayPage').children('a').eq(1);
		var prevBtn = $('div.dayPage').children('a').eq(0);
		var startNum = arguments[0]? arguments[0] : 1;
		var nowDay = arguments[1]? arguments[1] : startNum; 
		console.log(startNum);
		$('div.dayPage').attr('data-nowDay',nowDay);
		
		
		pageBtn.css({
			'transition-duration': '0.3s',
			'color' : '#322'
		});
		
		pageBtn.parent().css({
			'background-color' : '#fff',
			'border' : '1px solid #ddd'
		});
		
		
		for(var i = startNum ; i <= startNum+5 ; i++){
			var index = i-startNum;
			if(i <= days){
				pageBtn.eq(index).html(i);
				pageBtn.eq(index).unbind('mouseenter mouseleave');
				pageBtn.eq(index).off('click');
				
					pageBtn.eq(index).on('click',function(){
						$('div.dayPage').attr('data-nowDay',$(this).html());
						$('#map').remove();
						var mapTag = `<div id="map" style="position:relative;width:100%;height:100%;"></div>`;
						$('div.locationMap').eq(0).append(mapTag);
						$('div.contents_body').children().remove();
						var num = Number($(this).html());
						routeListLoad(num);
						dayPageClickFun(startNum,num);
					
						
					});
				if(i != $('div.dayPage').attr('data-nowDay')){
					pageBtn.eq(index).css({
						'transition-duration': '0.3s',
						'color' : '#322'
					});
					
					pageBtn.eq(index).parent().css({
						'background-color' : '#fff',
						'border' : '1px solid #ddd'
					});
					
					pageBtn.eq(index).hover(function(){
						$(this).css({
							'transition-duration': '0.3s',
							'color' : '#fff'
						});
						$(this).parent().css({
							'transition-duration': '0.3s',
							'border' : '1px solid #57BEEC',
							'background-color': '#57BEEC'
						});
					}, function(){
						$(this).css({
							'transition-duration': '0.3s',
							'color' : '#322'
						});
						$(this).parent().css({
							'transition-duration': '0.3s',
							'border' : '1px solid #ddd',
							'background-color': '#fff'
						});
					});
					
				} else {
					
					pageBtn.eq(index).unbind('mouseenter mouseleave');
					pageBtn.eq(index).off('click');
					
					pageBtn.eq(index).css({
						'color' : '#fff'
					});
					
					pageBtn.eq(index).parent().css({
						'border' : '1px solid #57BEEC',
						'background-color': '#57BEEC'
					});
					
				}
			} else {
				
				pageBtn.eq(index).html("");
				pageBtn.eq(index).unbind('mouseenter mouseleave');
				pageBtn.eq(index).off('click');
				pageBtn.eq(index).parent().css({
					'background-color' : '#eee'
				});
			
			}
		}
		
		if(startNum*5 < days){
			nextBtn.unbind('mouseenter mouseleave');
			nextBtn.off('click');
			nextBtn.on('click',function(){
			
				dayPageClickFun(startNum+5,nowDay);
			
			});
			
			pageBtn.eq(index).css({
				'transition-duration': '0.3s',
				'color' : '#322'
			});
			
			pageBtn.eq(index).parent().css({
				'background-color' : '#fff',
				'border' : '1px solid #ddd'
			});
			
			nextBtn.css({
				'border' : '1px solid #ddd',
		    	'background-color': '#fff'
			});
			
			nextBtn.children().css({
				'transition-duration': '0.3s',
				'border-left': '18px solid #bbb'
			});
			
			nextBtn.hover(function(){
				$(this).css({
					'transition-duration': '0.3s',
					'border' : '1px solid #57BEEC',
			    	'background-color': '#57BEEC'
				});
				$(this).children().css({
					'transition-duration': '0.3s',
					'border-left': '18px solid #fff'
				});
			}, function(){
				$(this).css({
					'transition-duration': '0.3s',
					'border' : '1px solid #ddd',
			    	'background-color': '#fff'
				});
				$(this).children().css({
					'transition-duration': '0.3s',
					'border-left': '18px solid #bbb'
				});
			});
			
		} else {
			nextBtn.children().css({
				'transition-duration': '0.3s',
				'border-left': '18px solid #bbb'
			});
			nextBtn.css({
				'border' : '1px solid #ddd',
		    	'background-color': '#eee'
			});
			nextBtn.unbind('mouseenter mouseleave');
			nextBtn.off('click');
		}
		if(startNum-5 > 0){
			
			prevBtn.unbind('mouseenter mouseleave');
			prevBtn.off('click');
			prevBtn.on('click',function(){
			
				dayPageClickFun(startNum-5,nowDay);
			
			});
			
			prevBtn.css({
				'border' : '1px solid #ddd',
		    	'background-color': '#fff'
			});
			
			prevBtn.children().css({
				'transition-duration': '0.3s',
				'border-right': '18px solid #bbb'
			});
			
			prevBtn.hover(function(){
				$(this).css({
					'transition-duration': '0.3s',
					'border' : '1px solid #57BEEC',
			    	'background-color': '#57BEEC'
				});
				$(this).children().css({
					'transition-duration': '0.3s',
					'border-right': '18px solid #fff'
				});
			}, function(){
				$(this).css({
					'transition-duration': '0.3s',
					'border' : '1px solid #ddd',
			    	'background-color': '#fff'
				});
				$(this).children().css({
					'transition-duration': '0.3s',
					'border-right': '18px solid #bbb'
				});
			});
			
		} else {
			prevBtn.children().css({
				'transition-duration': '0.3s',
				'border-right': '18px solid #bbb'
			});
			prevBtn.css({
				'border' : '1px solid #ddd',
		    	'background-color': '#eee'
			});
			prevBtn.unbind('mouseenter mouseleave');
			prevBtn.off('click');
		}
	};
	
	// 글 제목 수정
	
	var modifyTitleFun = function(){
		var title = $('span.locationName').html();
		var titleBox = `<input type='text' name='modifyTitle' value='${title}'/>`;
		var closeBtn = `<a class='modify'>취소</a>`;
		$('span.locationName').html('');
		$('span.locationName').append(titleBox);
		$('a.modify').eq(0).after(closeBtn);
	};
	
	var modifyBtn = function(){
		var title = $('span.locationName').html();
		$('a.modify').eq(1).remove();
		$('a.modify').eq(0).on('click',function(){
			modifyTitleFun();
			$(this).off('click');
			
			$('a.modify').eq(1).on('click',function(){
				$('input[name=modifyTitle]').remove();
				$('span.locationName').html(title);
				modifyBtn();
			});
			
			$(this).on('click',function(){
				var tv_title = $('input[name=modifyTitle]').val();
				var tv_no = $('input[name=rv_crno]').val();
				var chk = true;
				if($('input[name=modifyTitle]').val().lenght > 25){
					
					failBox('제목은 25자 이상 넘어갈 수 없습니다.');
					$('input[name=modifyTitle]').focus();
					chk = false;
				}
				if(chk){
					$.ajax({
					
						url:"TripReviewModify",
						data : {
							'tv_no' : tv_no,
							'tv_title' : tv_title 
						},
						method:'post',
						dataType : 'text',
						success : function(data){
							if(data > 0){
								$('a.modify').eq(0).off('click');
								$('input[name=modifyTitle]').remove();
								$('span.locationName').html(tv_title);
								modifyBtn();
							}
						}
					});
				}
			});
		
		});
		
	};
		
	
	// 첫 페이지 로드시 실행
	
	$('span.id').eq(0).hover(function(){
		$('ul.memberList').slideToggle(400);
	}, function(){
		$('ul.memberList').slideToggle(400);
	});
	
	modifyBtn();
	
	dayPageClickFun();
	
	routeListLoad(); 
	
});


// 스크랩

var favoriteInsert = function() {
	
	$.ajax({
		
		url : "FavoriteInsert",
		data : {
			f_pno : $('input[name=rv_crno]').val(),
			f_cate : arguments[0]
		},
		dataType : "text",
		success : (data) => {
			var chkNum = Number(data);
			if(chkNum > 0) {
				var a = `
				<a href="#" class="wishBtn wishDel" onclick="return favoriteDelete(1);">★</a>
				`;
				$('a.wishAdd').remove();
				$('span.locationName').after(a);
			}
		}
		
	});
	
	
};


var favoriteDelete = function(){
	
	$.ajax({
		
		url : "FavoriteDelete",
		data : {
			f_pno : $('input[name=rv_crno]').val()
		},
		dataType : "text",
		success : (data) => {
			var chkNum = Number(data);
			if(chkNum > 0) {
				var a = `
				<a href="#" class="wishBtn wishAdd" onclick="return favoriteInsert(1);">★</a>
				`;
				$('a.wishDel').remove();
				$('span.locationName').after(a);
			}
		}
		
	});
}


function markerInfoOut(){
	$('div.infoBox').remove();
};

function markerInfoOver(val, that){
	var infoBox = `
		<div class='infoBox' style='
					    display: block;
						position: absolute;
						top : 0;
						left: 0;
						padding: 5px 5px 5px 5px;
						background-color: #fff;
						border: 1px solid #ddd;
						border-radius: 6px;
						box-shadow: -1px 1px 2px #00000080;
						color: #322;
						z-index:9999;
					'>
						<span>${val}</span>
					</div>
	`;
	
	$('body').append(infoBox);
	var left = $(that).offset().left-$('div.infoBox').width()*0.3753;
	var top = $(that).offset().top-$('div.infoBox').height()*1.6;
	$('div.infoBox').css({
		'top' : top,
		'left' : left
	});
	
};