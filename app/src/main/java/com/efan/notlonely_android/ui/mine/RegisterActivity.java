package com.efan.notlonely_android.ui.mine;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.efan.basecmlib.activity.BaseActivity;
import com.efan.basecmlib.annotate.ContentView;
import com.efan.basecmlib.annotate.OnClick;
import com.efan.basecmlib.annotate.ViewInject;
import com.efan.notlonely_android.R;
import com.efan.notlonely_android.config.APIConfig;
import com.efan.notlonely_android.entity.RegisterEntity;
import com.efan.notlonely_android.view.BlurringView;
import com.efan.request.RequestUtils;
import com.efan.request.callback.Callback;
import com.efan.request.response.Response;
import com.google.gson.Gson;

/**
 * Created by thinkpad on 2016/4/6.
 */
@ContentView(id = R.layout.activity_register)
public class RegisterActivity extends BaseActivity {

    private Intent intent;
    private String username;
    private String password;
    private String password_confirmation;

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

    @Override
    public void initView() {
        blurringView.setBlurredView(background);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    @OnClick(value = {R.id.accomplish_btn, R.id.registered_login_in})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.accomplish_btn:
                username = phonenumberEdit.getText().toString();
                password = setpasswordEdit.getText().toString();
                password_confirmation = setagainEdit.getText().toString();

                if (checkRegister(username, password, password_confirmation)){
                    Register(username, password, password_confirmation);
                }
                break;
            case R.id.registered_login_in:
                intent = new Intent(this,LoginActivity.class);
                startActivity(intent);
                IdentityActivity.IdentityActivity.finish();
                finish();
                break;
        }
    }



    private void Register(String username, String password, String password_confirmation){
        RequestUtils.post()
                .url(APIConfig.Register)
                .addParams("username",username)
                .addParams("password",password)
                .addParams("password_confirmation",password_confirmation)
                .build()
                .execute(new Callback() {
                    @Override
                    public void onError(Exception e) {

                    }

                    @Override
                    public void onResponse(Response response) {
                        String string = response.getBody();
                        RegisterEntity register = new Gson().fromJson(string, RegisterEntity.class);
                        if(register != null) {
                            intent = new Intent(RegisterActivity.this,SuccessActivity.class);
                            startActivity(intent);
                            IdentityActivity.IdentityActivity.finish();
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
    private boolean checkRegister(String username, String password, String password_confirmation){
        if (username.equals("")){
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }else if (password.equals("")) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }else if (!password_confirmation.equals(password)){
            Toast.makeText(this, "您输入的密码不一致，请重新输入", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
