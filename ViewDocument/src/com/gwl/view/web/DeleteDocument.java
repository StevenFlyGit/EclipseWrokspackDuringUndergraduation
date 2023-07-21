package com.gwl.view.web;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gwl.view.dao.DocumentManage;

/** 删除文件的Servlet
 * Servlet implementation class DeleteServlet
 */
@WebServlet("/System/DeleteDocument")
public class DeleteDocument extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteDocument() {
        super();
        // TODO Auto-generated constructor stub
    }

	/** get方法，用于单条数据删除
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//修改字符编码
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		//获取需要删除的文件id
		int id = Integer.valueOf(request.getParameter("id"));
		//获取需要删除的文件名
		String filename = DocumentManage.GetNameById(id);
		//获取需要删除的文件路径
		String fileURL = this.getServletContext().getRealPath("/System/document/"+ filename);
		
		
		File file = new File(fileURL); //创建文件
		if (file.exists()) {
			file.delete(); //删除文件
		}
		
		int count = DocumentManage.DeleteDocument(id); //调用方法，删除数据库的记录
		if (count == 1) {
			response.sendRedirect("ViewDocument?type="+"all"); //重定向到查询界面
		}
		
		
		
	}

	/** post方法，用于多条数据删除
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//修改字符编码
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		//获取需要删除的所有文件id
		String ids[] = request.getParameterValues("ids");
		
		int judge = 1;
		for (int i = 0; i < ids.length; i++) {
			int id = Integer.valueOf(ids[i]);
			String filename = DocumentManage.GetNameById(id); //获取文件名
			//获取文件地址
			String fileURL = this.getServletContext().getRealPath("/System/document/"+ filename);
			
			File file = new File(fileURL); //创建文件
			if (file.exists()) {
				file.delete(); //删除文件
			}
			
			int count = DocumentManage.DeleteDocument(id);  //调用方法，删除数据库的记录
			judge = judge * count;  //成功删除一条记录则返回值为1，否则为0，全部相乘为1则全部删除成功
		}
		
		if (judge != 0) {  //判断是否删除成功
			response.sendRedirect("ViewDocument?type="+"all"); //重定向到查询界面
		}
	}

}
