package com.gwl.view.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class DocFilter
 */
@WebFilter("/System/*")
public class DocFilter implements Filter {

    /**
     * Default constructor. 
     */
    public DocFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here

		// pass the request along the filter chain
		HttpServletRequest request_filter = (HttpServletRequest) request;
		HttpServletResponse response_filter = (HttpServletResponse) response;
		request_filter.setCharacterEncoding("UTF-8");
		response_filter.setContentType("text/html;charset=UTF-8");
		
		Object user = request_filter.getSession().getAttribute("LoginUser");
		
		String root_path = request_filter.getContextPath();
		String page_url = request_filter.getRequestURI();
		String page_suburl = page_url.substring(root_path.length());
		
		if (page_suburl.contains("UploadDocument") || 
			page_suburl.contains("DeleteDocument") ||
			page_suburl.contains("assword") ||
			page_suburl.contains("Logout")) {
			if (user != null) {
				chain.doFilter(request_filter, response_filter);
			} else {

				PrintWriter out = response.getWriter();
				out.write("<script>");
				out.write("alert('请先登录');");
				out.write("location.href='"+ root_path +"/Login.jsp';");
				out.write("</script>");
				
				return;
			}
			
		} else {
			chain.doFilter(request_filter, response_filter);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
