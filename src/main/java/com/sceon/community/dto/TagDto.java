package com.sceon.community.dto;

import lombok.Data;

import java.util.List;

/**
 * @author shichenchong@inspur.com
 * data   2019/8/14 18:34
 */
@Data
public class TagDto {
    private String categoryName;
    private List<String> tags;
}
