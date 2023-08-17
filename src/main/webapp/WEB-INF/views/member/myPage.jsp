<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>        
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" href="/resources/css/main.css">
		<title>마이페이지</title>
	</head>
	<body>
		<form action="/member/myPage.kh" method="get">
			<fieldset>
				<legend>마이페이지</legend>
				<ul>
					<li>
						<label>아이디 </label>
						<span>${memberId }</span>
					</li>
					<li>
						<label>이름 *</label>
						<span>${member.memberName }</span>
					</li>
					<li>
						<label>나이</label>
						<span>${member.memberAge }</span>
					</li>
					<li>
						<label>성별</label>
						<span>${member.memberGender }</span>
					</li>
					<li>
						<label>이메일</label>
						<span>${member.memberEmail }</span>
					</li>
					<li>
						<label>전화번호</label>
						<span>${member.memberPhone }</span>
					</li>
					<li>
						<label>주소</label>
						<span>${member.memberAddress}</span>
					</li>
					<li>
						<label>취미</label>
						<span>${member.memberHobby }</span>
					</li>
				</ul>
			</fieldset>
			<div>
				<button><a href="/member/modify.kh?memberId=${memberId }">수정</a></button>
				<button><a href="/member/delete.kh?memberId=${memberId }">탈퇴</a></button>
			</div>
		</form>
		<script>
		
		</script>
	</body>
</html>