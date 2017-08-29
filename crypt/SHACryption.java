import exception.CRM2Errcodes;
import exception.CRM2Exception;
import StringHelper;

/**
 * SHA加密
 */
public class SHACryption {
    private static byte[] defaultShaKeyBytes = {(byte) 0xA3, (byte) 0x35, (byte) 0x48, (byte) 0x2A,
            (byte) 0x21, (byte) 0x24, (byte) 0x83, (byte) 0xE6, (byte) 0xBF, (byte) 0xB3,
            (byte) 0x72, (byte) 0xF8, (byte) 0x41, (byte) 0xDF, (byte) 0x0C, (byte) 0xCB };
    
    /**
     * 采用默认ShaKey加密
     * 
     * @param apcIn
     *            明文
     * @return 密文
     */
    public static String encrypt(String apcIn) {
        return encrypt(apcIn, defaultShaKeyBytes);
    }
    
    /**
     * 采用默认ShaKey加密
     * 
     * @param apcIn
     *            明文
     * @param shaKeyBytes
     *            ShaKey秘钥
     * @return 密文
     */
    public static String encrypt(String apcIn, byte[] shaKeyBytes) {
        try {
            java.security.MessageDigest alga = java.security.MessageDigest.getInstance("SHA-1");
            alga.update(shaKeyBytes);
            alga.update(apcIn.getBytes());
            alga.update(shaKeyBytes);
            byte[] digesta = alga.digest();
            return StringHelper.byte2hex(digesta);
        } catch (Exception e) {
            throw new CRM2Exception(CRM2Errcodes.CRYPT_ERROR, "SHA加密失败", e);
        }
    }
    
    /**
     * 采用指定密钥加密,返回base64加密后的结果
     * 
     * @param apcIn
     *            明文
     * @param shaKeyStr
     *            ShaKey秘钥
     * @return base64加密的密文
     */
    public static String encrypt(String apcIn, String shaKeyStr) {
        try {
            java.security.MessageDigest alga = java.security.MessageDigest.getInstance("SHA-1");
            byte[] shaKeyBytes = shaKeyStr.getBytes();
            alga.update(shaKeyBytes);
            alga.update(apcIn.getBytes());
            alga.update(shaKeyBytes);
            byte[] digesta = alga.digest();
            return Base64Cryption.encrypt(digesta);
        } catch (Exception e) {
            throw new CRM2Exception(CRM2Errcodes.CRYPT_ERROR, "SHA加密失败", e);
        }
    }
    
    public static void main(String[] args) {
        System.out.println(SHACryption.encrypt("admin@123"));
    }
    
}
