/**
 * 定义一个图书模块 引入其他依赖模块
 * @param elist   列表组件
 * @param epanelsplitter 分隔板组件
 * @param etabctrl  标签页组件
 * @param etree		树列表组件
 * @returns
 */
define(
		["eui/modules/elist","eui/modules/epanelsplitter",
			"eui/modules/etabctrl","eui/modules/etree"],
		function(elist,epanelsplitter,etabctrl,etree){
			"use strict";//使用严格模式
			//在模块内定义变量来接收引入模块中的对象
			var	EList = elist.EList, 
				EPanelSplitter = epanelsplitter.EPanelSplitter,
				ETabCtrl = etabctrl.ETabCtrl,
				ETree = etree.ETree;
			/**
			 * 定义一个图书类
			 */
			function Book(options) {
				this._initBook();
			}
					
			/**
			 * 初始化dom
			 */
			Book.prototype._initBook=function(){
				//初始化分割面板组件
				this._initEPanelSplitter();
				//初始化树
				this._initETree();
			}

			/**
			 * 销毁方法
			 */
			Book.prototype.dispose = function() {
				this.epanelsplitter.dispose();
				this.epanelsplitter = null;
				
				this.etree.dispose();
				this.etree=null;
				
				this.etabctrl.dispose();
				this.etabctrl=null;
			};
			
			/**
			 * 初始化分割面板
			 */
			Book.prototype._initEPanelSplitter=function(){
				this.epanelsplitter = new EPanelSplitter({
					 wnd : window,
				        ishorizontal: true,
				        fixedright: false,
				        container : EUI.getFirstChild(document.getElementById("epanelsplitter_dom")),
				        fixedSize: 180
				    });
				    var leftcontainer = this.epanelsplitter.getLeftComponentContainer(),
				        rightcontainer = this.epanelsplitter.getRightComponentContainer();
				    	leftcontainer.style+=";background-color:#093444;height:100%;width:180px";
				    	
			};
			
			/**
			 * 初始化树
			 */
			Book.prototype._initETree=function(){
				var self = this;
				this.etree = new ETree({
					wnd : this.wnd,
			        parentElement : document.getElementById("etree"),
			        width: "100%",
			        hieght: "100%",
				});	
				this.etree.setItemColor("#dfdfdf");
				var rootItem = this.etree.getRootItem();
				var root = rootItem.appendChild(I18N.getString("abistudy.js.book.js.server","服务器"));
				var bookManageItem = root.appendChild(I18N.getString("abistudy.js.book.js.server","图书管理"));
				bookManageItem.setOnClick(function(item){
					self.changeTabCtrl(item);
				});
				var recordQueryItem = root.appendChild(I18N.getString("abistudy.js.book.js.server","记录查询"));
				recordQueryItem.setOnClick(function(item){
					self.changeTabCtrl(item);
				});
				root.appendChild(I18N.getString("abistudy.js.book.js.server","分析表管理"));
			};
			/**
			 * 点击树节点对标签页进行改变
			 */
			Book.prototype.changeTabCtrl = function(item){
				var caption =  item.getItemPlainText();
				if(this.etabctrl == null){
					this._initETabCtrl();
				}
				var flag=false;
				for(var i=0;i<this.etabctrl.getCount();i++){
					if(this.etabctrl.getCaption(i) == caption){
						flag=true;
						break;
					}
				}
				//目前标签栏没有该标签 则添加
				if(!flag){
					var index = this.etabctrl.add(caption,"",{resetactive:true});
				}else{
					//已经有了该标签则切换该标签页为活跃状态
					this.etabctrl.setActive(this.etabctrl.getIndex(caption));
				}				
			}
			/**
			 * 初始化标签页
			 */
			Book.prototype._initETabCtrl=function(){
				var self = this;
				this.etabctrl = new ETabCtrl({
					wnd : window,
					parentElement : document.getElementById("etabctrl"),
					enableclosed: true
				});
				
				//当标签页进行切换后 改变左侧树的被选择项
				this.etabctrl.setOnSwitched(function(index){
					self.changeETree(self.etabctrl.getCaption(index));
				});
			}
			/**
			 * 当标签页切换后根据标签内容调整左树的高亮显示
			 */
			Book.prototype.changeETree = function(itemText){
					
			}
			
			return {
				Book : Book
			}
})