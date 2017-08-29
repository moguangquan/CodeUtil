/**
 * 公共底层的异常全集
 */
public class CRM2Errcodes {
    // 公共底层异常定义：
    public static final CRM2Errcode SUCCESS                     = define("0000", "成功", false);
    
    // 系统错误（PB1000~PB1999）
    public static final CRM2Errcode CONFIG_FILE_ERROR           = define("PB1000",
        "加载配置文件[{cfgFile}]失败");
    public static final CRM2Errcode CLASS_INSTANCE_ERROR        = define("PB1001",
        "类[{className}]实例化异常");
    public static final CRM2Errcode CLASS_NOTFOUND              = define("PB1002",
        "类[{className}]不存在");
    
    public static final CRM2Errcode FTP_CONNECT_ERROR           = define("PB1003",
        "FTP[{ftpKey}]连接失败");
    public static final CRM2Errcode FTP_LOGIN_ERROR             = define("PB1004",
        "FTP[{ftpKey}]登陆失败");
    public static final CRM2Errcode FTP_IO_ERROR                = define("PB1005",
        "FTP[{ftpKey}]IO异常");
    public static final CRM2Errcode FTP_UPLOAD_ERROR            = define("PB1006",
        "FTP[{ftpKey}]上传文件[{ftpFile}]失败");
    public static final CRM2Errcode FTP_DOWNLOAD_ERROR          = define("PB1007",
        "FTP[{ftpKey}]下载文件[{ftpFile}]失败");
    public static final CRM2Errcode FTP_RENAME_ERROR            = define("PB1008",
        "FTP[{ftpKey}]更改文件[{ftpFile}]为[{ftpNewFile}]失败");
    
    public static final CRM2Errcode FILE_NOT_EXISTS             = define("PB1009", "文件[{file}]不存在");
    public static final CRM2Errcode FILE_EMPTY                  = define("PB1010",
        "文件[{file}]内容为空");
    
    public static final CRM2Errcode POOL_NOT_EXISTS             = define("PB1011",
        "线程池[{poolName}]不存在");
    public static final CRM2Errcode POOL_INTERRUPTED            = define("PB1012",
        "线程池[{poolName}]服务请求中断");
    public static final CRM2Errcode POOL_EXECUTION_ERROR        = define("PB1013",
        "线程池[{poolName}]服务执行异常!{errMsg}");
    public static final CRM2Errcode POOL_TIMEOUT                = define("PB1014",
        "线程池[{poolName}]服务调用超时");
    public static final CRM2Errcode POOL_REJECTED               = define("PB1015",
        "线程池[{poolName}]拒绝请求[{errMsg}]");
    public static final CRM2Errcode POOL_ERROR                  = define("PB1016",
        "线程池[{poolName}]服务调用异常[{errMsg}]");
    
    /**
     * 定义异常
     * 
     * @param expCode
     *            异常编码
     * @param expDesc
     *            异常描述
     * @param needFormat
     *            是否格式化
     * @return CRM2Errcode
     */
    protected static final CRM2Errcode define(String expCode, String expDesc, boolean needFormat) {
        return new CRM2Errcode(expCode, expDesc, needFormat);
    }
    
    /**
     * 定义异常
     * 
     * @param expCode
     *            异常编码
     * @param expDesc
     *            异常描述
     * @return CRM2Errcode
     */
    protected static final CRM2Errcode define(String expCode, String expDesc) {
        return new CRM2Errcode(expCode, expDesc, true);
    }
    
}
