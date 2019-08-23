package com.esen.abistudy.orm.entity;

import com.esen.ecore.domain.IdEntityImpl;

/**
 * 成绩表实体类
 * @author yangk
 * @since 2019年8月19日
 */
public class ScoreEntity extends IdEntityImpl{

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 1230709537436200160L;
	/**
	 * 关键字 使用UNID类生成 
	 */
	private String id;
	/**
	 * 时间 格式：yyyyMMdd
	 */
	private String date;
	/**
	 * 学生姓名
	 */
	private String student;
	/**
	 * 科目
	 */
	private String course;
	/**
	 * 成绩
	 */
	private float score;

	/**
	 * 返回id
	 * @return id
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置id
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取date
	 * @return date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * 设置 date
	 * @param date
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * 获取姓名
	 * @return student
	 */
	public String getStudent() {
		return student;
	}
	/**
	 * 设置姓名
	 * @param student
	 */
	public void setStudent(String student) {
		this.student = student;
	}
	/**
	 * 获取课程
	 * @return course
	 */
	public String getCourse() {
		return course;
	}
  /**
   * 设置course
   * @param course
   */
	public void setCourse(String course) {
		this.course = course;
	}
	/**
	 * 获取 score
	 * @return score
	 */
	public float getScore() {
		return score;
	}
	/**
	 * 设置score
	 * @param score
	 */
	public void setScore(float score) {
		this.score = score;
	}
	
	
}
