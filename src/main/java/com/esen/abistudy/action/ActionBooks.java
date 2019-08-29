package com.esen.abistudy.action;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esen.abistudy.dao.BookCategoryRepository;
import com.esen.abistudy.dao.BookHistoryRepository;
import com.esen.abistudy.dao.BookTypeRepository;
import com.esen.abistudy.orm.entity.BookCategoryEntity;
import com.esen.abistudy.orm.entity.BookHistoryEntity;
import com.esen.abistudy.orm.entity.BookTypeEntity;
import com.esen.ecore.repository.PageRequest;
import com.esen.ecore.repository.PageResult;
import com.esen.ecore.util.jdbc.RowHandler;
import com.esen.util.JsonUtils;
import com.esen.util.StrFunc;

/**
 * 查看图书借阅信息的action
 * @author yangk
 * @since 2019年8月28日
 */
@Controller
@RequestMapping("/books")
public class ActionBooks {
	/**
	 * 注入bookCategoryRepository对象
	 */
	@Autowired
	private BookCategoryRepository bookCategoryRepository;
	/**
	 * 注入bookTypeRepository对象
	 */
	@Autowired
	private BookTypeRepository bookTypeRepository;
	/**
	 * 注入bookHistoryRepository对象
	 */
	@Autowired
	private BookHistoryRepository bookHistoryRepository;

	/**
	 * 界面访问入口
	 * @param req 向ftl传输数据
	 * @return ftl页面
	 */
	@RequestMapping()
	public String getIndexPage(HttpServletRequest req) {
		//向ftl传数据
		req.setAttribute("title", "图书管理");
		//调整ftl界面
		return "abistudy/books";
	}

	/**
	 * 在访问页面时加载的主分类信息
	 * @return 主分类list集合
	 */
	@RequestMapping("/findCategory")
	@ResponseBody
	public String findCategory() {
		Collection<BookCategoryEntity> bookCategoryList = bookCategoryRepository.findAll();
		return JsonUtils.toJSONString(bookCategoryList);
	}

	/**
	 * 展开主分类节点时加载子分类信息
	 * @param cid 主分类的id
	 * @return 返回主分类下的子分类列表
	 */
	@RequestMapping("/findTypeByCid")
	@ResponseBody
	public String findTypeByCid(String cid) {
		String sql = "select * from BOOK_TYPE where CID_=? order by TNAME_";
		PageRequest page = new PageRequest();
		RowHandler<BookTypeEntity> handler = new RowHandler<BookTypeEntity>() {
			@Override
			public BookTypeEntity processRow(ResultSet resultset) throws SQLException {
				BookTypeEntity bookType = new BookTypeEntity();
				bookType.setCid(resultset.getString("CID_"));
				bookType.setTid(resultset.getString("TID_"));
				bookType.setTname(resultset.getString("TNAME_"));
				return bookType;
			}
		};
		PageResult<BookTypeEntity> query = bookTypeRepository.query(sql, page, handler, cid);
		List<BookTypeEntity> typeList = query.list();
		return JsonUtils.toJSONString(typeList);
	}

	/**
	 * 点击子分类时加载图书借阅信息
	 * @param tid 选中的子分类id
	 * @return 图书借阅信息的json字符串
	 */
	@RequestMapping("/findBorrowBookInfo")
	@ResponseBody
	public String findBorrowBookInfo(String tid) {
		String sql = "select bname_,person_,fromdate_,todate_ "
				+ "from book_history t1,(select bid_,bname_ from book_info  where tid_=?) t2 " + "where t1.bid_ = t2.bid_ "
				+ "order by bname_,fromdate_";
		PageRequest page = new PageRequest();
		RowHandler<BookHistoryEntity> handler = new RowHandler<BookHistoryEntity>() {
			@Override
			public BookHistoryEntity processRow(ResultSet resultSet) throws SQLException {
				BookHistoryEntity bookHistoryEntity = new BookHistoryEntity();
				bookHistoryEntity.setBname(resultSet.getString("BNAME_"));
				bookHistoryEntity.setPerson(resultSet.getString("PERSON_"));
				bookHistoryEntity.setFromdate(resultSet.getDate("FROMDATE_"));
				bookHistoryEntity.setTodate(resultSet.getDate("TODATE_"));
				return bookHistoryEntity;
			}
		};
		PageResult<BookHistoryEntity> pageResult = bookHistoryRepository.query(sql, page, handler, tid);
		List<BookHistoryEntity> list = pageResult.list();
		return JsonUtils.toJSONString(list);
	}
	/**
	 * 显示统计图
	 * @param tid 子分类id
	 * @return 统计图所需信息
	 */
	@RequestMapping("/showStatisticalChart")
	@ResponseBody
	public String showStatisticalChart(String tid) {
		String sql = "select bname_,count(bname_) count_ "
				+ "from book_history t1,(select bid_,bname_ from book_info  where tid_=?) t2 "
				+ "where t1.bid_ = t2.bid_ group by bname_ order by bname_";
		PageRequest page = new PageRequest();
		RowHandler<BookHistoryEntity> handler = new RowHandler<BookHistoryEntity>() {
			@Override
			public BookHistoryEntity processRow(ResultSet resultSet) throws SQLException {
				BookHistoryEntity bookHistoryEntity = new BookHistoryEntity();
				bookHistoryEntity.setBname(resultSet.getString("BNAME_"));
				bookHistoryEntity.setCount(StrFunc.parseInt(resultSet.getString("COUNT_"), 0));
				return bookHistoryEntity;
			}
		};
		PageResult<BookHistoryEntity> pageResult = bookHistoryRepository.query(sql, page, handler , tid);
		return JsonUtils.toJSONString(pageResult.list());
	}
}
