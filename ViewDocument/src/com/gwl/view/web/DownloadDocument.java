package com.gwl.view.web;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gwl.view.dao.DocumentManage;

/**
 * Servlet implementation class DownloadDocument
 */
@WebServlet("/System/DownloadDocument")
public class DownloadDocument extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadDocument() {
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
		int id = Integer.valueOf(request.getParameter("id"));
		String filename = DocumentManage.GetNameById(id);
		
		/*
		 调整文件名的编码格式，以解决文件名乱码的问题
		 原因：浏览器解析只能用ISO8859-1的编码格式
		 步骤：1.打散为字节 2.按照目标的字符编码格式来重新组装字符
		 */
		// 1.打散：按当前的字符编码格式(UTF-8)进行打散
		// 2.组装：按目标字符编码格式组装
//		filename = new String(filename.getBytes("UTF-8"), "iso-8859-1");
		
		// 修改相应的头部属性content-disposition值为attachment,并修改文件名
		response.setHeader("content-disposition", "attachment;filename=" + java.net.URLEncoder.encode(filename, "UTF-8"));
		
		// 获取连接服务端资源文件的输入流
		InputStream is = this.getServletContext().getResourceAsStream("/System/document/" + filename);
		
		// 获取输出流
		ServletOutputStream os = response.getOutputStream();
		
		
		//将输入流中的数据写入到输出流中
		int len = -1;
		byte[] buf = new byte[1024];
		while ((len = is.read(buf)) != -1) {
			os.write(buf, 0, len);
		}
		
		//关闭流
		is.close();
		os.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
