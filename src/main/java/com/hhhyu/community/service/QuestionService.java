package com.hhhyu.community.service;


import com.hhhyu.community.dto.PaginationDTO;
import com.hhhyu.community.dto.QuestionDTO;
import com.hhhyu.community.mapper.QuestionMapper;
import com.hhhyu.community.mapper.UserMapper;
import com.hhhyu.community.model.Question;
import com.hhhyu.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public PaginationDTO list(Integer pageNo, Integer pageSize) {

        Integer totalCount = questionMapper.count();
        Integer totalPage;

        if(totalCount % pageSize == 0){
            totalPage = totalCount / pageSize;
        }else {
            totalPage = totalCount / pageSize + 1;
        }

        if(pageNo < 1){
            pageNo = 1;
        }
        if(pageNo > totalPage){
            pageNo = totalPage;
        }

        Integer offset = pageSize * (pageNo - 1);
        List<Question> list = questionMapper.list(offset, pageSize);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        if(list != null) {
            for (Question question : list) {
                User user = userMapper.findMyId(question.getCreator());
                QuestionDTO questionDTO = new QuestionDTO();
                BeanUtils.copyProperties(question, questionDTO);
                questionDTO.setUser(user);
                questionDTOList.add(questionDTO);
            }
        }

        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setQuestions(questionDTOList);
        paginationDTO.setPagination(pageNo, totalPage);
        return paginationDTO;
    }

    public PaginationDTO list(Integer userId, Integer pageNo, Integer pageSize) {

        Integer totalCount = questionMapper.countByUserId(userId);
        Integer totalPage;

        if(totalCount % pageSize == 0){
            totalPage = totalCount / pageSize;
        }else {
            totalPage = totalCount / pageSize + 1;
        }

        if(pageNo < 1){
            pageNo = 1;
        }
        if(pageNo > totalPage){
            pageNo = totalPage;
        }

        Integer offset = pageSize * (pageNo - 1);
        List<Question> list = questionMapper.listByUserId(userId, offset, pageSize);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        if(list != null) {
            for (Question question : list) {
                User user = userMapper.findMyId(question.getCreator());
                QuestionDTO questionDTO = new QuestionDTO();
                BeanUtils.copyProperties(question, questionDTO);
                questionDTO.setUser(user);
                questionDTOList.add(questionDTO);
            }
        }

        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setQuestions(questionDTOList);
        paginationDTO.setPagination(pageNo, totalPage);
        return paginationDTO;
    }
}
