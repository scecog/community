package com.sceon.community.advice;

import com.alibaba.fastjson.JSON;
import com.sceon.community.dto.ResponseDto;
import com.sceon.community.exception.CustomizeErrorCode;
import com.sceon.community.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author shichenchong@inspur.com
 * data   2019/8/9 14:55
 */
@ControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(Exception.class)
    Object handle(Throwable ex, Model model, HttpServletRequest request, HttpServletResponse response) {
        String contentType = request.getContentType();
        //System.out.println(contentType);
        if ("application/json".equals(contentType)) {
            ResponseDto resultDTO;
            // 返回 JSON
            if (ex instanceof CustomizeException) {
                resultDTO = ResponseDto.errorOf((CustomizeException) ex);
            } else {
                resultDTO = ResponseDto.errorOf(CustomizeErrorCode.SERVER_ERROR);
            }
            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("utf-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException ioe) {
            }
            return null;
        } else {
            // 错误页面跳转
            if (ex instanceof CustomizeException) {
                model.addAttribute("message", ex.getMessage());
            } else {

                model.addAttribute("message", CustomizeErrorCode.SERVER_ERROR.getMessage());
            }
            return new ModelAndView("error");
        }
    }
}
