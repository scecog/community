package com.sceon.community.dto;

import lombok.Data;

/**
 * @author shichenchong@inspur.com
 * data   2019/8/18 17:13
 */
@Data
public class QuestionQueryDto {
    private String search;
    private Integer pageNum;
    private Integer pageSize;
}
