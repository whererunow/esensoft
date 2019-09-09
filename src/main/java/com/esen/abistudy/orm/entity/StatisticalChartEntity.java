package com.esen.abistudy.orm.entity;

import com.esen.ecore.domain.IdEntityImpl;

/**
 * 统计图表实体类
 * @author yangk
 * @since 2019年9月6日
 */
public class StatisticalChartEntity extends IdEntityImpl {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -7595415273501240118L;

	/**
	 * 统计图id
	 */
	private String scid;

	/**
	 * 统计图名称
	 */
	private String scname;

	public String getScid() {
		return scid;
	}

	public void setScid(String scid) {
		this.scid = scid;
	}

	public String getScname() {
		return scname;
	}

	public void setScname(String scname) {
		this.scname = scname;
	}

}
