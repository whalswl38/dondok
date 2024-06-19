/**
 * http://usejsdoc.org/
 */
$(function(){
	
		var mapload = function(x,y, title, id){
			var container = document.getElementById('map');
			var options = {
					center: new kakao.maps.LatLng(y,x),
					level: 6
				};

			var map = new kakao.maps.Map(container, options);
			
			var markerPosition  = new kakao.maps.LatLng(y, x); 

			var marker = new kakao.maps.Marker({
			    position: markerPosition
			});

			marker.setMap(map);
			
			var iwContent = `<div style="padding:5px;">${title}<br><a style="color: #473a3a;font-size: 10pt;margin: 0 4px;" href="https://map.kakao.com/link/map/${id}" target="_blank">큰지도 보기</a> <a style="color: #473a3a;font-size: 10pt;margin: 0 4px;" href="https://map.kakao.com/link/to/${id}" target="_blank">길찾기</a></div>`,
				iwPosition = new kakao.maps.LatLng(y, x); //인포윈도우 표시 위치입니다

			var infowindow = new kakao.maps.InfoWindow({
				position : iwPosition, 
				content : iwContent 
			});
		  
			infowindow.open(map, marker); 
		};
		
		
		var placeNameSelect = function() {
					$.ajax({
						url : 'RouteInfo',
						data : {
							routeid : arguments[0]
						},
						dataType : 'json',
						method : 'post',
						success : function (data) {
							
							var locationTag = $('span.location');
							
							var place_url = data.place_url;
							var place_name = data.place_name;
							var road_address_name = data.road_address_name;
							var address_name = data.address_name;
							var category_name = data.category_name;
							var phone = data.phone;
							var x = data.x;
							var y = data.y;
							
							locationTag.html(place_name);
							
							var tag = `
							<article class="cr_placeInfo" style="visibility: hidden;">
								<div class="placeMap">
									<div id="map" style="width:100%;height:100%;"></div>
								</div>
								<div class="cr_infoItemCon">
									<ul class="ul_infoItem">
										<li><span class="info_title">상호명</span><a href="${place_url}" target="_blank"><span class="info_contents">${place_name}</span></a></li>		
										<li><span class="info_title">분류</span><span class="info_contents">${category_name}</span></li>		
										<li><span class="info_title">주소</span><span class="info_contents">${address_name}</span></li>		
										<li><span class="info_title">도로명</span><span class="info_contents">${road_address_name}</span></li>		
										<li><span class="info_title">전화번호</span><span class="info_contents">${phone}</span></li>		
									</ul>
								</div>
							</article>
							<div class="back" style='visibility: hidden;position:fixed; top:0; left:0; width:100%; height:100%; background-color:#00000080;z-index:999;'></div>
							`;
							
							console.log(x,y)
							
							
							$('section').prepend(tag);
							
							mapload(x,y,place_name,arguments[0]);
							
							var item = $('article.cr_placeInfo');
							var back = $('div.back');
							
							toolTipBoxOn('카카오페이지로...',$('span.info_contents').eq(0));
							
							back.on('click',function(){
								item.css('visibility','hidden');
								back.css('visibility','hidden');
							});
							
							locationTag.on('click',function(){
								
								if(item.css('visibility') == 'hidden'){
									item.css('visibility','visible');
									back.css('visibility','visible');
								}
							});
							
						}
					});
		
					
					
		};
		placeNameSelect($('span.location').attr('data-place_id'));
});	
		
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