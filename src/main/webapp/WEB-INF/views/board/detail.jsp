<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>게시글 상세 조회</title>
		<link rel="stylesheet" href="../resources/css/main.css">
		<link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap" rel="stylesheet">		
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
			<form action="/reply/add.kh" method="post">
				<input type="hidden" name="refBoardNo" value="${board.boardNo }" >
				<table width="500" border="1">
					<tr>
						<td>
							<textarea rows="3" cols="55" name="replyContent"></textarea>
						</td>
						<td>
							<input type="submit" value="완료">
						</td>
					</tr>
				</table>
			</form>
			<!-- 댓글 목록 -->

			<!-- forEach
				 items = 컨트롤러에서 보낸 값
				 var : items를 쓸 수 있는 변수
			 -->

			<table width="500" border="1">
				<c:forEach var="reply" items="${rList }">
					<tr>
						<td>${reply.replyWriter }</td>
						<td>${reply.replyContent }</td>
						<td>${reply.rCreateDate }</td>
						<td>
						<!-- 함수에 this를 넣어 obj를 넘겨준다.
							 obj.parentElement.parentElement.nextElementSibling.
							 : 		부모의		  부모의		다음
							 : 		(td)			(tr)		(다음 tr)
							 -->
							 <!-- 댓글 내용을 전달값으로 보내기, '' -> 오류방지 -->
<%-- 							<a href="javascript:void(0)" onclick="showModifyForm(this, '${reply.replyContent}')" >수정하기</a> --%>
							<a href="javascript:void(0)" onclick="showModifyForm(this)">수정하기</a>
							<a href="#">삭제하기</a>
						</td>
					</tr>
					<!-- 1. html로 해보기 -->
					<tr id="replyModifyForm" style="display: none">
<!-- 						<form action="/reply/update.kh" method="post"> -->
<%-- 							<input type="hidden" name="replyNo" value="${reply.replyNo }"> --%>
<%-- 							<input type="hidden" name="refBoardNo" value="${reply.refBoardNo }"> --%>
<%-- 							<td colspan="3" ><input type="text" size="50" name="replyContent" value="${reply.replyContent }"></td> --%>
<!-- 							<td><input type="submit" value="완료"></td> -->
<!-- 						</form> -->

<!-- 						JS로 테이블 쌓기 -->
<!-- 							<input type="hidden" name="replyNo"> -->
<!-- 							<input type="hidden" name="refBoardNo"> -->
							<!-- 실제로 가져오는 값이어서 꼭 있어야 함 -->
							<td colspan="3" ><input id="replyContent" type="text" size="50" name="replyContent" value="${reply.replyContent }"></td>
							<!-- 함수에 매개변수 넘겼는지 확인 -->
							${reply.replyNo}
							<td><input type="button" onclick="replyModify('this, ${reply.replyNo}, ${reply.refBoardNo }')" value="완료"></td>
					</tr>
				</c:forEach>
			</table>
		<script>
			function showModifyPage() {
				const noticeNo = "${board.boardNo }";
				location.href="/board/modify.kh?boardNo="+boardNo;
			}
			function showNoticeList(){
				location.href = "/board/list.kh";
			}
			
			function showModifyForm(obj, replyContent){

			// #1. html태그, display:none 사용하는 방법
//	 		// obj의 부모의    부모의		다음
	 		obj.parentElement.parentElement.nextElementSibling.style.display="";
			}
			
			function replyModify(obj, replyNo, refBoardNo){
				// 폼태그 만들기
				const formTag = document.createElement("form");
				formTag.action="/reply/update.kh";
				formTag.method="post";
				// 숨은 인풋태그 2개 만들기(replyNo, refBoardNo)
				const hInput1 = document.createElement("input");
				hInput1.type="hidden";
				hInput1.name="replyNo";
				hInput1.value=replyNo;
				const hInput2 = document.createElement("input");
				hInput2.type="hidden";
				hInput2.value=refBoardNo;
				hInput2.name="refBoardNo";

				// 수정할 것 입력하는 input 만들기(replyContent를 가지고 있어야 함)
				const inputTag = document.createElement("input");
				inputTag.type="text";
				inputTag.size="50";
				inputTag.name="replyContent";
				// 여기 this로 수정하기...
				inputTag.value=document.querySelector("#replyContent").value;
				formTag.appendChild(hInput1);
				formTag.appendChild(hInput2);
				formTag.appendChild(inputTag);
				
				console.log(formTag);
				
				document.body.appendChild(formTag);
// 				formTag.submit();				
				
// 				tdTag1.appendChild(inputTag);
// 				// input태그를 가진 td 만들기
// 				const tdTag1 = document.createElement("td");
// 				tdTag1.colspan="3";
// 				// 버튼 넣을 td 만들기
// 				const tdTag2 = document.createElement("td");
// 				// submit할 input 만들기
// 				const submitTag = document.createElement("input");
// 				submitTag.type="submit";
// 				value="수정";
// 				tdTag2.appendChild(submitTag);
				
// 				// 폼태그 넣을 tr 태그 넣기
// 				const trTag = document.createElement("tr");
// 				trTag.appendChild(formTag);
// 				console.log(trTag);
				
				
				
// 			// #2. DOM프로그래밍을 이용하는 방법
// // 			<tr id="replyModifyForm" style="display: none">
// // 				<td colspan="3" ><input type="text" size="50" value="${reply.replyContent }"></td>
// // 				<td><input type="button" value="완료"></td>
// // 			</tr>			
// //			ㄴ위의 HTML을 돔프로그래밍으로 만들기
// 			const trTag = document.createElement("tr");
// 			const tdTag1 = document.createElement("td");
// 			tdTag1.colSpan = 3;
// 			const inputTag1 = document.createElement("input");
// 			inputTag1.type="text";
// 			inputTag1.size=50;
// 			inputTag1.value=replyContent;
// 			tdTag1.appendChild(inputTag1);	// tdTag1에 자식을 만듦
// 			const tdTag2 = document.createElement("td");
// 			const inputTag2 = document.createElement("input");
// 			inputTag2.type="button";
// 			inputTag2.value="완료";
// 			tdTag2.appendChild(inputTag2);	// tdTag2에 자식 만듦
// 			// 최종으로 만든 것(trTag)
// 			trTag.appendChild(tdTag1);
// 			trTag.appendChild(tdTag2);
// 			console.log(trTag);
			// 만든 것(trTag)을 추가하기 
			// 클릭한 a를 포함하고 있는 tr 다음에 수정폼이 있는 tr 추가하기
			// prevTrTag의 위치는 obj의(function의 위치) 부모의 부모
// 			const prevTrTag = obj.parentElement.parentElement;
// // 			// insertBefore() : 부모노드의 기준 점 노드 앞에 삽입할 노드를 넣는다.
// // 			// 								(삽입할 노드, 기준 점 노드)
// // 			prevTrTag.parentNode.insertBefore(trTag, prevTrTag.nextSibling);
			
// 			if(prevTrTag.nextElementSibling == null || !prevTrTag.nextElementSibling.querySelector("input")){
// 				prevTrTag.parentNode.insertBefore(trTag, prevTrTag.nextSibling);
// 			}
			}
		</script>
	</body>
</html>