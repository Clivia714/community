package com.hhhyu.community.mapper;


import com.hhhyu.community.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.RequestParam;

@Mapper
public interface UserMapper {

    @Insert("insert into user(name, account_id, token, gmt_create, gmt_modified, avatar_url) " +
            "values(#{name}, #{accountId}, #{token}, #{gmtCreate}, #{gmtModified}, #{avatarUrl})")
    void insert(User user);


    @Select("select * from user where token = #{token}")
    User findMyToken(@Param("token") String token);//token不是类需要加注解

    @Select("select * from user where account_id = #{accountId}")
    User findMyAccountId(@Param("accountId") String accountId);

    @Update("update user set name = #{name}, gmt_modified = #{gmtModified} , token = #{token}, avatar_url = #{avatarUrl} where account_id = #{accountId}")
    int update(User user);
}
