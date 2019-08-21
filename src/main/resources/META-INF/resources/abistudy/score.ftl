<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>成绩查询页面</title>
<style>
	#main{
		widtd: 800px;
		border: 1px solid gray;
		position:absolute;
    top:50%;
    left:50%;
    margin:-175px 0 0 -400px;
	}
 	table,table tr th, table tr td { 
  	border:1px solid black;
  }
  table{ 
  	width:800px;
		border-collapse: collapse;
  }  
</style>
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
</head>
<body>
	<div id="main">
		<div style="border-bottom:solid 1px gray;height:30px;text-align:center">成绩查询</div>
		<div style="border-bottom:solid 1px gray;margin-bottom:-1px;height:30px">
			<span style="margin-left:10px">
				<select>
				<#list dateList as date>
					<option value = ${date_index}>${date}</option>
				</#list>
				</select>
			</span>
			<span style="margin-left:630px">
				<button> 导出 </button>
			</span>
		</div>
		<div>
			<table>
				<tr style="text-align:center;background:#D3D3D3">
					<td>姓名</td>
					<td>语文</td>
					<td>数学</td>
					<td>外语</td>
					<td>物理</td>
					<td>化学</td>
					<td>总分</td>
				</tr>
				<#list scoreList as score>
				<tr>
					<td>${score.STUDENT_}</td>
					<td style="text-align:right">${score.语文}</td>
					<td style="text-align:right">${score.数学}</td>
					<td style="text-align:right">${score.外语}</td>
					<td style="text-align:right">${score.物理}</td>
					<td style="text-align:right">${score.化学}</td>
					<td style="text-align:right">${score.总分}</td>
				</tr>
				</#list>
			</table>
		</div>
	</div>
</body>
</html>