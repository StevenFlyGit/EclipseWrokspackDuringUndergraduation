package com.lanou.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestJDBC {
	
	public static void update() {
		//加载驱动类
				try {
					Class.forName("com.mysql.jdbc.Driver");
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				
				String url = "jdbc:mysql://localhost:3306/sms";
				String username = "root";
				String password = "123456";
				
				//连接数据库
				try {
					Connection conn = DriverManager.getConnection(url, username, password);
					System.out.println(conn);
					
					//数据库的使用
					String sql = "update message set subject = ? , content = ? where id = ?";
					PreparedStatement ps = conn.prepareStatement(sql);
					ps.setObject(1, "上自习");
					ps.setObject(2, "在宾馆上自习");
					ps.setObject(3, 1);
					int row = ps.executeUpdate();//row表示影响了多少
					System.out.println(row);
					
					
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
	}
	//try with resource
	public static void insert() {
		//加载驱动类
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		String url = "jdbc:mysql://localhost:3306/sms";
		String username = "root";
		String password = "123456";
		
		String sql = "insert into message(subject,content,create_time) values(?,?,?)";
		//连接数据库
		try (Connection conn = DriverManager.getConnection(url, username, password);
				PreparedStatement ps = conn.prepareStatement(sql);
){
			
			//数据库的使用
			
			ps.setObject(1, "玩吃鸡222");
			ps.setObject(2, "抓紧时间玩吃鸡kai大是大非jile");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			String dateStr = sdf.format(date);
			ps.setObject(3,dateStr );
			int row = ps.executeUpdate();//row表示影响了多少
			System.out.println(row);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void update(String sql,Object...objects) {
		//加载驱动类
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		String url = "jdbc:mysql://localhost:3306/sms";
		String username = "root";
		String password = "123456";
		
		Connection conn = null;
		//连接数据库
		try {
			conn = DriverManager.getConnection(url, username, password);
			
			//数据库的使用
			PreparedStatement ps = conn.prepareStatement(sql);
			for(int i = 0 ;i < objects.length; i++) {
				ps.setObject(i+1,objects[i]);
			}				
			int row = ps.executeUpdate();//row表示影响了多少
			System.out.println(row);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(conn!=null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
	}
	
	public static void delete() {
		//加载驱动类
				try {
					Class.forName("com.mysql.jdbc.Driver");
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				
				String url = "jdbc:mysql://localhost:3306/sms";
				String username = "root";
				String password = "123456";
				
				//连接数据库
				try {
					Connection conn = DriverManager.getConnection(url, username, password);
					System.out.println(conn);
					
					//数据库的使用
					String sql = "delete from message where id > ?";
					PreparedStatement ps = conn.prepareStatement(sql);
					ps.setObject(1, 1);					
					int row = ps.executeUpdate();//row表示影响了多少
					System.out.println(row);
					
					
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}
	
	public static void selectAll() {
		//加载驱动类
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		String url = "jdbc:mysql://localhost:3306/sms";
		String username = "root";
		String password = "123456";
		
		//连接数据库
		try {
			Connection conn = DriverManager.getConnection(url, username, password);
			System.out.println(conn);
			
			//数据库的使用
			String sql = "select * from message where status != ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setObject(1, 3);					
			ResultSet rs = ps.executeQuery();//ResultSet不是集合。它类似于迭代器,不同在于
			//迭代器一开始就指向了第一个元素，ResultSet它指向的是第一个元素之前的位置。
			while(rs.next()) {
				Object id = rs.getObject(1);
				Object subject = rs.getObject(2); 
				Object createTime = rs.getObject("create_time");
				Object status = rs.getObject(4);
				System.out.println(id + " " + subject + " " + createTime + " " + status);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public static void main(String[] args) {
		
		//update();
		
		insert();
		
		//delete();
		//selectAll();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Date date = new Date();
//		String dateStr = sdf.format(date);
//		
//		
//		String sql = "insert into message(create_time,subject,content) values(?,?,?)";
//		update(sql,dateStr,"shou fang zu","gai jiao fang zu le");
		
		
		
		
		
	}

}
