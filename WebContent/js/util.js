/**
 * http://usejsdoc.org/
 */


var failBox = function(str){
	var failBox = `<div id='failBackground' style='display:block;position:fixed; top:0; left:0; width:100%; height:100%; background-color:#00000080;'>
					<div id='failBox' style='	display: block;
	    										position: relative;
	    										margin: 250px auto;
	    										z-index: 999;
	    										background-color: #fff;
	    										width: 350px;
	    										height: max-content;
	    										padding: 20px;
	    										border-radius: 4px;
												box-shadow: -1px 1px 2px #00000080;'>
						<p id='failComment' style='word-break: break-all;
    padding: 15px;margin: 0;
    text-align: center;
    letter-spacing: 0.5pt;
    font-weight: 600;
    font-size: 15pt;'>${str}</p>
						<button id='failClose' style='
	display: inline-block;
    text-align: center;
    background-color: #db362c;
    padding: 8px 10px 10px 10px;
    margin-top: 15px;
    margin-bottom: 15px;
    margin-left: 150px;
    font-size: 14pt;
    color: #fff;
    border: 0px;
    border-radius: 4pt;
    box-shadow: -1px 1px 2px #00000080;
    font-family: 'Do Hyeon', sans-serif;
						'>닫기</button>
					</div>
				</div>`;
	$('body').append(failBox);
	$('#failClose').hover(function(){
		$(this).css({
			'background-color': '#9f297a',
			'transition-duration': '0.3s'
		});
	},function(){
		$(this).css({
			'background-color': '#db362c',
			'transition-duration': '0.3s'
		});
	});
	$('#failClose').on('click',function(){
		$('#failBackground').remove();
	});
	
	return false;
};



var loadingBox = function(object,cssObj){
	
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
	
	$(object).prepend(loading);
	$('#loadingBox').css(cssObj);
};

var toolTipBoxOn = function(value,obj) {
	$(obj).on('mousemove',function(){
		$('.toolTip').text(value);
		$('.toolTip').css("display", "block");
		$('.toolTip').css("top", event.clientY + 10 + "px");
		$('.toolTip').css("left", event.clientX + 10 + "px");
	});
	
	$(obj).on('mouseout',function(){
		$('.toolTip').css("display", "none");
	});
};	

