package com.esen.abistudy.action;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esen.abistudy.orm.entity.AnalysisChartEntity;
import com.esen.abistudy.orm.entity.StatisticalChartEntity;
import com.esen.abistudy.service.AnalysisChartService;

import com.esen.util.JsonUtils;

/**
 * 分析表action
 * @author yangk
 * @since 2019年9月5日
 */
@Controller
@RequestMapping("/analysisChart")
public class ActionAnalysisChart {

	/**
	 * 注入分析表服务对象
	 */
	@Autowired
	private AnalysisChartService analysisChartService;

	/**
	 * 生成分析表  
	 * @param analysisChartEntity 包含分析表名 统计图id 以及关联数据
	 * @return 执行结果提示信息
	 * @throws Exception 
	 */
	@RequestMapping("/createAnalysisChart")
	@ResponseBody
	public void createAnalysisChart(AnalysisChartEntity analysisChartEntity) throws Exception {
		analysisChartService.addAnalysisChart(analysisChartEntity);
	}

	/**
	 * 查询统计图表中的所有记录 在生成分析记录的对话框的下拉框中显示
	 * @return 统计图表中所有记录的json字符串格式
	 */
	@RequestMapping("/findStatisticalChartList")
	@ResponseBody
	public String findStatisticalChartList() {
		Collection<StatisticalChartEntity> list = analysisChartService.findStatisticalChartList();
		return JsonUtils.toJSONString(list);
	}

	/**
	 * 查询分析表中的所有记录 在分析表管理树节点下显示
	 * @return 分析表中所有记录的json字符串格式
	 */
	@RequestMapping("/findAnalysisChartList")
	@ResponseBody
	public String findAnalysisChartList() {
		Collection<AnalysisChartEntity> analysisChartList = analysisChartService.findAnalysisChartList();
		return JsonUtils.toJSONString(analysisChartList);
	}

}
