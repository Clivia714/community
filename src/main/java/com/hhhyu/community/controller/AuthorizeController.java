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
        if(githubUser != null){
            //登录成功
            long accountId = githubUser.getId();
            System.out.println(accountId);
            User user;
            String token;
            if((user = userMapper.findMyAccountId(String.valueOf(accountId))) != null){
                token = user.getToken();
                user.setGmtModified(new Date(System.currentTimeMillis()));
//                System.out.println("My Git name is:"+ githubUser.getName());
                user.setName(githubUser.getName());
//                System.out.println("My database name is:"+ user.getName());
                user.setAccountId(String.valueOf(accountId));
                userMapper.updata(user);
            }
            else {
                user = new User();
                user.setAccountId(String.valueOf(accountId));
                user.setName(githubUser.getName());
                token = UUID.randomUUID().toString();
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
