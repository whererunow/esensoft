<@module.page>
	<@module.head title="${title!''}">
	</@module.head>
	<@module.body>
		<div style = "border:2px solid black;margin-bottom:-2px ;width:100%;height:30px">
  	${I18N.getString("com.esen.abistudy.main.ftl.result","首页")}
		</div>
		<div style="border:2px solid black;width:100%;height:500px" >
			<div style="margin-top:100px;margin-left:100px">
				<a href="javascript:document.getElementById('uploadFile').click();">
				${I18N.getString("com.esen.abistudy.main.ftl.firstlink","1：上传到VFS")}
				</a><br>
				<a href="actionWeekTest02/importToDb.do">
				${I18N.getString("com.esen.abistudy.main.ftl.secondlink","2：从VFS导入")}
				</a><br>
				<a href="javascript:queryScore();">
				${I18N.getString("com.esen.abistudy.main.ftl.thirdlink","3：进入查询列表")}
				</a><br>
				<form id ="uploadForm" action="actionWeekTest02/uploadToVfs.do" method="post" enctype="multipart/form-data">
					<input type="file" id="uploadFile" name="file" style="visibility:hidden" >
				</form>
			</div>
		</div>
	</@module.body>
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script type="text/javascript">
		//当上传文件的文本框发生变化时 发送请求进行文件上传vfs的操作
		$("#uploadFile").change(function(){
		$("#uploadForm").submit();
	})
		//查询成绩 默认的dateIndex=0查询最后月份的数据
		var queryScore = function(){
			location.href="actionWeekTest02/queryScore.do?dateIndex="+0;
		}
	</script>
</@module.page>