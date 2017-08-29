
import java.io.IOException;
import java.io.StringReader;
import bean.MapBean;

/**
 * 格式化工具类
 */
public class FormatUtil {
    private final static char prefix = '{';// 变量前缀
    private final static char suffix = '}';// 变量后缀
    
    /**
     * 格式化错误描述
     * 
     * @param input
     *            输入字符串参数名用{}，如“所选择的号码{accNbr}在系统中已经存在”
     * @param argMap
     *            输入参数
     * @return 返回格式后的字符串
     */
    public static String format(String input, MapBean argMap) {
        if (StringHelper.isEmpty(input) || argMap == null) {
            return input;
        }
        
        StringBuilder message = new StringBuilder(input.length() + 128);
        StringReader reader = new StringReader(input);
        try {
            int b = -1;
            StringBuffer param = null;
            while ((b = reader.read()) != -1) {
                if (b == prefix) {
                    if (param != null) {
                        // 出现连续两个{，忽略前面那个
                        message.append(prefix);
                        message.append(param);
                    }
                    param = new StringBuffer();
                    continue;
                } else if (b == suffix) {
                    if (param == null) {
                        // 缺少{,忽略
                        message.append(suffix);
                        continue;
                    }
                    
                    String paramCode = StringHelper.trimMore(param.toString());
                    if (StringHelper.isNotEmpty(paramCode)) {
                        message.append(argMap.get(paramCode));
                    }
                    param = null;
                    continue;
                }
                
                char c = (char) b;
                if (param != null) {
                    param.append(c);
                } else {
                    message.append(c);
                }
            }
            
            if (param != null) {
                // 缺少{,忽略
                message.append(param);
            }
        } catch (IOException e) {
            throw new RuntimeException("格式化错误描述失败", e);
        } finally {
            reader.close();
        }
        
        return message.toString();
    }
}
