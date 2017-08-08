package com.guo.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by lmm on 2017/3/30.
 */
public class HttpUtils {
    public static String post(String uri, List<NameValuePair> params) throws Exception {
        HttpClient client = new DefaultHttpClient();

        HttpPost post = new HttpPost(uri);

        HttpEntity formEntity = new UrlEncodedFormEntity(params);
        post.setEntity(formEntity);


        post.addHeader("Content-Type", "application/x-www-form-urlencoded");

        HttpResponse response =
                client.execute(post);

        String result = "";

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            InputStream is = response.getEntity().getContent();

            result = inStream2String(is);
        }

        return result;
    }

    public static String post(String uri, List<NameValuePair> params, String apiKey) throws Exception {
        HttpClient client = new DefaultHttpClient();

        HttpPost post = new HttpPost(uri);

        HttpEntity formEntity = new UrlEncodedFormEntity(params);
        post.setEntity(formEntity);


        if (apiKey != null && !apiKey.isEmpty()) {
            post.addHeader("shx_gov_api_key", apiKey);
        }
        post.addHeader("Content-Type", "application/json;charset=utf-8");
        post.setHeader("Accept", "application/json");
        post.setEntity(new StringEntity(formEntity.toString(), Charset.forName("UTF-8")));
        HttpResponse response =
                client.execute(post);

        String result = "";

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            InputStream is = response.getEntity().getContent();

            result = inStream2String(is);
        }

        return result;
    }

    public static String post_urlencoded(String uri, List<NameValuePair> params, String apiKey) throws Exception {
        HttpClient client = new DefaultHttpClient();

        HttpPost post = new HttpPost(uri);

        HttpEntity formEntity = new UrlEncodedFormEntity(params, "UTF-8");
        post.setEntity(formEntity);

        if (apiKey != null && !apiKey.isEmpty()) {
            post.addHeader("shx_gov_api_key", apiKey);
        }
//        post.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
//        post.setHeader("Accept", "application/x-www-form-urlencoded");
        post.setEntity(new StringEntity(formEntity.toString(), Charset.forName("UTF-8")));
        HttpResponse response =
                client.execute(post);

        String result = "";

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            InputStream is = response.getEntity().getContent();

            result = inStream2String(is);
        }

        return result;
    }

    static String inStream2String(InputStream is) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int len = -1;
        while ((len = is.read(buf)) != -1) {
            baos.write(buf, 0, len);
        }
        return new String(baos.toByteArray(), "UTF-8");
    }

    public static String postJson(String uri,String json, String apiKey) throws Exception {
        HttpClient client = new DefaultHttpClient();

        HttpPost post = new HttpPost(uri);
        if (apiKey != null && !apiKey.isEmpty()) {
            post.addHeader("shx_gov_api_key", apiKey);
        }
        post.setHeader("Content-type", "application/json");
        StringEntity requestEntity = new StringEntity(json,"utf-8");
        requestEntity.setContentEncoding("UTF-8");
        post.setEntity(requestEntity);
        HttpResponse response =
                client.execute(post);

        String result = "";

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            InputStream is = response.getEntity().getContent();

            result = inStream2String(is);
        }

        return result;
    }
}
