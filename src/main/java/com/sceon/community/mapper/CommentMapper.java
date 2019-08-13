package com.sceon.community.mapper;

import com.sceon.community.model.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author shichenchong@inspur.com
 * data   2019/8/10 11:36
 */
@Mapper
public interface CommentMapper {
    Integer insertComment(Comment comment);

    Comment selectByParentId(Long parentId);

    List<Comment> listByQuestionId(Long parentId, Integer type);
}
