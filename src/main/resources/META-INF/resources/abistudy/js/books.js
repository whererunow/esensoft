/**
 * 定义一个图书模块 引入其他依赖模块
 * @param elist   列表组件
 * @param epanelsplitter 分隔板组件
 * @param etabctrl  标签页组件
 * @param etree		树列表组件
 * @param statisticalchart  自定义柱状统计图组件
 * @returns
 */
define(
		["eui/modules/elist","eui/modules/epanelsplitter",
			"eui/modules/etabctrl","eui/modules/etree","abistudy/js/statisticalchart"],
		function(elist,epanelsplitter,etabctrl,etree,statisticalchart){
			"use strict";//使用严格模式
			//在模块内定义变量来接收引入模块中的对象
			var	EList = elist.EList, 
				EPanelSplitter = epanelsplitter.EPanelSplitter,
				ETabCtrl = etabctrl.ETabCtrl,
				ETree = etree.ETree,
				StatisticalChart = statisticalchart.StatisticalChart;
			/**
			 * 定义一个图书类
			 */
			function Books(options) {
				this._initBooks();
			}
					
			/**
			 * 初始化dom
			 */
			Books.prototype._initBooks=function(){
				//初始化分割面板组件
				this._initEPanelSplitter();
				//初始化树组件
				this._initETree();
				//初始化标签页组件
				this._initETabCtrl();
				//加载主分类数据
				this._loadCategoryData();
			}

			/**
			 * 销毁方法
			 */
			Books.prototype.dispose = function() {
				this.epanelsplitter.dispose();
				this.epanelsplitter = null;
				
				this.etree.dispose();
				this.etree=null;
				
				this.etabctrl.dispose();
				this.etabctrl=null;
				
				this.elist.dispose();
				this.elist=null;
				
				this.statisticalchart.dispose();
				this.statisticalchart=null;
			};
			
			/**
			 * 初始化分割面板
			 */
			Books.prototype._initEPanelSplitter=function(){
				this.epanelsplitter = new EPanelSplitter({
					wnd : window,
			        ishorizontal: true,
			        fixedright: false,
			        container : EUI.getFirstChild(document.getElementById("epanelsplitter_dom")),
			        fixedSize: 300,
				});
				//设置分隔条的位置
				this.epanelsplitter.setSplitbarPos("140%");
				//获取主容器对象
				var container = this.epanelsplitter.getContainer();
				//添加样式
				EUI.addClassName(container,"eui-outer-margin-5");
			    var leftcontainer = this.epanelsplitter.getLeftComponentContainer(),
			        rightcontainer = this.epanelsplitter.getRightComponentContainer();			    
			    leftcontainer.style+=";border:black 1px solid;width:10%;height:100%";
			    EUI.addClassName(leftcontainer,"eui-outer-margin-5");
			    EUI.addClassName(rightcontainer,"eui-outer-margin-5");		    
			};
			
			/**
			 * 初始化树
			 */
			Books.prototype._initETree=function(){
				var self=this;
				this.etree = new ETree({
					wnd : this.wnd,
			        parentElement : document.getElementById("etree"),
			        width: "100%",
			        hieght: "100%",
				});	
				//当树的节点被展开时调用findTypeByCid(item)方法
				this.etree.setOnExpand(function(item){
					self._findTypeByCid(item);
				});
			};
			
			/**
			 * 初始化标签页组件
			 */
			Books.prototype._initETabCtrl=function(){
				this.etabctrl = new ETabCtrl({
					wnd : window,
					parentElement : document.getElementById("etabctrl"),
					enableclosed: false
				})
				this.etabctrl.add(I18N.getString("abistudy.js.books.js.firstetab", "借阅记录"));
				this.etabctrl.getBodyDom(0).innerHTML = "<div id='mini' style='width:100%;height:300px'></div>";
				this.etabctrl.add(I18N.getString("abistudy.js.books.js.secondetab", "统计分析"));
				this.etabctrl.getBodyDom(1).innerHTML = "<div id='statisticalchart'></div>";
				//设置默认打开的标签页
				this.etabctrl.setActive(0);
			}
			
			/**
			 * 初始化列表 
			 */
			Books.prototype._initEList=function(){
				this.elist=new EList({
					parentElement: document.getElementById("mini"),
				  	width: "100%",
				  	height: "100%",
					columnResize: true,
					autoTotalWidth: false,
					baseCss: "eui-elist-mini",
					columns : [{
						caption : I18N.getString("abistudy.js.books.js.bname", "书名"),
						width : "20%",
						id : "bname"
					}, {
						caption : I18N.getString("abistudy.js.books.js.person", "借阅人"),
						width : "20%",
						id : "person"
					}, {
						caption : I18N.getString("abistudy.js.books.js.fromdate", "借阅时间"),
						width : "30%",
						id : "fromdate"
					} ,{
						caption : I18N.getString("abistudy.js.books.js.todate", "归还时间"),
						width : "30%",
						id : "todate"
					} ]
				});				
			};
			
			/**
			 * 初始化统计图
			 */
			Books.prototype._initStatisticalChart=function(){
				this.statisticalchart = new StatisticalChart({
					wnd:window,
					parentElement:document.getElementById("statisticalchart"),
					width:"100%",
					height:"100%"
				});
			}
			
			/**
			 * 加载主分类数据
			 */
			Books.prototype._loadCategoryData=function(){
				var self = this;
				EUI.post({
					url : EUI.getContextPath() + "books/findCategory.do",
					callback : function(q) {
						var categoryData = q.getResponseJSON();
						var rootitem = self.etree.getRootItem();
						
						for(var i=0;i<categoryData.length;i++){
							//添加节点并在节点中存储主分类数据
							var treeItem = rootitem.appendChild(categoryData[i].cname);
							treeItem.setUserObj(categoryData[i]);
							treeItem.setHasChildren(true);
						}						
					}
				});
			}		
			
			/**
			 * 展开节点时加载子分类列表
			 * item为被展开的主分类节点
			 */
			Books.prototype._findTypeByCid = function(item){
				var self=this;
				//清除已有子节点
				item.clearChildren();
				EUI.post({
					url : EUI.getContextPath() + "books/findTypeByCid.do",
					data : {
						cid:item.getUserObj().cid
					},
					callback : function(q) {
						var typeData = q.getResponseJSON();
						for(var i=0;i<typeData.length;i++){
							//添加子分类节点，并在节点上存储子分类数据
							var treeItem = item.appendChild(typeData[i].tname);
							treeItem.setUserObj(typeData[i]);
							//点击子分类时初始化list列表和统计图
							treeItem.setOnClick(function(ETreeItem,event){
								self._showList(ETreeItem,event);
								self._showStatisticalChart(ETreeItem,event);
							});
						}
					}
				});
			};
			
			/**
			 * 加载统计图
			 */
			Books.prototype._showStatisticalChart=function(ETreeItem,event){
				var self = this;
				EUI.post({
					url :EUI.getContextPath()+"books/showStatisticalChart.do",
					data:{
						tid:ETreeItem.getUserObj().tid
					},
					callback:function(q){
						var data = q.getResponseJSON();
						if(self.statisticalchart == null){
							self._initStatisticalChart();
						}
						//生成格式为:[{message:"柱状条名称",num:"数值"},...]的数据数组 
						var dataArray=[];
						for(var i=0;i<data.length;i++){
							dataArray.push({"message":data[i].bname,"num":data[i].count});
						}
						self.statisticalchart.createHistogram(dataArray);
					}
				});
			}
			
			/**
			 * 点击子分类节点时先从后端获取List中所需数据再初始化elist
			 */
			Books.prototype._showList=function(ETreeItem,event){
				var self = this;
				EUI.post({
					url :EUI.getContextPath()+"books/findBorrowBookInfo.do",
					data:{
						tid:ETreeItem.getUserObj().tid
					},
					callback:function(q){
						//从后端获取需要展示的数据
						var borrowBookData = q.getResponseJSON();
						//对日期进行格式化
						var data = self._dateFormat(borrowBookData);
						//初始化列表 
						if(self.elist == null){
							self._initEList();
						}						
						//加载数据
						self.elist.refreshData(data);	
					}
				});
			}
			/**
			 * 格式化数组中的日期字符串
			 */
			Books.prototype._dateFormat = function(data){
				for(var i=0;i<data.length;i++){
					data[i].fromdate = EUI.formatDate(new Date(data[i].fromdate),"yyyy/MM/dd HH:mm:ss");
					data[i].todate = EUI.formatDate(new Date(data[i].todate),"yyyy/MM/dd HH:mm:ss");
				}
				return data;
			}
											
			return {
				Books : Books
			}
})