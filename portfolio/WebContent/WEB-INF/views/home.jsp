<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="share/header.jsp"%>
<section class="content">
     <div class="box">
     <h1></h1>
         <div class="box-header with-border">
         <c:if test="${peoples==null}">
             <a href="${pageContext.request.contextPath}/peoples/register"/><h3 class="box-title">회원가입</h3></a>
             <a href="${pageContext.request.contextPath}/peoples/login"/><h3 class="box-title">로그인</h3></a>
             <a href="${pageContext.request.contextPath}/parsingpage/mfdsnews"/><h3 class="box-title">식약처 보도자료</h3></a>
             <a href="${pageContext.request.contextPath}/parsingpage/memo"/><h3 class="box-title">메모리스트</h3></a>
         </c:if>
         <c:if test="${peoples!=null}">
         ${peoples.pnick}님 환영합니다.
         <a href="${pageContext.request.contextPath}/peoples/logout"/><h3 class="box-title">로그아웃</h3></a>
         <a href="${pageContext.request.contextPath}/parsingpage/mfdsnews"/><h3 class="box-title">식약처 보도자료</h3></a>
         </c:if>
         </div>
     </div>
     <c:if test="${registerSuccess != null}">
     <script>
     //회원가입에 성공했을 때 출력
     alert(${registerSuccess});
     </script>
     </c:if>
<%@include file="share/footer.jsp"%>