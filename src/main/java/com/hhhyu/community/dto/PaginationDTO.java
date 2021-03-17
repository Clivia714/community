package com.hhhyu.community.dto;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;


//分页类
@Data
public class PaginationDTO {
    private List<QuestionDTO> questions;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showLastPage;
    //当前页
    private Integer pageNo;
    //当前显示页码数（一共显示多少页）
    private List<Integer> pages = new ArrayList<>();
    private Integer totalPage;

    public void setPagination(Integer pageNo, Integer totalPage) {
        this.totalPage = totalPage;
        this.pageNo = pageNo;
        pages.add(pageNo);
        for(int i = 1; i < 4; i++){
            if(pageNo - i > 0){
                pages.add(0, pageNo - i);
            }
            if(pageNo + i <= totalPage){
                pages.add(pageNo + i);
            }
        }

        //展示上一页
        if(pageNo == 1){
            this.showPrevious = false;
        }else {
            this.showPrevious = true;
        }
        //展示下一页
        if(pageNo == totalPage){
            this.showNext = false;
        }else {
            this.showNext = true;
        }
        //是否展示第一页
        if(pages.contains(1)){
            this.showFirstPage = false;
        }else {
            this.showFirstPage = true;
        }
        //是否展示最后一页
        if(pages.contains(totalPage)){
            this.showLastPage = false;
        }else {
            this.showLastPage = true;
        }

    }
}
