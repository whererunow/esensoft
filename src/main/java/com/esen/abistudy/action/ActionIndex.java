package com.esen.abistudy.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 用于返回首页
 * @author yangk
 * @since 2019年8月19日
 */
@Controller
public class ActionIndex {
	/**
	 * 返回首页
	 * @return  首页HTML文件
	 */
	@RequestMapping("/main")
	public String index() {
		return "abistudy/main.html";
	}
}
