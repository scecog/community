package com.sceon.community.model;

import lombok.Data;

/**
 * @author shichenchong@inspur.com
 * data   2019/8/15 11:00
 */
@Data
public class Notification {
    private Long id;
    private Long notifier;
    private Long receiver;
    private Long outerId;
    private Integer type;
    private Long gmtCreate;
    private Integer status;
    private String notifierName;
    private String outerTitle;
}
