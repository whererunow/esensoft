<@module.page>
	<@module.head title="${title!''}">
		<link href="/abistudy/abistudy/css/score.css" rel="stylesheet" type="text/css" />
	</@module.head>
	<@module.body>
	<div id="score_main">
		<div style="border-bottom:solid 1px gray;height:30px;text-align:center">${I18N.getString("com.esen.abistudy.score.ftl.topmessage","成绩查询")}</div>
		<div style="border-bottom:solid 1px gray;margin-bottom:-1px;height:30px">
			<span style="margin-left:10px">
				<select id="selectDate">
				<#list dateList as date>
					<option value = ${date_index}>${date}</option>
				</#list>
				</select>
			</span>
			<span style="margin-left:470px;margin-top:5px">
				<button id="export">${I18N.getString("com.esen.abistudy.score.ftl.exportbutton","导出")}</button>
			</span>
		</div>
		<div>
			<table>
				<tr style="text-align:center;background:#D3D3D3">
					<td><strong>${I18N.getString("com.esen.abistudy.score.ftl.name","姓名")}</strong></td>
					<td><strong>${I18N.getString("com.esen.abistudy.score.ftl.chinese","语文")}</strong></td>
					<td><strong>${I18N.getString("com.esen.abistudy.score.ftl.math","数学")}</strong></td>
					<td><strong>${I18N.getString("com.esen.abistudy.score.ftl.english","外语")}</strong></td>
					<td><strong>${I18N.getString("com.esen.abistudy.score.ftl.physics","物理")}</strong></td>
					<td><strong>${I18N.getString("com.esen.abistudy.score.ftl.chemistry","化学")}</strong></td>
					<td><strong>${I18N.getString("com.esen.abistudy.score.ftl.total","总分")}</strong></td>
				</tr>
				<#list scoreList as score>
				<tr>
					<td>${score.STUDENT_}</td>
					<td class="score" style="text-align:right">${score.语文}</td>
					<td class="score" style="text-align:right">${score.数学}</td>
					<td class="score" style="text-align:right">${score.外语}</td>
					<td class="score" style="text-align:right">${score.物理}</td>
					<td class="score" style="text-align:right">${score.化学}</td>
					<td style="text-align:right">${score.总分}</td>
				</tr>
				</#list>
			</table>
		</div>
	</div>
	</@module.body>
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script>
	$(document).ready(function(){
		 <#-- 当日期下拉框发生改变时 查询当前月份的成绩信息 -->
		 $("#selectDate").change(function(){
		 		var dateIndex = $("#selectDate").val();
		 		location.href="queryScore.do?dateIndex="+dateIndex;
		 })
		 <#-- 当页面重新加载时 保证日期下拉框中的选定值不变 -->
		 $.each($("#selectDate option"),function(){
		 	if($(this).attr("value") == ${dateIndex}){
		 		$(this).prop("selected",true);
		 	}
		 })
		 <#-- 根据成绩的不同显示对应的颜色 -->
		 $.each($(".score"),function(){
		 	if($(this).text()>=90){
		 		$(this).css("background","#009900");
		 		$(this).css("color","white");
		 	}
		 	if($(this).text()>=60 && $(this).text()<70){
		 		$(this).css("background","#FFFF00");
		 		$(this).css("color","black");
		 	}
		 	if($(this).text()<60){
		 		$(this).css("background","#FF0000");
		 		$(this).css("color","white");
		 	}
		 })
		 
		 $("#export").click(function(){
		 	location.href="/abistudy/actionWeekTest02/exportScore.do?dateIndex=${dateIndex}";
		 })
	})
	</script>
</@module.page>