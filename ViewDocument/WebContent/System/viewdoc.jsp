<%@page import="com.gwl.view.dao.DocumentManage"%>
<%@page import="com.gwl.view.bean.Document"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="menu.jsp" %>

    <!--/sidebar-->
    <div class="main-wrap">

        <div class="crumb-wrap">
            <div class="crumb-list"><i class="icon-font"></i><a href="index.html">首页</a><span class="crumb-step">&gt;</span><span class="crumb-name">作品管理</span></div>
        </div>
        
        <div class="search-wrap">
            <div class="search-content">
                <form action="${pageContext.request.contextPath }/System/UploadDocument" method="post" enctype="multipart/form-data">
                    <table class="search-tab">
                        <tr>
                            <th width="120">上传文件:</th>
                            <td>
                                <input class="common-text" placeholder="关键字" name="keywords" value="" id="" type="file">
                            </td>
                            <td><input class="btn btn-primary btn2" name="sub" value="上传" type="submit"></td>
                            <td><span>注：上传的文件格式仅支持doc、docx、xls、xlsx、ppt、pptx，文件大小最大10M</span></td>
                            <td><span style="color:red">${docwrong}</span></td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
        
        <div class="search-wrap">
            <div class="search-content">
                <form action="${pageContext.request.contextPath }/System/ViewDocument" method="get">
                    <table class="search-tab">
                        <tr>
                            <th width="120">选择分类:</th>
                            <td>
                                <select name="type" id="">
                                    <option value="all">全部</option>
                                    <option value="doc">doc</option>
                                    <option value="docx">docx</option>
                                    <option value="xls">xls</option>
                                    <option value="xlsx">xlsx</option>
                                    <option value="ppt">ppt</option>
                                    <option value="pptx">pptx</option>
                                </select>
                            </td>
                            <th width="150">文件名称关键字:</th>
                            <td><input class="common-text" placeholder="关键字" name="keyword" value="" id="" type="text"></td>
                            <td><input class="btn btn-primary btn2" name="sub" value="查询" type="submit"></td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
        <div class="result-wrap">
            <form name="myform" id="myform" method="post" action="${pageContext.request.contextPath }/System/DeleteDocument?">
                <div class="result-title">
                    <div class="result-list">
                        <a id="batchDel" href="javascript:Delete_select('是否删除选中文件？', 'myform')"><i class="icon-font"></i>批量删除</a>
                    </div>
                </div>
                <div class="result-content">
                    <table class="result-tab" width="100%">
                        <tr>
                            <th class="tc" width="5%"><input class="allChoose" name="" type="checkbox" onclick="Selectall(this)"></th>
                            <th>编号</th>
                            <th>文件名称</th>
                            <th>文件类型</th>
                            <th>上传时间</th>
                            <th>下载次数</th>
                            <th>操作</th>
                        </tr>
                        <%
                        // 获取request域中的文件列表
                        ArrayList<Document> documents = (ArrayList<Document>) request.getAttribute("documents");
                        %>
                        <%
                        for (Document document : documents) { // 用foreach循环以此显示消息条数%>
                        <tr>
                            <td class="tc"><input name="ids" value="<%=document.getId() %>" type="checkbox"></td>
                            
                            <td><%=document.getId() %></td>
                            <td><%=document.getName() %></td>
                            <td><%=document.getType() %></td>
                            <td><%=document.getUploadTime() %></td>
                            <td><%=document.getDownloadTimes() %></td>
                            <td>
                                <a class="link-update" href="${pageContext.request.contextPath }/System/DownloadDocument?id=<%=document.getId() %>">下载</a>
                                <a class="link-del" href="javascript:Delete('是否删除此文件<%=document.getName() %>','${pageContext.request.contextPath }/System/DeleteDocument?id=<%=document.getId() %>')">删除</a>
                            </td>
                        </tr>
                        	
                        <%
                        }
                        %>
                        
                        <script>
                        //全选框
                        function Selectall(o) {
                    		var a =  document.getElementsByName('ids');
                    		for(var i = 0; i < a.length; i++) {
                    			a[i].checked = o.checked;
                    		}
                    	}
                        
                        //删除功能弹出提示框
                        function Delete(mess, url) {
                    		if (confirm(mess)) {
                    			location.href=url;
                    		}
                    	}
                        
                        //批量删除弹出提示框
                        function Delete_select(mess, formname) {
                    		if (confirm(mess)) {
                    			var form = document.getElementById(formname);
                    			form.submit();
                    		}
                    	}
                        </script>
                        
                    </table>
                    <div class="list-page">
                    	共有${totalList }条记录，当前第${cp}页，总共${totalPage}页
						<a href="ViewDocument?type=${type}&keyword=${keyword}&cp=1 ">首页</a>
						<a href="ViewDocument?type=${type}&keyword=${keyword}&cp=${cp - 1 < 1 ? 1 : cp - 1}">上一页</a>
						<a href="ViewDocument?type=${type}&keyword=${keyword}&cp=${cp + 1 > totalPage ? totalPage : cp + 1}">下一页</a>
						<a href="ViewDocument?type=${type}&keyword=${keyword}&cp=${totalPage}">尾页</a>
                    	
					</div>
                </div>
            </form>
        </div>
    </div>
    <!--/main-->
</div>
</body>
</html>