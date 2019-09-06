package com.esen.abistudy.orm.entity;

import com.esen.ecore.domain.IdEntityImpl;
/**
 * 分析表实体类
 * @author yangk
 * @since 2019年9月6日
 */
public class AnalysisChartEntity extends IdEntityImpl{

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -2658927987580947338L;
	/**
	 * 分析表id
	 */
	private String acid;
	/**
	 * 分析表名称
	 */
	private String acname;
	/**
	 * 统计图id
	 */
	private String scid;
	/**
	 * 分析表数据
	 */
	private String data;
	
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getAcid() {
		return acid;
	}
	public void setAcid(String acid) {
		this.acid = acid;
	}
	public String getAcname() {
		return acname;
	}
	public void setAcname(String acname) {
		this.acname = acname;
	}
	public String getScid() {
		return scid;
	}
	public void setScid(String scid) {
		this.scid = scid;
	}
		
}
