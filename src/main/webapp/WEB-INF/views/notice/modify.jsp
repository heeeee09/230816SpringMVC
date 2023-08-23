<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>공지사항 수정</title>
		<link rel="stylesheet" href="../resources/css/main.css">
	</head>
	<body>
		<h1>공지 수정</h1>
		<form action="/notice/modify.kh" method="post" enctype="multipart/form-data">
		<!-- return에서 경로를 타기 위해 noticeNo, 
			 파일 수정할 때 사용하기 위해 Name심기 
			 서버에서만 사용하고 사용자에게는 필요없는 정보이기 때문에 hidden으로 -->
			 <!-- file과 관련된 데이터를 입력해놓지 않으면 수정한 게 없이 수정을 누르면 부적합한 열유형 오류가 발생함 -->
			 <!-- 데이터를 null로 넣으려면 mybatis-config 수정 -->
			<input type="hidden" name="noticeNo" value="${notice.noticeNo }">
			<input type="hidden" name="noticeFileName" value="${notice.noticeFileName }">
			<input type="hidden" name="noticeFileRename" value="${notice.noticeFileRename }">
			<input type="hidden" name="noticeFilepath" value="${notice.noticeFilepath }">
			<input type="hidden" name="noticeFileLength" value="${notice.noticeFileLength }">
			<ul>
				<li>
					<label>제목</label>
					<input type="text" name="noticeSubject" value="${notice.noticeSubject }">
				</li>
				<li>
					<label>작성자</label>
					<input type="text" name="noticeWriter" value="${notice.noticeWriter }">
				</li>
				<li>
					<label>내용</label>
					<textarea rows="4" cols="50" name="noticeContent">${notice.noticeContent }</textarea>
				</li>
				<li>
					<label>첨부파일</label>
					<a href="../resources/nuploadFiles/${notice.noticeFileName }" download>${notice.noticeFileName }</a>
					<!-- 파일은 객체로 받기 때문에 필드의 이름과 같이 적지 않는다. -->
					<input type="file" name="uploadFile">
					<!-- Stirng으로 받을 수 없고 객체로 받아서 사용해야 함
						(파싱해야함 -> 분석해서 변환하는 작업이 필요)
						*** 오타 주의!!!!!! -->
					<button type="button" onclick="deleteFile()">파일 삭제</button>
				</li>
			</ul>
			<div>
				<input type="submit" value="등록">
			</div> 
<!-- 			<script>
 				deleteFile = () => location.href="/notice/deleteFile.kh";
	 			</script> -->
		</form>
	</body>
</html>