<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	

<!DOCTYPE html>
<html>
<head>
<link href='fullcalendar/core/main.css' rel='stylesheet' />
<link href='fullcalendar/daygrid/main.css' rel='stylesheet' />
<link href='fullcalendar/timegrid/main.min.css' rel='stylesheet' />

<script src='fullcalendar/core/main.js'></script>
<script src='fullcalendar/daygrid/main.js'></script>
<script src='fullcalendar/interaction/main.js'></script>
<script src='fullcalendar/timegrid/main.js'></script>
<script src="fullcalendar/moment/main.min.js"></script>
<script src="fullcalendar/google-calendar/main.js"></script>
<script src='fullcalendar/core/locales/ko.js'></script>

<script type="text/javascript" src="http://code.jquery.com/jquery-3.4.1.min.js"></script>

<script>


document.addEventListener('DOMContentLoaded', function() {
	var Calendar = FullCalendar.Calendar;
	var Draggable = FullCalendarInteraction.Draggable;
	
	var containerEl = document.getElementById('external-events');
	var calendarEl = document.getElementById('calendar');
	var checkbox = document.getElementById('drop-remove');

	new Draggable(containerEl, {
		itemSelector: '.fc-event',
		eventData: function(eventEl) {
			return {
				title: eventEl.innerText
			};
		}
	});
	var dateArray = new Array();
	var calendar = new FullCalendar.Calendar(calendarEl, {
		// plugins: 모듈 js파일들을 사용한다.
		plugins : [ 'dayGrid', 'interaction', 'timeGrid', 'moment' ],

		// 머릿말엔 이전, 센터, 오른쪽으로 되어있다.
		header : {
	        left: 'addEventButton, dayGridMonth,timeGridWeek,timeGridDay',
	        center: 'title',
	        right: 'prev,next today'
		},
 		
		// 일정 등록하는 방법
		customButtons : {
			addEventButton : {
				text : '일정등록',
				click : function(info) {
					var dateStr = prompt('일정을 YYYY-MM-DD 형식으로 추가해라');
					var date = new Date(dateStr + 'T00:00:00'); // will be in local time
	
					if (!isNaN(date.valueOf())) { // valid?
						calendar.addEvent({
							/*
							- title : 캘린더에 표시되는 일정의 이름
							- start : 캘린더에 표시되는 일정의 시작일 (yyyy-mm-dd[THH:MM:SS] ; 2019-09-05T12:30:00)
							- end : 캘린더에 표시되는 일정의 마지막 일 (yyyy-mm-dd[THH:MM:SS] ; 2019-09-05T12:30:00)
							- 캘린더에는 start부터 end전날까지 표시됨
							- allDay : 일정이 종일인지 아닌지 여부(boolean)*/
							
							title : '일정 등록시 제목',
							start : date, //date2,
							allDay : true
						});
						alert('해당 날짜의 데이터가 달력에 저장됐다.');
					} else {
						alert('실용가능하지않다.');
					}
					// 내가 등록한 날짜 볼수있음
					console.log(dateStr);
					document.getElementById('day').value = dateStr;
				}
			}
		},
		buttonText : {
			today : '오늘'
		},
		// 기본값 달력이다.
		defaultView : 'dayGridMonth',

		// 달력화면 일자를 다중으로 선택할수있음: true
		selectable : true,

		// 시간대를 설정가능 (GMT: 한국, UTC: 협정 세계시)
		timeZone : 'UTF',

		// 일정들을 수정(길이가 길어질수도있고 다음달로 이월가능하다.)할수있다.
		editable : true,

		// this allows things to be dropped onto the calendar
		droppable: true,
		
		// 드롭체크박스삭제
		drop: function(info) {
		      // is the "remove after drop" checkbox checked?
			if (checkbox.checked) {
				// if so, remove the element from the "Draggable Events" list
				info.draggedEl.parentNode.removeChild(info.draggedEl);
			}
		},
		//
		navLinks : true, // can click day/week names to navigate views

	    
		// 달력에 한국어 표기하는법
		locale : 'ko',

		// 이벤트라주고 일정이 등록되어있는 것을 보여줌
		events : [ { // this object will be "parsed" into an Event Object
			title : '신정', // a property!
			url : 'cal.do?command=test',
			start : '2020-01-01', // a property!
			end : '2020-01-01', // a property! ** see important note below about 'end' **
			textColor : 'red'
		}]/*,	
		// 날짜를 클릭했을 때, 어느 날짜인지 알려주는 함수
		dateClick : function(info) {
			//alert('Clicked on: ' + info.dateStr);
			//alert('Coordinates: ' + info.jsEvent.pageX + ','
			//		+ info.jsEvent.pageY);
			//alert('Current view: ' + info.view.type);
			// change the day's background color just for fun
			info.dayEl.style.backgroundColor = 'blue';
			console.log(info.dateStr);
			dateArray.push(info.dateStr);
			console.log(dateArray);
			//var dateArr = new Array();
			//$("input[name = dateStr]").val(info.dateStr).each(function (i) {
			//	dateArr.push($(this).val());
			//});
			//alert($("input[name=dateStr]").val());
			
			
			$("input[name = dateStr]").val(info.dateStr);
			alert($("input[name=dateStr]").val());
		}*/,
	    select: function(info) {
	        alert('selected ' + info.startStr + ' to ' + info.endStr);
	    }
	});

	// 달력화면을 표시한다.
	calendar.render();
	
	var arrTest = getCalendarDataInDB();
	  $.each(arrTest, function(index, item){
	        console.log('outer loop_in_cal' + index + ' : ' + item);
	        $.each(item, function(iii, ttt){
	            console.log('inner loop_in_cal => ' + iii + ' : ' + ttt);
	 	    });
	});
	
	// 내 일정 저장을 눌르면
	  $("#btnAddTest").click(function(){
		  var arr = getCalendarDataInDB();
		  $.each(arr, function(index, item){
		   calendar.addEvent( item );
		  });
		  calendar.render();
		 });

});

function getCalendarEvent(){
    //var arr = [ {'title':'evt4', 'start':'2019-09-04', 'end':'2019-09-06'} ];
    var arr = { 'title':'evt4', 'start':'2020-01-20', 'end':'2020-01-22' };
    return arr;
}

function getCalendarDataInDB(){
    var arr = [{title: 'evt1', start:'ssssss'}, {title: 'evt2', start:'123123123'}];
    
    //배열 초기화
    var viewData = {};
    
    //data[키] = 밸류
    viewData["id"] = $("#currId").text().trim();
    viewData["title"] = $("#title").val();
    viewData["content"] = $("#content").val();
    
    $.ajax({
        contentType:'application/json',
        dataType:'json',
        url:'cal.do?command=test',
        type:'post',
        async: false,
        data:JSON.stringify(viewData),
        success:function(resp){
            //alert(resp.f.id + ' ggg');     
            $.each(resp, function(index, item){
                console.log(index + ' : ' + item);
                $.each(item, function(iii, ttt){
                    console.log('inner loop => ' + iii + ' : ' + ttt);
                });
            });
            arr = resp;
        },
        error:function(){
            alert('저장 중 에러가 발생했습니다. 다시 시도해 주세요.');
        }
    });
    
    return arr;
}


</script>



<style type="text/css">
body {
	margin: 40px 10px;
	padding: 0;
	font-family: "Lucida Grande", Helvetica, Arial, Verdana, sans-serif;
	font-size: 14px;
}

#external-events {
	position: fixed;
	z-index: 2;
	top: 300px;
	left: 140px;
	width: 150px;
	padding: 0 10px;
	border: 1px solid #ccc;
	background: #eee;
}

.demo-topbar + #external-events { /* will get stripped out */
	top: 60px;
}

#external-events .fc-event {
	margin: 1em 0;
	cursor: move;
}

#calendar-container {
	position: relative;
	z-index: 1;
	margin-left: 200px;
}

#calendar {
	max-width: 900px;
	margin: 20px auto;
}

.fc-sun {
	color: red !important;
}

.fc-sat {
	color: blue !important;
}
</style>
</head>
<body>

<div id="external-events">
    <p>
      <strong>그룹 인원</strong>
    </p>
    <div class="fc-event">박철규</div>
    <div class="fc-event">이다희</div>
    <div class="fc-event">조민지</div>
    <div class="fc-event">김동주</div>
    <div class="fc-event">배진한</div>
    <div class="fc-event">이정태</div>
    <p>
      <input type="checkbox" id="drop-remove">
      <label for="drop-remove">드롭 제거</label>
    </p>
</div>

	<div id='calendar'></div>

<form action="cal.do" method="post">
<input type="hidden" name="command" value="test">

<input type="hidden" name="dateStr" value="${info.dateStr}">

<input type="button" id="btnAddTest" value="추가">
<input type="submit" value="디비에 저장"> 

</form>

</body>
</html>

