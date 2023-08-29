<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" href="../resources/css/main.css">
		<link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap" rel="stylesheet">
		<title>회원 정보 수정</title>
	</head>
	<body>
		<form action="/member/modify.kh" method="post">
			<fieldset>
				<legend>회원 정보 수정</legend>
				<ul>
					<li>
						<label>아이디 *</label>
						<input type="text" name="memberId" value="${memberId }" readonly>
					</li>
					<li>
						<label>비밀번호 *</label>
						<input type="text" name="memberPw">
					</li>
					<li>
						<label>이름 *</label>
						<input type="text" name="memberName" value="${member.memberName }" readonly>
					</li>
					<li>
						<label>나이</label>
						<input type="text" name="memberAge" value="${member.memberAge }" readonly>
					</li>
					<li>
						<label>성별</label>
						<c:if test="${member.memberGender eq 'M' }">남</c:if>
						<c:if test="${member.memberGender eq 'F' }">여</c:if>
					</li>
					<li>
						<label>이메일</label>
						<input type="text" name="memberEmail" value="${member.memberEmail }">
					</li>
					<li>
						<label>전화번호</label>
						<input type="text" name="memberPhone" value="${member.memberPhone }">
					</li>
					<li>
						<label>주소</label>
						<input type="text" id="memberAddr" name="memberAddress" value="${member.memberAddress}">
						<input type="button" onclick="sample4_execDaumPostcode()"value="우편번호 찾기">
					</li>
					<li>
						<label>취미</label>
						<input type="text" name="memberHobby" value="${member.memberHobby }">
					</li>
				</ul>
			</fieldset>
			<div>
				<input type="submit" value="수정">
			</div>
		</form>
		<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
		<script>
			function sample4_execDaumPostcode() {
				// 객체 만들고       창을 띄움 => API 실행
				new daum.Postcode({
					oncomplete : function(data){
						document.querySelector("#memberAddr").value="("+data.zonecode + ") "+ data.autoJibunAddress +", "+data.buildingName;
					}					
				}).open();
				
			}
		</script>
	</body>
</html>