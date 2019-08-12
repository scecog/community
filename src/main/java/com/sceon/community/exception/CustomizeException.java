package com.sceon.community.exception;

/**
 * @author shichenchong@inspur.com
 * data   2019/8/9 15:15
 */
public class CustomizeException extends RuntimeException{
    private String message;
    private Integer code;
    //获取到接口的方法，接口向下找到子类的方法
    public CustomizeException(ICustomizeErrorCode errorCode){
        this.code=errorCode.getCode();
        this.message=errorCode.getMessage();
    }
    @Override
    public String getMessage() {
        return message;
    }
    public Integer getCode() {return code;}

}
