define([ "eui/modules/uibase" ], function(uibase){
	"use strict";
	var EComponent = uibase.EComponent;
	/**
	 * 统计图基类对象
	 */
	function StatisticalChart(options){
		EComponent.call(this, options);
		this.basedom;
		this._initStatisticalChart();
	}
	/**
	 * 继承 EComponent
	 */
	EUI.extendClass(StatisticalChart, EComponent, "StatisticalChart");
	
	/**
	 * 销毁方法
	 */
	StatisticalChart.prototype.dispose = function() {
        EComponent.prototype.dispose.call(this);
    };
    
	/**
	 * 初始化dom
	 */
	StatisticalChart.prototype._initStatisticalChart=function(){
		this.basedom=this.parentElement.appendChild(this.doc.createElement("div"));
	}
	/**
	 * 为统计图设置背景色
	 */
	StatisticalChart.prototype.setColor=function(i){
		var colorArr = ["green","red",'blue',"yellow"];
		return colorArr[(i%4)];
	}
		
	return{
		StatisticalChart:StatisticalChart
	}
})