/**
 * http://usejsdoc.org/
 */
var start = 1;
var end = 8;
var pageEndChk = true;

var reviewView = function() {
	if(pageEndChk){
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
					if($(document)[5]){
						$("#article_contents").append($(document)[5]);
						if($('.list_view:nth-last-child(1)>div:nth-child(2)').length == 0){
							$('.list_view:nth-last-child(1)').css("height","312.5px");
							$('.list_view:nth-last-child(1)>div:nth-child(1)').css("height","96%");
						}
					} else {
						if(start === 1){
							$("#article_contents").append(document);
							
						}
						pageEndChk = false;
					}
				} 
			},beforeSend : function(){
				loadingBox($('section'), {
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
};

