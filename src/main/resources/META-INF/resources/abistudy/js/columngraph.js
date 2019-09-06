define([ "abistudy/js/statisticalchart" ], function(statisticalchart){
	"use strict";
	var StatisticalChart = statisticalchart.StatisticalChart;
	/**
	 * 柱状图对象
	 */
	function ColumnGraph(options){
		StatisticalChart.call(this, options);
	}
	/**
	 * 继承 StatisticalChart 统计图基类
	 */
	EUI.extendClass(ColumnGraph, StatisticalChart, "ColumnGraph");
	
	/**
	 * 销毁方法
	 */
	ColumnGraph.prototype.dispose = function() {
		StatisticalChart.prototype.dispose.call(this);
    };
	/**
	 * 根据dataArray中的数据生成柱状图
	 */
    ColumnGraph.prototype.createColumnGraph = function(dataArray){
    	//先将原有HTML置空
		this.basedom.innerHTML="";
		this.basedom.style="width:100%;height:100%";
		//遍历数据数组
		for(var i=0,len=dataArray.length;i<len;i++){
			//在basedom下添加元素
			this.basedom.innerHTML+=(
					"<div class='eui-float-left' style='width:"+(1/dataArray.length)*100+"%"+";height:100%;'>" +
						"<div style='width:100%;height:70%;border-bottom:1px solid gray;position:relative'>"+
							"<div style='width:20px;height:100%;position: absolute;bottom: 0;left: 50%;transform: translateX(-50%);'>"+
								"<div style='width:100%;height:"+(100-20*dataArray[i].num)+"%;position:relative'>"+
									"<div style='width:100%;text-align:center;position: absolute;bottom: 0;'>"+dataArray[i].num+"</div>"+
								"</div>"+
								"<div style='width:100%;height:"+(20*dataArray[i].num)+"%;" +
										"background-color:"+this.setColor(i)+"'>" +
								"</div>"+				
							"</div>"+
						"</div>"+
						"<div  style='width:100%;height:30%;position:relative'>" +
							"<div style='width:40px;text-align:center;position: absolute;top: 5;left: 50%;transform: translateX(-50%);'>" +
								dataArray[i].bname+
							"</div>"+
						"</div>"+
					"</div>"
			);
		}
    }
    
	return{
		ColumnGraph:ColumnGraph
	}
})