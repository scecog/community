package com.sceon.community.model;

import lombok.Data;

/**
 * @author shichenchong@inspur.com
 * data   2019/8/10 11:11
 */
@Data
public class Comment {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentId;
    private String commentContent;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private Integer commentCount;
}
