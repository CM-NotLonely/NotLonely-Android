package com.efan.notlonely_android.ui.mine;

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
import com.efan.notlonely_android.entity.AlterpasswordEntity;
import com.efan.notlonely_android.entity.UserEntity;
import com.efan.request.RequestUtils;
import com.efan.request.callback.Callback;
import com.efan.request.response.Response;
import com.google.gson.Gson;

/**
 * Created by thinkpad on 2016/4/17.
 */
@ContentView(id = R.layout.activity_alterpassword)
public class AlterpasswordActivity extends BaseActivity{

    @ViewInject(id = R.id.newpassword)
    private EditText newpassword;
    @ViewInject(id = R.id.newpassword_again)
    private EditText newpassword_again;

    private String setpassword;
    private String setagain;

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    @OnClick(value = {R.id.password_yes})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.password_yes:
                setpassword = newpassword.getText().toString();
                setagain = newpassword_again.getText().toString();
                if (check()){
                    Yes();
                }
                break;
        }
    }

    private void Yes(){
        UserEntity user = MainApplication.getInstance().getUser();
        user.setPassword(setpassword);
        alterpassword();
    }

    /**
     * 输入合法性检查
     */
    private boolean check(){
        if (setpassword.equals("")){
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }else if (!setpassword.equals(setagain)){
            Toast.makeText(this, "您输入的密码不一致，请重新输入", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void alterpassword(){
        RequestUtils.patch()
                .url(APIConfig.Alterpassword)
                .addParams("password",setpassword)
                .addParams("password_confirmation",setagain)
                .build()
                .execute(new Callback() {
                    @Override
                    public void onError(Exception e) {

                    }

                    @Override
                    public void onResponse(Response response) {
                        String string = response.getBody();
                        AlterpasswordEntity alter = new Gson().fromJson(string, AlterpasswordEntity.class);

                        finish();
                    }
                });
    }

}
