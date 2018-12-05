<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!-- JSTL -->
    <%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- 모든 페이지에 적용할 타이틀 -->
<title>Food Information</title>
<!-- 너비를 디바이스 크기로 맞춤 -->
<meta
content='width=device, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
<!-- 부트스트랩의 css 파일 링크설정.  -->
<link href="${pageContext.request.contextPath }/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<!-- IE9버전 이하에서 HTML5태그를 출력하기 위한 설정 -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.comrespond/1.4.2respond.min.js"></script>
    <![endif]-->
</head>
<!-- jQuery 링크 설 -->
<!-- jQuery 2.1.4 -->
<script src="${pageContext.request.contextPath}/resources/jquery/jquery.js"></script>

<!-- 기본화면 설정 -->
<body class="skin-blue sidebar-mini">
     <div class="wrapper">
         <header class="main-header">
             <div class="page-header">
                 <h1>Hello Food Information</h1>
             </div>
         </header>
     </div>
     
     <!-- 기본메뉴 -->
     <aside class="main-sidebar">
         <section class="sidebar">
             <ul class="nav nav-tabs">
                 <li role="presentation" class="active"><a href="${pageContext.request.contextPath}/views/home.jsp">Home</a></li> <!-- active는 처음이 이게 선택되어 있도록 하는 설정이다. -->
                 <li role="presentation"><a href="#">목록보기</a></li>
                 <li role="presentation"><a href="${pageContext.request.contextPath}/peoples/login">로그인</a></li>
                 <li role="presentation"><a href="#">회원정보수정</a></li>
                 <li role="presentation"><a href="${pageContext.request.contextPath}/peoples/register">회원가입</a></li>
                 <li role="presentation"><a href="#">회원탈퇴</a></li>
                 <li role="presentation"><a href="#">내가 쓴 글</a></li>
                 <li role="presentation"><a href="${pageContext.request.contextPath}/parsingpage/mfdsnews">보도자료</a></li>
                 <li role="presentation"><a href="#">식품 최신동향</a></li>
                 <li role="presentation"><a href="#">HAPP이란?</a></li>
                 <li role="presentation"><a href="#">식중독 예측정보</a></li>
                 <li role="presentation"><a href="#">자유게시판</a></li>
                 <li role="presentation"><a href="#">Q&A</a></li>                                                     
             </ul>
         </section>
     </aside>
     <div>
</body>
</html>