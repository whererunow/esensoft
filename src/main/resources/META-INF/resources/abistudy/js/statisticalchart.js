/*
 * 自定义统计图模块
 */
define([ "eui/modules/uibase" ], function(uibase){
	"use strict";
	var EComponent = uibase.EComponent;
	/**
	 * 提供构造方法
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
	 * 需要一个数据数组 格式为:[{message:"柱状条名称",num:"数值"},...]  
	 * 生成柱状图
	 */
	StatisticalChart.prototype.createHistogram = function(dataArray){
		//先将原有HTML置空
		this.basedom.innerHTML="";
		//遍历数据数组
		for(var i=0;i<dataArray.length;i++){
			//在basedom下添加元素
			this.basedom.innerHTML+=(
					//根据数组中元素的个数将div水平上均等分
					"<div class='eui-float-left' style='width:"+(1/dataArray.length)*100+"%"+";height:300px;'>" +
						"<div style='width:100%;height:250px;border-bottom:1px solid black;position:relative' class='eui-align-center'>"+
						//根据数组中每个元素的num属性值生成对应高度的柱状div
							"<div  style='width:30px;height:"+(5*dataArray[i].num)+"%;border:1px solid black;border-bottom:none;" +
									"position: absolute;bottom: 0;left: 50%;transform: translateX(-50%);background-color:"+this.randomColor()+"'>" +			
							"</div>"+
						"</div>"+
						//显示底层文本信息
						"<div class='eui-align-center '>"+
							dataArray[i].message + "<br>" +"("+dataArray[i].num +")"+
						"</div>"+
					"</div>");
		}	
	}
	/**
	 * 生成随机背景色
	 */
	StatisticalChart.prototype.randomColor = function(){
      var colorArray =new Array("0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f");
      var color="#";
      for(var i=0;i<6;i++){
          color+=colorArray[Math.floor(Math.random()*16)];
      }
      return color;
	}
	
	return{
		StatisticalChart:StatisticalChart
	}
})