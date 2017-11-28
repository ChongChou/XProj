package com.talentwalker.xproj.network.entity;

/**
 * Created by Charles on 2017/10/11.
 * 用户信息实体类
 */

public class UserInfo {
    /** 用户类型*/
    public static final class Type {
        public static final String TIGER_GAME = "tg";
        public static final String FACEBOOK = "fb";
    }

    /** 用户id */
    private String id;
    /** 昵称 */
    private String name;
    /** 头像，图片url */
    private String avatar;
    /** 性别，0未知，1男，2女 */
    private int gender;
    /** 生日, 时间戳：ms */
    private long birthday;
    /** 用户令牌 */
    private String token;
    /** 用户类型 */
    private String type;
    /** 会话id */
    private String sessionId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday='" + birthday + '\'' +
                ", token='" + token + '\'' +
                ", type='" + type + '\'' +
                ", sessionId='" + sessionId + '\'' +
                '}';
    }
}
