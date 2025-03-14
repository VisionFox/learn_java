package com.test;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class Ele {
    private static final char[] DIGITS_LOWER = {'0', '1', '2', '3',
            '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String getMD5Str(String str) {
        byte[] digest = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            digest = md5.digest(str.getBytes("utf-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int len = digest.length;
        char[] out = new char[len << 1];//len*2
        for (int i = 0, j = 0; i < len; i++) {
            out[j++] = DIGITS_LOWER[(0xF0 & digest[i]) >>> 4];// ⾼位
            out[j++] = DIGITS_LOWER[0x0F & digest[i]];// 低位
        }
        return new String(out);
    }

    public static String getTimestamp() {
        return Long.toString(System.currentTimeMillis());
    }

    public static String getRandomString() {
        String str =
                "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 10; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    public static String gen_sign(String host, String path) {
        // sign 的⽣成规则如下
        // sign = SIGN(“https://{HOST}/path“, appkey, appsecret, ts, nonce);
        // 其中 appkey、appsecret 接⼊时告知
        // SIGN ⽅法如下：
        String appkey = "3CBPO";
        String appSecret = "EPPTYFJW1KMZNQQRZTREPCHTASDF5NTG";
//        String ts = getTimestamp();
        String ts = "1726894714742";
//        String nonce = getRandomString();
        String nonce = "JnxO5pTKlE";
        StringBuffer sb = new StringBuffer();
        sb.append("https://" + host + "/" + path)
                .append("&").append(appkey)
                .append("&").append(appSecret)
                .append("&").append(ts)
                .append("&").append(nonce);
        String sign = getMD5Str(sb.toString());
        return sign;
    }

    public static void main(String[] args) {
        System.out.println(getMD5Str("123"));
        System.out.println(getTimestamp());
        System.out.println(getRandomString());
        System.out.println(gen_sign("www.fq7wmx2lja.com","pt/productPangolinTask"));
    }
}
