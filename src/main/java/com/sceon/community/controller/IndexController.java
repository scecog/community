package com.sceon.community.controller;

import com.sceon.community.dto.PageDto;
import com.sceon.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author shichenchong@inspur.com
 * data   2019/8/5 19:23
 *
 */
@Controller
public class IndexController {
    @Autowired
    private QuestionService questionService;
    @RequestMapping("/")
    //分页功能需要页码和每页的数据量，计算出偏移量，例如第一页就是从0开始计算到5
    public String index(HttpServletRequest request, Model model,
                        @RequestParam(name = "pageNum",defaultValue = "1") Integer pageNum,
                        @RequestParam(name = "pageSize",defaultValue = "5") Integer pageSize,
                        @RequestParam(name = "search",required = false) String search) {
        PageDto page = questionService.listQuestion(pageNum,pageSize,search);
        //System.out.println(page.getList());
        model.addAttribute("search",search);
        model.addAttribute("pagei",page);
        return "index";
    }
}