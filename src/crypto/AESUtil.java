package crypto;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {

    private static final String AES_MODE = "AES/ECB/PKCS5Padding";

    public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        SecretKeySpec skey = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance(AES_MODE);
        cipher.init(Cipher.ENCRYPT_MODE, skey);
        return cipher.doFinal(data);
    }

    public static byte[] decrypt(byte[] encrypted, byte[] key) throws Exception {
        SecretKeySpec skey = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance(AES_MODE);
        cipher.init(Cipher.DECRYPT_MODE, skey);
        return cipher.doFinal(encrypted);
    }
}
