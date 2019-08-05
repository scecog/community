package com.sceon.community.controller;

import com.alibaba.fastjson.JSON;
import com.sceon.community.pojo.AccessTokenPojo;
import com.sceon.community.pojo.GithubUserPojo;
import com.sceon.community.provider.GithubProvider;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author shichenchong@inspur.com
 * data   2019/8/5 21:56
 */
@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;
    @GetMapping("callback")
    public String callback(@RequestParam (name = "code") String code,
                           @RequestParam (name = "state") String state){
        AccessTokenPojo accessTokenPojo = new AccessTokenPojo();
        accessTokenPojo.setCode(code);
        accessTokenPojo.setState(state);
        accessTokenPojo.setClient_id(clientId);
        accessTokenPojo.setClient_secret(clientSecret);
        accessTokenPojo.setRedirect_uri(redirectUri);
        String accessToken = githubProvider.getAccessToken(accessTokenPojo);
        GithubUserPojo userPojo = githubProvider.getUserInfo(accessToken);
        System.out.println(userPojo);
        return "index";
    }

}
