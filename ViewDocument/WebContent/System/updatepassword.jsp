<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>密码修改</title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resource/css/login.css"/>
	</head>
	<body>
		<div id="login">
			<h1>密码修改</h2>
			<div class="login_border">
				<form action="${pageContext.request.contextPath }/System/UpdatePassword" method="post" onsubmit="return type_submit()">
					<div class="login_input">
						<label for="userId">原密码</label>
						<input type="password" name="password" id="password" value="" />
						<label for="psw">新密码</label>
						<input type="password" name="newpassword" id="newpassword" value="" />
						<label for="psw">重复新密码</label>
						<input type="password" name="newpasswords" id="newpasswords" value="" />
					</div>
						
					<div class="button">
						<button type="button" onclick="register()">返回</button>
						<input type="submit" value="确认"/>
					</div>	
				</form>
			</div>
		</div>
		<script>
		function register(){
			location.href="register.jsp";
		}
		function type_submit(){
			var password = document.getElementById("password");
			var newpassword = document.getElementById("newpassword");
			var newpasswords = document.getElementById("newpasswords");

            if (password.value.length == 0){
            	alert("原密码不能为空！");
            	return false;
            }
            if (newpassword.value.length == 0){
            	alert("新密码不能为空！");
            	return false;
            }
            if (newpassword.value.length > 18){
            	alert("密码长度不能超过18位！");
            	return false;
            }
            if (newpasswords.value.length == 0){
            	alert("重复新密码不能为空！");
            	return false;
            }
            if (newpasswords.value != newpassword.value){
            	alert("两次输入的新密码不同！");
            	return false;
            }
		}
		</script>
	</body>
</html>
