package com.sceon.community.controller;

import com.sceon.community.mapper.QuestionMapper;
import com.sceon.community.mapper.UserMapper;
import com.sceon.community.model.Question;
import com.sceon.community.model.User;
import com.sceon.community.pojo.PageDto;
import com.sceon.community.pojo.QuestionDto;
import com.sceon.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author shichenchong@inspur.com
 * data   2019/8/5 19:23
 *
 */
@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;
    @RequestMapping("/")
    //分页功能需要页码和每页的数据量，计算出偏移量，例如第一页就是从0开始计算到5
    public String index(HttpServletRequest request, Model model,
                        @RequestParam(name = "pageNum",defaultValue = "1") Integer pageNum,
                        @RequestParam(name = "pagezSize",defaultValue = "5") Integer pageSize) {
        //这种逻辑下cookie好像不可能为空，因为清除了cookie的话，一点击登录直接就会创建cooki

        /*if(pageNum < 1){
            pageNum = 1;
        }*/

        Cookie[] cookies = request.getCookies();
        if(cookies == null){
            return "index";
        }
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
        PageDto page = questionService.listQuestion(pageNum,pageSize);
        model.addAttribute("pagei",page);
        return "index";
    }
}