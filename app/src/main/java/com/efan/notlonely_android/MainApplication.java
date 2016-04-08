package com.efan.notlonely_android;

import android.util.Log;

import com.efan.basecmlib.application.BaseApplication;
import com.efan.notlonely_android.config.SPConfig;
import com.efan.notlonely_android.entity.UserEntity;
import com.efan.notlonely_android.utils.PreferencesUtils;

/**
 * Created by 一帆 on 2016/3/22.
 */
public class MainApplication extends BaseApplication {

    private static MainApplication instance;

    private boolean isLogin = false;
    private UserEntity user;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static MainApplication getInstance() {
        return instance;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
        saveIsLoginSP(login);
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
        saveUserSP(user);
    }

    /**
     * 本地保存登录状态
     * @param isLogin
     */
    private void saveIsLoginSP(boolean isLogin) {
        PreferencesUtils.putBoolean(getApplicationContext(), SPConfig.ISLOGIN, isLogin);
    }

    /**
     * 本地保存用户数据
     * @param user
     */
    private void saveUserSP(UserEntity user) {
        PreferencesUtils.putLong(getApplicationContext(), SPConfig.USER_ID, user.getUserid());
        if(user.getUsername()!= null) {
            PreferencesUtils.putString(getApplicationContext(), SPConfig.USER_NAME, user.getUsername());
        }
        if(user.getPassword()!= null) {
            PreferencesUtils.putString(getApplicationContext(), SPConfig.USER_PASSWORD, user.getPassword());
        }
        if(user.getIntroduction()!= null) {
            PreferencesUtils.putString(getApplicationContext(), SPConfig.USER_INSTRODUCTION, user.getIntroduction());
        }
        if(user.getAvatar()!= null) {
            PreferencesUtils.putString(getApplicationContext(), SPConfig.USER_URL, user.getAvatar().getUrl());
        }
        PreferencesUtils.putBoolean(getApplicationContext(), SPConfig.USER_SEX, user.getSex());
        Log.d("haha",PreferencesUtils.getString(getApplicationContext(),SPConfig.USER_URL,null)+"");
    }
}
