package com.sceon.community.enums;

/**
 * @author shichenchong@inspur.com
 * data   2019/8/10 14:55
 */
public enum CommentTypeEnum {
    QUESTION(1),
    COMMENT(2);
    private Integer type;

    public static boolean isExit(Integer type) {
        for (CommentTypeEnum value : CommentTypeEnum.values()) {
            if (value.getType().equals(type)){
                return true;
            }

        }
        return false;
    }

    public Integer getType() {
        return type;
    }


    CommentTypeEnum(Integer type) {
        this.type = type;
    }
}
