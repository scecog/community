package com.sceon.community.service;

import com.sceon.community.dto.CommentDto;
import com.sceon.community.enums.CommentTypeEnum;
import com.sceon.community.exception.CustomizeErrorCode;
import com.sceon.community.exception.CustomizeException;
import com.sceon.community.mapper.CommentMapper;
import com.sceon.community.mapper.QuestionMapper;
import com.sceon.community.mapper.UserMapper;
import com.sceon.community.model.Comment;
import com.sceon.community.model.Question;
import com.sceon.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author shichenchong@inspur.com
 * data   2019/8/10 14:59
 */
@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    @Transactional
    public void insertComment(Comment comment) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType() == null || !CommentTypeEnum.isExit(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if (comment.getType() == CommentTypeEnum.QUESTION.getType()) {
            //System.out.println(comment.getParentId());
            //回复问题
            Question question = questionMapper.findById(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insertComment(comment);
            question.setCommentCount(1);
            questionMapper.addCommentCount(question);

        } else {
            //回复评论
            Comment dbComment = commentMapper.selectByParentId(comment.getParentId());
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insertComment(dbComment);
        }

    }

    /*
     * 获取到回复的列表，根据父类问题的id，查询出回复的全部内容,
     * 然后根据comment 获取到 comment_id 查询出user，进而获取到用户信息，
     * 注入用户信息到commentDto
     */
    public List<CommentDto> listByQuestionId(Long id) {
        //需要设定type为1
        List<Comment> commentList = commentMapper.listByQuestionId(id, CommentTypeEnum.QUESTION.getType());
        if (commentList.size() == 0) {
            return new ArrayList<>();
        }
        //获取去重的评论人id
        List<Integer> userIds = commentList.stream().map(comment -> comment.getCommentId()).collect(Collectors.toList());
        // 获取评论人的信息 转换为map
        //System.out.println(userIds);
        List<User> users = userMapper.listByIds(userIds);
        Map<Integer, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));
        //将评论的map转换为list
        List<CommentDto> commentDtos = commentList.stream().map(comment -> {
            CommentDto commentDto = new CommentDto();
            BeanUtils.copyProperties(comment, commentDto);
            commentDto.setUser(userMap.get(comment.getCommentId()));
            return commentDto;

        }).collect(Collectors.toList());
        return commentDtos;
    }
}
