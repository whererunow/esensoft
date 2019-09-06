package com.esen.abistudy.service;

import com.esen.abistudy.orm.entity.BookInfoEntity;
import com.esen.abistudy.orm.entity.BookRecordEntity;
import com.esen.abistudy.orm.pojo.RecordQueryPojo;
import com.esen.ecore.repository.PageResult;
/**
 * 图书记录service接口
 * @author yangk
 * @since 2019年9月6日
 */
public interface BookRecordService {
	/**
	 * 查询图书借阅记录列表
	 * @param pageIndex 分页页码索引
	 * @param pageSize  每页显示的记录数
	 * @return 包含总记录数和当前页记录列表PageResult对象
	 */
	PageResult<RecordQueryPojo> findRecordList(int pageIndex, int pageSize) ;
	/**
	 * 查询图书书名列表 在添加记录对话框的下拉框中展示
	 * @return 图书书名列表的PageResult对象
	 */
	PageResult<BookInfoEntity> findBookNameList() ;
	/**
	 * 添加图书借阅记录
	 * @param bookRecordEntity 封装了图书借阅记录数据的实体类
	 * @throws Exception 异常
	 */
	void addRecord(BookRecordEntity bookRecordEntity) throws Exception;

}
