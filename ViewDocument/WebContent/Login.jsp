<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>烟草工业培训部登录界面</title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resource/css/login.css"/>
	</head>
	<body>
		<div id="login">
			<h1>烟草工业培训部<br/>管理员登录</h2>
			<div class="login_border">
				<form action="${pageContext.request.contextPath }/Login" method="post" onsubmit="return type_submit()">
					<div class="login_input">
						<label for="userId">用户ID</label>
						<input type="text" name="user_id" id="userId" value="" />
						<label for="psw">密码</label>
						<input type="password" name="password" id="psw" value="" />
					</div>
						
					<div class="button">
						<button type="button" onclick="register()">注册</button>
						<input type="submit" value="登录"/>
					</div>	
				</form>
			</div>
		</div>
		<script>
		function register(){
			location.href="register.jsp";
		}
		function type_submit(){
			var psw = document.getElementById("psw");
			var id = document.getElementById("userId");

            if (id.value.length == 0){
            	alert("用户Id不能为空！");
            	return false;
            }
            if (psw.value.length == 0){
            	alert("密码不能为空！");
            	return false;
            }
		}
		</script>
	</body>
</html>
