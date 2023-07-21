package com.gwl.view.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.gwl.view.bean.Document;
import com.gwl.view.dao.DocumentManage;

/**
 * Servlet implementation class UploadDocument
 */
@WebServlet("/System/UploadDocument")
public class UploadDocument extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadDocument() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//修改字符编码
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		//创建文件名和文件类型的字符串
		String filename = null; String filetype = null;
		
		try {
			//创建一个FileItem处理工厂
			DiskFileItemFactory factory = new DiskFileItemFactory();
			//创建文件上传核心组件
			ServletFileUpload upload = new ServletFileUpload(factory);
			//设置单个上传文件的最大文件大小(单位为字节)，最大为10M
			upload.setFileSizeMax(1024 * 1024 * 10);
			//设置每一个item的头部字符编码，其可以解决文件名的中文乱码问题
			upload.setHeaderEncoding("UTF-8");
			//解析文件上传的请求,获取到所有的所用的item
			List<FileItem> items = upload.parseRequest(request);
			//遍历items
			for (FileItem files : items) { 
				if (files.isFormField()) { //判断item是否为普通表单项,是则返回true
				} else {
					String originfilename = files.getName(); //获取上传文件的原始文件名
					if (originfilename.equals("")) { //判断是否上传了文件
						request.setAttribute("docwrong", "请上传文件");
						request.getRequestDispatcher("ViewDocument?type="+"all").forward(request, response);
						return;
					} else if (DocumentManage.NameRepeatOrNot(originfilename)) { //调用方法，判断文件名是否重复
						request.setAttribute("docwrong", "文件名重复");
						request.getRequestDispatcher("ViewDocument?type="+"all").forward(request, response);
						return;
					} else {
						filetype = originfilename.substring(originfilename.lastIndexOf(".") + 1);
						//判断文件格式是否符合要求
						if (!(filetype.equals("doc") || filetype.equals("docx") || filetype.equals("xls") || filetype.equals("xlsx") || filetype.equals("ppt") || filetype.equals("pptx"))) {
							request.setAttribute("docwrong", "不支持该文件格式");
							request.getRequestDispatcher("ViewDocument?type="+"all").forward(request, response);
							return;
						}
						InputStream input = files.getInputStream(); //获取输入流,其中有上传文件的内容
						String path = this.getServletContext().getRealPath("System/document" ); //获取文件保存在服务器中的路径
						String eclipsepath = "E:\\Eclipse workspace\\ViewDocument\\WebContent\\System\\document\\".replace("/", "\\");
						
						File aimfile = new File(path, originfilename); //创建目标文件，用于保存上传文件
						File eclipsefile = new File(eclipsepath, originfilename);
						OutputStream output1 = new FileOutputStream(aimfile); //获取输出流
						OutputStream output2 = new FileOutputStream(eclipsefile);

						//将输入流写入到输出流中
						int len = -1;
						byte[] buf = new byte[1024];
						while ((len = input.read(buf)) != -1) {
							output1.write(buf, 0, len);
							output2.write(buf, 0, len);
						}
						
						//关闭流
						input.close();
						output1.close();
						output2.close();
						filename = originfilename; //获取文件名
					}
				}
			}
		
		} catch (FileUploadBase.FileSizeLimitExceededException e) { //若单个文件大小超出限制所抛出的异常
			request.setAttribute("docwrong", "文件大小超过规定范围");
			request.getRequestDispatcher("ViewDocument?type="+"all").forward(request, response);
			return;
		} catch (FileUploadException e) {
			e.printStackTrace();
		}

		Document document = new Document(0, filename, filetype, new Date(), 0); //创建文件对象
		int count = DocumentManage.InsertDocument(document); //调用方法，将文件对象插入到数据库中
		if (count == 1) {
			response.sendRedirect("ViewDocument?type="+"all"); //通过重定向跳转到jsp页面
		}
	}

}
