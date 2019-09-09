define(["eui/modules/edialog","eui/modules/ecombobox"],function(edialog,ecombobox){
	"use strict";
	var EDialog = edialog.EDialog,EListCombobox=ecombobox.EListCombobox;
	/**
	 * 添加分类对话框对象
	 * @param options
	 * @returns AddtypeDialog对象
	 */
	function AddtypeDialog(options) {
		EDialog.call(this,options);
		this._initAddtypeDialog();
	}
	//继承Edialog类
	EUI.extendClass(AddtypeDialog, EDialog, "AddtypeDialog");
	/**
	 * 销毁方法
	 */
	AddtypeDialog.prototype.dispose=function(){
		this.categoryCombobox.dispose();
		this.categoryCombobox == null;
		
		EDialog.prototype.dispose.call(this);
	}
	/**
	 * 初始化对话框
	 */
	AddtypeDialog.prototype._initAddtypeDialog=function(){
		var self = this;
		var content = this.getContent();
		content.innerHTML = 
			"<div style='margin-top:8px'>"+
    			"<div class='eui-float-left' style='text-align:center;margin-left:71px'><font color='red'>*</font>"+I18N.getString("abistudy.js.addtype_dialog.js.cname","大类")+":</div>" +
    			"<div id='category' class='eui-float-left' style='margin-left:7px'></div>"+
    		"</div>"+
    		"<div style='margin-top:40px'>"+
    			"<label class='eui-form-label eui-form-required'>"+I18N.getString("abistudy.js.addtype_dialog.js.tname","小类")+"：</label>"+
    			"<div class='eui-input-inline'><input id='tname' type='text' class='eui-form-input'></div>"+
			"</div>";
		 //初始化大类下拉框
        this._initcategoryCombobox();	
        //点击确定时 先校验 通过后提交
        this.addButton(I18N.getString("abistudy.js.addtype_dialog.js.sure","确定"), "", false, true, function(){	
        	 if(EUI.isFunction(self.onok)){
	                self.onok();
	         }			
		});
        //点击取消关闭对话框
		this.addButton(I18N.getString("abistudy.js.addtype_dialog.js.cancel","取消"), "", false, false, function(){
			self.close();
		});
	}
	
	AddtypeDialog.prototype.setOnOk = function(func){
		this.onok = func;
	};

	/**
	 * 清除对话框已有数据
	 */
	AddtypeDialog.prototype.clearDlg = function(){
		EUI.setDomValue(this.doc.getElementById("tname"),"");
		this.categoryCombobox.clearValue();
	}
	
	
	/**
	 * 初始化大类下拉框
	 */
	AddtypeDialog.prototype._initcategoryCombobox=function(){
		var self=this;
		this.categoryCombobox = new EListCombobox({
			wnd:this.wnd,
			parentElement: this.doc.getElementById("category"),
			width: 200,
			height: "100%",
			columns:[{caption: "标题", id:"caption", width: "100%"}],
			showFilter: false,
			showCheckAll: false,
			placeholder: I18N.getString("abistudy.js.addtype_dialog.js.placeholder","请选择")
		});
		//加载数据
		EUI.post({
			url :EUI.getContextPath()+"book/findCategoryList.do",
			callback:function(q){
				var data = q.getResponseJSON();
				var datas = [];
				for(var i=0,length=data.length;i<length;i++){
					datas.push({caption:data[i].cname,value:data[i].cid});
				}
				self.categoryCombobox.setDatas(datas);
			}
		})
	}
		
	return {
		AddtypeDialog : AddtypeDialog
	}
})