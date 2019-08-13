package com.sceon.community.dto;

import com.sceon.community.model.User;
import lombok.Data;

/**
 * @author shichenchong@inspur.com
 * data   2019/8/12 21:27
 */
@Data
public class CommentDto {
    private Long id;
    private Long parentId;
    private Integer type;
    private int commentId;
    private String commentContent;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private User user;
}
