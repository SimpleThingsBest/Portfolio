<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../share/header.jsp"%>
<section class="content">
	<div class="box">
		<div class="box-header with-border">
			<table class="table">
				<thead class="thead">
					<th scope="col">제목/내용</th>
					<th scope="col">날짜</th>
				</thead>
				<tbody>
					<c:forEach var="data" items="${data}">
						<tr>
							<td><a href="${data.link}">${data.title}</a>
							<br/>설명: ${data.content}</td>
							<td>${data.pubdate}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<%@include file="../share/footer.jsp"%>