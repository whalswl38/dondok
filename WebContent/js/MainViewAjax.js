/**
 * http://usejsdoc.org/
 */


	var mainView = function(){
		var i = arguments[4];
		var start = arguments[0];
		$.ajax({
			url : arguments[3],
			method : "post",
			data : {
				"start" : arguments[0] ? arguments[0] : 1,
				"end" : arguments[1] ? arguments[1] : 8,
				"keyword" : arguments[2]
			},
			dataType : "html",
			async : false,
			success : function(document) {
				if (document != "null") {
					var	articletag = $('article.article_contents').eq(i);
					if($(document)[5]){
						$(articletag).append($(document)[5]);
						if($(articletag).children('.list_view:nth-last-child(1)').children('div').length <= 1){
							$(articletag).children('.list_view:nth-last-child(1)').css("height","312.5px");
							$(articletag).children('.list_view:nth-last-child(1)').children('div').css("height","96%");
						}
					} else {
						if(start === 1){
							$(articletag).append(document);
						}
					}
				} 
			},beforeSend : function(){
				loadingBox($('article.article_contents').eq(i), {
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


