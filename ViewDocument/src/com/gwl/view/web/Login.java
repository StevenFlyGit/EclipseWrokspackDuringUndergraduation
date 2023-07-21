package com.gwl.view.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gwl.view.bean.Admin;
import com.gwl.view.dao.AdminManage;


/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
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
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		Object usercheck = request.getSession().getAttribute("LoginUser");
		
		if (usercheck != null) {
			PrintWriter out = response.getWriter();
			out.write("<script>");
			out.write("alert('您已登录，若需更换用户请先点击退出系统！');");
			out.write("location.href='index_superadministrator.jsp';");
			out.write("</script>");
		} else {
			String id = request.getParameter("user_id");
			String password = request.getParameter("password");
			String realpassword = AdminManage.SelectPasswordByUsername(id);
			if (realpassword.equals(password)) {
				Admin user = AdminManage.SelectAdminByPassword(password);
				request.getSession().setAttribute("LoginUser", user);
				request.getRequestDispatcher("Index.jsp").forward(request, response);
			} else {
				PrintWriter out = response.getWriter();
				out.write("<script>");
				out.write("alert('密码或账号错误，请重新输入！');");
				out.write("location.href='Login.jsp';");
				out.write("</script>");
			}
		}
	}

}
