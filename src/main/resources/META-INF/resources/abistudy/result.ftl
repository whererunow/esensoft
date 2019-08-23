<@module.page>
	<@module.head title="${title!''}">
	</@module.head>
	<@module.body>
		<div style = "border:2px solid black;margin-bottom:-2px ;width:100%;height:30px">
			${I18N.getString("com.esen.abistudy.result.ftl.result","操作结果")}
		</div>
		<div style="border:2px solid black;width:100%;height:500px" >
			<div id="msg_div" style="margin-top:100px;margin-left:100px">	
			${message}<br><br>
			<a href="javascript:history.back(-1)">${I18N.getString("com.esen.abistudy.result.ftl.back","返回")}</a>
			</div>
		</div>
	</@module.body>
</@module.page>