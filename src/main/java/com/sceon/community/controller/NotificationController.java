package com.sceon.community.controller;

import com.sceon.community.mapper.CommentMapper;
import com.sceon.community.model.Comment;
import com.sceon.community.model.Notification;
import com.sceon.community.model.User;
import com.sceon.community.service.CommentService;
import com.sceon.community.service.NotificationService;
import com.sceon.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

/**
 * @author shichenchong@inspur.com
 * data   2019/8/15 11:03
 */
@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String notification(@PathVariable(name = "id") Long id,
                               HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        if(user == null){
            return "redirect:/";
        }
        Notification notificationById = notificationService.findById(id);
        Long outerId = notificationById.getOuterId();
        notificationService.updateRead(notificationById);
        if(notificationById.getType() == 1){
            return "redirect:/question/" + outerId;
        }else {
            Comment commentByOuterId = notificationService.findCommentByOuterId(outerId);
            return "redirect:/question/" + commentByOuterId.getParentId();
        }

    }
}
