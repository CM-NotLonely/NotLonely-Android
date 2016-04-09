package com.efan.notlonely_android.entity;

/**
 * Created by 一帆 on 2016/4/8.
 */
public class LoginEntity {
    private long code;
    private String msg;
    private UserEntity user;

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "RegisterEntity{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", user=" + user +
                '}';
    }
}
