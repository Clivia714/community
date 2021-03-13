package com.hhhyu.community.controller;

import com.hhhyu.community.dto.AccessTokenDTO;
import com.hhhyu.community.dto.GithubUser;
import com.hhhyu.community.mapper.UserMapper;
import com.hhhyu.community.model.User;
import com.hhhyu.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSec;

    @Value("${github.redirect.uri}")
    private String uri;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response){

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(uri);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSec);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        System.out.println(githubUser);
        if(githubUser != null && githubUser.getId() != null){
            //登录成功
            Long accountId = githubUser.getId();
//            System.out.println(accountId);
            User user;
            String token = UUID.randomUUID().toString();
            //登录的时候，查看这个用户是否已经保存过了，保存过了就更新信息，不再添加一个新用户，因为accountId（user的key）是一样的
            if((user = userMapper.findMyAccountId(String.valueOf(accountId))) != null){
                System.out.println(user);
                user.setAvatarUrl(githubUser.getAvatar_url());
                user.setGmtModified(new Date(System.currentTimeMillis()));
                user.setToken(token);
                user.setName(githubUser.getName());
                int success = userMapper.update(user);
                System.out.println(success);
            }
            else {
                user = new User();

                user.setAvatarUrl(githubUser.getAvatar_url());
                user.setAccountId(String.valueOf(accountId));
                user.setName(githubUser.getName());
                user.setToken(token);
                user.setGmtCreate(new Date(System.currentTimeMillis()));
                user.setGmtModified(user.getGmtCreate());
                userMapper.insert(user);
            }
            response.addCookie(new Cookie("token", token));
            return "redirect:/";
        }else {
            //登录失败
            return "redirect:/";
        }
    }

}
