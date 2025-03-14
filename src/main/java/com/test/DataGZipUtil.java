package com.test;

import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class DataGZipUtil {
    public static final String GZIP_ENCODE_UTF_8 = "UTF-8";

    /**
     * base64 编码
     *
     * @param bytes 传⼊ bytes
     * @return 编码成 string 类型的 base64 返回
     */
    public static String base64encode(byte[] bytes) {
        return new String(Base64.getEncoder().encode(bytes));
    }

    /**
     * base64 解码
     *
     * @param string 传⼊ string 类型的 base64 编码
     * @return 解码成 byte 类型返回
     */
    public static byte[] base64decode(String string) {
        return Base64.getDecoder().decode(string);
    }

    /**
     * 压缩字符串
     *
     * @param string 需要压缩的字符串
     * @return 压缩后内容 并转 base64 返回
     */
    public static String gzip(String string) {
        String result = "";
        if (StringUtils.isBlank(string)) {
            return result;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(string.getBytes(GZIP_ENCODE_UTF_8));
            gzip.close();
            out.close();
            result = base64encode(out.toByteArray());
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("DataGZipUtil gzip error:");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 解压缩字符串
     *
     * @param string base64 格式的压缩后字符串
     * @return 解码并解压缩后返回
     */
    public static String unGzip(String string) {
        String result = "";
        if (StringUtils.isBlank(string)) {
            return result;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in;
        GZIPInputStream ungzip;
        byte[] bytes = base64decode(string);
        try {
            in = new ByteArrayInputStream(bytes);
            ungzip = new GZIPInputStream(in);
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = ungzip.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            ungzip.close();
            out.close();
            in.close();
            result = out.toString(GZIP_ENCODE_UTF_8);
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("DataGZipUtil unGzip error:");
            e.printStackTrace();
        }
        return result;
    }
}
