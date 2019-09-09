package com.esen.abistudy.orm.pojo;

import com.esen.ecore.domain.IdEntityImpl;

/**
 * 图书管理列表展示数据的pojo
 * @author yangk
 * @since 2019年9月6日
 */
public class BookManagePojo extends IdEntityImpl {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -8093290334610170378L;

	/**
	 * 图书id
	 */
	private String bid;

	/**
	 * 图书名
	 */
	private String bname;

	/**
	 * 图书所属子分类名
	 */
	private String tname;

	/**
	 * 图书所属主分类名
	 */
	private String cname;

	public String getBname() {
		return bname;
	}

	public void setBname(String bname) {
		this.bname = bname;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

}
