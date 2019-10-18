<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ include file="/WEB-INF/views/includes/jstl.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${path }/assets/css/user.css" rel="stylesheet" type="text/css">
<script src="${path }/assets/js/jquery/jquery-1.9.0.js" type="text/javascript"></script>
<script>
	$(function(){
		$("#input-email").change(function(){
			$("#btn-check-email").show();
			$("#img-check").hide();
		})
		
		var $btnCheckEamil = $("#btn-check-email"); 
		$btnCheckEamil.click(function(){
			var email = $("#input-email").val();
			if(email == ""){
				return;
			}
			
			// ajax 통신
			$.ajax({
				url: "/mysite3/api/user/checkemail?email="+email,
				type: "get",
				dataType: "json",
				data: "",
				success: function(response){
					if(response.result == "fail"){
						console.error(response.message);
						return;
					}
					
					if(response.data == true){
						alert("이미 존재하는 메일 입니다.");
						$("#input-email").val("");
						$("#input-email").focus();
						return;
					} 
					console.log("사용 가능합니다.");
					$("#btn-check-email").hide();
					$("#img-check").show();
					
					
				},
				error : function(xhr, error){
					
				}
			
			});
		})
	});
	
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="user">

				<form:form modelAttribute="userVo" id="join-form" name="joinForm" method="post" action="${path }/user/join">
					<input type="hidden" name="a" value="join" />
					<label class="block-label" for="name">이름</label>
					<form:input path="name" />
					<spring:hasBindErrors name="userVo">
						<c:if test='${errors.hasFieldErrors("name") }'>
							<p style="font-weight:bold; color:red; text-align:left; padding-left:0">
								<spring:message code='${errors.getFieldError("name").codes[0] }' text='${errors.getFieldError("name").defaultMessage }' />
							</p>
						</c:if>
					</spring:hasBindErrors>
					
					<label class="block-label" for="email">이메일</label>
					<form:input path="email" />
					<input id="btn-check-email" type="button" value="중복확인">
					<img id="img-checked" style='width:20px; display:none' src='${pageContext.servletContext.contextPath }/assets/images/check.png'/>
					<p style="font-weight:bold; color:red; text-align:left; padding:2px 0 0 0">
						<form:errors path="email" />
					</p>	
					
					<label class="block-label">패스워드</label>
					<form:input path="password"/>
					
					<fieldset>
						<legend>성별</legend>
						<label>여</label> <input type="radio" name="gender" value="female" checked="checked">
						<label>남</label> <input type="radio" name="gender" value="male">
					</fieldset>
					
		
					
					<fieldset>
						<legend>약관동의</legend>
						<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
						<label>서비스 약관에 동의합니다.</label>
					</fieldset>
					
					<input type="submit" value="가입하기">					
				</form:form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>