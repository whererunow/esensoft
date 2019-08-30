<@module.page>
	<@module.head title="${title!''}">
		<link href="${relpath}abistudy/css/book.css" rel="stylesheet" type="text/css">
	</@module.head>
	<@module.body>
		<div id="epanelsplitter_dom" style="width:100%;height:100%;">
    	<div class="eui-panelsplitter-container eui-row eui-col-space-4" style="width:100%;height:100%">
    		<div class="eui-panelsplitter-panel eui-col-xl-2">
    			<div id="etree" class="eui-panelsplitter-panel"></div>      	   	
    		</div>
        <div class='eui-panelsplitter-btn'></div>
        <div class="eui-panelsplitter-panel eui-col-xl-10">
        	<div id= "etabctrl"></div>
        </div>
    	</div>
		</div>
	</@module.body>
	<script>
		require(["abistudy/js/book"], function(book){
			var mainPage = new book.Book({
				wnd : window
			});
			EUI.addDispose(mainPage,window);
		});
	</script>
</@module.page>