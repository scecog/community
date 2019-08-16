package com.sceon.community.service;

import com.sceon.community.dto.NotifactionDto;
import com.sceon.community.dto.PageDto;
import com.sceon.community.enums.NotifactionStatusEnum;
import com.sceon.community.enums.NotifactionTypeEnum;
import com.sceon.community.mapper.CommentMapper;
import com.sceon.community.mapper.NotificationMapper;
import com.sceon.community.mapper.UserMapper;
import com.sceon.community.model.Comment;
import com.sceon.community.model.Notification;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shichenchong@inspur.com
 * data   2019/8/15 14:52
 */
@Service
public class NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommentMapper commentMapper;
    public PageDto list(Long id, Integer pageNum, Integer pageSize) {
        if(pageNum < 1){
            pageNum = 1;
        }
        Integer offset = pageSize * (pageNum - 1);
        Integer count = notificationMapper.countByReceiver(id);
        if(offset > count){
            offset = count-1;
        }
        List<Notification> notificationList = notificationMapper.list(id,offset,pageSize);
        List<NotifactionDto> notificationDtoList = new ArrayList<>();
        PageDto<NotifactionDto> pageDto = new PageDto<>();
        for (Notification notification : notificationList) {
            NotifactionDto notifactionDto = new NotifactionDto();
            BeanUtils.copyProperties(notification,notifactionDto);
            notifactionDto.setType(NotifactionTypeEnum.nameOfType(notification.getType()));
            notificationDtoList.add(notifactionDto);
        }
        pageDto.setList(notificationDtoList);
        pageDto.setPageSum(count,pageSize,pageNum);
        return pageDto;
    }

    public Integer unReadCount(Long id,Integer notificationStatus) {
        return notificationMapper.countByUnread(id,notificationStatus);
    }

    public Notification findById(Long id) {
        return notificationMapper.findById(id);
    }

    public Comment findCommentByOuterId(Long outerId) {
        return commentMapper.selectById(outerId);
    }

    public void updateRead(Notification notification) {
        notification.setStatus(NotifactionStatusEnum.READ.getStatus());
        notificationMapper.updateRead(notification);
    }
}
