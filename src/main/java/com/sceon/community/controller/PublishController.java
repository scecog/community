package com.sceon.community.controller;

import com.sceon.community.exception.CustomizeErrorCode;
import com.sceon.community.exception.CustomizeException;
import com.sceon.community.mapper.QuestionMapper;
import com.sceon.community.model.Question;
import com.sceon.community.model.User;
import com.sceon.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

/**
 * @author shichenchong@inspur.com
 * data   2019/8/6 22:31
 */
@Controller
public class PublishController {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionService questionService;
    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam("title") String title,
            @RequestParam("descriptions") String descriptions,
            @RequestParam("tag") String tag, HttpServletRequest request, Model model,
                            //前端将id传入进来，可以为空
                            @RequestParam("id") Long id){
        model.addAttribute("title",title);
        model.addAttribute("descriptions",descriptions);
        model.addAttribute("tag",tag);
        if(title == null || title==""){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if(descriptions == null || descriptions==""){
            model.addAttribute("error","描述不能为空");
            return "publish";
        }
        if(tag == null || tag == ""){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }
        User user = (User)request.getSession().getAttribute("user");
        if(user == null){
            return "redirect:/";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescriptions(descriptions);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(user.getGmtModified());
        question.setId(id);
        questionService.createOrUpdate(question);
        //questionMapper.create(question);
        return "redirect:/";
    }
    @GetMapping("/publish/{id}")
    public String updateQuestion(@PathVariable(name = "id") Long id,
                                 Model model){
        //将question根据id查询出来，然后传递给前端三个参数，id作为在前端的隐藏域，也可以为空，例如发布问题时
        Question question = questionMapper.findById(id);
        if(question == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        model.addAttribute("title",question.getTitle());
        model.addAttribute("descriptions",question.getDescriptions());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",id);
        return "publish";
    }
}
