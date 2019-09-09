package com.esen.abistudy.orm.entity;

import com.esen.ecore.domain.IdEntityImpl;

/**
 * 子分类实体类
 * @author yangk
 * @since 2019年8月30日
 */
public class BookTypeEntity extends IdEntityImpl {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 2919488873793551759L;

	/**
	 * 小类id
	 */
	private String tid;

	/**
	 * 小类名称
	 */
	private String tname;

	/**
	 * 大类id
	 */
	private String cid;

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

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

}
