package com.efan.notlonely_android.ui;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.efan.basecmlib.activity.BaseActivity;
import com.efan.basecmlib.okhttputils.OkHttpUtils;
import com.efan.basecmlib.okhttputils.callback.Callback;
import com.efan.notlonely_android.MainApplication;
import com.efan.notlonely_android.config.APIConfig;
import com.efan.notlonely_android.config.SPConfig;
import com.efan.notlonely_android.entity.LoginEntity;
import com.efan.notlonely_android.entity.UserEntity;
import com.efan.notlonely_android.event.RefreshEvent;
import com.efan.notlonely_android.utils.NetStateUtils;
import com.efan.notlonely_android.utils.PreferencesUtils;
import com.efan.notlonely_android.utils.ToastUtils;
import com.efan.request.RequestUtils;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by 一帆 on 2016/4/8.
 */
public class SplashActivity extends BaseActivity {

    private Handler mHandler;
    private boolean isLogin;
    private String username;
    private String password;

    @Override
    public void initView() {
        isLogin = PreferencesUtils.getBoolean(this, SPConfig.ISLOGIN, false);
        username = PreferencesUtils.getString(this, SPConfig.USER_NAME, null);
        password = PreferencesUtils.getString(this, SPConfig.USER_PASSWORD, null);

        if ((isLogin == true) && (username != null) && (password != null)) {
            login(username, password);
        }
        launchOprator();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 启动界面
     */
    public void launchOprator() {
        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1500);
    }

    /**
     * 网络请求-登录
     *
     * @param username
     * @param password
     */
    private void login(String username, String password) {
        if(!NetStateUtils.hasNetWorkConnection(SplashActivity.this)){
            ToastUtils.show(getApplicationContext(),"请检查网络连接设置", Toast.LENGTH_SHORT);
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        OkHttpUtils.post()
                .url(APIConfig.LOGIN)
                .params(params)
                .build()
                .execute(new Callback<UserEntity>() {
                    @Override
                    public UserEntity parseNetworkResponse(Response response) throws Exception {
                        String string = response.body().string();
                        LoginEntity register = new Gson().fromJson(string, LoginEntity.class);
                        return register.getUser();
                    }

                    @Override
                    public void onError(Call call, Exception e) {

                    }

                    @Override
                    public void onResponse(UserEntity user) {

                    }
                });
    }
}
