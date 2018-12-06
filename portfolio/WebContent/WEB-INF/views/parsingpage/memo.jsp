<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메모</title>
</head>
<body>
<div id="detaildisplay"></div>
	<font color='red'><h3 id="notice"></h3></font>
	<input type="button" value="메모추가" id=insertbtn />
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
				//DOM (Document Object Model) : html문서내에 있는 객체
				var detaildisplay = document.getElementById("detaildisplay");
				// detaildisplay = "" - 새로 출력
				// detaildisplay += "" - 새로 추가
				detaildisplay.innerHTML = "<p>"+memo.regdate+"</p>"
				detaildisplay.innerHTML += "<p><b>제목 : </b>"+memo.title+"</p>"
				detaildisplay.innerHTML += "<p><b>내용 : </b>"+memo.content+"</p>"
				if(memo.imagepath != " "){
					detaildisplay.innerHTML += "<img src='/portfolio/memoimage/"+memo.imagepath+"'/><br/>"
					//현재는 웹서버이므로 프로젝트이름까지는 생략해도 됩니다. 하지만 안드로이드나 ios는 꼭 전체 주소를 적어주어야 합니다.
					//"<img src='http://localhost:8080/portfolio/memoimage/"+memo.imagepath+"'/><br/>"
					//현재 이미지는 인라인태그라서 <br/>을 붙여주거나 <p>,<div>태그 안에 넣어서 옆에 다른 무엇이 올 수 없도록 해줍니다.
				}
				detaildisplay.innerHTML += "<input type='button' value='삭제' onclick='del("+memo.num+")' />"
			}
		})
	}
	
	//데이터를 삭제하는 함수
	function del(num){
		$.ajax({
			url: 'memodelete',
			data: {'num':num},
			dataType: 'json',
			type: 'POST',
			success: function(memo){
				var notice = document.getElementById("notice")
				if(memo.result == 'success'){
					//데이터 다시 출력
					memolist()
					notice.innerHTML = "삭제 성공!"
					var detaildisplay = document.getElementById("detaildisplay").innerHTML = ""
					setTimeout(function(){notice.innerHTML = "";}, 3000)
				}else{
					//메시지 띄우기
					notice.innerHTML = "삭제 실패...ㅠ"
					setTimeout(function(){notice.innerHTML = "";}, 3000)
				}
			}
		})
	}
	
	document.getElementById("insertbtn").addEventListener("click", function(){
		location.href = "http://localhost:8080/portfolio/parsingpage/memoinsert"
	})
	
	//jQuery에서 문서가 시작되자 마자 수행
	$(function(){
		memolist()
	})
	
</script>
</html>