package com.sceon.community.dto;

import lombok.Data;

/**
 * @author shichenchong@inspur.com
 * data   2019/8/5 22:11
 */
@Data
public class AccessTokenPojo {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;

}
