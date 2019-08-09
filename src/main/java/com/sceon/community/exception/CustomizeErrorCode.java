package com.sceon.community.exception;

/**
 * @author shichenchong@inspur.com
 * data   2019/8/9 15:33
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND("你要找的问题不存在，需要换一个哦");
    private String message;
    @Override
    public String getMessage() {
        return message;
    }

    CustomizeErrorCode(String message) {
        this.message = message;
    }
}
