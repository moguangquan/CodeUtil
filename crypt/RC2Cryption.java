/**
 * RC2加解密算法
 */
public class RC2Cryption {
    private static int[] midKeyData = {15853, 38894, 19718, 53449, 15281, 58010, 22498, 13115,
            55693, 56843, 3492, 38030, 55693, 56843, 3492, 38030, 55693, 56843, 3492, 38030, 55693,
            56843, 3492, 38030, 55693, 56843, 3492, 38030, 55693, 56843, 3492, 38030, 55693, 56843,
            3492, 38030, 55693, 56843, 3492, 38030, 55693, 56843, 3492, 38030, 55693, 56843, 3492,
            38030, 55693, 56843, 3492, 38030, 55693, 56843, 3492, 38030, 55693, 56843, 3492, 38030,
            55693, 56843, 3492, 38030 };
    private long         resD0      = 0;
    private long         resD1      = 0;
    
    private void hex2Data(String hexString, StringBuffer data) {
        int aiLen = hexString.length() / 2;
        int i = 0;
        while (i < aiLen) {
            char c1 = hexString.charAt(2 * i);
            char c2 = hexString.charAt(2 * i + 1);
            byte b1, b2;
            if (c1 <= '9')
                b1 = (byte) (c1 - '0');
            else
                b1 = (byte) (c1 - 'A' + 10);
            if (c2 <= '9')
                b2 = (byte) (c2 - '0');
            else
                b2 = (byte) (c2 - 'A' + 10);
            data.append((char) ((b1 << 4) + b2));
            i++;
        }
    }
    
    private long c2l(String c, int pos) {
        if (c.length() <= pos) {
            return 0;// 溢出了
        }
        long l = (long) c.charAt(pos);
        l = l | (long) c.charAt(pos + 1) << 8L;
        l = l | (long) c.charAt(pos + 2) << 16L;
        l = l | (long) c.charAt(pos + 3) << 24L;
        return l;
    }
    
    private String l2c(long l) {
        String s = "";
        s = s + (char) (l & 0xff);
        s = s + (char) ((l >> 8L) & 0xff);
        s = s + (char) ((l >> 16L) & 0xff);
        s = s + (char) ((l >> 24L) & 0xff);
        return s;
    }
    
    private void rc2_ecb_encrypt(String in, int pos, StringBuffer out, boolean encrypt) {
        long l, d0, d1;
        
        l = c2l(in, pos);
        d0 = l;
        l = c2l(in, pos + 4);
        d1 = l;
        rc2_encrypt(d0, d1, encrypt);
        d0 = resD0;
        d1 = resD1;
        l = d0;
        out.append(l2c(l));
        l = d1;
        out.append(l2c(l));
    }
    
    private void rc2_encrypt(long d0, long d1, boolean encrypt) {
        int i, n;
        int p0;
        int x0, x1, x2, x3, t;
        long l;
        
        l = d0;
        x0 = (int) l & 0xffff;
        x1 = (int) (l >> 16L);
        l = d1;
        x2 = (int) l & 0xffff;
        x3 = (int) (l >> 16L);
        
        n = 3;
        i = 5;
        if (encrypt) {
            p0 = 0;
            for (;;) {
                t = (x0 + (x1 & ~x3) + (x2 & x3) + midKeyData[p0]) & 0xffff;
                p0++;
                x0 = (t << 1) | (t >> 15);
                t = (x1 + (x2 & ~x0) + (x3 & x0) + midKeyData[p0]) & 0xffff;
                p0++;
                x1 = (t << 2) | (t >> 14);
                t = (x2 + (x3 & ~x1) + (x0 & x1) + midKeyData[p0]) & 0xffff;
                p0++;
                x2 = (t << 3) | (t >> 13);
                t = (x3 + (x0 & ~x2) + (x1 & x2) + midKeyData[p0]) & 0xffff;
                p0++;
                x3 = (t << 5) | (t >> 11);
                
                if (--i == 0) {
                    if (--n == 0)
                        break;
                    i = (n == 2) ? 6
                        : 5;
                    
                    x0 += midKeyData[x3 & 0x3f];
                    x1 += midKeyData[x0 & 0x3f];
                    x2 += midKeyData[x1 & 0x3f];
                    x3 += midKeyData[x2 & 0x3f];
                }
            }
        } else {
            p0 = 63;
            for (;;) {
                t = ((x3 << 11) | (x3 >> 5)) & 0xffff;
                x3 = (t - (x0 & ~x2) - (x1 & x2) - midKeyData[p0]) & 0xffff;
                p0--;
                t = ((x2 << 13) | (x2 >> 3)) & 0xffff;
                x2 = (t - (x3 & ~x1) - (x0 & x1) - midKeyData[p0]) & 0xffff;
                p0--;
                t = ((x1 << 14) | (x1 >> 2)) & 0xffff;
                x1 = (t - (x2 & ~x0) - (x3 & x0) - midKeyData[p0]) & 0xffff;
                p0--;
                t = ((x0 << 15) | (x0 >> 1)) & 0xffff;
                x0 = (t - (x1 & ~x3) - (x2 & x3) - midKeyData[p0]) & 0xffff;
                p0--;
                
                if (--i == 0) {
                    if (--n == 0)
                        break;
                    i = (n == 2) ? 6
                        : 5;
                    
                    x3 = (x3 - midKeyData[x2 & 0x3f]) & 0xffff;
                    x2 = (x2 - midKeyData[x1 & 0x3f]) & 0xffff;
                    x1 = (x1 - midKeyData[x0 & 0x3f]) & 0xffff;
                    x0 = (x0 - midKeyData[x3 & 0x3f]) & 0xffff;
                }
            }
        }
        
        d0 = (long) (x0 & 0xffff) | ((long) (x1 & 0xffff) << 16L);
        d1 = (long) (x2 & 0xffff) | ((long) (x3 & 0xffff) << 16L);
        resD0 = d0;
        resD1 = d1;
    }
    
    private String string2hex(String out) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < out.length(); n++) {
            stmp = (java.lang.Integer.toHexString(out.charAt(n) & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
        }
        return hs.toUpperCase();
    }
    
    /**
     * 输入加密后的HEX字符串，使用CRM的RC2模式进行解密
     * 
     * @param apcPass
     *            HEX密文串
     * @return 明文
     */
    private String doDecrypt(String apcPass) {
        StringBuffer sbPass = new StringBuffer();
        hex2Data(apcPass, sbPass);
        StringBuffer out = new StringBuffer();
        for (int i = 0; i < sbPass.length(); i += 8)
            rc2_ecb_encrypt(sbPass.toString(), i, out, true);
        while (out.length() > 1 && out.toString().charAt(out.length() - 1) == ' ')
            out.delete(out.length() - 1, out.length());
        return out.toString();
    }
    
    /**
     * 输入明文，使用CRM的RC2模式进行加密，加密结果为HEX字符串
     * 
     * @param apcIn
     *            明文
     * @return HEX密文串
     */
    private String doEncrypt(String apcIn) {
        StringBuffer out = new StringBuffer();
        int aiLen = apcIn.length();
        if (aiLen % 8 != 0) {
            int addl = 8 - aiLen % 8;
            for (int l = 0; l < addl; l++)
                apcIn = apcIn + " ";
            aiLen = aiLen + addl;
        }
        for (int i = 0; i < aiLen; i += 8)
            rc2_ecb_encrypt(apcIn, i, out, false);
        return string2hex(out.toString());
    }
    
    /**
     * 输入加密后的HEX字符串，使用CRM的RC2模式进行解密
     * 
     * @param apcPass
     *            HEX密文串
     * @return 明文
     */
    public static String decrypt(String apcPass) {
        RC2Cryption rc2 = new RC2Cryption();
        return rc2.doDecrypt(apcPass);
    }
    
    /**
     * 输入明文，使用CRM的RC2模式进行加密，加密结果为HEX字符串
     * 
     * @param apcIn
     *            明文
     * @return HEX密文串
     */
    public static String encrypt(String apcIn) {
        RC2Cryption rc2 = new RC2Cryption();
        return rc2.doEncrypt(apcIn);
    }
    
    public static void main(String[] args) {
        System.out.println(
            encrypt("http://localhost:8080/crm-web/salesmgr/adjustPrice/toAdjustPricePage.action"));
        System.out.println(encrypt("CRMGUI#dlq#200#200000000#200#20140804101519"));
    }
}
