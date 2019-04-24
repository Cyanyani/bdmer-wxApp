package com.bdmer.wxapp.tool;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * http请求类
 *
 * @since 2019-04-24
 * @author gongdelang
 */
public class HttpClientHelper {
    private static final Logger LOG = LoggerFactory.getLogger(HttpClientHelper.class);

    /**
     * get 请求 - 带参数
     * @param url
     * @param param
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public static String doGet(String url, Map<String, String> param) throws IOException, URISyntaxException {

        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;

        // 创建uri
        URIBuilder builder = new URIBuilder(url);
        if (param != null) {
            for (String key : param.keySet()) {
                builder.addParameter(key, param.get(key));
            }
        }
        URI uri = builder.build();

        // 创建GET请求
        HttpGet httpGet = new HttpGet(uri);

        // 执行请求
        LOG.info("[HttpClient-doGet]:" + "地址：" + uri);
        response = httpClient.execute(httpGet);
        if(response == null ){
            throw new RuntimeException("HttpClient Failed Response == null");
        }
        if (response.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException("HttpClient Failed Code:" + response.getStatusLine().getStatusCode());
        }

        response.close();
        httpClient.close();

        return EntityUtils.toString(response.getEntity(), "UTF-8");
    }

    /**
     * get 请求 - 不带参数
     * @param url
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public static String doGet(String url) throws IOException, URISyntaxException {
        return doGet(url, null);
    }

    /**
     * post 表单请求 - 带参数
     * @param url
     * @param param
     * @return
     * @throws IOException
     */
    public static String doPost(String url, Map<String, String> param) throws IOException {

        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;

        // 创建Post请求
        HttpPost httpPost = new HttpPost(url);
        if (param != null) {
            List<NameValuePair> paramList = new ArrayList<>();
            for (String key : param.keySet()) {
                paramList.add(new BasicNameValuePair(key, param.get(key)));
            }
            // 模拟表单
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
            httpPost.setEntity(entity);
        }

        // 执行请求
        LOG.info("[HttpClient-doGet]:" + "地址：" + url + "参数："+ param);
        response = httpClient.execute(httpPost);
        if(response == null ){
            throw new RuntimeException("HttpClient Failed Response == null");
        }
        if (response.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException("HttpClient Failed Code:" + response.getStatusLine().getStatusCode());
        }
        response.close();
        httpClient.close();

        return EntityUtils.toString(response.getEntity(), "UTF_8");
    }

    /**
     * post 请求 - 不带参数
     * @param url
     * @return
     * @throws IOException
     */
    public static String doPost(String url) throws IOException {
        return doPost(url, null);
    }

    /**
     * post json请求
     * @param url
     * @param json
     * @return
     * @throws IOException
     */
    public static String doPostJson(String url, String json) throws IOException {

        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;

        // 创建Http Post请求
        HttpPost httpPost = new HttpPost(url);
        // 创建请求内容
        StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
        httpPost.setEntity(entity);

        // 执行请求
        LOG.info("[HttpClient-doGet]:" + "地址：" + url + "参数："+ json);
        response = httpClient.execute(httpPost);
        if(response == null ){
            throw new RuntimeException("HttpClient Failed Response == null");
        }
        if (response.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException("HttpClient Failed Code:" + response.getStatusLine().getStatusCode());
        }
        response.close();
        httpClient.close();

        return EntityUtils.toString(response.getEntity(), "UTF-8");
    }
}
