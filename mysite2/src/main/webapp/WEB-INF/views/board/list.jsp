<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includes/jstl.jsp"%>
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
			<div id="board">
				<form id="search_form" action="${path }/board?a=list&page=1" method="post">
					<input type="text" id="kwd" name="kwd" value="">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>				
					<c:forEach items="${list }" var="vo" varStatus="status">	
					<tr>
						<td>${countAll-status.index }</td>
						<td style="padding-left:${30*vo.depth-20}px;text-align:left">
							<c:if test="${vo.depth > 0 }">
								<img src="${path }/assets/images/reply.png"/>
							</c:if>							
							<c:choose>
								<c:when test="${vo.removed==false}">
									<a href="${path }/board?a=view&no=${vo.no }&kwd=${param.kwd }&page=${param.page }">${vo.title }</a>							
								</c:when>								
								<c:when test="${vo.removed==true}">								
									<span>삭제된 글</span>	
								</c:when>								
							</c:choose>							
						</td>
						<td>${vo.userName }</td>
						<td>${vo.hit }</td>
						<td>${vo.regDate }</td>
						<td><a href="${path }/board?a=delete&no=${vo.no }&uno=${vo.userNo}" class="del">삭제</a></td>
					</tr>
					</c:forEach>	
				</table>
				
				<!-- pager 추가 -->
				<div class="pager">
					<ul>
						<c:if test="${param.page-5>=1 }">
							<li><a href="${path }/board?a=list&page=${startPage-1}&move=prev&kwd=${param.kwd}">◀</a></li>
						</c:if>						
						<c:forEach begin="${startPage }" end="${lastPage }" step="1" var="i">
							<c:choose>
								<c:when test="${param.page==i }">
									<li><span style="font-size:16px">${i }</span></li>
								</c:when>	
								<c:otherwise>
									<li><a href="${path }/board?a=list&page=${i }&kwd=${param.kwd}">${i }</a></li>
								</c:otherwise>
							</c:choose>								
						</c:forEach>
						<c:if test="${pageAll >= startPage+5 }">
							<li><a href="${path }/board?a=list&page=${lastPage+1 }&move=next&kwd=${param.kwd}">▶</a></li>
						</c:if>
						
					</ul>
				</div>					
				<!-- pager 추가 -->
				
				<div class="bottom">
					<c:if test="${authUser.no!=null}">
						<a href="${path }/board?a=writeform" id="new-book">글쓰기</a>
					</c:if>
				</div>				
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>