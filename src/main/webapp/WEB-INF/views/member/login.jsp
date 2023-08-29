<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" href="../resources/css/main.css">
		<link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap" rel="stylesheet">
		<title>로그인</title>
	</head>
	<body>
		<form action="/member/login.kh" method="post">
			<fieldset>
				<legend>로그인</legend>
				<ul>
					<li>
						<label>아이디</label>
						<input type="text" name="memberId">
					</li>
					<li>
						<label>비밀번호</label>
						<input type="password" name="memberPw">
					</li>
				</ul>
			</fieldset>
			<button type="submit" value="로그인">로그인</button>
		</form>
	
	</body>
</html>