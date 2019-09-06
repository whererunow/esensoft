define([ "abistudy/js/statisticalchart" ], function(statisticalchart){
	"use strict";
	var StatisticalChart = statisticalchart.StatisticalChart;
	/**
	 * 条形图对象
	 */
	function BarGraph(options){
		StatisticalChart.call(this, options);
	}
	/**
	 * 继承 StatisticalChart 统计图对象
	 */
	EUI.extendClass(BarGraph, StatisticalChart, "BarGraph");
	
	/**
	 * 销毁方法
	 */
	BarGraph.prototype.dispose = function() {
		StatisticalChart.prototype.dispose.call(this);
    };
	/**
	 * 根据数据生成条形图
	 */
    BarGraph.prototype.createBarGraph = function(dataArray){
    	//先将原有HTML置空
		this.basedom.innerHTML="";
		this.basedom.style="width:100%;height:100%";
		//遍历数据数组
		for(var i=0,len=dataArray.length;i<len;i++){
			//在basedom下添加元素
			this.basedom.innerHTML+=(
					"<div style='height:"+(1/dataArray.length)*100+"%"+";width:100%;'>" +
						"<div class='eui-float-left' style='width:30%;height:100%;'>"+
							"<div style='width:100%;position: relative; top: 50%; transform: translateY(-50%);text-align:right'>"+dataArray[i].bname+"</div>" +
						"</div>"+
						"<div  class='eui-float-left'  style='width:70%;height:100%;'>" +
							"<div  class='eui-float-left' style='height:20px;width:"+(20*dataArray[i].num)+"%;background-color:" + this.setColor(i) +
									";margin-left:10px;position: relative; top: 50%; transform: translateY(-50%);'></div>"+
							"<div  class='eui-float-left' style='margin-left:10px;position: relative; top: 50%; transform: translateY(-50%);'>"+dataArray[i].num+"</div>"+
						"</div>"+
					"</div>"
			);
		}
    }
    
	return{
		BarGraph:BarGraph
	}
})