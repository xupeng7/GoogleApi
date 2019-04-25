package com.googleTest.controller;

import com.googleTest.pojo.Google_AccessToken_Result;
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

    @RequestMapping("/googleCode")
    public String index(HttpServletRequest request){
        String  code=request.getParameter("code");
        System.out.println(code);
        Google_AccessToken_Result google_accessToken_result= googleAccessTokenHttpClient.doPost(code);
        System.out.println("token:"+google_accessToken_result.getAccess_token());
        return "success";
    }

}
