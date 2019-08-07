package com.sceon.community.pojo;

import lombok.Data;

/**
 * @author shichenchong@inspur.com
 * data   2019/8/5 22:46
 */
@Data
public class GithubUserPojo {
    private long id;
    private String name;
    private String login;
    private String avatar_url;
}
