/**
 * http://usejsdoc.org/
 */

$(function(){
	
	
	$.ajax({
		
		url : 'headerbar.jsp',
		dataType : 'html',
		async : false,
		success : function(data){
			$('body').prepend(data);
		},beforeSend : function(){
			loadingBox($('body'), {
				'background-color': '#ffffff80',
				'z-index': '10010',
		    	'width': '100%',
				'height': '100%'
			});
		},complete : function(){
			$('#loadingBox').remove();
		}
		
	});
	
});
