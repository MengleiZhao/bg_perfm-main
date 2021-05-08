package com.common.system.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.io.Resources;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * 更新文件/执行sql/重启Tomcat
 * @author jgx
 *
 */
public class UpdateSystemUtil {
	
	/**
	 * 更新文件
	 * @param path 路径（项目名称后面的）
	 * @param files
	 * @param request
	 * @return fileNames
	 * @throws Exception
	 */
	public static List<String> saveOrUpdateFiles(String path, MultipartFile[] files, HttpServletRequest request) throws Exception{
		List<String> list = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); 
		if(files != null && files.length > 0){
			String operation = "";
	        String rootPath =  request.getSession().getServletContext().getRealPath("")+ "\\" +path;
	        File fileRoot = new File(path);
	        if(!fileRoot.exists()){
	        	// 判断是否存在这个目录,不存在创建 
				fileRoot.mkdirs();
			}
			for (int i = 0; i < files.length; i++) {
				if(files[i].getSize() > 0){
					String fileName = files[i].getOriginalFilename();
					File file = new File(rootPath+"\\"+fileName);
					if(file.exists()){
						file.delete();
						operation = "Update";
					}else{
						operation = "Add";
					}
					files[i].transferTo(file);
					list.add(operation+"---"+path+"\\"+fileName +"---"+sdf.format(new Date()));
				}
			}
		}
		return list;
	}
	/**
	 * 删除文件
	 * @param path 路径（项目名称后面的）
	 * @param files
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static List<String> deleteFiles(String path, MultipartFile[] files, HttpServletRequest request) throws Exception{
		List<String> list = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); 
		String rootPath =  request.getSession().getServletContext().getRealPath("")+ "\\" +path;
		if(files != null && files.length > 0){
			for (int i = 0; i < files.length; i++) {
				String fileName = files[i].getOriginalFilename();
				File file = new File(rootPath+"\\"+fileName);
				if(file.exists()){
					file.delete();
				}
				list.add("Delete---"+path+"\\"+fileName +"---"+sdf.format(new Date()));
			}
		}
		return list;
	}
	
	/**
	 * 执行sql
	 * @param sql
	 * @param pwd 如果配置文件里加密了，须填写
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static int saveOrUpdateSql(String sql, String pwd, HttpServletRequest request) throws Exception{
		Connection con = null;
		Statement stmt = null;
		int result = 0;
		try{
			Properties props = Resources.getResourceAsProperties("jdbc.properties");
			String url = props.getProperty("jdbc.url");
			String driver = props.getProperty("jdbc.driverClassName");
			String username = props.getProperty("jdbc.username");
			String password = props.getProperty("jdbc.password");
			Class.forName(driver).newInstance();
			if(StringUtils.isNotEmpty(pwd)){
				password = pwd;
			}
			con = DriverManager.getConnection(url, username, password);
			stmt = con.createStatement();
			// DDL语言返回0为成功、DML语言返回1为成功
			result = stmt.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try{
				if(stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try{
				if(con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 重启Tomcat
	 * @param time 秒
	 * @return
	 */
	public static String restartTomcat(int time) throws Exception{
		try {
			String binPath = System.getProperty("user.dir");
			Runtime run = Runtime.getRuntime();
			String path = binPath+"\\restart.bat";
			File file = new File(path);
			if(file.exists()){
				file.delete();
			}
			new UpdateSystemUtil().writeRestartFile(path, time);
			Process pro = run.exec("cmd.exe /c start "+path);
			if(pro.waitFor() == 0){
				return "Restart the success!";
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Restart the failure!";
	}
	
	private void writeRestartFile(String path, int time) throws Exception{
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter(new File(path)));
			out.write("call shutdown.bat");
		    out.newLine(); 
		    out.write("ping -n "+time+" 127.0.0.1>nul");
		    out.newLine(); 
		    out.write("call startup.bat");
		    out.newLine(); 
		    out.write("echo");
		    out.newLine(); 
		    out.write("exit");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.flush();
		        out.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}
