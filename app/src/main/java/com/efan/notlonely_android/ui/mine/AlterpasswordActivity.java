package com.efan.notlonely_android.ui.mine;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.efan.basecmlib.activity.BaseActivity;
import com.efan.basecmlib.annotate.ContentView;
import com.efan.basecmlib.annotate.OnClick;
import com.efan.basecmlib.annotate.ViewInject;
import com.efan.basecmlib.okhttputils.OkHttpUtils;
import com.efan.basecmlib.okhttputils.callback.Callback;
import com.efan.notlonely_android.MainApplication;
import com.efan.notlonely_android.R;
import com.efan.notlonely_android.config.APIConfig;
import com.efan.notlonely_android.entity.AlterpasswordEntity;
import com.efan.notlonely_android.entity.UserEntity;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

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
    @OnClick(value = {R.id.password_yes, R.id.password_no})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.password_yes:
                setpassword = newpassword.getText().toString();
                setagain = newpassword_again.getText().toString();
                if (check()){
                    Yes();
                }
                finish();
                break;
            case R.id.password_no:
                finish();
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
        Map<String,String> params = new HashMap<>();
        params.put("password",setpassword);
        params.put("password_confirmation",setagain);

        OkHttpUtils.patch()
                .url(APIConfig.Alterpassword)
                .params(params)
                .build()
                .execute(new Callback<AlterpasswordEntity>() {
                    @Override
                    public AlterpasswordEntity parseNetworkResponse(Response response) throws Exception {
                        String string = response.body().string();
                        AlterpasswordEntity alter = new Gson().fromJson(string, AlterpasswordEntity.class);
                        return alter;
                    }

                    @Override
                    public void onError(Call call, Exception e) {

                    }

                    @Override
                    public void onResponse(AlterpasswordEntity alter) {

                    }
                });
    }

}
