package com.googleTest.utils;

import com.alibaba.fastjson.JSONObject;
import com.googleTest.pojo.Google_AccessToken_Result;
import com.googleTest.pojo.SubscriptionInfo;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GetSubscriptionInfo {
    /*
     *  access_token 是请求google api必须要有的
     *  token 是确定一个订阅的唯一标识 应该保存到db 用时去拿
     * */
    public SubscriptionInfo doGet(String access_token, String token) {

        SubscriptionInfo subscriptionInfo = new SubscriptionInfo();
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet("https://www.googleapis.com/androidpublisher/v3/applications/com.conepoke/purchases/subscriptions/conepoke_kaihi/tokens/" + token);
        httpGet.setHeader("Authorization", "Bearer " + access_token);

        CloseableHttpResponse response = null;
        try {
            System.out.println(1);
            // 由客户端执行(发送)请求
            response = httpClient.execute(httpGet);
            System.out.println(2);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            System.out.println("响应状态为:" + response.getStatusLine());

            if (responseEntity != null) {
                System.out.println("响应内容长度为:" + responseEntity.getContentLength());

                //不能直接做比较 一定要将响应内容变为byte[] 在转为string 类型
                byte[] bytes = EntityUtils.toByteArray(responseEntity);
                String content = new String(bytes);
                System.out.println(content);
                //获取返回的内容 如果有响应码200 就将数据返回

                if (response.getStatusLine().getStatusCode() == 200) {
                    //得到jsonObject对象
                    JSONObject jsonObject = JSONObject.parseObject(content);
                    //将JSONObject 对象转为 java对象
                    subscriptionInfo = JSONObject.toJavaObject(jsonObject, SubscriptionInfo.class);

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

        return subscriptionInfo;
    }
}
