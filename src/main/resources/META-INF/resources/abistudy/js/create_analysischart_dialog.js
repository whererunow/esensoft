define(["eui/modules/edialog","eui/modules/ecombobox"],function(edialog,ecombobox){
	"use strict";
	var EDialog = edialog.EDialog,EListCombobox=ecombobox.EListCombobox;
	/**
	 * 生成分析表对话框
	 */
	function CreateACDialog(options) {
		EDialog.call(this,options);
		//将book对象传入
		this.Book = options.book;
		this._initCreateACDialog();
	}
	//继承Edialog类
	EUI.extendClass(CreateACDialog, EDialog, "CreateACDialog");
	/**
	 * 销毁方法
	 */
	CreateACDialog.prototype.dispose=function(){
		this.StatisticalChartCombobox.dispose();
		this.StatisticalChartCombobox == null;
		
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
        this._initStatisticalChartCombobox();	
        //点击确定时 先校验 通过后提交
        this.addButton(I18N.getString("abistudy.js.create_analysischart_dialog.js.sure","确定"), "", false, true, function(){	
        	var acname = EUI.getDomValue(self.doc.getElementById("acname"));
			var scid= self.StatisticalChartCombobox.getValue();
			if(acname == null || acname == ""){
				alert(I18N.getString("abistudy.js.create_analysischart_dialog.js.tips","带*内容为必须添加"));
			}else if(scid == null || scid == ""){
				alert(I18N.getString("abistudy.js.create_analysischart_dialog.js.tips","带*内容为必须添加"));
			}else{
				self.commitDlg(acname,scid,JSON.stringify(self.Book.analysisChartData));
			}
		});
		
		this.addButton(I18N.getString("abistudy.js.create_analysischart_dialog.js.cancel","取消"), "", false, false, function(){
			self.close();
		});
	}
	/**
	 * 清除原有对话框数据
	 */
	CreateACDialog.prototype.clearDlg = function(){
		EUI.setDomValue(this.doc.getElementById("acname"),"");
		this.StatisticalChartCombobox.clearValue();
	}
	/**
	 * 提交对话框数据
	 */
	CreateACDialog.prototype.commitDlg = function(acname,scid,analysisChartData){
		var self=this;
		EUI.post({
			url :EUI.getContextPath()+"analysisChart/createAnalysisChart.do",
			data:{
				acname:acname,
				scid:scid,
				data:analysisChartData
			},
			callback:function(q){
				var result = q.getResponseText();
				//这两个对话框可以配合使用，
				EUI.showWaitDialog(I18N.getString("ES.COMMON.SAVING", "保存中"));
				//执行一些其它的操作后，需要更改提醒以及关闭提示
				EUI.hideWaitDialogWithComplete(1000, result);
				//关闭窗口
				self.close();
				//将选中提交的行清空
				self.Book.checkedRowsInfo = [];
				self.Book.showRecordList(0,14,false);
				//往分析表管理树节点下添加生成的分析表子节点
				var analysisChartManageItem = self.Book.etree.getRootItem().findItem("服务器").findItem("分析表管理");
				self.Book.loadAnalysisChart(analysisChartManageItem);
			}
		})
	}
	
	
	/**
	 * 初始化统计图下拉框
	 */
	CreateACDialog.prototype._initStatisticalChartCombobox=function(){
		var self=this;
		this.StatisticalChartCombobox = new EListCombobox({
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
				self.StatisticalChartCombobox.setDatas(datas);
			}
		})
	}
		
	return {
		CreateACDialog : CreateACDialog
	}
})