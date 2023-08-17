<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>공지사항 등록</title>
		<link rel="stylesheet" href="../resources/css/main.css">
	</head>
	<body>
		<h1>공지 등록</h1>
		<form action="/notice/insert.kh" method="post" enctype="multipart/form-data">
			<ul>
				<li>
					<label>제목</label>
					<input type="text" name="noticeSubject">
				</li>
				<li>
					<label>작성자</label>
					<input type="text" name="noticeWriter">
				</li>
				<li>
					<label>내용</label>
					<textarea rows="4" cols="50" name="noticeContent"></textarea>
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