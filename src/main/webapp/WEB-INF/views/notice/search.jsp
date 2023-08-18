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
				<c:forEach var="notice" items="${sList}" varStatus="i">
<%-- 				<c:forEach begin="1" end="10" var="notice" items="${sList }" varStatus="i"> --%>
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
						<c:forEach begin="${pInfo.startNavi }" end="${pInfo.endNavi }" var="p">
							<!-- <a href="/notice/list.kh?page=${p }">${p }</a>&nbsp; 
								url 관리를 쉽게 하기 위해 core태그로 변경 
								나중에 재사용이 편하다!-->
							<c:url var="pageUrl" value="/notice/search.kh">
<%-- 							<c:url var="pageUrl" value="/notice/search.kh?searchCondition=${paramMap.searchCondition }&searchKeyword=${paramMap.searchKeyword }&page=${p }"> --%>
								<c:param name="page" value="${p }"></c:param>
								<c:param name="searchCondition" value="${paramMap.searchCondition }"></c:param>
								<c:param name="searchKeyword" value="${paramMap.searchKeyword }"></c:param>
							</c:url>
							<a href="${pageUrl }">${p }</a>&nbsp;
						</c:forEach>
					</td>
				<tr>
					<td colspan="4">
						<form action="/notice/search.kh" method="get">
							<select name="searchCondition">
								<option value="all"<c:if test="${paramMap.searchCondition == 'all' }"> selected</c:if>>전체</option>
								<option value="writer"<c:if test="${paramMap.searchCondition == 'writer' }"> selected</c:if>>작성자</option>
								<option value="title"<c:if test="${paramMap.searchCondition == 'title' }"> selected</c:if>>제목</option>
								<option value="content"<c:if test="${paramMap.searchCondition == 'content' }"> selected</c:if>>내용</option>
							</select>
							<input type="text" name="searchKeyword" value="${paramMap.searchKeyword }" placeholder="검색어를 입력하세요">
							<input type="submit" value="검색">	
						</form>		
					</td>
					<td>
						<button>글쓰기</button>
					</td>	
				</tr>
			</tfoot>
		</table>
	</body>
</html>