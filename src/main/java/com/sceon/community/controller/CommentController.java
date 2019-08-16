package com.sceon.community.controller;

import com.sceon.community.dto.CommentDto;
import com.sceon.community.dto.ResponseDto;
import com.sceon.community.enums.CommentTypeEnum;
import com.sceon.community.exception.CustomizeErrorCode;
import com.sceon.community.mapper.NotificationMapper;
import com.sceon.community.model.Comment;
import com.sceon.community.dto.CommentCreateDto;
import com.sceon.community.model.Notification;
import com.sceon.community.model.User;
import com.sceon.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author shichenchong@inspur.com
 * data   2019/8/10 11:16
 */
@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private NotificationMapper notificationMapper;
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
        commentService.insertComment(comment,user);

            return ResponseDto.successOf();

            //return ResponseDto.errorOf(CustomizeErrorCode.INSERT_ERROR);



    }
    @ResponseBody
    @GetMapping("/comment/{id}")
    public ResponseDto<List<CommentDto>> comments(@PathVariable(name = "id") Long id){
        //System.out.println(id);
        List<CommentDto> commentDtos = commentService.listByTargetId(id, CommentTypeEnum.COMMENT);
        return ResponseDto.successOf(commentDtos);
    }
}
