package com.sceon.community.mapper;

import com.sceon.community.dto.QuestionQueryDto;
import com.sceon.community.model.Question;
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

    List<Question> list(Long userId, Integer offset, Integer pageSize);

    Integer countByUserId(Long userId);


    Question findById(Long id);

    Integer update(Question question);

    int addViewCount(Question question);

    void addCommentCount(Question question);
    List<Question> selectByRelated(Question question);

    Integer countBySerach(QuestionQueryDto questionQueryDto);

    List<Question> listBySearch(QuestionQueryDto questionQueryDto);
}
