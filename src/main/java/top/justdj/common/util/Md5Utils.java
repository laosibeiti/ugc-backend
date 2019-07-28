package top.justdj.common.util;

import org.apache.shiro.crypto.hash.SimpleHash;

import java.security.SecureRandom;

import static org.apache.commons.codec.binary.Base64.encodeBase64String;


/**
 * @author shan
 */
public class Md5Utils {
    
    private static final String type="MD5";
    private static final Integer Iterations=10;
    
    /**
     * 密码加密
     * @param password
     * @param salt
     * @return
     */
    public static String encryption(String password,String salt){
        SimpleHash simpleHash = new SimpleHash(type, password, salt, Iterations);
        return simpleHash.toString();
    }

    public static String generateSalt(){
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[15];
        random.nextBytes(bytes);
        String salt = encodeBase64String(bytes);
        return salt;
    }
}
