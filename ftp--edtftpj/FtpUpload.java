package djr.ftp;

import java.io.File;
import java.io.IOException;

import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPException;
/**
 * 将本地文件及文件夹上传到远程主机
 * @author Kevin
 *
 */
public class FtpUpload
{
	private FTPClient ftpClient;

	public FtpUpload(FTPClient ftpClient)
	{
		this.ftpClient = ftpClient;
	}

	//	 上传文件
	public void uploadFile(String name) throws IOException, FTPException
	{
		//	取得远程文件名
		String fileName = name.substring(name.lastIndexOf("\\") + 1);
		//  调用FTPClient的上传文件方法，如果远程主机已经存在该文件的话将被覆盖
		ftpClient.put(name, fileName);
	}

	// 上传文件夹方法
	public void uploadDir(String dir) throws IOException, FTPException
	{
		// 取得远程文件夹名
		String dirName = dir.substring(dir.lastIndexOf("\\") + 1);
		// 创建远程文件夹
		ftpMkDir(dirName);//
		// 取得远程主机当前文件夹
		String currentDir = ftpClient.pwd();
        // 进入远程文件夹
		ftpClient.chdir(dirName);
	    // 获得本地文件夹下的文件信息
		String[] subFileNames = this.getDirList(dir);
		for (int i = 0; i < subFileNames.length; i++)
		{// 对本地文件夹下的文件信息进行遍历
			String tmpPath = dir + "\\" + subFileNames[i];
			File tmpFile = new File(tmpPath);
			if (tmpFile.isFile())
			{ // 上传文件
				this.uploadFile(tmpPath);
			}
			else
			{// 上传文件夹
				this.uploadDir(tmpPath);
			}
		}
		// 返回到上一级目录
		ftpClient.chdir(currentDir);
	}

	// 获得本地文件夹下的文件信息
	public String[] getDirList(String path)
	{
		File file = new File(path);
		String[] subFileName = file.list();
		return subFileName;
	}

	//	 创建远程文件夹，保证创建文件夹时是要保证线程安全
	private synchronized void ftpMkDir(String dirName) throws IOException, FTPException
	{
		ftpClient.mkdir(dirName);
	}
	
	public FTPClient getFtpClient()
	{
		return ftpClient;
	}

	public void setFtpClient(FTPClient ftpClient)
	{
		this.ftpClient = ftpClient;
	}

}
