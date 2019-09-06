define(["eui/modules/edialog","eui/modules/ecombobox"],function(edialog,ecombobox){
	"use strict";
	var EDialog = edialog.EDialog,EListCombobox=ecombobox.EListCombobox;
	/**
	 * 添加图书对话框对象
	 * @param options
	 * @returns AddbookDialog
	 */
	function AddbookDialog(options) {
		EDialog.call(this,options);
		this.book = options.book;
		this._initAddbookDialog();
	}
	//继承Edialog类
	EUI.extendClass(AddbookDialog, EDialog, "AddbookDialog");
	/**
	 * 销毁方法
	 */
	AddbookDialog.prototype.dispose=function(){
		this.CategoryCombobox.dispose();
		this.CategoryCombobox == null;
		
		this.TypeCombobox.dispose();
		this.TypeCombobox == null;
		
		EDialog.prototype.dispose.call(this);
	}
	/**
	 * 初始化对话框
	 */
	AddbookDialog.prototype._initAddbookDialog=function(){
		var self = this;
		var content = this.getContent();
		//生成对话框
		content.innerHTML = 
	        	"<label class='eui-form-label eui-form-required'>"+I18N.getString("abistudy.js.addbook_dialog.js.bookname","书名")+"：</label>"+
	        	"<div class='eui-input-inline'><input id='bookName' type='text' class='eui-form-input'></div>"+
	        	"<div style='margin-top:8px'>"+
	        		"<div class='eui-float-left' style='text-align:center;margin-left:71px'><font color='red'>*</font>"+I18N.getString("abistudy.js.addbook_dialog.js.cname","大类")+":</div>" +
	        		"<div id='categoryEcombobox' class='eui-float-left' style='margin-left:7px'></div>"+
	        	"</div>"+
	        	"<div style='margin-top:40px'>"+
        			"<div class='eui-float-left' style='text-align:center;margin-left:71px'><font color='red'>*</font>"+I18N.getString("abistudy.js.addbook_dialog.js.tname","小类")+":</div>" +
        			"<div id='typeEcombobox' class='eui-float-left' style='margin-left:7px'></div>"+
        		"</div>"+
        		"<div class='eui-form-item' style='margin-top:72px'>"+
        			"<label class='eui-form-label'>"+I18N.getString("abistudy.js.addbook_dialog.js.briefintroduction","图书简介")+"：</label>"+
        			"<div class='eui-input-block'><textarea  id='briefIntroduction' type='textarea' class='eui-form-textarea' style='width:200px' ></textarea></div>"+
        		"</div>"
        //初始化大类下拉框
        this._initCategoryCombobox();
		//初始化小类下拉框
		this._initTypeCombobox();
		//点击确定按钮时 先校验 通过后再提交
		this.addButton(I18N.getString("abistudy.js.addbook_dialog.js.sure","确定"), "", false, true, function(){	
			var bookName = EUI.getDomValue(self.doc.getElementById("bookName"));
			var cid= self.CategoryCombobox.getValue();
			var tid = self.TypeCombobox.getValue();
			if(bookName == null || bookName == ""){
				alert(I18N.getString("abistudy.js.addbook_dialog.js.tips","带*内容为必须添加"));
			}else if(cid ==null || cid==""){
				alert(I18N.getString("abistudy.js.addbook_dialog.js.tips","带*内容为必须添加"));
			}else if(tid ==null || tid==""){
				alert(I18N.getString("abistudy.js.addbook_dialog.js.tips","带*内容为必须添加"));
			}else{
				var briefIntroduction = EUI.getDomValue(self.doc.getElementById("briefIntroduction"));
				self.commitDlg(bookName,tid,briefIntroduction);
			}		
		});
		//点击取消时关闭窗口
		this.addButton(I18N.getString("abistudy.js.addbook_dialog.js.cancel","取消"), "", false, false, function(){
			self.close();
		});
	};
	/**
	 * 提交对话框 进行添加操作
	 */
	AddbookDialog.prototype.commitDlg = function(bookName,tid,briefIntroduction){
		var self=this;
		EUI.post({
			url :EUI.getContextPath()+"book/addBook.do",
			data:{
				bname:bookName,
				tid:tid,
				briefIntroduction:briefIntroduction
			},
			callback:function(q){
				var result = q.getResponseText();
				//这两个对话框可以配合使用，
				EUI.showWaitDialog(I18N.getString("ES.COMMON.SAVING", "保存中"));
				//执行一些其它的操作后，需要更改提醒以及关闭提示
				EUI.hideWaitDialogWithComplete(1000, result);
				//关闭窗口
				self.close();
				//刷新列表
				self.book.showBookList(0,14,false);
			}
		})
	}
	/**
	 * 清除对话框记录
	 */
	AddbookDialog.prototype.clearDlg=function(){
		EUI.setDomValue(this.doc.getElementById("bookName"),"");
		this.CategoryCombobox.clearValue();
		this.TypeCombobox.clearValue();
		EUI.setDomValue(this.doc.getElementById("briefIntroduction"),"");
	}	
	/**
	 * 初始化大类下拉框
	 */
	AddbookDialog.prototype._initCategoryCombobox=function(){
		var self=this;
		this.CategoryCombobox = new EListCombobox({
			wnd:this.wnd,
			parentElement: this.doc.getElementById("categoryEcombobox"),
			width: 200,
			height: "100%",
			columns:[{caption: "标题", id:"caption", width: "100%"}],
			showFilter: false,
			showCheckAll: false,
			placeholder: I18N.getString("abistudy.js.addbook_dialog.js.placeholder","请选择")
		});
		EUI.post({
			url :EUI.getContextPath()+"book/findCategoryList.do",
			callback:function(q){
				var data = q.getResponseJSON();
				var datas = [];
				for(var i=0,length=data.length;i<length;i++){
					datas.push({caption:data[i].cname,value:data[i].cid});
				}
				self.CategoryCombobox.setDatas(datas);
				self.CategoryCombobox.setOnChange(function(combox){
					self.getTypeList(combox.getValue());
				});
			}
		})
	}
	/**
	 * 初始化小类下拉框
	 */
	AddbookDialog.prototype._initTypeCombobox=function(){
		this.TypeCombobox = new EListCombobox({
			wnd:this.wnd,
			parentElement: this.doc.getElementById("typeEcombobox"),
			width: 200,
			height: "100%",
			columns:[{caption: "标题", id:"caption", width: "100%"}],
			showFilter: false,
			showCheckAll: false,
			placeholder: I18N.getString("abistudy.js.addbook_dialog.js.placeholder","请选择")
		});
	}
	/**
	 * 当大类选择改变时获取小类下拉框数据  cid为大类id
	 */
	AddbookDialog.prototype.getTypeList = function(cid){
		var self = this;
		EUI.post({
			url :EUI.getContextPath()+"book/findTypeList.do",
			data:{
				cid:cid
			},
			callback:function(q){
				var data = q.getResponseJSON();
				var datas = [];
				for(var i=0,length=data.length;i<length;i++){
					datas.push({caption:data[i].tname,value:data[i].tid});
				}
				self.TypeCombobox.setDatas(datas);
			}
		});
	}
		
	return {
		AddbookDialog : AddbookDialog
	}
})