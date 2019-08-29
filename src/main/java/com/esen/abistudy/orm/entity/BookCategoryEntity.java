package com.esen.abistudy.orm.entity;

import com.esen.ecore.domain.IdEntityImpl;
/**
 * 书籍分类表实体类
 * @author yangk
 * @since 2019年8月27日
 */
public class BookCategoryEntity extends IdEntityImpl {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 5794264078362126800L;
	/**
	 * 分类id
	 */
	private String cid;
	/**
	 * 分类名称
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
