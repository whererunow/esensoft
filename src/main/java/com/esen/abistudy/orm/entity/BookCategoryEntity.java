package com.esen.abistudy.orm.entity;

import com.esen.ecore.domain.IdEntityImpl;
/**
 * 图书主分类实体类
 * @author yangk
 * @since 2019年8月29日
 */
public class BookCategoryEntity extends IdEntityImpl {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -1301878322740921705L;
	/**
	 * 主分类id
	 */
	private String cid;
	/**
	 * 主分类名
	 */
	private String cname;
	
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	
}
