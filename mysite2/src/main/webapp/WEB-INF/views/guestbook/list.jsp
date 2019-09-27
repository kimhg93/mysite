<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includes/jstl.jsp"%>
<%	pageContext.setAttribute("newline", "\n"); %>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${path }/assets/css/guestbook.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<form action="${path }/guestbook" method="post">
					<table>
						<tr>
							<td>이름</td><td><input type="text" name="name" value="${authUser.name }" required></td>
							<td>비밀번호</td><td><input type="password" name="password" required></td>
						</tr>
						<tr>
							<td colspan=4><textarea name="contents" id="content"></textarea></td>
						</tr>
						<tr>
							<td colspan=4 align=right><input type="submit" VALUE=" 확인 "></td>
						</tr>
					</table>
					<input type="hidden" name="a" value="add">
				</form>
				<ul>
					<c:set var="count" value="${fn:length(list) }"/>
					<c:forEach items="${list }" var="vo" varStatus="status">					
						<li>						
							<table>
								<tr>
									<td>${count-status.index }</td>
									<td>${vo.name }</td>
									<td>${vo.date }</td>
									<td><a href="${path }/guestbook?a=deleteform&no=${vo.no }">삭제</a></td>
								</tr>
								<tr>
									<td colspan=4>
										${fn:replace(vo.contents, newline, '<br>') }
									</td>
								</tr>
							</table>
							<br>
						</li>
					</c:forEach>
						<%-- <%=vo.getContents().replaceAll("\n", "<br>") %> --%>
				</ul>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>