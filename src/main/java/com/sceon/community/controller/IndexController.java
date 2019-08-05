package com.sceon.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author shichenchong@inspur.com
 * data   2019/8/5 19:23
 */
@Controller

public class IndexController {
    @RequestMapping("/")
    public String index() {
        //System.out.println("1111");
        return "index";
    }
}