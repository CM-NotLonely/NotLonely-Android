package com.efan.notlonely_android.entity;

/**
 * Created by 一帆 on 2016/4/7.
 */
public class AvatarEntity {

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "AvatarEntity{" +
                "url='" + url + '\'' +
                '}';
    }
}
