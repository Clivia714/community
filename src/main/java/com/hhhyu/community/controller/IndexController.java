package com.hhhyu.community.controller;

import com.hhhyu.community.dto.PaginationDTO;
import com.hhhyu.community.dto.QuestionDTO;
import com.hhhyu.community.mapper.QuestionMapper;
import com.hhhyu.community.mapper.UserMapper;
import com.hhhyu.community.model.Question;
import com.hhhyu.community.model.User;
import com.hhhyu.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.jws.WebParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                        @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize){
        Cookie[] cookies = request.getCookies();
        //直接用cookie登录，没有关闭页面则不用再授权一次
        if(cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userMapper.findMyToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
        PaginationDTO pagination = questionService.list(pageNo, pageSize);
        model.addAttribute("pagination", pagination);
        return "index";
    }

}
