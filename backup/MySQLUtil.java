package mo.pss.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * 用于数据库的备份操作
 * @author Kevin
 */
public class MySQLUtil {
	private volatile static MySQLUtil uniqueInstance = null;
	/**
	 * 要备份的文件名
	 */
	private String filename;
	/**
	 * 需要备份到哪个文件夹中
	 */
	private String backupDir;
	/**
	 * 需要备份的数据库名
	 */
	private String database;
	/**
	 * 数据库的用户名
	 */
	private String username;
	/**
	 * 数据库的密码
	 */
	private String password;
	
	
	private MySQLUtil(){}
	
	public static MySQLUtil getInstance() {
		if(uniqueInstance==null){
			synchronized (MySQLUtil.class) {
				if(uniqueInstance==null){
					uniqueInstance = new MySQLUtil();
				}
			}
		}
		return uniqueInstance;
	}
	/**
	 * 设置属性值
	 * @param filename 要备份的数据库的名称
	 * @param backupDir 备份的目录
	 * @param database 生成.sql文件的名称
	 * @param username 数据库用户名
	 * @param password 数据库密码
	 */
	public void setCfg(String filename,String backupDir,String database,String username,String password) {
		this.filename = filename;
		this.backupDir = backupDir;
		this.database = database;
		this.username = username;
		this.password = password;
	}
	/**
	 * 完成数据库的备份
	 */
	public void backup() {
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			String cmd = "cmd /c mysqldump -u"+username+" -p"+password+" "+database;
			Process proc = Runtime.getRuntime().exec(cmd);
			br = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			bw = new BufferedWriter(
					new FileWriter(backupDir+File.separator+filename+".sql"));
			System.out.println(backupDir+File.separator+filename);
			String str = null;
			while((str=br.readLine())!=null) {
				bw.write(str);
				bw.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(br!=null) br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(bw!=null) bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 完成数据库的恢复
	 */
	public void resume() {
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			String cmd = "cmd /c mysql -u"+username+" -p"+password+" "+database;
			Process proc = Runtime.getRuntime().exec(cmd);
			bw = new BufferedWriter(new OutputStreamWriter(proc.getOutputStream()));
			br = new BufferedReader(new FileReader(backupDir+File.separator+filename+".sql"));
			String str = null;
			while((str=br.readLine())!=null) {
				bw.write(str);
				bw.newLine();
			}
			br.close();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(br!=null) br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(bw!=null) bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
