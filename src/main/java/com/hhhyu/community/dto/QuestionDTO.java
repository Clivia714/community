package com.hhhyu.community.dto;


import com.hhhyu.community.model.User;
import lombok.Data;

import java.util.Date;


@Data
public class QuestionDTO {

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
    private User user;

}
