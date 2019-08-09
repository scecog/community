package com.sceon.community.mapper;

import com.sceon.community.model.Question;
import com.sceon.community.pojo.QuestionDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author shichenchong@inspur.com
 * data   2019/8/7 8:51
 */
@Mapper
public interface QuestionMapper {
    public void create(Question question);

    List<Question> listQuestion(Integer offset, Integer pageSize);

    Integer count();

    List<Question> list(int userId, Integer offset, Integer pageSize);

    Integer countByUserId(int userId);


    Question findById(Integer id);
}
