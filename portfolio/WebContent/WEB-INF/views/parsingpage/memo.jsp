<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메모</title>
</head>
<body>
	<div id="listdisplay"></div>
</body>
<!-- jQuery 링크 추가 : 자바스크립트 보다 먼저 작성해야 함.-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.js"></script>
<script>
	//ajax로 mobile/memolist 요청을 수행해주는 함수
	function memolist(){
		$.ajax({
			url: 'memolist',
			dataType: 'json',
			success: function(memolist){
				//매개변수로 받은 변수는 서버로부터 받아온 데이터이다.
				//listdisplay라는 id를 가진 DOM객체를 찾아오는 것
				//value 속성은 입력한 값을 가져오는 속성
				//innerHTML은 태그와 태그 사이의 내용을 가져오거나 설정하는 속성
			   	var listdisplay = document.getElementById("listdisplay")
			   	//제목 출력
			   	listdisplay.innerHTML = "<h3 align='center'>메모 목록</h3>"
			   	//데이터 개수 출력
			   	listdisplay.innerHTML += "<p>메모 개수:"+memolist.memototalcount+"</p>"
			   	//테이블 생성 태
			   	var display = "<table border='1'>"
			   	//테이블의 제목 셀 만들기
			   	display += "<tr><th>메모번호</th><th>메모제목</th><th>작성일</th>"
			   	var ar = memolist.memolist;
			   	//자바스크립트에서 배열 순회 - 다른 언어는 임시변수에 값이 대입되는데 자바스립트는 임시변수에 인덱스가 대입됨
			   	for (i in ar){
			   		record = ar[i]
			   		display += "<tr><td>"+record.num+"</td>"
			   		//상세보기를 구현하려면 기본키 값을 넘겨주는 방법을 고민해야 합니다.
			   		display += "<td><a href='#' onclick='detail("+record.num+")'>"+record.title+"</a></td>"
			   		display += "<td>"+record.regdate+"</td></tr>"
			   	}
			   	display += "</table>"
			   	
			   	listdisplay.innerHTML += display
			   	
			}
			})
	}
	
	//상세보기를 위한 함수
	function detail(num){
		$.ajax({
			url: 'memodetail',
			data: {'num':num},
			dataType: 'json',
			success: function(memo){
				alert(memo.content)
			}
		})
	}
	
	//jQuery에서 문서가 시작되자 마자 수행
	$(function(){
		memolist()
	})
	
</script>
</html>