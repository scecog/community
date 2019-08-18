package com.sceon.community.service;

import com.sceon.community.dto.CommentDto;
import com.sceon.community.enums.CommentTypeEnum;
import com.sceon.community.enums.NotifactionStatusEnum;
import com.sceon.community.enums.NotifactionTypeEnum;
import com.sceon.community.exception.CustomizeErrorCode;
import com.sceon.community.exception.CustomizeException;
import com.sceon.community.mapper.CommentMapper;
import com.sceon.community.mapper.NotificationMapper;
import com.sceon.community.mapper.QuestionMapper;
import com.sceon.community.mapper.UserMapper;
import com.sceon.community.model.Comment;
import com.sceon.community.model.Notification;
import com.sceon.community.model.Question;
import com.sceon.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author shichenchong@inspur.com
 * data   2019/8/10 14:59
 */
@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NotificationMapper notificationMapper;

    @Transactional
    public void insertComment(Comment comment,User user) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType() == null || !CommentTypeEnum.isExit(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if (comment.getType() == CommentTypeEnum.QUESTION.getType()) {
            //System.out.println(comment.getParentId());
            //回复问题
            Question question = questionMapper.findById(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insertComment(comment);
            question.setCommentCount(1);
            questionMapper.addCommentCount(question);
            createNotify(comment, question.getCreator(),NotifactionTypeEnum.REPLAY_QUESTION,user,question.getTitle());

        } else {
            //回复评论
            //System.out.println(comment.getParentId());
            Comment dbComment = commentMapper.selectById(comment.getParentId());
            //System.out.println(dbComment);
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }

            commentMapper.insertComment(comment);
            dbComment.setCommentCount(1);
            commentMapper.addCommentCount(dbComment);
            //回复评论进插入到通知数据库中
            createNotify(comment,dbComment.getCommentId(),NotifactionTypeEnum.REPLAY_COMMENT,user,dbComment.getCommentContent());
        }

    }
    /*
     * 回复问题插入通知
     */
    private void createNotify(Comment comment, Long receiver, NotifactionTypeEnum replay,User user,String title) {
        //在回复问题之后需要插入到通知的列表中，因为是回复问题，所以需要获取到parentid
        //进而获取到问题列表的creator来作为接收方
        if(receiver == comment.getCommentId()){
            return;
        }
        Notification notification = new Notification();
        notification.setNotifier(comment.getCommentId());
        //获取到回复的问题的id，查询出问题的所有信息
        //Long receiver = question.getCreator();
        notification.setReceiver(receiver);
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(replay.getStatus());
        notification.setOuterId(comment.getParentId());
        notification.setStatus(NotifactionStatusEnum.UNREAD.getStatus());
        notification.setOuterTitle(title);
        notification.setNotifierName(user.getName());
        notificationMapper.insertNotification(notification);
    }

    /*
     * 获取到回复的列表，根据父类问题的id，查询出回复的全部内容,
     * 然后根据comment 获取到 comment_id 查询出user，进而获取到用户信息，
     * 注入用户信息到commentDto
     */
    public List<CommentDto> listByTargetId(Long id, CommentTypeEnum type) {
        //需要设定type为1
        List<Comment> commentList = commentMapper.listByTargetId(id, type.getType());
        if (commentList.size() == 0) {
            return new ArrayList<>();
        }
        //获取去重的评论人id
        List<Long> userIds = commentList.stream().map(comment -> comment.getCommentId()).collect(Collectors.toList());
        // 获取评论人的信息 转换为map
        //System.out.println(userIds);
        List<User> users = userMapper.listByIds(userIds);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));
        //将评论的map转换为list
        List<CommentDto> commentDtos = commentList.stream().map(comment -> {
            CommentDto commentDto = new CommentDto();
            BeanUtils.copyProperties(comment, commentDto);
            commentDto.setUser(userMap.get(comment.getCommentId()));
            return commentDto;

        }).collect(Collectors.toList());
        return commentDtos;
    }
}
