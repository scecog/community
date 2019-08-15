package com.sceon.community.dto;

import com.sceon.community.exception.CustomizeErrorCode;
import com.sceon.community.exception.CustomizeException;
import lombok.Data;

import java.util.List;

/**
 * @author shichenchong@inspur.com
 * data   2019/8/10 14:45
 */
@Data
public class ResponseDto<T> {
    private Integer code;
    private String message;
    private T data;

   /* public static ResponseDto errorOf(Integer code,String message){
        ResponseDto responseDto = new ResponseDto();
        responseDto.setCode(code);
        responseDto.setMessage(message);
        return responseDto;
    }
*/
    public static ResponseDto errorOf(CustomizeErrorCode errorCode) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage(errorCode.getMessage());
        responseDto.setCode(errorCode.getCode());
        return responseDto;
    }

    public static ResponseDto successOf() {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage("成功");
        responseDto.setCode(200);
        return responseDto;
    }

    public static ResponseDto errorOf(CustomizeException ex) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage(ex.getMessage());
        responseDto.setCode(ex.getCode());
        return responseDto;
    }
    public static <T> ResponseDto successOf(T t) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage("成功");
        responseDto.setCode(200);
        responseDto.setData(t);
        return responseDto;
    }
}
