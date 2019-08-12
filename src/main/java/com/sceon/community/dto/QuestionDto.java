package com.sceon.community.dto;

import com.sceon.community.model.User;
import lombok.Data;

/**
 * @author shichenchong@inspur.com
 * data   2019/8/7 15:40
 */
@Data
public class QuestionDto {
    private Integer id;
    private String title;
    private String descriptions;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private String tag;
    private User user;
}
