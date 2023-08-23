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
					<!-- a링크에 적었던 값 중 value에는 ? 전의 값을 넣고
						 c:param에는 ? 뒤의 값을 넣는다
						 why? 긴 url을 하나의 번수로 만들어 사용하는 것
					 -->
<%--  					<td><a href="/notice/detail.do?noticeNo=${notice.noticeNo} }">${notice.noticeSubject }</a></td> --%>
					<c:url var="detailUrl" value="/notice/detail.kh">
						<c:param name="noticeNo" value="${notice.noticeNo}"></c:param>
					</c:url>
 					<td><a href="${detailUrl }">${notice.noticeSubject }</a></td>
					<td>${notice.noticeWriter }</td>
					<td>
						<fmt:formatDate pattern="yyyy-MM-dd" value="${notice.nCreateDate }"/>
					</td>
					<td>
						<c:if test="${!empty notice.noticeFileRename }">O</c:if>
						<c:if test="${empty notice.noticeFileRename }">X</c:if>
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
							<c:url var="pageUrl" value="/notice/list.kh">
								<c:param name="page" value="${p }"></c:param>
							</c:url>
							<a href="${pageUrl }">${p }</a>&nbsp;
						</c:forEach>
					</td>
				<tr>
					<td colspan="4">
						<form action="/notice/search.kh" method="get">
							<select name="searchCondition">
								<option value="all">전체</option>
								<option value="writer">작성자</option>
								<option value="title">제목</option>
								<option value="content">내용</option>
							</select>
							<input type="text" name="searchKeyword" placeholder="검색어를 입력하세요">
							<input type="submit" value="검색">	
						</form>		
					</td>
					<td>
						<button type="button" onclick="showRegisterForm()">글쓰기</button>
					</td>	
				</tr>
			</tfoot>
		</table>
		<script>
			showRegisterForm = () => 
				location.href="/notice/insert.kh";
				
		</script>
	</body>
</html>