package com.sceon.community.controller;

import com.sceon.community.mapper.UserMapper;
import com.sceon.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author shichenchong@inspur.com
 * data   2019/8/5 19:23
 *
 */
@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;
    @RequestMapping("/")
    public String index(HttpServletRequest request) {
        //这种逻辑下cookie好像不可能为空，因为清除了cookie的话，一点击登录直接就会创建cooki
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie: cookies) {
            if(cookie.getName().equals("token")){
                String token = cookie.getValue();
                User user = userMapper.findByToken(token);
                if(user != null){
                        request.getSession().setAttribute("user",user);
                }
                break;
            }
        }

        return "index";
    }
}