<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" href="../resources/css/main.css">
		<link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap" rel="stylesheet">
		<title>게시글 수정</title>
	</head>
	<body>
		<h1>게시글 수정</h1>
		<form action="/board/modify.kh" method="post" enctype="multipart/form-data">
			<input type="hidden" name="boardNo" value="${board.boardNo }">
			<input type="hidden" name="boardFileName" value="${board.boardFileName }">
			<input type="hidden" name="boardFileRename" value="${board.boardFileRename }">
			<input type="hidden" name="boardFilePath" value="${board.boardFilePath }">
			<input type="hidden" name="boardFileLength" value="${board.boardFileLength }">			
			<ul>
				<li>
					<label>제목</label>
					<input type="text" name="boardTitle" value="${board.boardTitle }">
				</li>
				<li>
					<label>작성자</label>
					<p>${board.boardWriter }</p>
					<input type="hidden" name="boardWriter" value="${board.boardWriter }">
<%-- 					<input type="text" name="boardWriter"  value="${board.boardWriter }" readonly> --%>
				</li>
				<li>
					<label>내용</label>
					<textarea rows="4" cols="50" name="boardContent" >${board.boardContent }</textarea>
				</li>
				<li>
					<label>첨부파일</label>
					<a href="../resources/buploadFiles/${board.boardFileName }" download>${board.boardFileName }</a>
					<input type="file" name="uploadFile" value="${board.boardFileName }">
					<!-- Stirng으로 받을 수 없고 객체로 받아서 사용해야 함
						(파싱해야함 -> 분석해서 변환하는 작업이 필요)
						*** 오타 주의!!!!!! -->
				</li>
			</ul>
			<div>
				<input type="submit" value="수정">
				<button type="button" onclick = "showBoardList()" value="목록">목록</button>
				<button type="button" onclick = "javascript:history.go(-1);" value="뒤로가기">뒤로가기</button>
			</div> 
		</form>	
		<script type="text/javascript">
			showBoardList = () => {
				location.href = "/board/list.kh";
			}
		</script>
	</body>
</html>