<@module.page>
	<@module.head title="${title}">
	</@module.head>
	<@module.body>
		<div id="epanelsplitter_dom" style="width:100%;height:100%;border:black 1px solid;">
    	<div class="eui-panelsplitter-container" style="width:90%;height:100%">
        <div id="etree" class="eui-panelsplitter-panel">      	
        </div>
        <div class='eui-panelsplitter-btn'></div>
        <div class="eui-panelsplitter-panel">
        	<div id= "etabctrl"></div>	
        </div>
    	</div>
		</div>
	</@module.body>
	<script>
		require(["abistudy/js/books"], function(books){
			var mainPage = new books.Books({
				wnd : window
			});
		});
	</script>
</@module.page>