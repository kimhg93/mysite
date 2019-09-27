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
					<div class="bottom">
					<a href="${path }/board?a=list&kwd=${param.kwd}&page=${param.page }">글목록</a>
						<a href="${path }/board?a=writeform&no=${viewVo.no }">답글</a>
						<c:choose>
							<c:when test="${viewVo.userNo==authUser.no}">
								<a href="${path }/board?a=modifyform&no=${viewVo.no }">글수정</a>								
							</c:when>
							<c:otherwise>								
							
							</c:otherwise>
						</c:choose>
					</div>				
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>