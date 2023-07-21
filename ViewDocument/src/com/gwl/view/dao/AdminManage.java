package com.gwl.view.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.gwl.view.bean.Admin;
import com.gwl.view.dao.jdbc.BaseDao;

public class AdminManage {

	
	public static String SelectPasswordByUsername(String username) {
		String password = "";
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection conn = BaseDao.getconn();
		String sql = "SELECT password FROM document.admin where username = ?;";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			rs = ps.executeQuery();
			while (rs.next()) {
				password = rs.getString("password");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			BaseDao.closeall(rs, ps, conn);
		}
		
		return password;
	}
	
	public static int UpdatePasswordById(String username, String newpassword) {
		String sql = "UPDATE document.admin SET password=? WHERE username=?;";
		Object[] params = {newpassword, username};
		return BaseDao.exectuIUD(sql, params);
	}
	
	public static Admin SelectAdminByPassword(String password) {
		Connection conn = BaseDao.getconn();
		ResultSet rs = null;
		PreparedStatement ps = null;
		Admin admin = new Admin();
		
		String sql = "SELECT * FROM document.admin where password = ?;";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, password);
			rs = ps.executeQuery();
			while (rs.next()) {
				admin = new Admin(
					rs.getString("username"), 
					rs.getString("password")
				);		
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			BaseDao.closeall(rs, ps, conn);
		}
		return admin;
	}
}
