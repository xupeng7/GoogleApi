package com.googleTest.controller;

import com.googleTest.pojo.Google_AccessToken_Result;
import com.googleTest.pojo.SubscriptionInfo;
import com.googleTest.utils.GetSubscriptionInfo;
import com.googleTest.utils.GoogleAccessTokenHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@CrossOrigin
public class CommonController {

    @Autowired
    private GoogleAccessTokenHttpClient googleAccessTokenHttpClient;
    @Autowired
    private GetSubscriptionInfo getSubscriptionInfo;

    SubscriptionInfo subscriptionInfo = new SubscriptionInfo();

    @RequestMapping("/googleCode")
    public String index(HttpServletRequest request) {
        String code = request.getParameter("code");
        System.out.println(code);
        Google_AccessToken_Result google_accessToken_result = googleAccessTokenHttpClient.doPost(code);
        System.out.println("token:" + google_accessToken_result.getAccess_token());
        //一个订阅信息的唯一标识
        String token = "ahdnkgmecnpdoioeeajkmmgo.AO-J1OwYH7n1QdPTn-5bS2BBqHZXxOVwBzPOUsFYOZIdN-eLhCNajSrJ8Ci-ShOlGhHMPQESpMgI2-zyAYwUorbG_zGaxC8NGGnN5ShJwj5jnUfif9E1A7k";
        subscriptionInfo = getSubscriptionInfo.doGet(google_accessToken_result.getAccess_token(), token);
        System.out.println(subscriptionInfo.getOrderId());
        return "success";
    }

    @RequestMapping("/subcriptionInfo")
    public String getSubcriptionInfo(HttpServletRequest request) {

        String access_tokon = "ya29.Glz2Bmbwl-3l1w1xeH8KDZhNLxAp61ZV62ReFI0cxKmOnpOP3yG5kQDk8TZXT2rhkUy7GyxfAe2z-ZfoNZkhPDPa3p240EfAAqCGwA8kUbVXpTZFvrx8OcJEwat57w";
        String token = "ahdnkgmecnpdoioeeajkmmgo.AO-J1OwYH7n1QdPTn-5bS2BBqHZXxOVwBzPOUsFYOZIdN-eLhCNajSrJ8Ci-ShOlGhHMPQESpMgI2-zyAYwUorbG_zGaxC8NGGnN5ShJwj5jnUfif9E1A7k";
        subscriptionInfo = getSubscriptionInfo.doGet(access_tokon, token);
        System.out.println(subscriptionInfo.getAutoRenewing());

        return "success";
    }

}
