package com.esen.abistudy.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esen.abistudy.orm.entity.BookCategoryEntity;
import com.esen.abistudy.orm.entity.BookInfoEntity;
import com.esen.abistudy.orm.entity.BookTypeEntity;
import com.esen.abistudy.orm.pojo.BookManagePojo;
import com.esen.abistudy.service.BookManageService;
import com.esen.ecore.repository.PageResult;
import com.esen.util.JsonUtils;
import com.esen.util.i18n.I18N;

/**
 * 图书管理action
 * @author yangk
 * @since 2019年8月29日
 */
@Controller
@RequestMapping("/book")
public class ActionBook {
	/**
	 * 注入bookManageService 
	 */
	@Autowired
	private BookManageService bookManageService;

	/**
	 * 主页面入口
	 * @param req 绑定数据传到前台
	 * @return	ftl页面
	 */
	@RequestMapping()
	public String getIndexPage(HttpServletRequest req) {
		//向ftl传数据
		req.setAttribute("title", I18N.getString("com.esen.abistudy.action.actionbook.title", "图书管理"));
		//调整ftl界面
		return "abistudy/book";
	}
	/**
	 * 分页查询图书列表
	 * @param pageIndex 页码索引
	 * @param pageSize  每页显示的条数
	 * @return 包含总记录数与当前页数据的json字符串
	 */
	@RequestMapping("/findBookList")
	@ResponseBody
	public String findBookList(int pageIndex, int pageSize) {
		Map<String, Object> map = new HashMap<>();
		PageResult<BookManagePojo> pageResult = bookManageService.findBookList(pageIndex, pageSize);
		map.put("totalCount", pageResult.getTotalCount());
		map.put("result", pageResult.list());
		return JsonUtils.toJSONString(map);
	}
	/**
	 * 查询主分类列表 在下拉框进行展示
	 * @return  包含所有主分类数据的json字符串
	 */
	@RequestMapping("/findCategoryList")
	@ResponseBody
	public String findCategoryList() {
		PageResult<BookCategoryEntity> pageResult = bookManageService.findCategoryList();
		return JsonUtils.toJSONString(pageResult.list());
	}
	/**
	 * 根据主分类id查询子分类列表
	 * @param cid  主分类id
	 * @return  包含所有子分类数据的json字符串
	 */
	@RequestMapping("/findTypeList")
	@ResponseBody
	public String findTypeList(String cid) {
		PageResult<BookTypeEntity> pageResult = bookManageService.findTypeList(cid);
		return JsonUtils.toJSONString(pageResult.list());
	}
	/**
	 * 根据添加图书对话框中的数据添加bookInfoEntity
	 * @param bookInfoEntity 封装了新加图书信息的实体类
	 * @return 添加操作结果提示信息
	 */
	@RequestMapping("/addBook")
	@ResponseBody
	public String addBook(BookInfoEntity bookInfoEntity) {
		try {
			bookManageService.addBook(bookInfoEntity);
			return I18N.getString("com.esen.abistudy.actionbook.addbooksuccessmsg","添加成功！");
		} catch (Exception e) {
			return I18N.getString("com.esen.abistudy.actionbook.addbookfailmsg","添加失败！");
		}
	}
	/**
	 * 根据新建分裂对话框中的数据添加bookTypeEntity
	 * @param bookTypeEntity 封装了分类信息的实体类
	 * @return 添加操作结果提示信息
	 */
	@RequestMapping("/addType")
	@ResponseBody
	public String addType(BookTypeEntity bookTypeEntity) {
		try {
			bookManageService.addType(bookTypeEntity);
			return I18N.getString("com.esen.abistudy.actionbook.addtypesuccessmsg","添加成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return I18N.getString("com.esen.abistudy.actionbook.addtypefailmsg","添加失败！");
		}
	}
}
