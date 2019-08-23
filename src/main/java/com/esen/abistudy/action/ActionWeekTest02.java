package com.esen.abistudy.action;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.esen.abistudy.Server;
import com.esen.abistudy.dao.ScoreRepository;
import com.esen.abistudy.orm.entity.ScoreEntity;
import com.esen.ecore.util.jdbc.RowHandler;
import com.esen.util.FileFunc;
import com.esen.util.StmFunc;
import com.esen.util.UNID;
import com.esen.util.i18n.I18N;
import com.esen.vfs2.Vfs2;
import com.esen.vfs2.VfsFile2;
import com.esen.vfs2.VfsOperator;

/**
 * 第二周作业的action
 * @author yangk
 * @since 2019年8月19日
 */
@Controller
@RequestMapping("/actionWeekTest02")
public class ActionWeekTest02 {
	/**
	 * 注入server对象
	 */
	@Autowired
	private Server server;

	/**
	 * 注入scoreRepository对象
	 */
	@Autowired
	private ScoreRepository scoreRepository;

	/**
	 * 上传文件到vfs
	 * @param file  用于接收上传的文件对象
	 * @return 操作结果
	 */
	@RequestMapping("/uploadToVfs")
	public String uploadToVfs(HttpServletRequest req, @RequestParam("file")MultipartFile file) {
		try {
			Vfs2 vfs = server.getVfs();
			VfsOperator operator = server.getVfsOperatorAsAdmin();
			VfsFile2 importDir = vfs.getVfsFile("/import", operator);
			//如果 /import 目录不存在则创建
			importDir.ensureExists(true);
			// 在import目录下导入上传文件，
			InputStream is = file.getInputStream();
			try {
				importDir.importStm(is, file.getOriginalFilename());
			} finally {
				is.close();
			}
			req.setAttribute("message", I18N.getString("com.esen.abistudy.action.actionweektest02.successmessage", "操作成功"));
		} catch (Exception e) {
			//出现异常时 返回操作失败的结果信息以及异常信息
			req.setAttribute("message", I18N.getString("com.esen.abistudy.action.actionweektest02.failmessage",
					"操作失败  错误信息：" + e.getLocalizedMessage()));
		}
		return "abistudy/result";
	}

	/**
	 * 将dataImport.txt导入到数据库表score中
	 * @return 操作结果
	 */
	@RequestMapping("/importToDb")
	public String importToDb(HttpServletRequest req) {
		try {
			//导入前先删除原有数据
			scoreRepository.removeAll();
			Vfs2 vfs = server.getVfs();
			VfsOperator oper = server.getVfsOperatorAsAdmin();
			VfsFile2 file = vfs.getVfsFile("/import/dataImport.txt", oper);
			InputStream is = file.getInputStream();
			//获取整个文本中的字符串
			String string = StmFunc.readString(is, file.getCharset());
			//根据空行进行分割 得到每个月份的成绩信息字符串
			String[] strings = string.split("\n\r\n");
			for (int i = 0; i < strings.length; i++) {
				//根据换行符进行拆分  得到每一行的字符串
				String[] lines = strings[i].split("\n");
				//获取第一行的日期字符串 yyyyMMdd格式
				String date = lines[0].split("：")[1].replace("-", "").trim();
				for (int j = 2; j < lines.length; j++) {
					//获取成绩信息 循环插入数据库
					String[] line = lines[j].trim().split(",");
					String student = line[0];
					String course = line[1];
					String score = line[2];
					ScoreEntity entity = new ScoreEntity();
					entity.setId(UNID.randomID());
					entity.setDate(date);
					entity.setStudent(student);
					entity.setCourse(course);
					entity.setScore(Float.parseFloat(score));
					scoreRepository.add(entity);
				}
			}
			req.setAttribute("message", I18N.getString("com.esen.abistudy.action.actionweektest02.successmessage", "操作成功"));
		} catch (Exception e) {
			req.setAttribute("message", I18N.getString("com.esen.abistudy.action.actionweektest02.failmessage",
					"操作失败  错误信息：" + e.getLocalizedMessage()));
		}
		return "abistudy/result";
	}

	/**
	 * 查询成绩
	 * @param req 用来往ftl页面传递数据
	 * @param dateIndex  当前日期在日期列表中的索引
	 * @return 操作结果
	 */
	@RequestMapping("/queryScore")
	public String queryScore(HttpServletRequest req, Integer dateIndex) {
		//获取日期下拉列表 转换为页面展示所需格式
		List<String> list = getDateList();
		List<String> dateList = new ArrayList<>();
		for (String dateStr : list) {
			String date = dateStr.substring(0, 4) + "年" + dateStr.substring(4, 6) + "月";
			dateList.add(date);
		}
		req.setAttribute("dateList", dateList);
		//查询成绩列表 传递到ftl页面
		List<HashMap<String, Object>> scoreList = getScoreList(list, dateIndex);
		req.setAttribute("scoreList", scoreList);
		//将选中的日期索引也传递给ftl页面，保证下拉框中对应日期的option被选中
		req.setAttribute("dateIndex", dateIndex);
		return "abistudy/score";
	}

	/**
	 * 将成绩信息导出
	 * @param dateIndex 当前日期在日期列表中的索引
	 * @return 操作结果
	 */
	@RequestMapping("/exportScore")
	public String exportScore(HttpServletRequest req, Integer dateIndex) {
		try {
			//获取日期下拉列表
			List<String> list = getDateList();
			//获取成绩列表
			List<HashMap<String, Object>> scoreList = getScoreList(list, dateIndex);
			String date = list.get(dateIndex).substring(0, 6);
			//文件名为dataExport_yyyyMM.txt格式
			File file = new File("D:\\dataExport_" + date + ".txt");
			FileFunc.ensureExists(file, false, true);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
			try {
				//向文件中写入成绩信息
				bw.write("月份：" + date.substring(0, 4) + "年" + date.substring(4) + "月");
				bw.newLine();
				bw.write("姓名,语文,数学,外语,物理,化学,总分");
				for (HashMap<String, Object> map : scoreList) {
					bw.newLine();
					bw.write(map.get("STUDENT_").toString() + "," + map.get("语文").toString() + "," + map.get("数学").toString()
							+ "," + map.get("外语").toString() + "," + map.get("物理").toString() + "," + map.get("化学").toString() + ","
							+ map.get("总分").toString());
				}
			} finally {
				bw.close();
			}
			req.setAttribute("message", I18N.getString("com.esen.abistudy.action.actionweektest02.successmessage", "操作成功"));
		} catch (Exception e) {
			req.setAttribute("message", I18N.getString("com.esen.abistudy.action.actionweektest02.failmessage",
					"操作失败  错误信息：" + e.getLocalizedMessage()));
		}
		return "abistudy/result";
	}

	/**
	 * 获取日期下拉列表
	 * @return
	 */
	private List<String> getDateList() {
		RowHandler<String> handler = new RowHandler<String>() {
			@Override
			public String processRow(ResultSet resultset) throws SQLException {
				return resultset.getString(1);
			}
		};
		List<String> list = scoreRepository.queryAll("select distinct date_ from score t order by date_ desc", handler,
				null);
		return list;
	}

	/**
	 * 根据日期获取成绩列表
	 * @param list  日期列表
	 * @param dateIndex  当前日期在日期列表中的索引
	 * @return 成绩列表 List中装map的形式
	 */
	private List<HashMap<String, Object>> getScoreList(List<String> list, Integer dateIndex) {
		List<HashMap<String, Object>> scoreList = scoreRepository.queryForMapList(
				"select student_,语文,数学,外语,物理,化学,语文+数学+外语+物理+化学 as 总分"
						+ " from (select * from (select student_,course_,score_ from score where date_ = '" + list.get(dateIndex)
						+ "') "
						+ " pivot(max(score_) for course_ in ('语文' as 语文,'数学' as 数学,'外语' as 外语,'物理' as 物理,'化学' as 化学))) order by 总分 DESC",
				null);
		return scoreList;
	}
}
