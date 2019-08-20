package com.esen.abistudy.action;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.esen.abistudy.Server;
import com.esen.abistudy.dao.ScoreRepository;
import com.esen.abistudy.orm.entity.Result;
import com.esen.abistudy.orm.entity.ScoreEntity;
import com.esen.util.StmFunc;
import com.esen.util.StrFunc;
import com.esen.util.UNID;
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
	 */
	@RequestMapping("/uploadToVfs")
	@ResponseBody
	public Result uploadToVfs(MultipartFile file) {
		try {
			//出现异常时 返回操作失败的结果信息以及异常信息
			//int i = 1/0;
			Vfs2 vfs = server.getVfs();
			VfsOperator operator = server.getVfsOperatorAsAdmin();
			VfsFile2 import_dir = vfs.getVfsFile("/import", operator);
			//如果 /import 目录不存在则创建
			import_dir.ensureExists(true);
			// 在import目录下导入上传文件，
			InputStream is = file.getInputStream();
			//第一个参数为文件的流对象 第二个参数为文件名 这个方法在文件已存在时会覆盖已有文件
			import_dir.importStm(is, file.getOriginalFilename());	
			return new Result(true, "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "操作失败  错误信息：" + e.getMessage());
		}		
	}
	/**
	 * 将dataImport.txt导入到数据库表score中
	 */
	@RequestMapping("/importToDb")
	@ResponseBody
	public Result importToDb() {
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
			for(int i = 0; i< strings.length;i++) {
				//根据换行符进行拆分  得到每一行的字符串
				String[] lines = strings[i].split("\n");
				//获取第一行的日期字符串 yyyyMMdd格式
				String date = lines[0].split("：")[1].replace("-", "").trim();
				for(int j=2; j<lines.length -1; j++) {
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
			return new Result(true, "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "操作失败  错误信息：" + e.getMessage());
		}
	}
	
	@RequestMapping("/queryScore")
	public String queryScore() {
		return "abistudy/score";
	}
}
