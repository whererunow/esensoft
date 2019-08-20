<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>成绩查询页面</title>
<style>
	#main{
		width: 800px;
		height: 350px;
		border: 1px solid gray;
		position:absolute;
    top:50%;
    left:50%;
    margin:-175px 0 0 -400px;
	}
</style>
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
</head>
<body>
	<div id="main">
		<div style="border-bottom:solid 1px gray;height:30px;text-align:center">成绩查询</div>
		<div style="border-bottom:solid 1px gray;height:30px">
			<span style="margin-left:10px">
				<select>
					<option>2011年12月</option>
				</select>
			</span>
			<span style="margin-right:10px">
				<button> 导出 </button>
			</span>
		</div>
	</div>
</body>
</html>