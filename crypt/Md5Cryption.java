
import java.security.NoSuchAlgorithmException;
import exception.CRM2Errcodes;
import exception.CRM2Exception;
import StringHelper;

/**
 * MD5加密
 */
public class Md5Cryption {
    /**
     * MD5加密
     * 
     * @param apcIn
     *            明文
     * @return 十六进制的MD5密文串
     */
    public static String encrypt(String apcIn) {
        byte[] buff = md5Digest(apcIn.getBytes());
        return StringHelper.byte2hex(buff);
    }
    
    /**
     * MD5 摘要计算(byte[]).
     * 
     * @param src
     *            byte[]
     * @throws Exception
     * @return byte[] 16 bit digest
     */
    public static byte[] md5Digest(byte[] src) {
        try {
            java.security.MessageDigest alg = java.security.MessageDigest.getInstance("MD5");
            return alg.digest(src);
        } catch (NoSuchAlgorithmException e) {
            throw new CRM2Exception(CRM2Errcodes.CRYPT_ERROR, "MD5加密失败", e);
        }
    }
    
    public static void main(String[] args) {
        System.out.println(Md5Cryption.encrypt("SPSARCH2015"));
    }
}
