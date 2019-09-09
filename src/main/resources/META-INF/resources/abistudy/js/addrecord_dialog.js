define(["eui/modules/edialog","eui/modules/ecombobox","eui/modules/ealmanac"],function(edialog,ecombobox,ealmanac){
	"use strict";
	var EDialog = edialog.EDialog,EListCombobox=ecombobox.EListCombobox,
		ECalendarCombobox = ealmanac.ECalendarCombobox;
	/**
	 * 添加记录对话框对象
	 * @param options
	 * @returns AddrecordDialog
	 */
	function AddrecordDialog(options) {
		EDialog.call(this,options);
		this.book = options.book;
		this._initAddrecordDialog();
	}
	//继承Edialog类
	EUI.extendClass(AddrecordDialog, EDialog, "AddrecordDialog");
	/**
	 * 销毁方法
	 */
	AddrecordDialog.prototype.dispose=function(){
		this.bookNameCombobox.dispose();
		this.bookNameCombobox == null;
		
		this.fromdateCombobox.dispose();
		this.fromdateCombobox == null;
		
		this.todateCombobox.dispose();
		this.todateCombobox == null;
		
		EDialog.prototype.dispose.call(this);
	}
	/**
	 * 初始化对话框
	 */
	AddrecordDialog.prototype._initAddrecordDialog=function(){
		var self = this;
		var content = this.getContent();
		content.innerHTML=
			"<div style='margin-top:8px'>"+
				"<div class='eui-float-left' style='text-align:center;margin-left:71px'><font color='red'>*</font>"+I18N.getString("abistudy.js.addrecord_dialog.js.bookname","书名")+":</div>" +
				"<div id='record-bname' class='eui-float-left' style='margin-left:7px'></div>"+
			"</div>"+
			"<div style='margin-top:40px'>"+
				"<label class='eui-form-label eui-form-required'>"+I18N.getString("abistudy.js.addrecord_dialog.js.person","借阅人")+"：</label>"+
				"<div class='eui-input-inline'><input id='person' type='text' class='eui-form-input'></div>"+
			"</div>"+
			"<div style='margin-top:8px'>"+
				"<div class='eui-float-left' style='text-align:center;margin-left:46px'><font color='red'>*</font>"+I18N.getString("abistudy.js.addrecord_dialog.js.fromdate","借阅日期")+":</div>" +
				"<div id='fromdate' class='eui-float-left' style='margin-left:7px'></div>"+
			"</div>"+
			"<div style='margin-top:40px'>"+
				"<div class='eui-float-left' style='text-align:center;margin-left:46px'><font color='red'>*</font>"+I18N.getString("abistudy.js.addrecord_dialog.js.todate","归还日期")+":</div>" +
				"<div id='todate' class='eui-float-left' style='margin-left:7px'></div>"+
			"</div>";
		//初始化书名下拉框
		this._initbookNameCombobox();
		//初始化借阅日期下拉框
		this._initfromdateCombobox();
		//初始化归还日期下拉框
		this._inittodateCombobox();
		//点击确定时获取对话框数据 先校验 通过后提交
		this.addButton(I18N.getString("abistudy.js.addrecord_dialog.js.sure","确定"), "", false, true, function(){	
			if(EUI.isFunction(self.onok)){
                self.onok();
			}				
		});
		this.addButton(I18N.getString("abistudy.js.addrecord_dialog.js.cancel","取消"), "", false, false, function(){
			self.close();
		});
	}
	
	AddrecordDialog.prototype.setOnOk = function(func){
		this.onok = func;
	};
	/**
	 * 清除对话框中的数据记录
	 */
	AddrecordDialog.prototype.clearDlg =function(){
		EUI.setDomValue(this.doc.getElementById("person"),"");
		this.bookNameCombobox.clearValue();
		this.fromdateCombobox.setValue(EUI.formatDate(new Date(),"yyyyMMdd"));
		this.todateCombobox.setValue(EUI.formatDate(new Date(),"yyyyMMdd"));
	}

	/**
	 * 初始化借阅日期下拉框
	 */
	AddrecordDialog.prototype._initfromdateCombobox=function(){
		this.fromdateCombobox = new ECalendarCombobox({
			parentElement:this.doc.getElementById("fromdate"),
			width:200
		});
	}
	/**
	 * 初始化借阅日期下拉框
	 */
	AddrecordDialog.prototype._inittodateCombobox=function(){
		this.todateCombobox = new ECalendarCombobox({
			parentElement:this.doc.getElementById("todate"),
			width:200
		});

	}
	/**
	 * 初始化书名下拉框
	 */
	AddrecordDialog.prototype._initbookNameCombobox=function(){
		var self=this;
		this.bookNameCombobox = new EListCombobox({
			wnd:this.wnd,
			parentElement: this.doc.getElementById("record-bname"),
			width: 200,
			height: "100%",
			columns:[{caption: "标题", id:"caption", width: "100%"}],
			showFilter: false,
			showCheckAll: false,
			placeholder: I18N.getString("abistudy.js.addrecord_dialog.js.placeholder","请选择")
		});
		//加载数据
		EUI.post({
			url :EUI.getContextPath()+"record/findBookNameList.do",
			callback:function(q){
				var data = q.getResponseJSON();
				var datas = [];
				for(var i=0,length=data.length;i<length;i++){
					datas.push({caption:data[i].bname,value:data[i].bid});
				}
				self.bookNameCombobox.setDatas(datas);
			}
		})
	}
	
	return {
		AddrecordDialog : AddrecordDialog
	}
})