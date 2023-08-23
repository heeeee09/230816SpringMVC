<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>공지사항 상세 조회</title>
		<link rel="stylesheet" href="../resources/css/main.css">
	</head>
	<body>
		<h1>공지사항 상세 조회</h1>
			<ul>
				<li>
					<label>제목</label>
					<span>${notice.noticeSubject }</span>
				</li>
				<li>
					<label>작성자</label>
					<span>${notice.noticeWriter }</span>
				</li>
				<li>
					<label>내용</label>
					<p>${notice.noticeContent }</p>
				</li>
				<li>
					<c:if test="${notice.noticeFileRename ne null }">
						<label>첨부파일</label>
						<!-- 이미지만 올리게 했으면 img로 할 수 있고
							 다양한 파일을 올렸으면 링크로 다운받을 수 있음 -->
	<%-- 					<img alt="첨부파일" src="${notice.noticeFilepath }"> --%>
						<img alt="첨부파일" src="../resources/nuploadFiles/${notice.noticeFileRename }">
						<!-- 파일이 DB에 절대경로로 저장되어 있기 때문에 Filepath가 아니라 name으로  -->
						<a href="../resources/nuploadFiles/${notice.noticeFileRename }" download>${notice.noticeFileName }</a>
					</c:if>
				</li>
			</ul>
			<div>
				<button type="button" onclick="showModifyPage();">수정하기</button>
				<button type="button" onclick="showNoticeList();">목록으로 이동</button>
			</div> 
		<script>
			function showModifyPage() {
				const noticeNo = "${notice.noticeNo }";
				location.href="/notice/modify.kh?noticeNo="+noticeNo;
			}
			function showNoticeList(){
				location.href = "/notice/list.kh";
			}
		</script>
	</body>
</html>