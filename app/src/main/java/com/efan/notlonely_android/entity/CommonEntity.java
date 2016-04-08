package com.efan.notlonely_android.entity;

/**
 * Created by 一帆 on 2016/3/30.
 */
public class CommonEntity {
    private int status;
    private String msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "CommonEntity{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                '}';
    }
}
