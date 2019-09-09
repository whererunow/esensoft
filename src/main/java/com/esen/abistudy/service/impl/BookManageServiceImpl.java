package com.esen.abistudy.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;

import com.esen.abistudy.dao.BookInfoRepository;
import com.esen.abistudy.dao.BookTypeRepository;
import com.esen.abistudy.orm.entity.BookCategoryEntity;
import com.esen.abistudy.orm.entity.BookInfoEntity;
import com.esen.abistudy.orm.entity.BookTypeEntity;
import com.esen.abistudy.orm.pojo.BookManagePojo;
import com.esen.abistudy.service.BookManageService;
import com.esen.ecore.annotation.ApplicationService;
import com.esen.ecore.repository.PageRequest;
import com.esen.ecore.repository.PageResult;

import com.esen.ecore.util.jdbc.RowHandler;
import com.esen.util.UNID;

/**
 * 图书管理服务实现类
 * @author yangk
 * @since 2019年9月6日
 */
@ApplicationService
public class BookManageServiceImpl implements BookManageService {
	/**
	 * 注入bookInfoRepository 图书信息表持久层
	 */
	@Autowired
	private BookInfoRepository bookInfoRepository;

	/**
	 * 注入BookTypeRepository 图书分类表持久层
	 */
	@Autowired
	private BookTypeRepository bookTypeRepository;

	/**
	 * 分页查询图书列表
	 * @param pageIndex 页码索引
	 * @param pageSize  每页显示的条数
	 * @return 包含总记录数与当前页数据PageResult对象
	 */
	@Override
	public PageResult<BookManagePojo> findBookList(int pageIndex, int pageSize) {
		String sql = "select t1.bid_,t1.bname_,t2.tname_,t3.cname_ "
				+ "from week04_book_info t1,week04_book_type t2,week04_book_category t3 "
				+ "where t1.tid_=t2.tid_ and t2.cid_ = t3.cid_ order by t3.cname_,t2.tname_,t1.bid_";
		PageRequest page = new PageRequest(pageIndex, pageSize);
		RowHandler<BookManagePojo> handler = new RowHandler<BookManagePojo>() {
			@Override
			public BookManagePojo processRow(ResultSet resultset) throws SQLException {
				BookManagePojo bookManagePojo = new BookManagePojo();
				bookManagePojo.setBid(resultset.getString("BID_"));
				bookManagePojo.setBname(resultset.getString("BNAME_"));
				bookManagePojo.setTname(resultset.getString("TNAME_"));
				bookManagePojo.setCname(resultset.getString("CNAME_"));
				return bookManagePojo;
			}
		};
		PageResult<BookManagePojo> pageResult = bookInfoRepository.query(sql, page, handler);
		return pageResult;
	}

	/**
	 * 查询主分类列表 在下拉框进行展示
	 * @return  包含所有主分类数据的PageResult对象
	 */
	@Override
	public PageResult<BookCategoryEntity> findCategoryList() {
		String sql = "select cid_,cname_ from week04_book_category";
		PageRequest page = new PageRequest();
		RowHandler<BookCategoryEntity> handler = new RowHandler<BookCategoryEntity>() {
			@Override
			public BookCategoryEntity processRow(ResultSet resultSet) throws SQLException {
				BookCategoryEntity bookCategoryEntity = new BookCategoryEntity();
				bookCategoryEntity.setCid(resultSet.getString("cid_"));
				bookCategoryEntity.setCname(resultSet.getString("cname_"));
				return bookCategoryEntity;
			}
		};
		PageResult<BookCategoryEntity> pageResult = bookInfoRepository.query(sql, page, handler);
		return pageResult;
	}

	/**
	 * 根据主分类id查询子分类列表
	 * @param cid  主分类id
	 * @return  包含所有子分类数据的PageResult对象
	 */
	@Override
	public PageResult<BookTypeEntity> findTypeList(String cid) {
		String sql = "select tid_,tname_ from week04_book_type where cid_=?";
		PageRequest page = new PageRequest();
		RowHandler<BookTypeEntity> handler = new RowHandler<BookTypeEntity>() {
			@Override
			public BookTypeEntity processRow(ResultSet resultSet) throws SQLException {
				BookTypeEntity bookTypeEntity = new BookTypeEntity();
				bookTypeEntity.setTid(resultSet.getString("tid_"));
				bookTypeEntity.setTname(resultSet.getString("tname_"));
				return bookTypeEntity;
			}
		};
		PageResult<BookTypeEntity> pageResult = bookInfoRepository.query(sql, page, handler, cid);
		return pageResult;
	}

	/**
	 * 根据添加图书对话框中的数据添加bookInfoEntity
	 * @param bookInfoEntity 封装了新加图书信息的实体类
	 * @throws Exception 异常
	 */
	@Override
	public void addBook(BookInfoEntity bookInfoEntity) throws Exception {
		bookInfoEntity.setBid(UNID.randomID());
		bookInfoRepository.add(bookInfoEntity);
	}

	/**
	 * 根据新建分裂对话框中的数据添加bookTypeEntity
	 * @param bookTypeEntity 封装了分类信息的实体类
	 * @throws Exception 异常
	 */
	@Override
	public void addType(BookTypeEntity bookTypeEntity) throws Exception {
		// TODO Auto-generated method stub
		bookTypeEntity.setTid(UNID.randomID());
		bookTypeRepository.add(bookTypeEntity);
	}
}
