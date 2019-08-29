package com.esen.abistudy.orm.expandentity;

import com.esen.ecore.domain.IdEntityImpl;
/**
 * BookHistoryEntity属性扩展类
 * @author yangk
 * @since 2019年8月28日
 */
public class BookHistoryEntityExpand extends IdEntityImpl{

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -3600478743766034269L;
	/**
	 * 图书名称
	 */
	private String bname;
	/**
	 * 图书借阅次数
	 */
	private int count;
	
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getBname() {
		return bname;
	}

	public void setBname(String bname) {
		this.bname = bname;
	}

}
