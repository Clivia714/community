package com.hhhyu.community.model;



import lombok.Data;

import java.util.Date;

@Data
public class Question {

    private Integer id;
    private String title;
    private String description;
    private Date gmtCreate;
    private Date gmtModified;
    private Integer creator;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private String tag;


}
