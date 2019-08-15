package com.sceon.community.service;

import com.sceon.community.exception.CustomizeErrorCode;
import com.sceon.community.exception.CustomizeException;
import com.sceon.community.mapper.QuestionMapper;
import com.sceon.community.mapper.UserMapper;
import com.sceon.community.model.Question;
import com.sceon.community.model.User;
import com.sceon.community.dto.PageDto;
import com.sceon.community.dto.QuestionDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shichenchong@inspur.com
 * data   2019/8/7 15:42
 */
/*
 * 组装User和Question
 */
@Service
public class QuestionService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;

    public PageDto listQuestion(Integer pageNum, Integer pageSize) {
        if(pageNum < 1){
            pageNum = 1;
        }
        Integer offset = pageSize * (pageNum - 1);
        Integer count = questionMapper.count();
        //针对于页数超过总页数进行处理，
        if(offset > count){
            offset = count-1;
        }
        List<Question> questionList = questionMapper.listQuestion(offset,pageSize);
        List<QuestionDto> questionDtoList = new ArrayList<>();
        PageDto pageDto = new PageDto();
        for (Question question: questionList) {
            User user = userMapper.findById(question.getCreator());
            QuestionDto questionDto = new QuestionDto();
            //将前一个对象的属性复制到后一个对象中
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        pageDto.setList(questionDtoList);
        //Integer count = questionMapper.count();
        pageDto.setPageSum(count,pageSize,pageNum);
        //pageDto.setCurrentPage();
        return pageDto;
    }


    public PageDto list(int userId, Integer pageNum, Integer pageSize) {
        if(pageNum < 1){
            pageNum = 1;
        }
        Integer offset = pageSize * (pageNum - 1);
        Integer count = questionMapper.countByUserId(userId);
        //针对于页数超过总页数进行处理，
        if(offset > count){
            offset = count-1;
        }
        List<Question> questionList = questionMapper.list(userId,offset,pageSize);
        List<QuestionDto> questionDtoList = new ArrayList<>();
        PageDto pageDto = new PageDto();
        for (Question question: questionList) {
            User user = userMapper.findById(userId);
            QuestionDto questionDto = new QuestionDto();
            //将前一个对象的属性复制到后一个对象中
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        pageDto.setList(questionDtoList);
        //Integer count = questionMapper.count();
        pageDto.setPageSum(count,pageSize,pageNum);
        //pageDto.setCurrentPage();
        return pageDto;

    }

    public QuestionDto getById(Long id) {
        Question question = questionMapper.findById(id);
        if(question == null){
          throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        User user = userMapper.findById(question.getCreator());
        QuestionDto questionDto = new QuestionDto();
        BeanUtils.copyProperties(question,questionDto);
        questionDto.setUser(user);
        return questionDto;

    }



    public void createOrUpdate(Question question) {
        if(question.getId() == null){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.create(question);
        }else{
            question.setGmtModified(System.currentTimeMillis());
            int update = questionMapper.update(question);
            if(update != 1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }

        }
    }

    public void addView(Long id) {

        Question question = questionMapper.findById(id);
        question.setViewCount(1);
        int update = questionMapper.addViewCount(question);
        if(update != 1){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
    }
    public List<QuestionDto> selectByRelated(QuestionDto queryDto){
        if(StringUtils.isBlank(queryDto.getTag())){
            return new ArrayList<>();
        }
        String tags = StringUtils.replace(queryDto.getTag(), ",", "|");
        Question question = new Question();
        question.setId(queryDto.getId());
        question.setTag(tags);
        List<Question> questionList = questionMapper.selectByRelated(question);
        List<QuestionDto> questionDtoList = questionList.stream().map(q -> {
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(q,questionDto);
            return questionDto;
        }).collect(Collectors.toList());
        return questionDtoList;
    }
}
