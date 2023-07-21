package com.gwl.view.web;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gwl.view.bean.Document;
import com.gwl.view.dao.DocumentManage;

/**
 * Servlet implementation class ViewDocument
 */
@WebServlet("/System/ViewDocument")
public class ViewDocument extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static int page_count = 5;
    private static int current_page = 1;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewDocument() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//修改字符编码
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		String keyword = request.getParameter("keyword");  //获取搜索关键词
		String cp = request.getParameter("cp");  //获取当前页码
		String type = request.getParameter("type");  //获取搜索类别
		
		if (keyword == null) {
			keyword = "";  //判断关键字是否为null，若为null则改为空字符串
		}
		
		if (cp!=null) {
			current_page = Integer.parseInt(cp);  //将页码从String类型转换为int类型
		}
		
		//调用方法，获取数据库的记录
		ArrayList<Document> documents = DocumentManage.SelectAllDocument(type, keyword, current_page, page_count);
		//调用方法，获取总页数和记录条数
		int arr[] = DocumentManage.GetTotalPage(type, keyword, current_page, page_count);
		
		request.setAttribute("documents", documents); //将文件列表存储到request域中
		request.setAttribute("totalList" , arr[0]); //将记录总条数存储到request域中
		request.setAttribute("totalPage" , arr[1]); //将总页数存储到request域中
		request.setAttribute("cp" , current_page); //将当前页码数存储到request域中
		request.setAttribute("keyword", keyword); //将文件列表存储到request域中
		request.setAttribute("type", type); //将文件列表存储到request域中
		//通过转发跳转到jsp页面
		request.getRequestDispatcher("viewdoc.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
