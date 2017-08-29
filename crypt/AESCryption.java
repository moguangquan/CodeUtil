
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import StringHelper;

/**
 * AES加解密
 */
public class AESCryption {
    
    /**
     * 功能:加密(128位)
     * 
     * @param content
     * @param password
     * @return 十六进制的AES密文串
     */
    public static String doEncrypt(String content, String password) {
        byte[] result = encrypt(content, password);
        return StringHelper.byte2hex(result);
    }
    
    /**
     * 功能：加密(128位)
     * 
     * @param content
     *            加密前明文内容
     * @param password
     *            密钥
     * @return 加密后字节数组 (不可强转为字符串)
     */
    public static byte[] encrypt(String content, String password) {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = keyGen.generateKey();
            byte[] encodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(encodeFormat, "AES");
            
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            
            byte[] bytecontent = content.getBytes("utf-8");
            
            byte[] result = cipher.doFinal(bytecontent);
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 功能：解密
     * 
     * @param content
     *            加密后密文内容
     * @param password
     *            密钥
     * @return 解后的数组 (可强转为字符串)
     */
    public static byte[] decrypt(byte[] content, String password) {
        KeyGenerator keyGen;
        try {
            keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = keyGen.generateKey();
            byte[] encodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(encodeFormat, "AES");
            
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 设置seed属性后的加密 xujw 2015-10-21
     * 
     * @param content
     * @param password
     * @return
     */
    public static String doEncryptSeed(String content, String password) {
        byte[] result = encryptSeed(content, password);
        return StringHelper.byte2hex(result);
    }
    
    /**
     * 功能：加密(128位)
     * 
     * @param content
     *            加密前明文内容
     * @param password
     *            密钥
     * @return 加密后字节数组 (不可强转为字符串)
     */
    public static byte[] encryptSeed(String content, String password) {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(password.getBytes());
            keyGen.init(128, secureRandom);
            SecretKey secretKey = keyGen.generateKey();
            byte[] encodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(encodeFormat, "AES");
            
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            
            byte[] bytecontent = content.getBytes("utf-8");
            
            byte[] result = cipher.doFinal(bytecontent);
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 功能：对应encryptSeed解密(128)
     * 
     * @param content
     *            加密后密文内容
     * @param password
     *            密钥
     * @return 解后的数组 (可强转为字符串)
     */
    public static byte[] decryptSeed(byte[] content, String password) {
        KeyGenerator keyGen;
        try {
            keyGen = KeyGenerator.getInstance("AES");
            // keyGen.init(128, new SecureRandom(password.getBytes()));
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(password.getBytes());
            keyGen.init(128, secureRandom);
            SecretKey secretKey = keyGen.generateKey();
            byte[] encodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(encodeFormat, "AES");
            
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
