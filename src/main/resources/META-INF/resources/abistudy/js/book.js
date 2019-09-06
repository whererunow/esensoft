define(
		[ "eui/modules/elist", "eui/modules/epanelsplitter",
				"eui/modules/etabctrl", "eui/modules/etree" ,
				"eui/modules/epagebar"],
		function(elist, epanelsplitter, etabctrl, etree,epagebar) {
			//在模块内定义变量来接收引入模块中的对象
			var EList = elist.EList, 
				EPanelSplitter = epanelsplitter.EPanelSplitter,
				ETabCtrl = etabctrl.ETabCtrl, 
				ETree = etree.ETree,
				EPageBar = epagebar.EPageBar;
			/**
			 * 主界面对象
			 */
			function Book(options) {
				this.wnd = options.wnd || window;
				this.doc = this.wnd.document;
				this._initBook();
			}

			/**
			 * 初始化dom
			 */
			Book.prototype._initBook = function() {
				//初始化分割面板组件
				this._initEPanelSplitter();
				//初始化树
				this._initETree();				
			}
			
			/**
			 * 销毁方法
			 */
			Book.prototype.dispose = function() {
							
				if(this.bookepagebar!=null){
					this.bookepagebar.dispose();
					this.bookepagebar = null;
				}
				
				if(this.recordepagebar!=null){
					this.recordepagebar.dispose();
					this.recordepagebar = null;
				}
				
				if(this.recordList!=null){
					this.recordList.dispose();
					this.recordList = null;
				}
				
				if(this.bookList!=null){
					this.bookList.dispose();
					this.bookList = null;
				}
				
				if(this.addbookDialog!=null){
					this.addbookDialog.dispose();
					this.addbookDialog = null;
				}
				
				if(this.addtypeDialog!=null){
					this.addtypeDialog.dispose();
					this.addtypeDialog = null;
				}
				
				if(this.createACDialog!=null){
					this.createACDialog.dispose();
					this.createACDialog = null;
				}
				
				if(this.addrecordDialog!=null){
					this.addrecordDialog.dispose();
					this.addrecordDialog = null;
				}
				
				if(this.barGraph!=null){
					this.barGraph.dispose();
					this.barGraph = null;
				}
				
				if(this.columnGraph!=null){
					this.columnGraph.dispose();
					this.columnGraph = null;
				}
				
				this.etree.dispose();
				this.etree = null;
				
				this.etabctrl.dispose();
				this.etabctrl = null;
				
				this.epanelsplitter.dispose();
				this.epanelsplitter = null;				
			};

			/**
			 * 初始化分割面板
			 */
			Book.prototype._initEPanelSplitter = function() {
				this.epanelsplitter = new EPanelSplitter({
					wnd : this.wnd,
					ishorizontal : true,
					fixedright : false,
					container : EUI.getFirstChild(this.doc
							.getElementById("epanelsplitter_dom")),
					fixedSize : "12%",
					width : "100%",
					height : "100%"
				});
			};

			/**
			 * 初始化树
			 */
			Book.prototype._initETree = function() {
				var self = this;
				this.etree = new ETree({
					wnd : this.wnd,
					parentElement : this.doc.getElementById("etree"),
					width : "100%",
					hieght : "100%",
				});
				//设置树的字体颜色
				this.etree.setItemColor("#dfdfdf");
				//添加默认存在树节点
				var rootItem = this.etree.getRootItem();
				var root = rootItem.appendChild(I18N.getString(
						"abistudy.js.book.js.server", "服务器"));
				var bookManageItem = root.appendChild(I18N.getString(
						"abistudy.js.book.js.bookmanage", "图书管理"));
				var recordQueryItem = root.appendChild(I18N.getString(
						"abistudy.js.book.js.recordquery", "记录查询"));
				//点击图书管理树节点时改变标签页 或添加 或切换
				bookManageItem.setOnClick(function(item) {
					self.changeTabCtrl(item);							
				});				
				//点击记录查询树节点时改变标签页 或添加 或切换
				recordQueryItem.setOnClick(function(item) {
					self.changeTabCtrl(item);
				});
				var analysisChartManageItem = root.appendChild(I18N.getString("abistudy.js.book.js.acmanage",
						"分析表管理"));
				//加载分析表管理下的分析表节点
				self.loadAnalysisChart(analysisChartManageItem);
			};
			
			/**
			 * 加载分析表管理下的分析表节点
			 */
			Book.prototype.loadAnalysisChart = function(analysisChartManageItem){
				var self = this;
				EUI.post({
					url :EUI.getContextPath()+"analysisChart/findAnalysisChartList.do",
					callback:function(q){						
						var data = q.getResponseJSON();
						//加载前先清空分析表管理下的所有子节点
						analysisChartManageItem.clearChildren();
						for(var i=0,len=data.length;i<len;i++){
							var analysisChartItem = analysisChartManageItem.appendChild(data[i].acname);
							analysisChartItem.setUserObj(data[i]);
							analysisChartItem.setOnClick(function(item){
								self.changeTabCtrl(item);
							})
						}
					}
				})
			}
			/**
			 * 初始化标签页
			 */
			Book.prototype._initETabCtrl = function() {
				var self = this;
				this.etabctrl = new ETabCtrl({
					wnd : this.wnd,
					parentElement : this.doc.getElementById("etabctrl"),
					enableclosed : true,
					width : "100%",
					height : "100%"
				});	
				//当标签页进行切换后 改变左侧树的被选择项
				this.etabctrl.setOnSwitched(function(index) {
					var item = self.etabctrl.getData(index,"item");
					self.etree.selectItemSingleMode(item,false);
				});
			};
			
			/**
			 * 点击树节点对标签页进行改变
			 */
			Book.prototype.changeTabCtrl = function(item) {
				var self = this;
				//获取树节点的文本内容
				var caption = item.getItemPlainText();
				if (this.etabctrl == null) {
					this._initETabCtrl();
				}			
				//遍历标签页 如果有了标题为当前点击树节点文本内容的标签页 将flag置为true 为false代表当前还没有该标签页
				var flag = false;
				for (var i = 0, count = this.etabctrl.getCount(); i < count; i++) {
					if (this.etabctrl.getCaption(i) == caption) {
						flag = true;
						break;
					}
				}
				//目前标签栏没有该标签 则添加
				if (!flag) {
					var index = this.etabctrl.add(caption, "", {
						data:{"item":item},
						resetactive : true
					});
					//如果是图书管理标签
					if (caption == "图书管理") {
						this.etabctrl.getBodyDom(index).innerHTML = 
							"<div style='width:100%;height:100%'>"+ 
								"<div style='width:100%;height:30px'>" +
									"<button id='addBook' class='eui-btn eui-btn-m' type='button' >"+I18N.getString("abistudy.js.book.js.addbook","添加图书")+"</button>" +
											" <button id='addType' class='eui-btn eui-btn-m' type='button' >"+I18N.getString("abistudy.js.book.js.addtype","添加图书分类")+"</button>" +
								 "</div>"+
								 "<div id='bookManageList' style='width:100%;height:90%'></div>"+ 
								 "<div id='bookManageEpagebar' style='width:100%;height:5%;text-align:center'></div>"+
							"</div>";
						//为图书管理页面的按钮绑定点击事件
						self.onClickBookButton();	
						//初始化图书管理列表
						self._initBookList();
						//加载图书管理列表数据
						self.showBookList(0,14,true);
					} else if (caption == "记录查询") {
						this.etabctrl.getBodyDom(index).innerHTML = 
							"<div style='width:100%;height:100%'>" +
								"<div style='width:100%;height:30px'>" +
									"<button id='createAnalysisTable' class='eui-btn eui-btn-m' type='button' >"+I18N.getString("abistudy.js.book.js.createanalysischart","生成分析表")+"</button> " +
											"<button id='addRecord' class='eui-btn eui-btn-m' type='button' >"+I18N.getString("abistudy.js.book.js.addrecord","添加记录")+"</button>" +
								"</div>"+
								"<div id='bookRecordList' style='width:100%;height:90%'></div>"+ 
								"<div id='bookRecordEpagebar' style='width:100%;height:5%;text-align:center'></div>"+
							"</div>";
						//为记录查询页面的按钮绑定点击事件
						self.onClickRecordButton();
						//初始化记录查询列表
						self._initRecordList();						
						//加载记录查询列表数据
						self.showRecordList(0,14,true);
						//如果是分析表
					}else if(item.getUserObj().scid == "001" || item.getUserObj().scid == "002"){
						//设置标签内容
						this.etabctrl.getBodyDom(index).innerHTML="<div id='"+item.getUserObj().acid+"' " +
								"style='width:100%;height:100%'></div>"
						//初始化统计图
						this._initStatisticalChart(item.getUserObj());
					}
				} else {
					//已经有了该标签则切换该标签页为活跃状态
					this.etabctrl.setActive(this.etabctrl.getIndex(caption));
				}
			}
			
			/**
			 * 初始化统计图 data包含分析表id，名称，统计图类型 所需展示数据
			 */
			Book.prototype._initStatisticalChart = function(data){
				var self = this;
				//如果是条状图
				if(data.scid == "001"){
					require(["abistudy/js/bargraph"],function(bargraph){
						//如果barGraph不存在或者其父元素发生了变化则初始化
						if(self.barGraph == null || self.barGraph.parentElement !=self.doc.getElementById(data.acid)){
							self.barGraph = new bargraph.BarGraph({
								wnd:self.wnd,
								parentElement:self.doc.getElementById(data.acid),
								width:"100%",
								height:"100%"
							});
							//根据需要展示的数据生成条形图
							self.barGraph.createBarGraph(EUI.parseJson(data.data));
						}				
					})
					//如果是柱状图
				}else if(data.scid == "002"){
					require(["abistudy/js/columngraph"],function(columngraph){
						//如果columnGraph不存在或者其父元素发生了变化则初始化
						if(self.columnGraph == null || self.columnGraph.parentElement !=self.doc.getElementById(data.acid)){
							self.columnGraph = new columngraph.ColumnGraph({
								wnd:self.wnd,
								parentElement:self.doc.getElementById(data.acid),
								width:"100%",
								height:"100%"
							});
							//根据需要展示的数据生成柱状图
							self.columnGraph.createColumnGraph(EUI.parseJson(data.data));
						}				
					})
				}				
			}
			/**
			 * 为图书管理页面的按钮绑定点击事件
			 */
			Book.prototype.onClickBookButton = function(){
				var self=this;
				//点击添加图书时生成添加图书对话框
				EUI.addEvent(this.doc.getElementById("addBook"),"click",function(){				
						require(["abistudy/js/addbook_dialog"],function(addbook_dialog){
							if(self.addbookDialog == null){
								self.addbookDialog = new addbook_dialog.AddbookDialog({
									wnd: EUI.getRootWindow(),
									width:400,
									height:300,
									caption:I18N.getString("abistudy.js.book.js.newbook","新建图书"),
									canResizable:false,
									book:self
								});
							}
							//弹出对话框
							self.addbookDialog.showModal();
							//关闭对话框时先清除对话框数据 再重新加载数据
							self.addbookDialog.setOnClose(function(){
								self.addbookDialog.clearDlg();
							});
						})
					
				});
				//点击添加分类时弹出添加分类对话框
				EUI.addEvent(this.doc.getElementById("addType"),"click",function(){
					require(["abistudy/js/addtype_dialog"],function(addtype_dialog){
						if(self.addtypeDialog == null){
							self.addtypeDialog = new addtype_dialog.AddtypeDialog({
								wnd: EUI.getRootWindow(),
								width:300,
								height:200,
								caption:I18N.getString("abistudy.js.book.js.newtype","新建分类"),
								canResizable:false
							});
						}
						self.addtypeDialog.showModal();
						self.addtypeDialog.setOnClose(function(){
							self.addtypeDialog.clearDlg();
						});
					})
				});
			}			
											
			/**
			 * 为记录查询页面的按钮绑定点击事件
			 */
			Book.prototype.onClickRecordButton = function(){
				var self=this;
				EUI.addEvent(this.doc.getElementById("createAnalysisTable"),"click",function(){	
					//获取分析表所需要的有关被选中记录的信息 绑在book对象中
					self.analysisChartData = self.getAnalysisChartData();
					//如果没有被选中的记录则提示 并直接return
					if(self.analysisChartData.length == 0){
						alert("请先勾选需要生成分析表的数据");
						return;
					}
					require(["abistudy/js/create_analysischart_dialog"],function(create_analysischart_dialog){
						if(self.createACDialog == null){
							self.createACDialog = new create_analysischart_dialog.CreateACDialog({
								wnd: EUI.getRootWindow(),
								width:300,
								height:200,
								caption:I18N.getString("abistudy.js.book.js.newanalysischart","生成分析表"),
								canResizable:false,							
								book:self
							});
						}
						self.createACDialog.showModal();
						self.createACDialog.setOnClose(function(){
							self.createACDialog.clearDlg();						
						});
					})
				});
				EUI.addEvent(this.doc.getElementById("addRecord"),"click",function(){
					require(["abistudy/js/addrecord_dialog"],function(addrecord_dialog){
						if(self.addrecordDialog == null){
							self.addrecordDialog = new addrecord_dialog.AddrecordDialog({
								wnd: EUI.getRootWindow(),
								width:300,
								height:250,
								caption:I18N.getString("abistudy.js.book.js.addrecord","添加记录"),
								canResizable:false,
								book:self
							});
						}
						self.addrecordDialog.showModal();
						self.addrecordDialog.setOnClose(function(){
							self.addrecordDialog.clearDlg();
						});
					})
				});
			}
			
			/**
			 * 获取recordList中被选中生成分析表的数据
			 */
			Book.prototype.getAnalysisChartData=function(){
				//获取所有被选中行的数据存放到data中 格式为{bname:"书名",num:1}
				var data=[];
				for(var i=0,len_i=this.checkedRowsInfo.length;i<len_i;i++){
					var checkDatas = this.checkedRowsInfo[i].checkDatas;
					if(checkDatas == null || checkDatas.length == 0){
						continue;
					}
					for(var j=0,len_j=checkDatas.length;j<len_j;j++){
						data.push({bname:checkDatas[j].bname,num:1});
					}
				}
				//合并data中书名相同的项 个数以num显示
				for(var i=0;i<data.length-1;i++){
					for(var j=i+1;j<data.length;j++){
						if(data[i].bname==data[j].bname){
							data.splice(j,1);
							j--;
							data[i] = {bname:data[i].bname,num:(data[i].num + 1)};
						}
					}
				}
				return data;
			}
			/**
			 * 初始化图书管理列表
			 */
			Book.prototype._initBookList = function() {
				this.bookList = new EList({
					parentElement : this.doc.getElementById("bookManageList"),
					width : "100%",
					height : "100%",
					columnResize : true,
					autoTotalWidth : false,
					baseCss : "eui-elist-parity",
					columns : [ {
						caption : I18N.getString("abistudy.js.book.js.bid","书号"),
						width : "25%",
						id : "bid",
						hint : true
					}, {
						caption : I18N.getString("abistudy.js.book.js.bname","书名"),
						width : "25%",
						id : "bname",
						hint : true
					}, {
						caption : I18N.getString("abistudy.js.book.js.cname","大类"),
						width : "25%",
						id : "cname",
						hint : true
					}, {
						caption : I18N.getString("abistudy.js.book.js.tname","小类"),
						width : "25%",
						id : "tname",
						hint : true
					} ]
				});
			}
			
			/**
			 * 初始化记录查询列表
			 */
			Book.prototype._initRecordList = function() {
				var self = this;
				this.recordList = new EList({
					parentElement : this.doc.getElementById("bookRecordList"),
					width : "100%",
					height : "100%",
					columnResize : true,
					autoTotalWidth : false,
					baseCss : "eui-elist-parity",
					onCheck : function(){
						self.onCheckRow();
					},
					columns : [{
			            checkbox: true
			        }, {
						caption :  I18N.getString("abistudy.js.book.js.bname","书名"),
						width : "25%",
						id : "bname",
						hint : true
					}, {
						caption :  I18N.getString("abistudy.js.book.js.person","借阅人"),
						width : "25%",
						id : "person",
						hint : true
					}, {
						caption :  I18N.getString("abistudy.js.book.js.fromdate","借阅日期"),
						width : "25%",
						id : "fromdate",
						hint : true
					}, {
						caption :  I18N.getString("abistudy.js.book.js.todate","还书日期"),
						width : "25%",
						id : "todate",
						hint : true
					} ]
				});
			}
			
			//定义一个全局变量存储选中行信息 包括被选中行所在的pageIndex 和被选中行的rowIndex
			Book.prototype.checkedRowsInfo=[];
			/**
			 * 当勾选recordList的复选框时 往checkedRowsInfo中存储被选中行信息
			 */			
			Book.prototype.onCheckRow = function(){
				//获取当前页的索引
				var pageIndex = this.recordepagebar.paramobj.pageIndex;
				//获取被选中的行
				var checkRows = this.recordList.getCheckRows();
				//获取被选中行中的数据
				var checkDatas = this.recordList.getCheckDatas();
				//获取当前页中所有被选中行的rowIndex 形成数组
				var checkedRowIndexs = [];
				if(checkRows!=null){
					for(var i=0,length=checkRows.length;i<length;i++){
						checkedRowIndexs.push(checkRows[i].rowIndex);
					}
				}			
				//判断存储选中行信息的数组中是否包含了当前页 如果包含则覆盖 否则新加元素
				for(var i=0,length=this.checkedRowsInfo.length;i<length;i++){
					if(this.checkedRowsInfo[i].pageIndex == pageIndex){
						this.checkedRowsInfo[i] = {
								pageIndex:pageIndex,
								checkedRowIndexs: checkedRowIndexs,
								checkDatas:checkDatas};
						return;
					}
				}
				this.checkedRowsInfo.push({
					pageIndex:pageIndex,
					checkedRowIndexs: checkedRowIndexs,
					checkDatas:checkDatas});					
			}
			
			/**
			 * 获取图书管理列表list
			 * @param pageIndex 页码索引
			 * @param pageSize 每页显示的条数
			 * @param isInitEpagebar 是否初始化分页栏
			 */
			Book.prototype.showBookList=function(pageIndex,pageSize,isInitEpagebar){
				var self = this;
				EUI.post({
					url :EUI.getContextPath()+"book/findBookList.do",
					data:{
						pageIndex:pageIndex,
						pageSize:pageSize
					},
					callback:function(q){
						//从后端获取list数据与分页信息
						var data = q.getResponseJSON();					
						//加载数据
						self.bookList.refreshData(data.result);
						//如果需要初始化分页条则进行初始化
						if(isInitEpagebar){
							self._initBookEpageBar(pageIndex,pageSize,data.totalCount);
						}else{//否则刷新分页条
							self.bookepagebar.resetDom(data.totalCount, pageIndex)
						}
					}
				});
			}
			
			/**
			 * 获取记录查询list数据
			 * @param pageIndex 页码索引
			 * @param pageSize 每页显示的条数
			 * @param isInitEpagebar 是否初始化分页栏
			 */
			Book.prototype.showRecordList=function(pageIndex,pageSize,isInitEpagebar){
				var self = this;
				EUI.post({
					url :EUI.getContextPath()+"record/findRecordList.do",
					data:{
						pageIndex:pageIndex,
						pageSize:pageSize
					},
					callback:function(q){
						//从后端获取list数据与分页信息
						var data = q.getResponseJSON();		
						//格式化日期
						var record_list = self.dateFormat(data.result);
						//加载数据
						self.recordList.refreshData(record_list);
						//如果需要初始化分页条则进行初始化
						if(isInitEpagebar){
							self._initRecordEpageBar(pageIndex,pageSize,data.totalCount);
						}else{//否则刷新分页条
							self.recordepagebar.resetDom(data.totalCount, pageIndex);
						}
						// 根据保存的被选中行信息 将对应的行设置为选中状态
						for(var i=0,length=self.checkedRowsInfo.length;i<length;i++){
							if(pageIndex == self.checkedRowsInfo[i].pageIndex){
								self.recordList.setCheckRows(self.checkedRowsInfo[i].checkedRowIndexs,true);
								break;
							}
						}
					}
				});
			}
			
			/**
			 * 格式化数组中的日期字符串
			 */
			Book.prototype.dateFormat = function(data){
				for(var i=0,len=data.length;i<len;i++){
					data[i].fromdate = EUI.formatDate(new Date(data[i].fromdate),"yyyy年MM月dd日 ");
					data[i].todate = EUI.formatDate(new Date(data[i].todate),"yyyy年MM月dd日");
				}
				return data;
			}
			
			/**
			 * 初始化图书管理分页条
			 */
			Book.prototype._initBookEpageBar = function(pageIndex,pageSize,totalCount){
				var self = this;
				this.bookepagebar = new EPageBar({
					parentElement: document.getElementById("bookManageEpagebar"),
					paramobj: {
						style: 'text',
						pageSize: pageSize,
						totalCount: totalCount,
						pageIndex: pageIndex
					},
					onshowpage: function(pageIndex, pageSize, userdata){
						//翻页事件	
						self.showBookList(pageIndex, pageSize,false);
					}
				});
			}
			
			/**
			 * 初始化记录查询分页条
			 */
			Book.prototype._initRecordEpageBar=function(pageIndex,pageSize,totalCount){
				var self = this;
				this.recordepagebar = new EPageBar({
					parentElement: document.getElementById("bookRecordEpagebar"),
					paramobj: {
						style: 'text',
						pageSize: pageSize,
						totalCount: totalCount,
						pageIndex: pageIndex
					},
					onshowpage: function(pageIndex, pageSize, userdata){					
						//翻页事件
						self.showRecordList(pageIndex, pageSize,false);					
					}
				});
			}					
									
			return {
				Book : Book
			}
		})