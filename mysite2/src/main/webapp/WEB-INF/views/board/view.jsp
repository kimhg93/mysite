<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includes/jstl.jsp"%>
<%	pageContext.setAttribute("newline", "\n"); %>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${path }/assets/css/board.css" rel="stylesheet" type="text/css">
<style>
	.reply {
		width:100%;
	}
	.reply tr{
		height:30px;
	}
	.reply td{
		border-bottom:1px solid #c3c3c3;
		
	}
	.reply_tlt{
		border-bottom:2px dotted black;
		padding:0 0 5px 20px;
		margin-bottom:2px;
	}
</style>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="board" class="board-form">
				<table class="tbl-ex">
					<tr>
						<th colspan="2">글보기</th>
					</tr>
					<tr>
						<td class="label">제목</td>
						<td>${viewVo.title }</td>
					</tr>
					<tr>
						<td class="label">내용</td>
						<td>
							<div class="view-content">
								${fn:replace(viewVo.contents, newline, '<br>') }
							</div>
						</td>
					</tr>
				</table>	
				
				<h3 class="reply_tlt">댓글</h3>
					<table class="reply" cellspacing="0">
					<c:if test="${viewVo.userNo==authUser.no}">
						<form name="reply" method="post" action="${path}/board">
						<tr>							
							<td align="center">
								${authUser.name }
								<input type="hidden" name="a" value="reply">
								<input type="hidden" name="userNo" value="${authUser.no }">
								<input type="hidden" name="boardNo" value="${param.no }">	
								<input type="hidden" name="keyWord" value="${param.kwd}">
								<input type="hidden" name="page" value="${param.page}">
							</td>
							<td colspan="2"><textarea name="contents" style="width:90%;resize:none"></textarea></td>
							<td><input type="submit" value="등록"></td>	
												
						</tr>
						</form>
					</c:if>
						<c:forEach items="${reply }" var="replyVo" varStatus="status">
						<tr>
							<td width="10%" align="center">${replyVo.userName }</td>
							<td width="60%">${fn:replace(replyVo.contents, newline, '<br>') }</td>
							<td width="20%">${replyVo.regDate }</td>
							<td width="10%">
								<form name="deleteReply" method="post" action="${path}/board">
									<input type="hidden" name="a" value="deleteReply">
									<input type="hidden" name="boardNo" value="${param.no }">	
									<input type="hidden" name="keyWord" value="${param.kwd}">
									<input type="hidden" name="page" value="${param.page}">
									<input type="hidden" name="replyNo" value="${replyVo.no}">
									<input type="hidden" name="userNo" value="${replyVo.userNo }">
									<c:if test="${replyVo.userNo==authUser.no}">
									<input type="submit" class="del" value="삭제" style="padding:2px">
									</c:if>
								</form>
								
							</td>
						</tr>
						</c:forEach>
					</table>			
					<div class="bottom">
					<a href="${path }/board?a=list&kwd=${param.kwd}&page=${param.page }">글목록</a>
						<c:if test="${authUser.no!=null}">
							<a href="${path }/board?a=writeform&no=${viewVo.no }">답글</a>
						</c:if>
						<c:if test="${viewVo.userNo==authUser.no}">
							<a href="${path }/board?a=modifyform&no=${viewVo.no }">글수정</a>								
						</c:if>
					</div>				
			</div>
			
		</div>
		
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>