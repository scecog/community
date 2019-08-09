package com.sceon.community.controller;

import com.sceon.community.mapper.QuestionMapper;
import com.sceon.community.pojo.QuestionDto;
import com.sceon.community.service.QuestionService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author shichenchong@inspur.com
 * data   2019/8/8 16:34
 */
@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id,
                           Model model){
        QuestionDto questionDto = questionService.getById(id);
        model.addAttribute("question",questionDto);
        return "question";
    }
}
