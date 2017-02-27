package testFTP;

/*import cn.sinobest.jzpt.framework.utils.JdbcService;
import org.apache.commons.lang.StringUtils;*/
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * ftp�����࣬����org.apache.commons.net.ftp.FTPClientʵ��
 */
public class FtpUtil {
    private static FTPClient ftpClient = new FTPClient();
    private static FtpUtil ftpUtil = null;
    static String ipAddr = "127.0.0.1";
    static String username ="admin";
    static String password = "123456";
    static int port = Integer.parseInt("2121");
    static String rootPath = "ftp";
    static{

    }

    public static synchronized FtpUtil getInstance() {
    	 if (ftpUtil == null) {
                ftpUtil = new FtpUtil();
        }
        return ftpUtil;
    }
    private FtpUtil() {
        /*   ��ʼ��ftp������Ϣ   */
	}


    /**
     * ����ftp������
     */
	public static boolean connect() {
        try {
            if(port > 0){
                ftpClient.connect(ipAddr,port);
            }else{
                ftpClient.connect(ipAddr);
            }
            if(FTPReply.isPositiveCompletion(ftpClient.getReplyCode())){
                if(ftpClient.login(username, password)){
                    ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                    ftpClient.makeDirectory(rootPath);
                    ftpClient.changeWorkingDirectory(rootPath);
                    System.out.println("����ftp�������ɹ�...");
                    return true;
                }else{
                    throw new RuntimeException("��¼ftp�ļ�������ʧ�ܣ��������ã�");
                }
            }else{
                throw new RuntimeException("����ftp�ļ�������ʧ�ܣ��������ã�");
            }

		} catch (IOException e) {
			e.printStackTrace();
		}
        return false;
	}
	public static void main(String[] args) {
		System.out.println(connect());
	}
    /**
     * ��ftp�������Ͽ�
     */
    public void disconnect(){
        if(ftpClient != null && ftpClient.isConnected()){
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * �ݹ鴴��Զ�̷�����Ŀ¼
     * @param dirPath Զ�̷������ļ�����·��
     * @return Ŀ¼�����Ƿ�ɹ�
     * @throws java.io.IOException
     */
    public boolean CreateDirecroty(String dirPath) throws IOException{
        boolean status = true;
        String directory = dirPath.substring(0, dirPath.lastIndexOf("/")+1);
        if(!directory.equalsIgnoreCase("/")&&!ftpClient.changeWorkingDirectory(new String(directory.getBytes("GBK"),"iso-8859-1"))){
            //���Զ��Ŀ¼�����ڣ���ݹ鴴��Զ�̷�����Ŀ¼
            int start=0;
            int end = 0;
            if(directory.startsWith("/")){
                start = 1;
            }else{
                start = 0;
            }
            end = directory.indexOf("/",start);
            while(true){
                String subDirectory = new String(dirPath.substring(start,end).getBytes("GBK"),"iso-8859-1");
                if(!ftpClient.changeWorkingDirectory(subDirectory)){
                    if(ftpClient.makeDirectory(subDirectory)){
                        ftpClient.changeWorkingDirectory(subDirectory);
                    }else {
                        System.out.println("����Ŀ¼ʧ��");
                        return false;
                    }
                }

                start = end + 1;
                end = directory.indexOf("/",start);

                //�������Ŀ¼�Ƿ񴴽����
                if(end <= start){
                    break;
                }
            }
        }
        return status;
    }

   /**
     * �ϴ��ļ���ftpָ��Ŀ¼
     * @param content
     * @param dirPath
     * @param fileName
     * @return
     */
    public boolean upload(byte[] content,String dirPath,String fileName){

        if(content!=null && content.length > 0 && !StringUtils.isEmpty(fileName)){
            ByteArrayInputStream bais = null;
            try {
                ftpClient.changeWorkingDirectory("/");
                if(!StringUtils.isEmpty(dirPath)){
                    dirPath = this.rootPath + dirPath;
                }else{
                    dirPath = this.rootPath;
                }
                if(!ftpClient.changeWorkingDirectory(dirPath)){
                    if(CreateDirecroty(dirPath)){
                        System.out.println("ftp����Ŀ¼�ɹ�");
                        ftpClient.changeWorkingDirectory(dirPath);
                    }else {
                        System.out.println("ftp����Ŀ¼ʧ��");
                    }
                }
                ftpClient.setBufferSize(1024*4);
                ftpClient.setControlEncoding("UTF-8");
                ftpClient.setFileType(ftpClient.BINARY_FILE_TYPE);
                bais = new ByteArrayInputStream(content);
                ftpClient.storeFile(fileName, bais);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }else{
            return false;
        }


    }

    /**
     * ��ftp�����������ļ�
     * @param dirPath
     * @param fileName
     * @return
     * @throws java.io.IOException
     */
	public byte[] download(String dirPath,String fileName) throws IOException {

        //У�����
        if(StringUtils.isEmpty(fileName)){
            return null;
        }
        InputStream inputStream = null;
        //1.���ӵ�¼ftp�ļ�������
        FTPClient ftpClient1 = new FTPClient();
        try {
            if(port > 0){
                ftpClient1.connect(ipAddr,port);
            }else{
                ftpClient1.connect(ipAddr);
            }
            if(FTPReply.isPositiveCompletion(ftpClient1.getReplyCode())){
                if(ftpClient1.login(username, password)){
                    ftpClient1.setFileType(FTP.BINARY_FILE_TYPE);
                    ftpClient1.makeDirectory(this.rootPath);
                    ftpClient1.changeWorkingDirectory(this.rootPath);
                }else{
                    throw new RuntimeException("��¼ftp�ļ�������ʧ�ܣ��������ã�");
                }
            }else{
                throw new RuntimeException("����ftp�ļ�������ʧ�ܣ��������ã�");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ftpClient1.changeWorkingDirectory("/");
        if(!StringUtils.isEmpty(dirPath)){
            dirPath = this.rootPath + dirPath;
        }else{
            dirPath = this.rootPath;
        }
        ftpClient1.changeWorkingDirectory(dirPath);
        ftpClient1.setBufferSize(1024 * 4);
        //�����Զ����Ʒ�ʽ����
        ftpClient1.setFileType(ftpClient1.BINARY_FILE_TYPE);
        //2.��ȡ�ļ���
        inputStream = ftpClient1.retrieveFileStream(fileName);
        if(inputStream == null){
            System.err.println("ftp������û�и��ļ���");
            return null;
        }

        //3.��InputStream���������ת����byte[]
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int len = 0;
        byte[] b = new byte[1024];
        while ((len = inputStream.read(b, 0, b.length)) != -1) {
            baos.write(b, 0, len);
        }
        byte[] buffer =  baos.toByteArray();

        //4.�ر�
        inputStream.close();
        if(ftpClient1 != null && ftpClient1.isConnected()){
            try {
                ftpClient1.logout();
                ftpClient1.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return buffer;

	}

    /**
     * ���ظ�Ŀ¼�µ��ļ�
     * @param fileName
     * @return
     * @throws java.io.IOException
     */
    public byte[] download(String fileName) throws IOException {
        return download(null, fileName);
    }
}