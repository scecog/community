package com.sceon.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shichenchong@inspur.com
 * data   2019/8/7 19:47
 */
@Data
public class PageDto<T> {
    private List<T> list;
    private boolean showPrevious;
    private boolean showFirst;
    private boolean showNext;
    private boolean showEnd;
    private Integer currentPage;//当前页码
    private List<Integer> pages = new ArrayList<>();
    private Integer totalPage;


    public void setPageSum(Integer count, Integer pageSize, Integer pageNum) {

        //Integer totalPage = 0;
        totalPage = (count%pageSize==0) ? count/pageSize : count/pageSize + 1;
        if(pageNum<1){
            pageNum = 1;
        }
        if(pageNum > totalPage){
            pageNum=totalPage;
        }
        this.currentPage = pageNum;
        pages.add(currentPage);
        //当前页面可以进行向前插入和向后插入值
        for(int i = 1; i <= 3 ; i++){
            if(currentPage - i > 0){
                pages.add(0,currentPage-i);
            }
            if(currentPage + i <= totalPage){
                pages.add(currentPage + i);
            }
        }
        //当页面是第一页时，没有向前一页的按钮
        if(currentPage == 1){
            showPrevious = false;
        }else {
            showPrevious = true;
        }
        if(currentPage == totalPage){
            showNext = false;
        }else {
            showNext = true;
        }
        //是否展示回到首页，当页码都不包含首页时，出现这个按钮
        if(!pages.contains(1)){
            showFirst = true;
        }else {
            showFirst = false;
        }
        //展示到尾页，相同的道理
        if(!pages.contains(totalPage)){
            showEnd = true;
        }else {
            showEnd = false;
        }
    }
}
