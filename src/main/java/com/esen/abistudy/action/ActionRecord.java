package com.esen.abistudy.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esen.abistudy.orm.entity.BookInfoEntity;
import com.esen.abistudy.orm.entity.BookRecordEntity;
import com.esen.abistudy.orm.pojo.RecordQueryPojo;
import com.esen.abistudy.service.BookRecordService;
import com.esen.ecore.repository.PageResult;

import com.esen.util.JsonUtils;
import com.esen.util.StrFunc;
import com.esen.util.UNID;


/**
 * 记录查询action
 * @author yangk
 * @since 2019年9月6日
 */
@Controller
@RequestMapping("/record")
public class ActionRecord {

	/**
	 * 注入bookRecordService
	 */
	@Autowired
	private BookRecordService bookRecordService;

	/**
	 * 查询图书借阅记录列表
	 * @param pageIndex 分页页码索引
	 * @param pageSize  每页显示的记录数
	 * @return 包含总记录数和当前页记录列表的json字符串
	 */
	@RequestMapping("/findRecordList")
	@ResponseBody
	public String findRecordList(int pageIndex, int pageSize) {
		PageResult<RecordQueryPojo> pageResult = bookRecordService.findRecordList(pageIndex, pageSize);
		long totalCount = pageResult.getTotalCount();
		List<RecordQueryPojo> recordList = pageResult.list();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("totalCount", totalCount);
		map.put("result", recordList);
		return JsonUtils.toJSONString(map);
	}

	/**
	 * 查询图书书名列表 在添加记录对话框的下拉框中展示
	 * @return 图书书名列表的json字符串
	 */
	@RequestMapping("/findBookNameList")
	@ResponseBody
	public String findBookNameList() {
		PageResult<BookInfoEntity> pageResult = bookRecordService.findBookNameList();
		return JsonUtils.toJSONString(pageResult.list());
	}

	/**
	 * 添加图书借阅记录
	 * @param bid 图书id
	 * @param person 借阅人姓名
	 * @param fromdate 借阅日期时间字符串
	 * @param todate   还书日期时间字符串
	 * @throws Exception 
	 */
	@RequestMapping("/addRecord")
	@ResponseBody
	public void addRecord(String bid, String person, String fromdate, String todate) throws Exception {
			BookRecordEntity bookRecordEntity = new BookRecordEntity();
			bookRecordEntity.setBid(bid);
			bookRecordEntity.setPerson(person);
			bookRecordEntity.setFromdate(StrFunc.str2date(fromdate, "yyyyMMdd").getTime());
			bookRecordEntity.setTodate(StrFunc.str2date(todate, "yyyyMMdd").getTime());
			bookRecordEntity.setRid(UNID.randomID());
			bookRecordService.addRecord(bookRecordEntity);
	}
}
