package com.esen.abistudy.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.esen.abistudy.dao.AnalysisChartRepository;
import com.esen.abistudy.dao.StatisticalChartRepository;
import com.esen.abistudy.orm.entity.AnalysisChartEntity;
import com.esen.abistudy.orm.entity.StatisticalChartEntity;
import com.esen.abistudy.service.AnalysisChartService;
import com.esen.ecore.annotation.ApplicationService;
import com.esen.util.UNID;
/**
 * 分析表服务实现类
 * @author yangk
 * @since 2019年9月6日
 */
@ApplicationService
public class AnalysisChartServiceImpl implements AnalysisChartService {
	/**
	 * 注入statisticalChartRepository  统计图表持久层
	 */
	@Autowired
	private StatisticalChartRepository statisticalChartRepository;
	/**
	 * 注入analysisChartRepository 分析表持久层
	 */
	@Autowired
	private AnalysisChartRepository analysisChartRepository;
	/**
	 * 查询统计图表中的所有记录 
	 * @return 统计图表中所有记录的Collection集合
	 */
	@Override
	public Collection<StatisticalChartEntity> findStatisticalChartList() {
		Collection<StatisticalChartEntity> list = statisticalChartRepository.findAll();
		return list;
	}
	/**
	 * 添加分析表
	 * @param analysisChartEntity 封装了分析表所需数据的实体类
	 * @throws Exception 异常
	 */
	@Override
	public void addAnalysisChart(AnalysisChartEntity analysisChartEntity) throws Exception {
		analysisChartEntity.setAcid(UNID.randomID());
		analysisChartRepository.add(analysisChartEntity);
	}

	/**
	 * 查询分析表
	 * @return 分析表实体Collection集合
	 */
	@Override
	public Collection<AnalysisChartEntity> findAnalysisChartList() {
		Collection<AnalysisChartEntity> list = analysisChartRepository.findAll();
		// TODO Auto-generated method stub
		return list;
	}	
}
