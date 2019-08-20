package com.esen.abistudy.orm.entity;
/**
 * 用于返回操作结果
 * @author yangk
 * @since 2019年8月20日
 */
public class Result {
	/**
	 * flag代表操作是否成功 
	 */
	private boolean flag;
	/**
	 * 操作的结果信息
	 */
	private String message;
	
	public Result(boolean flag, String message) {
		this.flag = flag;
		this.message = message;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
