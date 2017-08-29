/**
 * 身份证工具类
 */
public final class SocialUtil {
    /**
     * 将15位身份证号码换算成18位
     * 
     * @param perId15
     *            15位身份证号码
     * @return 18位身份证号码
     */
    public static String perID15To18(String perId15) {
        if (perId15 == null || perId15.length() != 15) {
            return perId15;
        }
        
        int i = 0;
        char c;
        for (i = 0; i < 15; ++i) {
            c = perId15.charAt(i);
            if (c > '9' || c < '0') {// 出现非数字字符
                return perId15;
            }
        }
        
        int iSum = 0;
        int[] iW = new int[] {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };// 加权因子常数
        
        // 校验码常数
        String strLastCode = "10X98765432";
        StringBuilder strPerIDNum = new StringBuilder(perId15.substring(0, 6));
        strPerIDNum.append("19");
        strPerIDNum.append(perId15.substring(6, 15));
        
        // 进行加权求和
        for (i = 0; i < 17; i++) {
            iSum += ((int) strPerIDNum.charAt(i) - '0') * iW[i];
        }
        
        // 取模运算，得到模值
        int iY = iSum % 11;
        strPerIDNum.append(strLastCode.charAt(iY));
        return strPerIDNum.toString();
    }
    
    /**
     * 将18位身份证号码换算成15位
     * 
     * @param perId18
     *            18位身份证号码
     * @return 15位身份证号码
     */
    public static String perID18To15(String perId18) {
        if (perId18 == null || perId18.length() != 18) {
            return perId18;
        }
        StringBuilder strPerIDNum = new StringBuilder(perId18.substring(0, 6));
        strPerIDNum.append(perId18.substring(8, 17));
        return strPerIDNum.toString();
    }
}
