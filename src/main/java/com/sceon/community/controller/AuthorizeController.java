package com.sceon.community.controller;

import com.alibaba.fastjson.JSON;
import com.sceon.community.mapper.UserMapper;
import com.sceon.community.model.User;
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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * @author shichenchong@inspur.com
 * data   2019/8/5 21:56
 */
@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @Autowired
    private UserMapper userMapper;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;
    @GetMapping("callback")
    public String callback(@RequestParam (name = "code") String code,
                           @RequestParam (name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response){
        AccessTokenPojo accessTokenPojo = new AccessTokenPojo();
        accessTokenPojo.setCode(code);
        accessTokenPojo.setState(state);
        accessTokenPojo.setClient_id(clientId);
        accessTokenPojo.setClient_secret(clientSecret);
        accessTokenPojo.setRedirect_uri(redirectUri);
        String accessToken = githubProvider.getAccessToken(accessTokenPojo);
        GithubUserPojo githubUser = githubProvider.getUserInfo(accessToken);
        if(githubUser != null){
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            //System.out.println(user);
            userMapper.insert(user);
            response.addCookie(new Cookie("token",token));
            //登录成功设置cookie，session
            /*HttpSession session = request.getSession();
            //用户信息设置到session
            session.setAttribute("user",githubUser);*/
            //重定向至主页
            return "redirect:/";
        }else {
            //登录失败重新登录
            return "redirect:/";
        }

    }

}