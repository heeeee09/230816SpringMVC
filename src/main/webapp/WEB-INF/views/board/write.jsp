<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" href="../resources/css/main.css">
		<link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap" rel="stylesheet">
		<title>게시글 등록</title>
	</head>
	<body>
		<h1>게시글 등록</h1>
		<form action="/board/write.kh" method="post" enctype="multipart/form-data">
			<ul>
				<li>
					<label>제목</label>
					<input type="text" name="boardTitle">
				</li>
				<li>
					<label>작성자</label>
<%-- 					<input type="text" name="boardWriter" value="${memberId }" readonly> --%>
					<span>${memberId }</span>
				</li>
				<li>
					<label>내용</label>
					<textarea rows="4" cols="50" name="boardContent"></textarea>
				</li>
				<li>
					<label>첨부파일</label>
					<input type="file" name="uploadFile">
					<!-- Stirng으로 받을 수 없고 객체로 받아서 사용해야 함
						(파싱해야함 -> 분석해서 변환하는 작업이 필요)
						*** 오타 주의!!!!!! -->
				</li>
			</ul>
			<div>
				<input type="submit" value="등록">
			</div> 
		</form>	
	</body>
</html>