package com.sceon.community.controller;

import com.sceon.community.dto.QuestionDto;
import com.sceon.community.enums.NotifactionStatusEnum;
import com.sceon.community.model.Notification;
import com.sceon.community.model.User;
import com.sceon.community.dto.PageDto;
import com.sceon.community.service.CommentService;
import com.sceon.community.service.NotificationService;
import com.sceon.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author shichenchong@inspur.com
 * data   2019/8/8 10:48
 */
@Controller
public class ProfileController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private NotificationService notificationService;
    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          Model model, HttpServletRequest request,
                          @RequestParam(name = "pageNum",defaultValue = "1") Integer pageNum,
                          @RequestParam(name = "pageSize",defaultValue = "5") Integer pageSize){
        User user = (User)request.getSession().getAttribute("user");
        if(user == null){
            return "index";
        }
        if("question".equals(action)) {
            PageDto pagedto = questionService.list(user.getId(), pageNum, pageSize);
            model.addAttribute("pagedto",pagedto);
            model.addAttribute("section","question");
            model.addAttribute("sectionName","我的提问");
        }else if("replies".equals(action)){
            //50 获取到的list
            PageDto pageDto = notificationService.list(user.getId(),pageNum,pageSize);
            Integer unReadCount = notificationService.unReadCount(user.getId(), NotifactionStatusEnum.UNREAD.getStatus());
            model.addAttribute("pagedto",pageDto);
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
            model.addAttribute("unReadCount",unReadCount);

        }

        return "profile";
    }
    @GetMapping("/delete/{id}")
    public String deleteQuestion(@PathVariable(name = "id") Long id,
                                 HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        if(user == null){
            return "redirect:/";
        }
        questionService.deleteById(id,user);
        return "redirect:/profile/question";
    }
}
