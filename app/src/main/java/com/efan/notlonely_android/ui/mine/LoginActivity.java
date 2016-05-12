package com.efan.notlonely_android.ui.mine;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.efan.basecmlib.activity.BaseActivity;
import com.efan.basecmlib.annotate.ContentView;
import com.efan.basecmlib.annotate.OnClick;
import com.efan.basecmlib.annotate.ViewInject;
import com.efan.notlonely_android.MainApplication;
import com.efan.notlonely_android.R;
import com.efan.notlonely_android.config.APIConfig;
import com.efan.notlonely_android.config.SPConfig;
import com.efan.notlonely_android.entity.LoginEntity;
import com.efan.notlonely_android.entity.UserEntity;
import com.efan.notlonely_android.event.RefreshEvent;
import com.efan.notlonely_android.utils.PreferencesUtils;
import com.efan.notlonely_android.view.BlurringView;
import com.efan.request.RequestUtils;
import com.efan.request.callback.Callback;
import com.efan.request.response.Response;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by 一帆 on 2016/4/5.
 */
@ContentView(id = R.layout.activity_login)
public class LoginActivity extends BaseActivity {

    private Intent intent;

    @ViewInject(id = R.id.blurring_view)
    private BlurringView blurringView;
    @ViewInject(id = R.id.background)
    private ImageView background;
    @ViewInject(id = R.id.user_icon)
    private SimpleDraweeView userIcon;
    @ViewInject(id = R.id.username)
    private EditText usernameEdit;
    @ViewInject(id = R.id.password)
    private EditText passwordEdit;
    @ViewInject(id = R.id.login)
    private Button login;

    private String username;
    private String password;
    private static final String TAG="LoginActivity";

    @Override
    public void initView() {
        blurringView.setBlurredView(background);
        userIcon.setImageURI(Uri.parse("res:///"+ R.mipmap.touxiang));
    }

    @Override
    public void initData() {
        UserEntity user = MainApplication.getInstance().getUser();
        username = PreferencesUtils.getString(this, SPConfig.USER_NAME, null);
        password = PreferencesUtils.getString(this, SPConfig.USER_PASSWORD, null);
        if(username != null) {
            usernameEdit.setText(username);
        }
        if(password != null) {
            passwordEdit.setText(password);
        }
    }

    @Override
    public void initEvent() {

    }

    @OnClick(value = {R.id.login, R.id.login_register})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                username = usernameEdit.getText().toString();
                password = passwordEdit.getText().toString();
                if (checkLogin(username, password)){
                    login(username, password);
                }
                break;
            case R.id.login_register:
                intent = new Intent(this,IdentityActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    /**
     * 网络请求-登录
     * @param username
     * @param password
     */
    private void login(String username, String password){
        RequestUtils.post()
                .url(APIConfig.LOGIN)
                .addParams("username",username)
                .addParams("password",password)
                .build()
                .execute(new Callback() {
                    @Override
                    public void onError(Exception e) {
                        Log.d(TAG,e.toString());
                    }

                    @Override
                    public void onResponse(Response response) {
                        String string = response.getBody();
                        Log.d(TAG,string);
                        LoginEntity login = new Gson().fromJson(string, LoginEntity.class);
                        UserEntity user = login.getUser();
                        if(user != null) {
                            MainApplication.getInstance().setLogin(true);
                            MainApplication.getInstance().setUser(user);
                            EventBus.getDefault().post(new RefreshEvent(RefreshEvent.RefreshType.LOGIN));
                            finish();
                        }
                    }
                });
    }

    /**
     * 输入合法性检查
     * @param username
     * @param password
     * @return
     */
    private boolean checkLogin(String username, String password){
        if (username.equals("")){
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }else if (password.equals("")){
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
