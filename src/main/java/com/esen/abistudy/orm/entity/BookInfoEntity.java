package com.esen.abistudy.orm.entity;

import com.esen.ecore.domain.IdEntityImpl;
/**
 * 图书信息表实体类
 * @author yangk
 * @since 2019年8月30日
 */
public class BookInfoEntity extends IdEntityImpl {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -8914586003650493297L;
	/**
	 * 图书id
	 */
	private String bid;
	/**
	 * 图书名称
	 */
	private String bname;
	/**
	 * 图书简介
	 */
	private String briefIntroduction;
	/**
	 * 图书所属子分类id
	 */
	private  String tid;
	


	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public String getBname() {
		return bname;
	}

	public void setBname(String bname) {
		this.bname = bname;
	}

	public String getBriefIntroduction() {
		return briefIntroduction;
	}

	public void setBriefIntroduction(String briefIntroduction) {
		this.briefIntroduction = briefIntroduction;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}
	
	
}
