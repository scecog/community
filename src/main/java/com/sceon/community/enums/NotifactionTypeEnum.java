package com.sceon.community.enums;

/**
 * @author shichenchong@inspur.com
 * data   2019/8/15 13:48
 */
public enum NotifactionTypeEnum {
    REPLAY_QUESTION(1,"回复了问题"),
    REPLAY_COMMENT(2,"回复了评论");
    private int status;
    private String name;

    NotifactionTypeEnum(int status, String name) {
        this.status = status;
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }
    public static String nameOfType(int type){
        for (NotifactionTypeEnum notifactionTypeEnum : NotifactionTypeEnum.values()) {
            if(notifactionTypeEnum.getStatus() == type){
                return notifactionTypeEnum.getName();
            }
        }
        return "";
    }

}
