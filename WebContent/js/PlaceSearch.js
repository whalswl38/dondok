/**
 * http://usejsdoc.org/
 */
var key;
var category

var notResult = `
	<div class = "NotResult">
		<p class = "NotResultText">키워드 관련된 검색 결과가 없습니다.</p>
		<a href="#" onclick="return resultClose();" class="resultClose">닫기</a>
	</div>
`;

var resultClose = ()=>{
	if($('div.NotResult')){
		$('div.NotResult').remove();
	}
	return false;
};


var searchFun = function (){
	resultClose();
	key = $('input[name=searchKey]').val();
	category = $('input[name=category]').val();
	console.log(key);
	console.log(category);
	if(key){
	$.ajax({
		url : "kakaoPlaceList",
		dataType : "json",
		data : {
			"category" : category,
			"keyword" : key,
			"page" : arguments[0]? arguments[0] : null
		},
		success : (data) => {
			console.log(data);
			var dom = data.documents;
			if(dom.length !== 0){
			dom.forEach((out, i) => {
				var id = out.id;
				var place_name = out.place_name;
				var address_name = out.address_name;
				var road_address_name = out.road_address_name;
				var category_name = out.category_name;
				var phone = out.phone;
				var place_url = out.place_url;
				$.ajax({
					url : "kakaoPlaceMainImgSearch",
					data : {"place_name" : place_name},
					dataType : "text",
					success : (img_path) => {
						var img = img_path;
						$('div.img img').eq(i).prop("src",img);
						$('.info:'+'eq('+i+') p').eq(0).children('span').text(place_name);
						$('.info:'+'eq('+i+') p').eq(1).children('span + span').text(address_name+"("+road_address_name+")");
						$('.info:'+'eq('+i+') p').eq(2).children('span + span').text(category_name);
						$('.info:'+'eq('+i+') p').eq(3).children('span + span').text(out.phone);
						$('.listValue').eq(i).attr('data-id',id);
						$('div.kakaoBtn a').eq(i).attr({
							"href":"https://place.map.kakao.com/"+id,
							"target":"_blank"
						});
						$("div.listValue").eq(i).hover(()=>{
						
							$("div.selectDiv").eq(i).css({
								"transition-duration": "0.3s",
								"visibility": "visible",
								"width":"100%"
							});
						},()=>{
							
							$("div.selectDiv").eq(i).css({
								"transition-duration": "0.3s",
								"visibility": "hidden",
								"width":"0%"
							});
						});
						
						
						
						$('div.img').eq(i).css("visibility","visible");
						$('.info').eq(i).css("visibility","visible");
					}
				});
			});
			
			var meta = data.meta;
			var btnCount = meta.pageable_count%4 == 0? Math.floor(meta.pageable_count/4) : Math.floor(meta.pageable_count/4+1);
			
			var pageNumFun = function(index, page){
				var attrValue = ( page <= 0 || page > btnCount)? 'false' : ' searchFun('+ Number(page) +');';
				$('div.pageGroup > a').eq(index).attr('onclick','return ' + attrValue);
				$('span.num').eq(index-1).html((page<=0 || page > btnCount)? "" : page);
				if(page<=0 || page > btnCount){
					
					$('div.pageGroup > a').eq(index).removeAttr("href");
					$('.pageGroup > a').eq(index).unbind('mouseenter mouseleave');
					$('.pageGroup > a > div').eq(index).removeAttr("style")
					$('span.num').eq(index-1).removeAttr("style");
					
				} else {
					
					if(!arguments[2]){
						$('div.pageGroup > a').eq(index).attr("href","#");
					
						$('.pageGroup > a').eq(index).hover(()=>{
						
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
				if(page<=0 || page > btnCount) { 
					$('div.pageGroup > a').eq(index).attr("onclick","return false");
					$('div.pageGroup > a').eq(index).removeAttr("href");
					$('.pageGroup > a').eq(index).unbind('mouseenter mouseleave');
					$('.pageGroup > a > div').eq(index).removeAttr("style")
					if(img === "prevBtnImg"){
						$('div.prevBtnImg').removeAttr("style");
					} else {
						$('div.nextBtnImg').removeAttr("style");
					}
				} else {
					$('div.pageGroup > a').eq(index).attr("onclick","return searchFun("+ Number(page) +");");
					$('div.pageGroup > a').eq(index).attr("href","#");
					
					$('.pageGroup > a').eq(index).hover(()=>{
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
					},()=>{
						$('.pageGroup > a > div').eq(index).removeAttr("style");
						if(img === "prevBtnImg"){
							$('div.prevBtnImg').removeAttr("style");
						} else {
							$('div.nextBtnImg').removeAttr("style");
						}
					});
				}
			}
			
			if(arguments[0]){
				var page = arguments[0];
				
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
				pageBtnFun(6, 5,'nextBtnImg');
			}
			$('.pageGroup').css("visibility","visible");
		} else {
			$("#searchBox").append(notResult)
		}
		}
	});
	} else {
		$("#searchBox").append(notResult)
	}
	return false;
};

var wishSelect = (i) => {
	$('.placeP > span:nth-child(3) > span').html($('span.infoTitle').eq(i).text());
	$('.placeP > span:nth-child(3) > span').attr("title", $('span.infoTitle').eq(i).text());
	$('input[name=cr_placeid]').val($('.listValue').eq(i).attr('data-id'));
	$(".backgroundBlack").remove();
	$("article#searchBox").remove();

};


var closeBox = function(){
	$("div.backgroundBlack").remove();
	$("article#searchBox").remove();
}
