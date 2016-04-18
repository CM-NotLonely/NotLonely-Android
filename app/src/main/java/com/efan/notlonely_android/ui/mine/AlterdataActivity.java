package com.efan.notlonely_android.ui.mine;

import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.efan.basecmlib.activity.BaseActivity;
import com.efan.basecmlib.annotate.ContentView;
import com.efan.basecmlib.annotate.OnClick;
import com.efan.basecmlib.annotate.ViewInject;
import com.efan.basecmlib.okhttputils.OkHttpUtils;
import com.efan.basecmlib.okhttputils.callback.Callback;
import com.efan.notlonely_android.MainApplication;
import com.efan.notlonely_android.R;
import com.efan.notlonely_android.config.APIConfig;
import com.efan.notlonely_android.entity.AlterdataEntity;
import com.efan.notlonely_android.entity.UserEntity;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by thinkpad on 2016/4/11.
 */
@ContentView(id = R.layout.activity_alterdata)
public class AlterdataActivity extends BaseActivity {

    @ViewInject(id = R.id.btnMan)
    private RadioButton btnMan;
    @ViewInject(id = R.id.btnWoman)
    private RadioButton btnWoman;
    @ViewInject(id = R.id.alter_name)
    private EditText alter_name;
    @ViewInject(id = R.id.alter_introduction)
    private EditText alter_introduction;

    private String sex;

    @Override
    public void initView() {
        Initialize();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    @OnClick(value = {R.id.alter_yes, R.id.alter_no})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.alter_yes:
                Yes();
                finish();
                break;
            case R.id.alter_no:
                finish();
                break;
        }
    }

    //初始化数据
    private void Initialize(){
        UserEntity user = MainApplication.getInstance().getUser();
        if (user.getSex()){
            btnMan.setChecked(true);
        }
        else {
            btnWoman.setChecked(true);
        }
        alter_name.setText(user.getNickname());
        alter_introduction.setText(user.getIntroduction());
    }

    public void Yes(){
        UserEntity user = MainApplication.getInstance().getUser();
        if (btnMan.isChecked())
            user.setSex(true);
        else user.setSex(false);
        user.setNickname(alter_name.getText().toString());
        user.setIntroduction(alter_introduction.getText().toString());
        alterdata(user);
    }

    private void alterdata(UserEntity user){
        Map<String,String> params = new HashMap<>();
        params.put("nickname",user.getNickname());
        params.put("introduction",user.getIntroduction());
        if (user.getSex()) {
            sex = "true";
        }
        else sex = "false";
        params.put("sex",sex);

        OkHttpUtils.patch()
                .url(APIConfig.Alterdata)
                .params(params)
                .build()
                .execute(new Callback<AlterdataEntity>() {
                    @Override
                    public AlterdataEntity parseNetworkResponse(Response response) throws Exception {
                        String string = response.body().string();
                        AlterdataEntity alter = new Gson().fromJson(string, AlterdataEntity.class);
                        return alter;
                    }

                    @Override
                    public void onError(Call call, Exception e) {

                    }

                    @Override
                    public void onResponse(AlterdataEntity alter) {

                    }
                });
    }

}
