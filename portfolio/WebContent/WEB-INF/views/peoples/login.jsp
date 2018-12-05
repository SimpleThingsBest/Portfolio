<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>로그인</title>
</head>
<body>
	<%@ include file="../share/header.jsp"%>
	<div class="container">
		<div class="row">
			<div class="col-md-4"></div>
			<div class="col-md-4">
				<div class="login-box well">
					<form accept-charset="UTF-8" role="form" method="post"
						action="login" onsubmit="return checkId()">
						<legend>로그인</legend>
						<div style='color: red'>${msg}</div>
						<div class="form-group">
							<label for="username-email">이메일</label> <input type="email"
								name="email" id="email" required="required"
								placeholder="이메일을 입력하세요" class="form-control" />
						</div>
						<div class="form-group">
							<label for="password">비밀번호</label> <input type="password"
								name="pw" id="pw" placeholder="비밀번호를 입력하세요" class="form-control" />
						</div>
						<div class="form-group">
							<input type="submit"
								class="btn btn-primary btn-login-submit btn-block m-t-md"
								value="로그인" />
						</div>
						<div class="form-group">
							<a href="register" class="btn btn-warning btn-block m-t-md">회원가입</a>
						</div>

						<div class="form-group">
							<a href="../" class="btn btn-success btn-block m-t-md">메인으로</a>
						</div>
					</form>
				</div>
			</div>
			<div class="col-md-4"></div>
		</div>
	</div>
	<%@ include file="../share/footer.jsp"%>
</body>
<script>
	var effectivenessIdPw = false;
	
	function checkId(){
		//데이터 가져오기
		var email = document.getElementById("email").value
		var pw = document.getElementById("pw").value
		
		//email에 ||가 포함되어 있으면 거르기
		var array = email.split("\||")
		if(array.length >= 2){
			//||가 1개 이상 포함되었을 때
		}else{
			effectivenessIdPw = true;
		}
		//email을 통과했을 때 패스워드에 ||가 포함되어 있는지 검사해서 거르기
		if(effectivenessIdPw == true){
			array = pw.split("\||")
			if(array.length >= 2){
				effectivenessIdPw = false;
			}
		}
		//모두 통과한 경우에만 로그인이 되며(true)
		if(effectivenessIdPw == true){
			return true;
		}
		//하나라도 통과하지 못했을 경우 대화상자를 통해 알림 주기
		else{
			alert("아이디와 비밀번호에 포함될 수 없는 특수문자를 사용하셨습니다.")
			return false;
		}
	}
	
/*
	var email = document.getElementById("email").value
	var pw = document.getElementById("pw").value
	var arrayEmail = email.split("\\|\\|");
	var arrayPw = pw.split("\\|\\|");
	if(arrayEmail.length >= 2 || arrayPw.length >= 2){
		var strEmail = email.substring(arrayEmail[0].length, arrayEmail[0].length+2)
		var strPw = pw.substring(arrayPw[0].length, arrayPw[0].length+2)
		if(strEmail == "||" || strPw == "||"){
			//request.setAttribute("msg","아이디와 비밀번호에 포함되면 안 되는 특수문자를 입력하셨습니다.\n관리자에게 문의하세요.")
			alert("아이디와 비밀번호에 포함되면 안 되는 특수문자를 입력하셨습니다.\n관리자에게 문의하세요.")
		}
	}
	*/
		
</script>
</html>