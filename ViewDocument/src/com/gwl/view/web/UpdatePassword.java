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
 * Servlet implementation class UpdatePassword
 */
@WebServlet("/System/UpdatePassword")
public class UpdatePassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdatePassword() {
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
		
		String password = request.getParameter("password");
		String newpassword = request.getParameter("newpassword");
		Admin user = (Admin) request.getSession().getAttribute("LoginUser");
		
		if (password.equals(user.getPassword())) {
			AdminManage.UpdatePasswordById(user.getUsername(), newpassword);
			response.sendRedirect("../Index.jsp");
		} else {
			PrintWriter out =response.getWriter();
			out.write("<script>");
			out.write("alert('原密码错误！');");
			out.write("location.href='"+ request.getContextPath() +"/System/updatepassword.jsp';" );
			out.write("</script>");
		}
		
	}

}
