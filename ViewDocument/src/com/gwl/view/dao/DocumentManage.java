package com.gwl.view.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.gwl.view.bean.Document;
import com.gwl.view.dao.jdbc.BaseDao;

public class DocumentManage {
	
	/**
	 * 按用户需要查找数据库中相应记录的方法
	 * @param type 前台页面选择的文件类别
	 * @param keyword 前台页面提交的搜索文件名的关键词
	 * @param current_page 当前的页面的页数
	 * @param page_count 每一面所显示的信息条数
	 * @return 返回值为文件类列表，里面包含数据库的文件表中的所有信息
	 */
	public static ArrayList<Document> SelectAllDocument(String type, String keyword, int current_page, int page_count) {
		ArrayList<Document> documents = new ArrayList<Document>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = BaseDao.getconn();
		String sql = null;
		 
		try {
			// 用户没有选择分类也没有填写关键词的数据库搜索过程
			if (type.equals("all") && keyword.equals("")) {
				sql = "SELECT * FROM document.document order by id desc limit ?, ?;";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, (current_page - 1) * page_count);
				ps.setInt(2, page_count);
			// 用户没有选择分类，但填写了关键词的数据库搜索过程
			} else if (type.equals("all") && (!keyword.equals(""))){
				sql = "SELECT * FROM document.document where name like ? order by id desc limit ?, ?;";
				ps = conn.prepareStatement(sql);
				ps.setString(1, "%" + keyword + "%");
				ps.setInt(2, (current_page - 1) * page_count);
				ps.setInt(3, page_count);
			// 用户选择了分类，但没有填写关键词的数据库搜索过程
			} else if ((!type.equals("all")) && (keyword.equals(""))){
				sql = "SELECT * FROM document.document where type = ? order by id desc limit ?, ?;";
				ps = conn.prepareStatement(sql);
				ps.setString(1, type);
				ps.setInt(2, (current_page - 1) * page_count);
				ps.setInt(3, page_count);
			// 用户没有选择分类，也没有填写关键词的数据库搜索过程
			} else if ((!type.equals("all")) && (!keyword.equals(""))) {
				sql = "SELECT * FROM document.document where type = ? and name like ? order by id desc limit ?, ?;";
				ps = conn.prepareStatement(sql);
				ps.setString(1, type);
				ps.setString(2, "%" + keyword + "%");
				ps.setInt(3, (current_page - 1) * page_count);
				ps.setInt(4, page_count);
			}
			
			rs = ps.executeQuery(); //根据数据库语句返回结果集
			
			while (rs.next()) {
				// 根据结果集来获取文件对象
				Document document = new Document(
					rs.getInt("id"),
					rs.getString("name"), 
					rs.getString("type"), 
					rs.getDate("uploadTime"), 
					rs.getInt("downloadTimes")
				);
				documents.add(document); //添加到文件列表中
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			BaseDao.closeall(rs, ps, conn); //关闭对数据库的链接
		}
		return documents;
	}
	
	/**
	 * 获取用户查找的对应的数据的总条数和总页数的方法
	 * @param type 前台页面选择的文件类别
	 * @param keyword 前台页面提交的搜索文件名的关键词
	 * @param current_page 当前的页面的页数
	 * @param page_count 每一面所显示的信息条数
	 * @return 数组，0号元素表示查询到的记录总数，1号元素表示总页数
	 */
	public static int[] GetTotalPage(String type, String keyword, int current_page, int page_count) {
		int arr[] = {0,1};
		Connection conn = BaseDao.getconn();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			// 用户没有选择分类也没有填写关键词的数据库搜索过程
			if (type.equals("all") && keyword.equals("")) {
				sql = "SELECT count(*) FROM document.document;";
				ps = conn.prepareStatement(sql);
			// 用户没有选择分类，但填写了关键词的数据库搜索过程
			} else if (type.equals("all") && (!keyword.equals(""))){
				sql = "SELECT count(*) FROM document.document where name like ?;";
				ps = conn.prepareStatement(sql);
				ps.setString(1, "%" + keyword + "%");
			// 用户选择了分类，但没有填写关键词的数据库搜索过程
			} else if ((!type.equals("all")) && (keyword.equals(""))){
				sql = "SELECT count(*) FROM document.document where type = ?;";
				ps = conn.prepareStatement(sql);
				ps.setString(1, type);
			// 用户没有选择分类，也没有填写关键词的数据库搜索过程
			} else if ((!type.equals("all")) && (!keyword.equals(""))) {
				sql = "SELECT count(*) FROM document.document where type = ? and name like ?;";
				ps = conn.prepareStatement(sql);
				ps.setString(1, type);
				ps.setString(2, "%" + keyword + "%");
			}
			
			rs = ps.executeQuery();	//根据数据库语句返回结果集
			
			while (rs.next()) {
				// 根据结果集来过去记录总数并根据每一面能显示的记录条数来计算总页数
				arr[0] = rs.getInt(1);
				if (arr[0]%page_count==0) {
					arr[1] = arr[0]/page_count;
				}else {
					arr[1] = arr[0]/page_count + 1;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			BaseDao.closeall(rs, ps, conn); //关闭对数据库的链接
		}
		return arr;
	}
	
	/**
	 * 获取数据库中所有文件名的方法
	 * @return 数组类型的列表，里面包含了所有文件的文件名
	 */
	public static ArrayList<String> GetAllDocumentName() {
		ArrayList<String> documentsname = new ArrayList<String>();
		Connection conn = BaseDao.getconn();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT name FROM document.document;"; //查询所有文件名的sql语句
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery(); //根据数据库语句返回结果集
			
			while (rs.next()) {
				// 根据结果集来获取各个文件的文件名
				String documentname = rs.getString("name");
				// 将文件名添加到列表中
				documentsname.add(documentname);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			BaseDao.closeall(rs, ps, conn); //关闭对数据库的链接
		}
		
		return documentsname;
	}
	
	/**
	 * 判断文件名是否重复的方法
	 * @param name 需要做判断的文件名
	 * @return boolean值，表示是否重复
	 */
	public static boolean NameRepeatOrNot(String name) {
		ArrayList<String> documentsname = GetAllDocumentName(); //获取已有的文件名
		for (String documentname : documentsname) {
			// 用foreach循环依次判断是否重复
			if (name.equals(documentname)) {
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * 通过文件id来获取文件名的方法
	 * @param id 需要获取文件名的文件id
	 * @return 文件名字符串
	 */
	public static String GetNameById(int id) {
		String name = "";
		Connection conn = BaseDao.getconn();
		PreparedStatement ps = null;
		ResultSet rs = null;
		//根据文件id查询所有文件名的sql语句
		String sql = "SELECT name FROM document.document where id = ?;";
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery(); //根据数据库语句返回结果集
			
			while (rs.next()) {
				name = rs.getString(1); // 根据结果集来获取文件名
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			BaseDao.closeall(rs, ps, conn); //关闭对数据库的链接
		}
		return name;
	}
	
	/**
	 * 添加
	 * @param document
	 * @return
	 */
	public static int InsertDocument(Document document) {
		//添加记录的语句
		String sql = "INSERT INTO document.document (name, `type`, uploadTime, downloadTimes) VALUES(?, ?, ?, ?);";
		//文件的各个属性，其中文件id已在数据库中设置了自动增加
		Object params[] = {
			document.getName(),
			document.getType(),
			document.getUploadTime(),
			document.getDownloadTimes()
		};
		return BaseDao.exectuIUD(sql, params); //调用链接数据库的方法
	}
	
	/**
	 * 删除文件记录的方法
	 * @param id 需要删除的文件id
	 * @return 是否删除成功，1表示成功，0表示不成功
	 */
	public static int DeleteDocument(int id) {
		String sql = "DELETE FROM document.document WHERE id=?;"; //删除记录的语句
		Object params[] = {id};
		return BaseDao.exectuIUD(sql, params); //调用链接数据库的方法
	}
}
