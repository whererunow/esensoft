package com.esen.abistudy.orm.pojo;

import java.util.Date;

import com.esen.ecore.domain.IdEntityImpl;
/**
 * 记录查询列表展示数据的pojo
 * @author yangk
 * @since 2019年9月6日
 */
public class RecordQueryPojo  extends IdEntityImpl {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -7468704051521768287L;
	/**
	 * 图书id
	 */
	private String bid;
	/**
	 * 图书名
	 */
	private String bname;
	/**
	 * 借阅人姓名
	 */
	private String person;
	/**
	 * 借阅日期
	 */
	private Date fromdate;
	/**
	 * 还书日期
	 */
	private Date todate;

	public String getBname() {
		return bname;
	}

	public void setBname(String bname) {
		this.bname = bname;
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

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}


}
