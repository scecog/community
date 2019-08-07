package com.sceon.community.mapper;

import com.sceon.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author shichenchong@inspur.com
 * data   2019/8/6 11:03
 */
@Mapper
public interface UserMapper {
    void insert(User user);
    User findByToken(String token);

    User findById(Integer id);
}
