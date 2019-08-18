package com.sceon.community.controller;

import com.qcloud.cos.model.ObjectMetadata;
import com.sceon.community.dto.FileDto;
import com.sceon.community.provider.TencentCloudProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * @author shichenchong@inspur.com
 * data   2019/8/16 14:49
 */
@Controller
public class FileController {
    @Autowired
    private TencentCloudProvider tencentCloudProvider;
    @ResponseBody
    @RequestMapping("/file/upload")
    public FileDto upload(HttpServletRequest request){
        //MultipartHttpServletRequest 文件上传使用的request，获取文件好获取
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartRequest.getFile("editormd-image-file");
        String url = tencentCloudProvider.upload(multipartFile);
        FileDto fileDto = new FileDto();
        fileDto.setSuccess(1);
        fileDto.setMessage("成功");
        fileDto.setUrl(url);
        return fileDto;
    }
}
