<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>게시글 상세 조회</title>
		<link rel="stylesheet" href="../resources/css/main.css">
	</head>
	<body>
		<h1>게시글 상세 조회</h1>
			<ul>
				<li>
					<label>제목</label>
					<span>${board.boardTitle }</span>
				</li>
				<li>
					<label>작성자</label>
					<span>${board.boardWriter }</span>
				</li>
				<li>
					<label>내용</label>
					<p>${board.boardContent }</p>
				</li>
				<li>
					<c:if test="${board.boardFileRename ne null }">
						<label>첨부파일</label>
						<!-- 이미지만 올리게 했으면 img로 할 수 있고
							 다양한 파일을 올렸으면 링크로 다운받을 수 있음 -->
	<%-- 					<img alt="첨부파일" src="${notice.noticeFilepath }"> --%>
						<img alt="첨부파일" src="../resources/nuploadFiles/${board.boardFileRename }">
						<!-- 파일이 DB에 절대경로로 저장되어 있기 때문에 Filepath가 아니라 name으로  -->
						<a href="../resources/buploadFiles/${board.boardFileRename }" download>${board.boardFileName }</a>
					</c:if>
				</li>
			</ul>
			<div>
				<button type="button" onclick="showModifyPage();">수정하기</button>
				<button type="button" onclick="showNoticeList();">목록으로 이동</button>
			</div> 
			<!-- 댓글 등록 -->
			<form action="/board/addReply.kh" method="post">
				<table>
					<tr>
						<td>
							<textarea rows="3" cols="55"></textarea>
						</td>
						<td>
							<input type="submit" value="완료">
						</td>
					</tr>
				</table>
			</form>
			<!-- 댓글 목록 -->
			<table width="500" border="1">
				<tr>
					<td>일용자</td>
					<td>아 처음이시군요 환영합니다.</td>
					<td>2023-08-24</td>
					<td>
						<a href="#">수정하기</a>
						<a href="#">삭제하기</a>
					</td>
				</tr>
			</table>
		<script>
			function showModifyPage() {
				const noticeNo = "${board.boardNo }";
				location.href="/board/modify.kh?boardNo="+boardNo;
			}
			function showNoticeList(){
				location.href = "/board/list.kh";
			}
		</script>
	</body>
</html>