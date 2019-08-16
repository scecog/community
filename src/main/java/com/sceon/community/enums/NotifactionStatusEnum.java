package com.sceon.community.enums;

/**
 * @author shichenchong@inspur.com
 * data   2019/8/15 14:03
 */
public enum NotifactionStatusEnum {
    UNREAD(0,"未读"),
    READ(1,"已读");
    private int status;
    private String name;

    NotifactionStatusEnum(int status, String name) {
        this.status = status;
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }
}
