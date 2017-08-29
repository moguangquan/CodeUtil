import org.springframework.util.Assert;

/**
 * 异常编码
 */
public final class CRM2Errcode {
    private final String  expCode;   // 异常编码
    private final String  expDesc;   // 异常描述
    private final boolean needFormat;// 是否需要格式化异常描述
    
    CRM2Errcode(String expCode, String expDesc, boolean needFormat) {
        Assert.hasText(expCode, "expCode can't be null");
        Assert.hasText(expDesc, "expDesc can't be null");
        this.expCode = expCode;
        this.expDesc = expDesc;
        this.needFormat = needFormat;
    }
    
    public String getExpCode() {
        return expCode;
    }
    
    public String getExpDesc() {
        return expDesc;
    }
    
    public boolean isNeedFormat() {
        return needFormat;
    }
    
}
