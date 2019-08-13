package com.sceon.community.dto;

import lombok.Data;

/**
 * @author shichenchong@inspur.com
 * data   2019/8/10 14:00
 */
@Data
public class CommentCreateDto {
    private Long parentId;
    private int type;
    private String commentContent;
}
