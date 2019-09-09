package com.esen.abistudy.orm.entity;

import java.util.Date;

import com.esen.ecore.domain.IdEntityImpl;

/**
 * 借阅记录表实体类
 * @author yangk
 * @since 2019年8月30日
 */
public class BookRecordEntity extends IdEntityImpl {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 5174783890036138335L;

	/**
	 * 图书id
	 */
	private String bid;

	/**
	 * 借阅人姓名
	 */
	private String person;

	/**
	 * 借阅日期
	 */
	private Date fromdate;

	/**
	 * 归还日期
	 */
	private Date todate;
	/**
	 * 借阅记录id
	 */
	private String rid;
	

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public Date getFromdate() {
		return fromdate;
	}

	public void setFromdate(Date fromdate) {
		this.fromdate = fromdate;
	}

	public Date getTodate() {
		return todate;
	}

	public void setTodate(Date todate) {
		this.todate = todate;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

}
