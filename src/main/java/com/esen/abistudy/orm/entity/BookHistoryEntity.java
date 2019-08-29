package com.esen.abistudy.orm.entity;


import java.util.Date;

import com.esen.abistudy.orm.expandentity.BookHistoryEntityExpand;


/**
 * 借书记录实体类
 * @author yangk
 * @since 2019年8月27日
 */
public class BookHistoryEntity extends BookHistoryEntityExpand {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 608716624391005263L;
	/**
	 * 图书id
	 */
	private String bid;
	/**
	 * 借书人姓名
	 */
	private String person;
	/**
	 * 起始日期
	 */
	private Date fromdate;
	/**
	 * 结束日期
	 */
	private Date todate;

	
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

}
