package com.gwl.view.bean;

import java.util.Date;

/**
 * 
 * @author wpf - steven
 * 文件类，包含了文件的具体属性，get/set方法，带参和无参的构造方法
 *
 */

public class Document {
	private int id; //文件id编号，数据库中的主键
	private String name; //文件名
	private String type; //文件类型
	private Date uploadTime; //文件下载时间
	private int downloadTimes; //文件下载次数
	
	//各个属性的get和set方法
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	public int getDownloadTimes() {
		return downloadTimes;
	}
	public void setDownloadTimes(int downloadTimes) {
		this.downloadTimes = downloadTimes;
	}
	
	//带参构造方法
	public Document(int id, String name, String type, Date uploadTime, int downloadTimes) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.uploadTime = uploadTime;
		this.downloadTimes = downloadTimes;
	}
	
	//无参构造方法
	public Document() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
