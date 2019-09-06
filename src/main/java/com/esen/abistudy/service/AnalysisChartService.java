package com.esen.abistudy.service;

import java.util.Collection;

import com.esen.abistudy.orm.entity.AnalysisChartEntity;
import com.esen.abistudy.orm.entity.StatisticalChartEntity;


/**
 * 分析表服务接口
 * @author yangk
 * @since 2019年9月6日
 */
public interface AnalysisChartService {
	/**
	 * 查询统计图表中的所有记录 
	 * @return 统计图表中所有记录的集合
	 */
	Collection<StatisticalChartEntity> findStatisticalChartList();
	/**
	 * 添加分析表
	 * @param analysisChartEntity 封装了分析表所需数据的实体类
	 * @throws Exception 异常
	 */
	void addAnalysisChart(AnalysisChartEntity analysisChartEntity) throws Exception;
	/**
	 * 查询分析表
	 * @return 分析表实体Collection集合
	 */
	Collection<AnalysisChartEntity> findAnalysisChartList();

}
