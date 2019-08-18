package com.sceon.community.controller;

import com.sceon.community.mapper.UserMapper;
import com.sceon.community.model.User;
import com.sceon.community.dto.AccessTokenPojo;
import com.sceon.community.dto.GithubUserPojo;
import com.sceon.community.provider.GithubProvider;
import com.sceon.community.service.UserService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
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
        if(githubUser != null && githubUser.getName() != null){
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            //System.out.println(githubUser.getAvatarUrl());
            user.setAvatarUrl(githubUser.getAvatar_url());
            //System.out.println(user);
            userService.createOrUpdate(user);
            //userMapper.insert(user);
            response.addCookie(new Cookie("token",token));
            return "redirect:/";
        }else {
            log.error("callback get github error ,{}",githubUser);
            //登录失败重新登录
            return "redirect:/";
        }

    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response){
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }

}
