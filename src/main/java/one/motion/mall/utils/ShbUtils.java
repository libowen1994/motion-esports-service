package one.motion.mall.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ShbUtils {

    private static Logger logger = LoggerFactory.getLogger(ShbUtils.class);

    private static String shbPublicKeyString = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDUz5nUtbeapcWqsQk5Lq5zvfmXGld1isS8XYL1hYOwM9fD9yg5dT3Av7DidZ9nkWse7G35kKvmPCrEz8ddnZhsL2J1g0+o339cdU8D2Ly5J7BlsHfTdWylJa/MDUKXz68GLS3IKt3iIwOXdJ5rwmnUyMHhVAV4IPfs8jjiRvz9VwIDAQAB";

    private static String privateKeyString = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAKySngucskSXcbTa\n" +
            "GC6LgXonwULgh7DJ00hnghlSextC9B6QASShfRuTxqnpW8yu3z/WEnVvWLpGu0hM\n" +
            "px8KWLKLcphqrte1P2mWeZvbkth/1ptEYfQO+ync8Bci2jP5iib+y3mO7VDBmanT\n" +
            "QUWBvbv/cPvd0aGChtWh/xrg2j3lAgMBAAECgYBI2w1GMb1BH/6tL3YOEG91Ntts\n" +
            "dIZUqKA+bRSLxDqc48NUM8+Hu3Wp8tVoXlCXNSyjMqlQUtXOrpXajCHyBNg0tu8K\n" +
            "Dr3A8DtQmf2cmegzxWj2xfvvSzcFnuAQhjN2lXkqd0KLMDzcEOcZNlNPJo84ks8y\n" +
            "e/q0WU8nhyM4i1H8gQJBANtybgNEG8tu0mDTkJLST3QO9ICpEC9TapeYmNpBdBzy\n" +
            "RGW4WHMuos6Ih+8UnnADmwZAkHUNvxqaykaz124cSrkCQQDJUWTdHH9PJYWBrylg\n" +
            "w6pqE0L+nhU3WSEutoETTVC2EWPx49g1nVSXJCLXJFlBoid36haNZPM1XuEkyhnA\n" +
            "SsaNAkAQa7eftvD9gAVuCWbAFcysOpJBLmVvpSZMwbZuXod5wwcrNWgo9kxod/7z\n" +
            "/O7+Isbu6NXmbGjobLzGHZ75NlTBAkAV0nzcevpR2f9ez13s30jZtrzMXNZv6Xlv\n" +
            "Vtkm/nqBQeFTlx8YsLU6rPAZ4hbKxokAtp87NHYokuBU3JNuBK0BAkAsm9W0EnlJ\n" +
            "fyfSjk9FajhN/nl5AGrmGAut43P6LCfM2uS0y6GFPBeM0fSNwOcKbJ2RJLBANj4f\n" +
            "tB1f1PbykF27";

    private static String publicKeyString = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCskp4LnLJEl3G02hgui4F6J8FC\n" +
            "4IewydNIZ4IZUnsbQvQekAEkoX0bk8ap6VvMrt8/1hJ1b1i6RrtITKcfCliyi3KY\n" +
            "aq7XtT9plnmb25LYf9abRGH0Dvsp3PAXItoz+Yom/st5ju1QwZmp00FFgb27/3D7\n" +
            "3dGhgobVof8a4No95QIDAQAB";

    public static JSONObject decodeContext(String context) {
        String decodedContext;
        try {
            decodedContext = new String(RSAUtils.decrypt(privateKeyString, Base64.decodeBase64(context)), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
        return JSON.parseObject(decodedContext);
    }

    public static String encodeContext(JSONObject data) throws Exception {
        return signAndEncryptionToString(data.getJSONObject("businessContext"), data.getJSONObject("businessHead"));
    }

    public static boolean verify(JSONObject data) {
        try {
            return verifyResponseSign(data.getJSONObject("businessContext"), data.getJSONObject("businessHead"));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    private static boolean verifyResponseSign(JSONObject businessContext, JSONObject businessHead) throws Exception {
        if (businessContext == null || businessHead == null) {
            return false;
        }
        String sign = businessHead.getString("sign");
        return RSAUtils.verify(businessContext.toJSONString().getBytes(), shbPublicKeyString, sign);
    }

    private static String signAndEncryptionToString(JSONObject businessContext, JSONObject businessHead) throws Exception {
        businessHead.put("sign", RSAUtils.sign(businessContext.toJSONString().getBytes(), privateKeyString));
        JSONObject context = new JSONObject();
        context.put("businessContext", businessContext);
        context.put("businessHead", businessHead);
        return Base64.encodeBase64String(RSAUtils.encrypt(shbPublicKeyString, context.toJSONString().getBytes()));

    }
}
