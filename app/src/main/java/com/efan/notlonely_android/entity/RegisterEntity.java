package com.efan.notlonely_android.entity;

/**
 * Created by thinkpad on 2016/4/14.
 */
public class RegisterEntity {

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private long code;
    private String msg;
}
