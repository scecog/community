package com.sceon.community.exception;

/**
 * @author shichenchong@inspur.com
 * data   2019/8/9 15:33
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND(2001,"你要找的问题不存在，需要换一个哦"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何的问题或者评论进行回复操作"),
    NO_LOGIN(2003,"未登录请登录后进行回复操作"),
    INSERT_ERROR(2004,"提交回复失败"),
    SERVER_ERROR(2005,"服务器开小差，稍后再战" ),
    TYPE_PARAM_WRONG(2006,"评论类型错误或不存在" ),
    COMMET_NOT_FOUND(2007,"回复的评论不存在" );

    private String message;
    private Integer code;
    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }
}
