define(["eui/modules/edialog","eui/modules/ecombobox"],function(edialog,ecombobox){
	"use strict";
	var EDialog = edialog.EDialog,EListCombobox=ecombobox.EListCombobox;
	/**
	 * 生成分析表对话框
	 */
	function CreateACDialog(options) {
		EDialog.call(this,options);
		this._initCreateACDialog();
	}
	//继承Edialog类
	EUI.extendClass(CreateACDialog, EDialog, "CreateACDialog");
	/**
	 * 销毁方法
	 */
	CreateACDialog.prototype.dispose=function(){
		this.statisticalChartCombobox.dispose();
		this.statisticalChartCombobox == null;
		
		EDialog.prototype.dispose.call(this);
	}
	/**
	 * 初始化对话框
	 */
	CreateACDialog.prototype._initCreateACDialog=function(){
		var self = this;
		var content = this.getContent();
		content.innerHTML = 
			"<div style='margin-top:8px'>"+
				"<label class='eui-form-label eui-form-required'>"+I18N.getString("abistudy.js.create_analysischart_dialog.js.acname","分析表名称")+"：</label>"+
				"<div class='eui-input-inline'><input id='acname' type='text' class='eui-form-input'></div>"+
			"</div>"+
			"<div style='margin-top:8px'>"+
    			"<div class='eui-float-left' style='text-align:center;margin-left:34px'><font color='red'>*</font>"+I18N.getString("abistudy.js.create_analysischart_dialog.js.statisticalchart","统计图类型")+":</div>" +
    			"<div id='statisticalchart' class='eui-float-left' style='margin-left:7px'></div>"+
    		"</div>";
    		
		 //初始化下拉框
        this._initstatisticalChartCombobox();	
        //点击确定时 先校验 通过后提交
        this.addButton(I18N.getString("abistudy.js.create_analysischart_dialog.js.sure","确定"), "", false, true, function(){	
        	if(EUI.isFunction(self.onok)){
                self.onok();
            }
      	});
		
		this.addButton(I18N.getString("abistudy.js.create_analysischart_dialog.js.cancel","取消"), "", false, false, function(){
			self.close();
		});
	}
	
	CreateACDialog.prototype.setOnOk = function(func){
        this.onok = func;
	};
	/**
	 * 清除原有对话框数据
	 */
	CreateACDialog.prototype.clearDlg = function(){
		EUI.setDomValue(this.doc.getElementById("acname"),"");
		this.statisticalChartCombobox.clearValue();
	}
	
	
	/**
	 * 初始化统计图下拉框
	 */
	CreateACDialog.prototype._initstatisticalChartCombobox=function(){
		var self=this;
		this.statisticalChartCombobox = new EListCombobox({
			wnd:this.wnd,
			parentElement: this.doc.getElementById("statisticalchart"),
			width: 200,
			height: "100%",
			columns:[{caption: "标题", id:"caption", width: "100%"}],
			showFilter: false,
			showCheckAll: false,
			placeholder: I18N.getString("abistudy.js.create_analysischart_dialog.js.placeholder","请选择")
		});
		//加载数据
		EUI.post({
			url :EUI.getContextPath()+"analysisChart/findStatisticalChartList.do",
			callback:function(q){
				var data = q.getResponseJSON();
				var datas = [];
				for(var i=0,length=data.length;i<length;i++){
					datas.push({caption:data[i].scname,value:data[i].scid});
				}
				self.statisticalChartCombobox.setDatas(datas);
			}
		})
	}
		
	return {
		CreateACDialog : CreateACDialog
	}
})