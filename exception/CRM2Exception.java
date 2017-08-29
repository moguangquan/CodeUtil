
import org.springframework.util.Assert;

import bean.MapBean;
import utils.FormatUtil;
import utils.ObjectUtils;
import utils.StringHelper;

/**
 * 自定义异常类
 */
public final class CRM2Exception extends RuntimeException {
    private static final long serialVersionUID = 3856935301311065813L;
    private String            originalCode;                           // 最原始的异常编码
    private MapBean           expMap;                                 // 异常参数Map
    private String            expCode;                                // 异常编码
    private String            expDesc;                                // 异常描述
    private boolean           needFormat;                             // 是否需要格式化异常描述
    
    protected CRM2Exception() {
        super();
        this.originalCode = StringHelper.EMPTY;
        this.expCode = StringHelper.EMPTY;
        this.expDesc = StringHelper.EMPTY;
        this.expMap = null;
        this.needFormat = false;
    }
    
    public CRM2Exception(final CRM2Errcode errcode) {
        this(errcode, null, null);
    }
    
    public CRM2Exception(final CRM2Errcode errcode, Throwable t) {
        this(errcode, null, t);
    }
    
    public CRM2Exception(final CRM2Errcode errcode, String message) {
        this(errcode, message, null);
    }
    
    public CRM2Exception(final CRM2Errcode errcode, String message, Throwable t) {
        super(message, t);
        Assert.notNull(errcode, "errcode can't be null");
        if (t != null && t instanceof CRM2Exception) {// 已有CRM2Exception
            CRM2Exception e = (CRM2Exception) t;
            this.originalCode = e.originalCode;
            this.expMap = e.expMap;
        } else {// 不是CRM2Exception
            this.originalCode = errcode.getExpCode();
        }
        this.expCode = errcode.getExpCode();
        this.expDesc = errcode.getExpDesc();
        this.needFormat = errcode.isNeedFormat();
        if (this.expMap == null) {
            this.expMap = new MapBean();
        }
    }
    
    /**
     * 增加异常参数
     * 
     * @param name
     *            参数名称
     * @param value
     *            参数值
     * @return CRM2Exception对象本身
     */
    public CRM2Exception addParam(String name, Object value) {
        this.expMap.put(name, ObjectUtils.toString(value));
        return this;
    }
    
    public final String getOriginalCode() {
        return originalCode;
    }
    
    public final MapBean getExpMap() {
        return expMap;
    }
    
    public final String getExpCode() {
        return expCode;
    }
    
    public final String getExpDesc() {
        if (StringHelper.isEmpty(expDesc)) {
            return expCode;
        }
        
        if (needFormat) {
            this.expMap.put("$errorCode", expCode == null ? StringHelper.EMPTY
                : expCode);
            this.expMap.put("$originalCode", originalCode == null ? StringHelper.EMPTY
                : originalCode);
            this.expMap.put("$message", super.getMessage() == null ? StringHelper.EMPTY
                : super.getMessage());
            return FormatUtil.format(expDesc, this.expMap);
        }
        
        return expDesc;
    }
    
    @Override
    public String getMessage() {
        String message = super.getMessage();
        if (StringHelper.isNotEmpty(message)) {
            return message;
        }
        return getExpDesc();
    }
    
}
