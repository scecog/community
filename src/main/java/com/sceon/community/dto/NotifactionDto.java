package com.sceon.community.dto;

import com.sceon.community.model.Question;
import com.sceon.community.model.User;
import lombok.Data;

/**
 * @author shichenchong@inspur.com
 * data   2019/8/15 14:46
 */
@Data
public class NotifactionDto {
    private Long id;
    private Long gmtCreate;
    private Long outerId;
    private Integer status;
    private Long notifier;
    private String notifierName;
    private String outerTitle;
    private String type;
}
