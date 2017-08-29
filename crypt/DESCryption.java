
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import CharacterHelper;
import StringHelper;

/**
 * DES加解密算法
 */
public class DESCryption {
    private SecretKey          key;
    private Cipher             cipher;
    public static final String DEFAULT_KEY = "ACCSECKEY";
    
    /**
     * @param strPwd
     *            被加密明文
     * @param encodeKey
     *            加密密钥
     * @return
     * @throws Exception
     */
    public String Encrypt(String strPwd, String encodeKey) throws Exception {
        try {
            String keyStr = getEcodeKey(encodeKey);
            byte[] rawKey = new byte[keyStr.length()];
            rawKey = keyStr.getBytes();
            DESKeySpec dks = new DESKeySpec(rawKey);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("des");
            this.key = keyFactory.generateSecret(dks);
            this.cipher = Cipher.getInstance("DES");
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
        
        byte[] data = strPwd.getBytes();
        try {
            this.cipher.init(1, this.key);
            byte[] result = this.cipher.doFinal(data);
            
            String strTeturn = StringHelper.byte2hex(result);
            return strTeturn;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
    
    /**
     * @param strPwd
     *            被加密明文
     * @param encodeKey
     *            加密密钥
     * @return
     * @throws Exception
     */
    public String Unrypt(String strPwd, String encodeKey) throws Exception {
        try {
            String keyStr = getEcodeKey(encodeKey);
            byte[] rawKey = new byte[keyStr.length()];
            rawKey = keyStr.getBytes("utf-8");
            
            DESKeySpec dks = new DESKeySpec(rawKey);
            
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("des");
            
            this.key = keyFactory.generateSecret(dks);
            
            this.cipher = Cipher.getInstance("DES");
        } catch (Exception ex) {
            ex.printStackTrace();
            
            throw ex;
        }
        
        String str_Retrun = "";
        try {
            byte[] byteTemp = new byte[strPwd.length() / 2];
            
            for (int i = 0; i < strPwd.length() / 2; ++i) {
                byteTemp[i] = (byte) (CharacterHelper.Char2Byte(strPwd.charAt(2 * i)) * 16
                    + CharacterHelper.Char2Byte(strPwd.charAt(2 * i + 1)) & 0xFF);
            }
            
            this.cipher.init(2, this.key);
            
            byte[] original = this.cipher.doFinal(byteTemp);
            
            str_Retrun = new String(original);
            
            return str_Retrun;
        } catch (Exception ex) {
            ex.printStackTrace();
            
            throw ex;
        }
    }
    
    public byte[] EncryptTTT(String strPwd) throws Exception {
        byte[] data = strPwd.getBytes();
        try {
            this.cipher.init(1, this.key);
            
            byte[] result = this.cipher.doFinal(data);
            
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            
            throw ex;
        }
    }
    
    public String UnryptTTT(byte[] byteTemp) throws Exception {
        String str_Retrun = "";
        try {
            this.cipher.init(2, this.key);
            
            byte[] original = this.cipher.doFinal(byteTemp);
            
            str_Retrun = new String(original);
            
            return str_Retrun;
        } catch (Exception ex) {
            ex.printStackTrace();
            
            throw ex;
        }
    }
    
    public String EncryptDES(String strPwd, String encodeKey) throws Exception {
        try {
            String keyStr = getEcodeKey(encodeKey);
            byte[] rawKey = new byte[keyStr.length()];
            rawKey = keyStr.getBytes();
            
            DESKeySpec dks = new DESKeySpec(rawKey);
            
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("des");
            
            this.key = keyFactory.generateSecret(dks);
            
            this.cipher = Cipher.getInstance("DES");
        } catch (Exception ex) {
            ex.printStackTrace();
            
            throw ex;
        }
        
        byte[] data = strPwd.getBytes();
        try {
            this.cipher.init(1, this.key);
            
            byte[] result = this.cipher.doFinal(data);
            
            String strTeturn = StringHelper.byte2hex(result);
            
            return strTeturn;
        } catch (Exception ex) {
            ex.printStackTrace();
            
            throw ex;
        }
    }
    
    public String UnryptDES(String strPwd, String encodeKey) throws Exception {
        try {
            String keyStr = getEcodeKey(encodeKey);
            byte[] rawKey = new byte[keyStr.length()];
            rawKey = keyStr.getBytes("utf-8");
            
            DESKeySpec dks = new DESKeySpec(rawKey);
            
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("des");
            
            this.key = keyFactory.generateSecret(dks);
            
            this.cipher = Cipher.getInstance("DES");
        } catch (Exception ex) {
            ex.printStackTrace();
            
            throw ex;
        }
        
        String str_Retrun = "";
        try {
            byte[] byteTemp = new byte[strPwd.length() / 2];
            
            for (int i = 0; i < strPwd.length() / 2; ++i) {
                byteTemp[i] = (byte) (CharacterHelper.Char2Byte(strPwd.charAt(2 * i)) * 16
                    + CharacterHelper.Char2Byte(strPwd.charAt(2 * i + 1)) & 0xFF);
            }
            
            this.cipher.init(2, this.key);
            
            byte[] original = this.cipher.doFinal(byteTemp);
            
            str_Retrun = new String(original);
            
            return str_Retrun;
        } catch (Exception ex) {
            ex.printStackTrace();
            
            throw ex;
        }
    }
    
    /**
     * 获取密钥
     * 
     * @return
     */
    public static String getEcodeKey(String encodeKey) {
        String accEcodeKey = "ACCSECKEY";
        if (StringHelper.isBlank(encodeKey)) {
            return accEcodeKey;
        } else
            return encodeKey;
    }
    
}
