<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>공지사항 목록 조회</title>
		<link rel="stylesheet" href="../resources/css/notice.css">
	</head>
	<body>
		<h1>공지사항 목록</h1>
		<table>
			<colgroup>
				<col width="10%">
				<col width="55%">
				<col width="10%">
				<col width="15%">
				<col width="10%">
			</colgroup>
			<thead>
				<tr>
					<th>번호</th>
					<th>제목</th>
					<th>작성자</th>
					<th>작성일</th>
					<th>첨부파일</th>
				</tr>
			</thead>
			<tbody>
			<!-- list 데이터는 items에 넣었고 var에서 설정한 변수로 list데이터에서
				꺼낸 값을 사용하고 i의 값은 varStatus로 사용 -->
				<c:forEach begin="1" end="10" var="notice" items="${nList }" varStatus="i">
				<tr>
					<td>${i.count }</td>
					<td>${notice.noticeSubject }</td>
					<td>${notice.noticeWriter }</td>
					<td>
						<fmt:formatDate pattern="yyyy-MM-dd" value="${notice.nCreateDate }"/>
					</td>
					<td>
						<c:if test="${!empty notice.noticeFileName }">O</c:if>
						<c:if test="${empty notice.noticeFileName }">X</c:if>
					</td>
					<td>
					<fmt:formatNumber pattern="##,###,###" value="123000"></fmt:formatNumber>
					</td>
				</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr align="center">
					<td colspan="5">
						1 2 3 4 5
					</td>
				<tr>
					<td colspan="4"><input type="text"></td>
					<td><input type="submit" value="검색"></td>				
				</tr>
			</tfoot>
		</table>
	</body>
</html>