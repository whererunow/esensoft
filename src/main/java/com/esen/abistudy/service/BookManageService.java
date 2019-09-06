package com.esen.abistudy.service;

import com.esen.abistudy.orm.entity.BookCategoryEntity;
import com.esen.abistudy.orm.entity.BookInfoEntity;
import com.esen.abistudy.orm.entity.BookTypeEntity;
import com.esen.abistudy.orm.pojo.BookManagePojo;
import com.esen.ecore.repository.PageResult;
/**
 * 图书管理service接口
 * @author yangk
 * @since 2019年9月6日
 */
public interface BookManageService {
	/**
	 * 分页查询图书列表
	 * @param pageIndex 页码索引
	 * @param pageSize  每页显示的条数
	 * @return 包含总记录数与当前页数据PageResult对象
	 */
	PageResult<BookManagePojo> findBookList(int pageIndex,int pageSize);
	/**
	 * 查询主分类列表 在下拉框进行展示
	 * @return  包含所有主分类数据的PageResult对象
	 */
	PageResult<BookCategoryEntity> findCategoryList();
	/**
	 * 根据主分类id查询子分类列表
	 * @param cid  主分类id
	 * @return  包含所有子分类数据的PageResult对象
	 */
	PageResult<BookTypeEntity> findTypeList(String cid);
	/**
	 * 根据添加图书对话框中的数据添加bookInfoEntity
	 * @param bookInfoEntity 封装了新加图书信息的实体类
	 * @throws Exception 异常
	 */
	void addBook(BookInfoEntity bookInfoEntity) throws Exception;
	/**
	 * 根据新建分裂对话框中的数据添加bookTypeEntity
	 * @param bookTypeEntity 封装了分类信息的实体类
	 * @throws Exception 异常
	 */
	void addType(BookTypeEntity bookTypeEntity) throws Exception;
}
