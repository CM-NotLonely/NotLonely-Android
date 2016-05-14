package com.efan.notlonely_android.ui.mine;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.efan.basecmlib.activity.BaseActivity;
import com.efan.basecmlib.annotate.ContentView;
import com.efan.basecmlib.annotate.OnClick;
import com.efan.basecmlib.annotate.ViewInject;
import com.efan.notlonely_android.R;
import com.efan.notlonely_android.config.APIConfig;
import com.efan.notlonely_android.config.SPConfig;
import com.efan.notlonely_android.entity.RegisterEntity;
import com.efan.notlonely_android.event.RefreshEvent;
import com.efan.notlonely_android.utils.NetStateUtils;
import com.efan.notlonely_android.utils.PreferencesUtils;
import com.efan.notlonely_android.utils.ToastUtils;
import com.efan.notlonely_android.view.BlurringView;
import com.efan.notlonely_android.view.ProgressWheel;
import com.efan.request.RequestUtils;
import com.efan.request.callback.Callback;
import com.efan.request.response.Response;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by thinkpad on 2016/4/6.
 */
@ContentView(id = R.layout.activity_register)
public class RegisterActivity extends BaseActivity implements View.OnKeyListener {
    private String username;
    private String password;
    private String password_confirmation;
    private String msg;
    private Boolean isSuccess;

    @ViewInject(id = R.id.rl_register_success)
    private RelativeLayout rl_register_success;
    @ViewInject(id = R.id.rl_register)
    private RelativeLayout rl_register;
    @ViewInject(id = R.id.progresswheel)
    private ProgressWheel progressWheel;
    @ViewInject(id = R.id.tv_register_msg)
    private TextView tv_register_msg;
    @ViewInject(id = R.id.accomplish_blurring_view)
    private BlurringView blurringView;
    @ViewInject(id = R.id.accomplish_background)
    private ImageView background;
    @ViewInject(id = R.id.phone_number)
    private EditText phonenumberEdit;
    @ViewInject(id = R.id.set_password)
    private EditText setpasswordEdit;
    @ViewInject(id = R.id.set_password_again)
    private EditText setagainEdit;
    @ViewInject(id = R.id.accomplish_btn)
    private Button accomplish;

    @Subscribe
    public void onEventMainThread(RefreshEvent event) {
        if (event.type == RefreshEvent.RefreshType.REGISTER) {
            if (isSuccess) {
                rl_register_success.setVisibility(View.VISIBLE);
                progressWheel.beginDrawTick();
                tv_register_msg.setText("注 册 成 功 ！");
                rl_register.setVisibility(View.GONE);
            } else {
                rl_register_success.setVisibility(View.VISIBLE);
                progressWheel.beginDrawFork();
                tv_register_msg.setText(msg);
                rl_register.setVisibility(View.GONE);
            }
        }
    }


    @Override
    public void initView() {
        blurringView.setBlurredView(background);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        setagainEdit.setOnKeyListener(this);
    }

    @Override
    @OnClick(value = {R.id.accomplish_btn, R.id.registered_login_in, R.id.rl_register_success})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_register_success:
                if (isSuccess) {
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    rl_register_success.setVisibility(View.GONE);
                    rl_register.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.accomplish_btn:
                username = phonenumberEdit.getText().toString();
                password = setpasswordEdit.getText().toString();
                password_confirmation = setagainEdit.getText().toString();

                if (checkRegister(username, password, password_confirmation)) {
                    Register(username, password, password_confirmation);
                }
                break;
            case R.id.registered_login_in: {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
            break;
        }
    }


    private void Register(final String username, final String password, String password_confirmation) {
        if(!NetStateUtils.hasNetWorkConnection(RegisterActivity.this)){
            ToastUtils.show(RegisterActivity.this,"请检查网络连接设置");
            return;
        }
        RequestUtils.post()
                .url(APIConfig.Register)
                .addParams("username", username)
                .addParams("password", password)
                .addParams("password_confirmation", password_confirmation)
                .build()
                .execute(new Callback() {
                    @Override
                    public void onError(Exception e) {

                    }

                    @Override
                    public void onResponse(Response response) {
                        String string = response.getBody();
                        Log.e("linqihao", string);
                        RegisterEntity register = new Gson().fromJson(string, RegisterEntity.class);
                        if (register.getCode() == 0) {
                            isSuccess = true;
                            PreferencesUtils.putString(RegisterActivity.this, SPConfig.USER_NAME, username);
                            PreferencesUtils.putString(RegisterActivity.this, SPConfig.USER_PASSWORD, password);
                            EventBus.getDefault().post(new RefreshEvent(RefreshEvent.RefreshType.REGISTER));
                        } else {
                            isSuccess = false;
                            msg = register.getMsg();
                            EventBus.getDefault().post(new RefreshEvent(RefreshEvent.RefreshType.REGISTER));
                        }
                    }
                });
    }

    /**
     * 输入合法性检查
     *
     * @param username
     * @param password
     * @return
     */
    private boolean checkRegister(String username, String password, String password_confirmation) {
        if (username.equals("")) {
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.equals("")) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!password_confirmation.equals(password)) {
            Toast.makeText(this, "您输入的密码不一致，请重新输入", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_ENTER){
            switch (v.getId()){
                case R.id.set_password_again:
                    username = phonenumberEdit.getText().toString();
                    password = setpasswordEdit.getText().toString();
                    password_confirmation = setagainEdit.getText().toString();
                    if (checkRegister(username, password, password_confirmation)) {
                        Register(username, password, password_confirmation);
                    }
                    break;
            }
            return true;
        }
        return false;
    }
}
