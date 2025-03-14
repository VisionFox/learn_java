package com.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.brotli.dec.BrotliInputStream;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.apache.kafka.common.utils.Utils.isBlank;


public class HttpTest {
    public static void main(String[] args) throws IOException {
        ss();
        if (true){
            return;
        }
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet("https://io.dexscreener.com/dex/log/amm/v2/solamm/top/solana/6Xmm3zYK2FTMRXQyQes3EoTfp8oGvTp5KrEBDF58dhAC?q=So11111111111111111111111111111111111111112");
        httpGet.addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chro" +
                "me/53.0.2785.104 Safari/537.36 Core/1.53.2372.400 QQBrowser/9.5.10548.400");
        httpGet.addHeader("accept-encoding", "gzip, deflate, br, zstd");

        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            System.out.println(response.getCode());
            System.out.println(JSONObject.toJSONString(response.getHeaders()));

            BrotliInputStream brotliInputStream = new BrotliInputStream(response.getEntity().getContent());

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] tempBuffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = brotliInputStream.read(tempBuffer)) != -1) {
                buffer.write(tempBuffer, 0, bytesRead);
            }

            byte[] byteArray = buffer.toByteArray();
            System.out.println(byteArray.length);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void ss() throws IOException {
        URL url = new URL("https://io.dexscreener.com/dex/log/amm/v2/solamm/top/solana/6Xmm3zYK2FTMRXQyQes3EoTfp8oGvTp5KrEBDF58dhAC?q=So11111111111111111111111111111111111111112");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
//        con.setRequestMethod("GET");
//        con.setRequestProperty("Transfer-Encoding", "chunked");
        con.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chro" +
                "me/53.0.2785.104 Safari/537.36 Core/1.53.2372.400 QQBrowser/9.5.10548.400");
        con.setRequestProperty("accept-encoding", "gzip, deflate, br, zstd");
        con.setRequestProperty("Transfer-Encoding", "chunked");

        int responseCode = con.getResponseCode();
        System.out.println("Response code: " + responseCode);

        InputStream inputStream = con.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line.length());
            System.out.println(new String(line.getBytes(),"utf8"));
        }

        reader.close();
        con.disconnect();
    }

}
