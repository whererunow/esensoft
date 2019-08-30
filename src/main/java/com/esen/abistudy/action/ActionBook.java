package com.esen.abistudy.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
	 * 主页面入口
	 * @param req 绑定数据传到前台
	 * @return	ftl页面
	 */
	@RequestMapping()
	public String getIndexPage(HttpServletRequest req) {
		//向ftl传数据
		req.setAttribute("title", I18N.getString("com.esen.abistudy.action.actionbook.title","图书管理"));
		//调整ftl界面
		return "abistudy/book";
	}
}
