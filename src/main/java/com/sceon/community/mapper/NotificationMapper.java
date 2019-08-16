package com.sceon.community.mapper;

import com.sceon.community.model.Notification;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author shichenchong@inspur.com
 * data   2019/8/15 11:09
 */
@Mapper
public interface NotificationMapper {
    void insertNotification(Notification notification);

    Integer countByUnread(Long id,Integer status);

    List<Notification> list(Long id, Integer offset, Integer pageSize);

    Notification findById(Long id);

    void updateRead(Notification notification);

    Integer countByReceiver(Long id);
}

