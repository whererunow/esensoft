package com.esen.abistudy.orm.entity;

import com.esen.ecore.domain.IdEntityImpl;
/**
 * 子分类表实体类
 * @author yangk
 * @since 2019年8月27日
 */
public class BookTypeEntity extends IdEntityImpl{

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 2919488873793551759L;
	/**
	 * 主分类id
	 */
	private String cid;
	/**
	 * 子分类id
	 */
	private String tid;
	/**
	 * 子分类名
	 */
	private String tname;
	
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getTname() {
		return tname;
	}
	public void setTname(String tname) {
		this.tname = tname;
	}
	
	
}
