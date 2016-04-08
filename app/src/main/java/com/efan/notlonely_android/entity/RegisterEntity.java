package com.efan.notlonely_android.entity;

/**
 * Created by 一帆 on 2016/3/30.
 */
public class RegisterEntity {

    /**
     * id : 3
     * username : 1234567
     * password : 1234567
     * nickname : test
     * sex : true
     * introduction : test
     * created_at : 2016-03-30T14:41:40.514Z
     * updated_at : 2016-03-30T14:41:40.514Z
     * avatar : {"url":"avatar/.png"}
     */

    private int code;
    private String msg;
    private UserEntity user;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
