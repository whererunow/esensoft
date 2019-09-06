<@module.page>
	<@module.head title="${title!''}">
	</@module.head>
	<@module.body>
		<div id="epanelsplitter_dom" style="width:100%;height:100%;">
    	<div class="eui-panelsplitter-container " style="width:100%;height:100%">
    		<div class="eui-panelsplitter-panel " style="background-color:#093444;height:100%;width:13%">
    			<div id="etree" class="eui-panelsplitter-panel" style="width:100%"></div>      	   	
    		</div>
        <div class='eui-panelsplitter-btn' style="width: 4px; cursor: e-resize; left: 13%;"></div>
        <div class="eui-panelsplitter-panel" style="height:100%;width:86.75%">
        	<div id= "etabctrl" style="width:100%;height:100%"></div>
        </div>
    	</div>
		</div>
	</@module.body>
	<script>
		require(["abistudy/js/book"], function(book){
			var book = new book.Book({
				wnd : window
			});
			EUI.addDispose(book,window);
		});
	</script>
</@module.page>