package com.gwl.view.dao.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class BaseDao {
	
	static {
		//加载驱动
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Connection getconn() {
		Connection conn = null;
		
		try {
			//链接数据库的默认语句
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/document?CharSet=utf8&useSSL=false&useUnicode=true&characterEncoding=utf-8&autoReconnect=true", "root", "123456");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	public static int exectuIUD(String sql, Object[] params) {
		int count = 0;
		
		Connection conn = BaseDao.getconn();
		
		//准备SQL
		PreparedStatement ps = null;
		
		//insert into xxx(xxx为表名)( , , , , ,.....) VALUE(?,?,?,?,?.....)(插入数据语句)
		try {
			ps = conn.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				ps.setObject(i+1, params[i]);
			}
			
			count = ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			BaseDao.closeall(null, ps, conn); //关闭数据库链接
		}
		
		return count;
	}
	
	public static void closeall(ResultSet rs, PreparedStatement ps, Connection conn) {
		
		try {
			if (rs!=null) {
				rs.close();
			}
			if (ps!=null) {
				ps.close();
			}
			if (conn!=null) {
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
	}
	
	public static void main(String[] args) {
		System.out.println(getconn());
	}
	
}
