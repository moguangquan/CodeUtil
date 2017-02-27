package djr.ftp;

import java.io.IOException;

import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPException;
/**
 * 生成FTPClient类
 * @author Kevin
 *
 */
public class FtpFactory
{
	// 默认远程主机地址
	public static final String CST_FTP_HOST = "127.0.0.1";

	// 默认用户名
	public static final String CST_FTP_USER = "XXXX";

	// 默认密码
	public static final String CST_FTP_PASSWORD = "1";

	// 第三方FTP客户端类
	private FTPClient ftpClient;

    // 远程FTP主机地址
	private String ftpHost;

    // 用户名
	private String ftpUser;

    // 密码
	private String ftpPassword;

	public FtpFactory()
	{
		this(CST_FTP_HOST, CST_FTP_USER, CST_FTP_PASSWORD);
	}

	public FtpFactory(String ftpHost, String ftpUser, String ftpPassword)
	{
		this.ftpHost = ftpHost;
		this.ftpUser = ftpUser;
		this.ftpPassword = ftpPassword;
	}

	public FTPClient getFtpClient()
	{
		return ftpClient;
	}

    //	 取得连接到远程主机的FTPClient对象
	public FTPClient getLoginedFtpClient() throws IOException, FTPException
	{
		if (this.ftpClient == null)
		{ // 对象未创建
			init();
		}
		
		if (this.ftpClient.connected() == false)
		{ // 对象未连接
			this.ftpClient.setRemoteHost(this.ftpHost);//设置远程主机的地址
			System.out.println("ceshi");
			this.ftpClient.connect();//连接主机
			this.ftpClient.login(this.ftpUser, this.ftpPassword);//登陆主机
		}
//返回一个已经连接到远程主机并完成登陆操作的FTPClient对象
		return ftpClient;
	}

	//	 关闭FTP连接
	public static void closeFtpClient(FTPClient ftpClient) throws IOException, FTPException
	{
		if (ftpClient != null || ftpClient.connected())
		{// 处于连接状态
			ftpClient.quit();
			ftpClient = null;
		}
	}

	//	 初始化FTPClient
	private void init() throws IOException, FTPException
	{
		if (this.ftpClient == null)
		{
			this.ftpClient = new FTPClient();
			this.ftpClient.setConnectMode(FTPConnectMode.PASV);//设置为被动模式
		}
	}
	
	public void setFtpClient(FTPClient ftpClient)
	{
		this.ftpClient = ftpClient;
	}

	public String getFtpHost()
	{
		return ftpHost;
	}

	public void setFtpHost(String ftpHost)
	{
		this.ftpHost = ftpHost;
	}

	public String getFtpPassword()
	{
		return ftpPassword;
	}

	public void setFtpPassword(String ftpPassword)
	{
		this.ftpPassword = ftpPassword;
	}

	public String getFtpUser()
	{
		return ftpUser;
	}

	public void setFtpUser(String ftpUser)
	{
		this.ftpUser = ftpUser;
	}

}
