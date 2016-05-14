package com.efan.notlonely_android.ui.mine;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.efan.basecmlib.activity.BaseActivity;
import com.efan.basecmlib.annotate.ContentView;
import com.efan.basecmlib.annotate.OnClick;
import com.efan.basecmlib.annotate.ViewInject;
import com.efan.notlonely_android.MainApplication;
import com.efan.notlonely_android.R;
import com.efan.notlonely_android.config.APIConfig;
import com.efan.notlonely_android.config.SPConfig;
import com.efan.notlonely_android.entity.AlterpasswordEntity;
import com.efan.notlonely_android.entity.UserEntity;
import com.efan.notlonely_android.utils.NetStateUtils;
import com.efan.notlonely_android.utils.PreferencesUtils;
import com.efan.notlonely_android.utils.ToastUtils;
import com.efan.request.RequestUtils;
import com.efan.request.callback.Callback;
import com.efan.request.response.Response;
import com.google.gson.Gson;

/**
 * Created by thinkpad on 2016/4/17.
 */
@ContentView(id = R.layout.activity_alterpassword)
public class AlterpasswordActivity extends BaseActivity implements View.OnKeyListener {

    @ViewInject(id = R.id.et_old_password)
    private EditText et_old_password;
    @ViewInject(id = R.id.et_new_password)
    private EditText et_new_password;
    @ViewInject(id = R.id.et_new_passwordagain)
    private EditText et_new_passwordagain;

    private static final String TAG = "AlterpasswordActivity";
    private String password_old;
    private String password_new;
    private String password_confirm;

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        et_new_passwordagain.setOnKeyListener(this);
    }

    @Override
    @OnClick(value = {R.id.password_yes, R.id.iv_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.password_yes:
                password_old = et_old_password.getText().toString();
                password_new = et_new_password.getText().toString();
                password_confirm = et_new_passwordagain.getText().toString();
                if (check()) {
                    alterpassword();
                }
                break;
        }
    }

    /**
     * 输入合法性检查
     */
    private boolean check() {
        if (!password_old.equals(PreferencesUtils.getString(AlterpasswordActivity.this, SPConfig.USER_PASSWORD))) {
            Toast.makeText(getApplicationContext(), "旧密码输入错误", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password_new.equals("")) {
            Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!password_new.equals(password_confirm)) {
            Toast.makeText(getApplicationContext(), "您输入的密码不一致，请重新输入", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void alterpassword() {
        if (!NetStateUtils.hasNetWorkConnection(AlterpasswordActivity.this)) {
            ToastUtils.show(AlterpasswordActivity.this, "请检查网络连接设置");
            return;
        }
        RequestUtils.patch()
                .url(APIConfig.Alterpassword)
                .addParams("password", password_new)
                .addParams("password_confirmation", password_confirm)
                .build()
                .execute(new Callback() {
                    @Override
                    public void onError(Exception e) {
                        Log.e(TAG, e.toString());
                    }

                    @Override
                    public void onResponse(Response response) {
                        String string = response.getBody();
                        Log.e(TAG, string);
                        AlterpasswordEntity alter = new Gson().fromJson(string, AlterpasswordEntity.class);
                        if (alter.getCode() == 0) {
                            PreferencesUtils.putString(AlterpasswordActivity.this, SPConfig.USER_PASSWORD, password_confirm);
                            ToastUtils.show(getApplicationContext(), "密码修改成功！");
                            finish();
                        } else {
                            ToastUtils.show(getApplicationContext(), alter.getMsg());
                        }
                    }
                });
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            switch (v.getId()) {
                case R.id.et_new_passwordagain:
                    password_old = et_old_password.getText().toString();
                    password_new = et_new_password.getText().toString();
                    password_confirm = et_new_passwordagain.getText().toString();
                    if (check()) {
                        alterpassword();
                    }
                    break;
            }
            return true;
        }
        return false;
    }
}
