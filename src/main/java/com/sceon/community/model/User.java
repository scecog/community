package com.sceon.community.model;

import lombok.Data;

/**
 * @author shichenchong@inspur.com
 * data   2019/8/6 11:05
 */
@Data
public class User {
    private int id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarUrl;
}
