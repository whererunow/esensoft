package com.esen.abistudy.service.impl;

import java.sql.ResultSet;

import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;

import com.esen.abistudy.dao.BookInfoRepository;
import com.esen.abistudy.dao.BookRecordRepository;
import com.esen.abistudy.orm.entity.BookInfoEntity;
import com.esen.abistudy.orm.entity.BookRecordEntity;
import com.esen.abistudy.orm.pojo.RecordQueryPojo;
import com.esen.abistudy.service.BookRecordService;
import com.esen.ecore.annotation.ApplicationService;
import com.esen.ecore.repository.PageRequest;
import com.esen.ecore.repository.PageResult;
import com.esen.ecore.util.jdbc.RowHandler;
/**
 * 图书记录服务实现类
 * @author yangk
 * @since 2019年9月6日
 */
@ApplicationService
public class BookRecordServiceImpl implements BookRecordService {
	/**
	 * 注入bookRecordRepository 图书记录表持久层
	 */
	@Autowired
	private BookRecordRepository bookRecordRepository;
	/**
	 * 注入bookInfoRepository 图书信息表持久层
	 */
	@Autowired
	private BookInfoRepository bookInfoRepository;
	
	/**
	 * 查询图书借阅记录列表
	 * @param pageIndex 分页页码索引
	 * @param pageSize  每页显示的记录数
	 * @return 包含总记录数和当前页记录列表PageResult对象
	 */
	@Override
	public PageResult<RecordQueryPojo> findRecordList(int pageIndex, int pageSize){
		String sql = "select t1.bid_,bname_,person_,fromdate_,todate_ "
				+ "from week04_book_record t1,week04_book_info t2 "
				+ "where t1.bid_ = t2.bid_ order by bname_,fromdate_ desc";
		PageRequest page = new PageRequest(pageIndex,pageSize);
		RowHandler<RecordQueryPojo> handler = new RowHandler<RecordQueryPojo>() {
			@Override
			public RecordQueryPojo processRow(ResultSet resultSet) throws SQLException {
				RecordQueryPojo recordQueryPojo = new RecordQueryPojo();
				recordQueryPojo.setBid(resultSet.getString("bid_"));
				recordQueryPojo.setBname(resultSet.getString("bname_"));
				recordQueryPojo.setPerson(resultSet.getString("person_"));
				recordQueryPojo.setFromdate(resultSet.getDate("fromdate_"));
				recordQueryPojo.setTodate(resultSet.getDate("todate_"));
				return recordQueryPojo;
			}
		};
		PageResult<RecordQueryPojo> pageResult = bookRecordRepository.query(sql, page, handler);
		return pageResult;
	}
	/**
	 * 查询图书书名列表 在添加记录对话框的下拉框中展示
	 * @return 图书书名列表的PageResult对象
	 */
	@Override
	public PageResult<BookInfoEntity> findBookNameList(){
		String sql = "select bid_,bname_ from week04_book_info";
		PageRequest page = new PageRequest();
		RowHandler<BookInfoEntity> handler = new RowHandler<BookInfoEntity>() {
			@Override
			public BookInfoEntity processRow(ResultSet resultSet) throws SQLException {
				BookInfoEntity bookInfoEntity = new BookInfoEntity();
				bookInfoEntity.setBid(resultSet.getString("bid_"));
				bookInfoEntity.setBname(resultSet.getString("bname_"));
				return bookInfoEntity;
			}
		};
		PageResult<BookInfoEntity> pageResult = bookInfoRepository.query(sql , page , handler);
		return pageResult;
	}
	
	/**
	 * 添加图书借阅记录
	 * @param bookRecordEntity 封装了图书借阅记录数据的实体类
	 * @throws Exception 异常
	 */
	@Override
	public void addRecord(BookRecordEntity bookRecordEntity) throws Exception {
		bookRecordRepository.add(bookRecordEntity);
	}
}
