package com.sceon.community.controller;

import com.sceon.community.dto.CommentCreateDto;
import com.sceon.community.dto.CommentDto;
import com.sceon.community.dto.QuestionDto;
import com.sceon.community.service.CommentService;
import com.sceon.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author shichenchong@inspur.com
 * data   2019/8/8 16:34
 */
@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           Model model){
        QuestionDto questionDto = questionService.getById(id);
        List<CommentDto> comments = commentService.listByQuestionId(id);
        model.addAttribute("comments",comments);

        //增加阅读数
        questionService.addView(id);
        model.addAttribute("question",questionDto);
        return "question";
    }

}
