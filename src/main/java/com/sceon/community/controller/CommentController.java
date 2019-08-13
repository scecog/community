package com.sceon.community.controller;

import com.sceon.community.dto.ResponseDto;
import com.sceon.community.exception.CustomizeErrorCode;
import com.sceon.community.mapper.CommentMapper;
import com.sceon.community.model.Comment;
import com.sceon.community.dto.CommentCreateDto;
import com.sceon.community.model.User;
import com.sceon.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author shichenchong@inspur.com
 * data   2019/8/10 11:16
 */
@Controller
public class CommentController {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private CommentService commentService;
    @ResponseBody
    @PostMapping("/comment")
    public Object post(@RequestBody CommentCreateDto commentdto,
                       HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        if(user == null){
            return ResponseDto.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        if (StringUtils.isBlank(commentdto.getCommentContent())){
            return ResponseDto.errorOf(CustomizeErrorCode.COMMENT_NOT_EMPTY);
        }
        Comment comment = new Comment();
        comment.setParentId(commentdto.getParentId());
        comment.setCommentContent(commentdto.getCommentContent());
        comment.setType(commentdto.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentId(user.getId());
        commentService.insertComment(comment);
            return ResponseDto.successOf();

            //return ResponseDto.errorOf(CustomizeErrorCode.INSERT_ERROR);



    }
}
