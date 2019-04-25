package com.googleTest.utils;

import com.alibaba.fastjson.JSONObject;
import com.googleTest.pojo.Google_AccessToken_Result;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GoogleAccessTokenHttpClient {



    public Google_AccessToken_Result doPost(String code){

        Google_AccessToken_Result accessToken_result= new Google_AccessToken_Result();
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 参数
        StringBuffer params = new StringBuffer();
        try {
            // 封装参数 cmd固定是1
            params.append("grant_type=authorization_code");
            params.append("&");
            params.append("code="+code);
            params.append("&");
            params.append("client_id=949901794911-f16s6a1aoh36lm99stpo892h5lt5tncp.apps.googleusercontent.com");
            params.append("&");
            params.append("client_secret=2h2HACjAiKzmh3tlQD6qCIsT");
            params.append("&");
            params.append("redirect_uri=http://seven.natapp1.cc/googleCode");
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        // 创建Post请求
        HttpPost httpPost = new HttpPost("https://accounts.google.com/o/oauth2/token" + "?" + params);

        // 设置ContentType(注:如果只是传普通参数的话,ContentType不一定非要用application/json)
        httpPost.setHeader("Content-Type", "application/text;charset=utf8");

        //设置请求超时时间 好像不管用
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000).setConnectionRequestTimeout(5000)
                .setSocketTimeout(5000).build();
        httpPost.setConfig(requestConfig);
        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 由客户端执行(发送)请求
            response = httpClient.execute(httpPost);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            System.out.println("响应状态为:" + response.getStatusLine());

            if (responseEntity != null) {
                System.out.println("响应内容长度为:" + responseEntity.getContentLength());

                //不能直接做比较 一定要将响应内容变为byte[] 在转为string 类型
                byte[] bytes=EntityUtils.toByteArray(responseEntity);
                String  content=new String(bytes);
                System.out.println(content);
                //获取返回的内容 如果有响应码200 就将数据返回

                if(response.getStatusLine().getStatusCode()==200){
                    //得到jsonObject对象
                   JSONObject jsonObject= JSONObject.parseObject(content);
                   //将JSONObject 对象转为 java对象
                    accessToken_result=JSONObject.toJavaObject(jsonObject,Google_AccessToken_Result.class);

                }




            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

          return accessToken_result;
    }
}
