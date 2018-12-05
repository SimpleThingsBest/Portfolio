<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../share/header.jsp"%>
<body>
	<h1 id="wow" onfocus="hi()"></h1>
	<section class="content">
		<!-- 회원가입 -->
		<form id="registerform" enctype="multipart/form-data" method="post"
			onsubmit="return check()">
			<p align="center">
			<table border="1" width="50%" height="80%" align="center">
				<tr>
					<td colspan="3" align="center"><h2>회원가입</h2></td>
				</tr>
				<tr>
					<td rowspan="5" align="center">
						<p></p> <img id="img" width="100" height="100" border="1" /><br />
						<br /> <input type='file' id="image" name="image" /><br />
					</td>
				</tr>
				<tr>
					<td bgcolor="#f5f5f5"><font size="2">&nbsp;&nbsp;&nbsp;&nbsp;이메일</font></td>
					<td>&nbsp;&nbsp;&nbsp; <input type="email" name="email"
						id="email" size="30" maxlength=50 required="required" onblur="confirmId()"/>
						<div id="emailDiv"></div>
					</td>
				</tr>
				<tr>
					<td bgcolor="#f5f5f5"><font size="2">&nbsp;&nbsp;&nbsp;&nbsp;비밀번호</font></td>
					<td>&nbsp;&nbsp;&nbsp; <input type="password" name="pw"
						id="pw" size="20" required="required" />
					</td>
				</tr>
				<tr>
					<td bgcolor="#f5f5f5"><font size="2">&nbsp;&nbsp;&nbsp;&nbsp;비밀번호
							확인</font></td>
					<td>&nbsp;&nbsp;&nbsp; <input type="password" id="pwconfirm"
						size="20" required="required" />
						<div id="pwDiv"></div>
					</td>
				</tr>
				<tr>
					<td width="17%" bgcolor="#f5f5f5"><font size="2">&nbsp;&nbsp;&nbsp;&nbsp;이름</font></td>
					<td>&nbsp;&nbsp;&nbsp; <input type="text" name="nickname" id="nickname"
						size="20" pattern="([a-z, A-Z, 가-힣]){2,}" required="required"
						title="닉네임은 문자 2자 이상입니다." onblur="confirmId()"/>
						<div id="nicknameDiv"></div>
					</td>
				</tr>
				<tr>
					<td align="center" colspan="3">
						<p></p> <input type="submit" value="회원가입" class="btn btn-warning" />
						<input type="button" value="메인으로" class="btn btn-success"
						onclick="javascript:window.location='../'">
						<p></p>
					</td>
				</tr>
			</table>
		</form>
		<br /> <br />
	</section>
</body>

    
<!-- 이미지 미리보기 관련 작업 -->
<script>
	var filename = ''
	//change 이벤트가 발생하면 readURL 호출
	//change는 내용이 변경되면 호출되는 이벤트다.
	document.getElementById("image").addEventListener('change', function() {
		readURL(this);
	})
	//이미지파일을 선택했을 때 미리보기를 수행해주는 메소드
	function readURL(input) {
		if (input.files && input.files[0]) {
			filename = input.files[0].name;
			//확장자 뽑아내기
			//alert(filename) //스크린샷 2018-10-25 오후 4.18.25.png
			var ext = filename.substr(filename.length - 3, filename.length);
			//특정 확장자의 파일만 등록할 수 있게 필터링
			var idCheck = false;
			if ((ext.toLowerCase() == "jpg" || ext.toLowerCase() == "gif" || ext
					.toLowerCase() == "png")) {
				isCheck = true;
			}
			if (isCheck == false) {
				alert("jpg 나 gif, png 파일만 업로드 가능합니다.");
				return;
			}

			var reader = new FileReader();
			reader.onload = function(e) {
				document.getElementById('img').src = e.target.result;

				//alert(e.target.result)// 스크린샷 2018-10-21 오후 8.44.08.png
			}
			reader.readAsDataURL(input.files[0]);
		}
	};
</script>
<!-- 아이디 중복체크 -->
<script>
	//아이디 중복 검사 여부를 저장할 변수(Bool) + ||가 들어가면 실행되지 않게 하는 코드를 추가 + 닉네임 중복 검사 실행
	//작성한 아이디가 데이터베이스에 있으면 false를 반환한다.
	var idcheck = false;
	function confirmId() {
		//이렇게 작성하면 peoples/idcheck의 주소를 요청할 것이다.
		var addr = "idcheck";
		var pid = document.getElementById("email").value;
		var pnick = document.getElementById("nickname").value;
		var togle = false;
		$.ajax({
					url : addr,
					data : {
						'pid' : pid,
						//'togle' : togle
						'pnick' : pnick
					},
					dataType : "json",
					success : function(map) {
						//result가 idFalse면 아이디중복
						//result가 nickFalse면 닉네임중복
						//result가 true이면 통과
						if (map.result == "true" && map.oR == "true") {
							document.getElementById("emailDiv").innerHTML = "사용 가능한 아이디";
							document.getElementById("emailDiv").style.color = 'blue';
							idcheck = true;
						} else if(map.result == "idFalse" || map.oR == "false"){
							document.getElementById("emailDiv").innerHTML = "사용 불가능한 아이디";
							document.getElementById("emailDiv").style.color = 'red';
							idcheck = false;
						} else{
							idcheck = true;
						}
						if(map.result == false){
							document.getElementById("nicknameDiv").innerHTML = "아이디 중복체크를 먼저 해주세요."
							document.getElementById("nicknameDiv").style.color = 'red';
						}else{
							if(map.result == "true" && pnick.length > 0){
							document.getElementById("nicknameDiv").innerHTML = "사용 가능한 이름"
							document.getElementById("nicknameDiv").style.color = 'blue';
							idcheck = true;
							} else if(map.result == "nickFalse"){
							document.getElementById("nicknameDiv").innerHTML = "사용 불가능한 이름";
							document.getElementById("nicknameDiv").style.color = 'red';
							idcheck = false;
							}
						}
					}
				});
	}
	//<!-- 유효성검사 : onsubmit은 데이터를 전송하는 역할이 있는 함수인데 여기에 값을 return true 또는 false로 주게 되면 false일 때는 원래 하던 일을 하지 않음.
	//이런 역할을 하는 애들은 onsubmit과 키보드 이벤트 등이 있음.
	// onblur나 click는 원래 하려는 일이 없어서 리턴을 트루 폴스로 주지 않음-->
	function check() {
		
		if (idcheck == false) {
			document.getElementById("emailDiv").innerHTML = "이메일 중복검사를 수행하세요!"
			document.getElementById("emailDiv").style.color = 'red';
			document.getElementById("email").focus();
			return false;
		}
		var pw = document.getElementById("pw").value;
		var pwconfirm = document.getElementById("pwconfirm").value;

		if (pw != pwconfirm) {
			document.getElementById("pwDiv").innerHTML = "2개의 비밀번호가 일치하지 않습니다."
			document.getElementById("pwDiv").style.color = 'red';
			document.getElementById("pwconfirm").focus();
			return false;
		}
		var pattern1 = /[0-9]/; //숫자
		var pattern2 = /[a-zA-Z]/; //문자
		var pattern3 = /[~!@#$%^&*()_+|<>?:]/; //특수문자
		if (!pattern1.test(pw) || !pattern2.test(pw) || !pattern3.test(pw)
				|| pw.length < 8) {
			alert("비밀번호는 8자리 이상 문자, 숫자, 특수문자로 구성해야 합니다.");
			return false;
		}
	}
		
</script>

<%@include file="../share/footer.jsp"%>
