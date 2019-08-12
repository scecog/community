package com.sceon.community.service;

import com.sceon.community.enums.CommentTypeEnum;
import com.sceon.community.exception.CustomizeErrorCode;
import com.sceon.community.exception.CustomizeException;
import com.sceon.community.mapper.CommentMapper;
import com.sceon.community.mapper.QuestionMapper;
import com.sceon.community.model.Comment;
import com.sceon.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void insertComment(Comment comment) {
        if(comment.getParentId() == null || comment.getParentId() == 0){
            throw  new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if(comment.getType() == null || !CommentTypeEnum.isExit(comment.getType())){
            throw  new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if (comment.getType() == CommentTypeEnum.QUESTION.getType()){
            System.out.println(comment.getParentId());
            //回复问题
            Question question = questionMapper.findById(comment.getParentId());
            if (question == null){
                throw  new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insertComment(comment);
            question.setCommentCount(1);
            questionMapper.update(question);

        }else {
            //回复评论
            Comment dbComment = commentMapper.selectByParentId(comment.getParentId());
            if(dbComment == null){
                throw  new CustomizeException(CustomizeErrorCode.COMMET_NOT_FOUND);
            }
            commentMapper.insertComment(dbComment);
        }

    }
}
