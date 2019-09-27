<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includes/jstl.jsp"%>
		<div id="navigation">
			<ul>
				<li><a href="${path }">메인</a></li>
				<li><a href="${path }/guestbook">방명록</a></li>
				<li><a href="${path }/board?page=1">게시판</a></li>
			</ul>
		</div>