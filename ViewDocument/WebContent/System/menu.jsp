<%@page import="com.gwl.view.bean.Admin"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>烟草工业培训部文件管理</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resource/css/common.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resource/css/main.css"/>
</head>
<body>
<div class="topbar-wrap white">
    <div class="topbar-inner clearfix">
        <div class="topbar-logo-wrap clearfix">
            <ul class="navbar-list clearfix">
                <li><a class="on" href="${pageContext.request.contextPath }/Index.jsp">首页</a></li>
                
<!--                 <li><a href="#" target="">网站首页</a></li> -->
            </ul>
        </div>
        <div class="top-info-wrap">
            <ul class="top-info-list clearfix">
            	<%
            	Admin user = (Admin)session.getAttribute("LoginUser");
            	if (user != null) {%>
            		<li><a href="${pageContext.request.contextPath }/System/updatepassword.jsp">修改密码</a></li>
            		<li><a href="${pageContext.request.contextPath }/System/Logout">退出登录</a></li>
            	<%	
            	} else {
            	%>
               		<li><a href="${pageContext.request.contextPath }/Login.jsp">登录界面</a></li>
                <%
                }
            	%>
            </ul>
        </div>
    </div>
</div>
<div class="container clearfix">
    <div class="sidebar-wrap">
        <div class="sidebar-title">
            <h1>菜单</h1>
        </div>
        <div class="sidebar-content">
            <ul class="sidebar-list">
                <li>
                    <a href="#"><i class="icon-font">&#xe003;</i>常用操作</a>
                    <ul class="sub-menu">
                        <li><a href="${pageContext.request.contextPath }/System/ViewDocument?type=all"><i class="icon-font">&#xe008;</i>文件管理</a></li>
                        
                    </ul>
                </li>
                <!-- <li>
                    <a href="#"><i class="icon-font">&#xe018;</i>系统管理</a>
                    <ul class="sub-menu">
                        <li><a href="system.html"><i class="icon-font">&#xe017;</i>系统设置</a></li>
                        
                    </ul>
                </li> -->
            </ul>
        </div>
    </div>