package djr.ftp;

import java.io.File;
import java.io.IOException;

import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPException;

public class FtpDownload
{
	private FTPClient ftpClient;
	
	public FtpDownload(FTPClient ftpClient) {
		this.ftpClient = ftpClient;
	}
	
	
	/**
	 * 	 下载远程文件
	 * @param localPath 指定本地保存该文件的路径
	 * @param remoteFile	指定了远程文件的名称
	 * @throws IOException	
	 * @throws FTPException
	 */
	public void downloadFile(String localPath, String remoteFile) throws IOException, FTPException {
		ftpClient.get(localPath, remoteFile);
	}
	
	//	 下载远程文件夹
	public void downloadDir(String localPath, String remoteDir) throws IOException, FTPException
	{
		//  获得远程文件夹(remoteDir)下的文件及子文件夹的详细信息
		String[] subFileNames = ftpClient.dir(remoteDir, true);
		//  进入远程文件夹
		ftpClient.chdir(remoteDir);
		//	 创建本地用于保存远程文件的位置
		String localDirName = localPath + "\\" + remoteDir;
		File localDir = new File(localDirName);
		localDir.mkdir();
		for (int i = 1; i < subFileNames.length; i++)
		{	// 过滤文件信息
			int lastIndex = subFileNames[i].lastIndexOf(":");
			subFileNames[i] = subFileNames[i].substring(lastIndex + 4);
			if (subFileNames[i] == null || ".".equals(subFileNames[i]) || "..".equals(subFileNames[i]))
			{ // 文件为空、或根目录、返回上一级目录符号
				continue;
			}
			else if (subFileNames[i].indexOf(".") == -1)
			{ // 下载文件夹
				this.downloadDir(localDirName, subFileNames[i]);
			}
			else { // 下载文件
				this.downloadFile(localDirName, subFileNames[i]);
			}
		}
		 // 返回到上一级目录
		ftpClient.cdup();
	}
	
	public String[] getDirList(String path)
	{
		File file = new File(path);
		String[] subFileName = file.list();
		return subFileName;
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
