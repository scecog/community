package com.sceon.community.model;

import lombok.Data;
import org.apache.ibatis.annotations.Insert;

/**
 * @author shichenchong@inspur.com
 * data   2019/8/7 8:52
 */
@Data
public class Question {
    private Long id;
    private String title;
    private String descriptions;
    private Long gmtCreate;
    private Long gmtModified;
    private Long creator;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private String tag;

}
