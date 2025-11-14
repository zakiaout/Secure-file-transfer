package client;

import crypto.AESUtil;
import crypto.HashUtil;

import java.nio.file.Files;
import java.nio.file.Path;

public class FilePreProcessor {

    public static byte[] readFile(String path) throws Exception {
        return Files.readAllBytes(Path.of(path));
    }

    public static String hashFile(byte[] data) throws Exception {
        return HashUtil.sha256(data);
    }

    public static byte[] encryptFile(byte[] data, byte[] key) throws Exception {
        return AESUtil.encrypt(data, key);
    }
}
