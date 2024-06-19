/**
 * http://usejsdoc.org/
 */
	$(document).ready(
			function() {

				var placeNameSelect = function() {
					$.ajax({
						url : "metaDateCrawling",
						data : {
							url : "https://place.map.kakao.com/" + arguments[0],
							selector : "meta[property=og:title]"
						},
						dataType : "text",
						method:"post",
						success : (data) => {
							$('p.placeP > span:nth-child(3) > span').html(data);
						}
					});
				};
				
				placeNameSelect($('input[name=cr_placeid]').val());
				
				var searchPlace = function() {
					var category = arguments[0];
					$.ajax({
						url : "ReviewCateSearchList",
						data : {
							"category" : category
						},
						dataType : "html",
						success : (data)=>{
							var background = `<div class='backgroundBlack'></div>`;
							$('body').append(background);
							/*
							$(".backgroundBlack").on("click", ()=>{
								
								$(".backgroundBlack").remove();
								$("article#searchBox").remove();
								
							});
							*/
							$('div.backgroundBlack').after(data);
							
							$('div.closeBtn').on('click', ()=>{
								$("div.backgroundBlack").remove();
								$("article#searchBox").remove();
							});
						}
					});
					return false;
				};
				
				var tmpDelete = function() {
					$.ajax({
						url : "CateTmpDelete",
						success : function(res) {
							if (res === 1) {
								location.back(-1);
							} else {
								alert("임시 폴더 삭제 실패");
							}
						}
					});
				}

				var tmpImagesUploadFunc = function(file, el) {
					console.log(file);
					var formData = new FormData();
					formData.append('file', file);
					$.ajax({
						url : "CateTmpImagesUpload",
						method : "POST",
						data : formData,
						cache : false,
						contentType : false,
						enctype : 'multipart/form-data',
						processData : false,
						success : function(img_name) {
							console.log(img_name);
							$('#summernote').summernote('editor.insertImage',
									img_name, function($image) {

									});
						}
					});
				}


				$('#summernote')
						.summernote(
								{
									height : 500,
									width : 1000,
									lang : 'ko-KR',
									toolbar : [
											[ 'Font Style', [ 'fontname' ] ],
											[
													'style',
													[ 'bold', 'italic',
															'underline' ] ],
											[ 'font', [ 'strikethrough' ] ],
											[ 'fontsize', [ 'fontsize' ] ],
											[ 'color', [ 'color' ] ],
											[ 'para', [ 'paragraph' ] ],
											[ 'height', [ 'height' ] ],
											[ 'Insert', [ 'picture' ] ],
											[ 'Insert', [ 'link' ] ],
											[ 'Misc', [ 'fullscreen' ] ], ],
									callbacks : {
										onImageUpload : function(files) {
											for(var i = 0 ; i < files.length ;i++){
												tmpImagesUploadFunc(files[i]);	
											}
										},
										onMediaDelete : function(target) {
											
											var imgIndex = target[0].src.indexOf('images');
											
											var realPath = target[0].src.substring(imgIndex);
											
											var cr_path = $('input[name=cr_path]').val();
											
											var cr_pathArray = cr_path.split('|');
											
											cr_path = '';
											
											cr_pathArray.forEach((val,index)=>{
												if (val === realPath){
													
												} else {
													var bar = (cr_path === '')? "" : "|";
													cr_path += bar;
													cr_path += val; 
													
												}
											});
											
											console.log("[path] " + cr_path);
											
											$('input[name=cr_path]').val(cr_path);
											
										}
									},
									dialogsInBody: true
								});
				
				$(".placeP > span:nth-child(3), a.searchIcon").hover(()=>{
					$(".placeP > .searchIcon").css({
						"transition-duration": "0.3s",
						"background-image" : "url(images/board_icon/lowSearchNotRounding_icon_over.jpg)"
					});
				}, ()=>{
					$(".placeP > .searchIcon").css({
						"transition-duration": "0.3s",
						"background-image" : "url(images/board_icon/lowSearchNotRounding_icon.jpg)"
					});
				});
				
				$(".placeP > span:nth-child(3), a.searchIcon, .placeP img").on("click", () => {
					searchPlace($('input[name=category]').val());
				});
				
				
				$(".writeFoot > input[type=button]").on("click",() => {
					var category = $("input[name=category]").val();
					if(category === '숙소'){
						location.href='RoomsReviewList';
					} else if (category === '맛집'){
						location.href='RestaurantReviewList';
					} else if (category === '명소'){
						location.href='TouristReviewList';
					}
				});
				

				$('form').submit(function(){
					if(!$('input[name=cr_title]').val()){
						failBox('제목이 비어 있습니다.');
						$('input[name=cr_title]').focus();
						return false;
					}
					
					if($('input[name=cr_title').val().length > 25){
						
						failBox('제목은 25자 이상 넘어갈 수 없습니다.');
						$('input[name=cr_title]').focus();
						return false;
						
					}
					
					if(!$('input[name=cr_placeid]').val()){
						failBox('리뷰 장소를 선택해주세요.');
						$('input[name=cr_placeid]').focus();
						return false;
					}
					
					if ($('#summernote').summernote('isEmpty')) {
						failBox('내용이 비어 있습니다.');
						$('textarea').focus();
						return false;
					}
				});
				
			});

	
