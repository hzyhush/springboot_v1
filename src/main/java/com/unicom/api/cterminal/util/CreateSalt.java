package com.unicom.api.cterminal.util;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import java.security.SecureRandom;

/**
 * 生成salt值
 */
public class CreateSalt {

    /**
     * 生成salt值
     * @return
     */
    public static String getSalt(){
        byte [] bytes = new byte [16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(bytes);
        String salt = Base64.encodeToString(bytes);
        return salt;
    }

    public static void main(String[] arge){
        //散列算法类型为MD5
        String ALGORITH_NAME = "md5";
        //hash的次数
        int HASH_ITERATIONS = 2;
        ByteSource salt = ByteSource.Util.bytes("3mMGHHeXF5wNQ/1xj5f8Ng==");
        String newPassword = new SimpleHash(ALGORITH_NAME, "123", salt,HASH_ITERATIONS).toHex();
        System.out.println(newPassword);
    }
}
