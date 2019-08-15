package com.esen.abistudy.weektest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.junit.Test;

import com.esen.jdbc.ConnectionFactory;
import com.esen.jdbc.PoolConnectionFactory;
import com.esen.jdbc.dialect.DbDefiner;
import com.esen.util.FileFunc;
import com.esen.util.StrFunc;


/**
 * 新人作业第一周
 * @author yangk
 * @since 2019年8月14日
 */
public class WeekTest01 {
	/**
	 * 连接池工厂对象  
	 */
	private ConnectionFactory factory ;
	/**
	 * 成绩解析异常时返回的默认值
	 */
	private final int def = 999999;
	
	/**
	 * 定义表名
	 */
	private String tableName = "TbStudent";
	
	/**
	 * 主程序入口
	 */
	@Test
	public void testWeek01() {
		try {
			ConnectionFactory cf = getConnectionFactory();
			createTable(cf);
			importIntoDb(cf);
			exportToFile(cf);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将数据库的数据导出到文件
	 * @param cf 
	 */
	private void exportToFile(ConnectionFactory cf) {
		BufferedWriter bw = null;
		//確保d:\\out.txt 存在
		File file = new File("D:\\out.txt");
		try {
			FileFunc.ensureExists(file, false, true);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
			try {
				//写入头部信息
				bw.write("+------------------------------------------------------------------------------+");
				bw.newLine();
				bw.write(" |学号          |        姓名         |        班级        |        课程        |        成绩        |");
				bw.newLine();
				bw.write("+------------------------------------------------------------------------------+");
				Connection conn = null;
				try {
					conn = cf.getConnection();
					String sql = "select xh,xm,bj,kc,cj from " + tableName; 
					Statement stmt = conn.createStatement();
					try {
						ResultSet rs = stmt.executeQuery(sql);
						try {
							while(rs.next()) {
								bw.newLine();
								//每行的学号信息
								StringBuffer lineXhInfo = new StringBuffer("");
								lineXhInfo.append(" |").append(rs.getString(1)).append("    ");
								bw.write(lineXhInfo.toString());
								//每行的姓名信息
								StringBuffer lineXmInfo = new StringBuffer("");
								lineXmInfo.append("|        ").append(rs.getString(2));
								if(rs.getString(2).length() == 3) {
									lineXmInfo.append("       |");
								}if(rs.getString(2).length() == 4) {
									lineXmInfo.append("     |");
								}	
								bw.write(lineXmInfo.toString());
								//每行的班级信息
								StringBuffer lineBjInfo = new StringBuffer("");
								lineBjInfo.append("        ").append(rs.getString(3)).append("        |");
								bw.write(lineBjInfo.toString());
								//每行的课程信息
								StringBuffer lineKcInfo = new StringBuffer("");
								lineKcInfo.append("        ").append(rs.getString(4)).append("        |");
								bw.write(lineKcInfo.toString());
								//每行的成绩信息
								StringBuffer lineCjInfo = new StringBuffer("");
								lineCjInfo.append("        ").append(rs.getString(4)).append("        |");
								bw.write(lineCjInfo.toString());
							}
							bw.newLine();
							bw.write("+------------------------------------------------------------------------------+");
						}finally {
							rs.close();
						}
					}finally{
						stmt.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}finally {
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally {
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	/**
	 * 读取"D:\\大文件.txt" 的内容导入数据库
	 * @param cf
	 */
	private void importIntoDb(ConnectionFactory cf){
		BufferedReader br = null;
		try {
			Connection conn = cf.getConnection();
			try {
				String sql = "insert into " + tableName + " (xh,xm,bj,kc,cj) values (?,?,?,?,?)";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				try {
					br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("D:\\大文件.txt")),"GBK"));
					String lineInfo = null;
					//分批插入信息
					while ((lineInfo = br.readLine()) != null) {
						String[] infos = lineInfo.split(",");
						pstmt.setString(1, infos[0]);
						pstmt.setString(2, infos[1]);
						pstmt.setString(3, infos[2]);
						pstmt.setString(4, infos[3]);
						pstmt.setInt(5, StrFunc.parseInt(infos[4], def));
						pstmt.addBatch();
					}
				//批量执行
					pstmt.executeBatch();
				}finally {
					pstmt.close();
				}
			}finally {
				conn.close();
			}	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	/**
	 * 创建表
	 * @param cf
	 */
	private void createTable(ConnectionFactory cf) {
			DbDefiner dbf = cf.getDbDefiner();
			//定义字段
			dbf.defineStringField("Xh",  20, null, false, false);
			dbf.defineStringField("Xm", 20, null, false, false);
			dbf.defineStringField("Bj", 20, null, false, false);
			dbf.defineStringField("Kc", 20, null, false, false);
			dbf.defineIntField("Cj", 12, null, false, false);
			try {
				Connection conn = cf.getConnection();
				try {
					//如果表存在 则先删除在创建  如果不存在 则直接创建
					if(dbf.tableExists(conn, null, tableName)) {
						dbf.dropTable(conn, null, tableName);
					}
					dbf.createTable(conn, null, tableName);
				} finally {
					// 释放connection，将连接返回连接池
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	/**
	 * 获取数据库连接池工厂单例对象
	 * @throws Exception 
	 */
	private synchronized  ConnectionFactory getConnectionFactory() throws Exception {
		if (factory == null) {
			InputStream input = WeekTest01.class.getResourceAsStream("jdbc.conf");
			try {
				Properties p = new Properties();
				try {
					p.load(input);
				} catch (IOException e) {
					throw new RuntimeException("jdbc配置文件读取失败！");
				}
				factory = new PoolConnectionFactory(null, p);
			} finally {
				input.close();
			}

		}
		return factory;
	}
}
