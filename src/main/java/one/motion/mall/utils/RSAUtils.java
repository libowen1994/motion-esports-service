package one.motion.mall.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSAUtils {

    private static final String ALGORITHM = "RSA";
    private static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    /**
     * 使用指定的公钥加密数据。
     *
     * @param publicKey 给定的公钥。
     * @param data      要加密的数据。
     * @return 加密后的数据。
     */
    public static byte[] encrypt(String publicKey, byte[] data) {
        try {
            byte[] keyBytes = Base64.decodeBase64(publicKey);
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            PublicKey publicK = keyFactory.generatePublic(x509KeySpec);
            Cipher ci = Cipher.getInstance(ALGORITHM);
            ci.init(Cipher.ENCRYPT_MODE, publicK);
            return ci.doFinal(data);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | InvalidKeySpecException e) {
            throw new IllegalStateException(e);
        } catch (IllegalBlockSizeException e) {
            throw new IllegalArgumentException("can not encrypt data.", e);
        }
    }

    /**
     * 使用指定的私钥解密数据。
     *
     * @param privateKey 给定的私钥。
     * @param data       要解密的数据。
     * @return 原数据。
     */
    public static byte[] decrypt(String privateKey, byte[] data) {
        try {
            byte[] keyBytes = Base64.decodeBase64(privateKey);
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
            Cipher ci = Cipher.getInstance(ALGORITHM);
            ci.init(Cipher.DECRYPT_MODE, privateK);
            return ci.doFinal(data);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | InvalidKeySpecException e) {
            throw new IllegalStateException(e);
        } catch (IllegalBlockSizeException e) {
            throw new IllegalArgumentException("input data is not a validate RSA encrypted data", e);
        }
    }

    /**
     * <p>
     * 用私钥对信息生成数字签名
     * </p>
     *
     * @param data       已加密数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateK);
        signature.update(data);
        return Base64.encodeBase64String(signature.sign());
    }

    public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        PublicKey publicK = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicK);
        signature.update(data);
        return signature.verify(Base64.decodeBase64(sign));
    }

}
