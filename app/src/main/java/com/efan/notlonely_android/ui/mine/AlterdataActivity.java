package com.efan.notlonely_android.ui.mine;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.efan.basecmlib.activity.BaseActivity;
import com.efan.basecmlib.annotate.ContentView;
import com.efan.basecmlib.annotate.OnClick;
import com.efan.basecmlib.annotate.ViewInject;
import com.efan.notlonely_android.MainApplication;
import com.efan.notlonely_android.R;
import com.efan.notlonely_android.config.APIConfig;
import com.efan.notlonely_android.entity.AlterdataEntity;
import com.efan.notlonely_android.entity.UserEntity;
import com.efan.request.RequestUtils;
import com.efan.request.callback.Callback;
import com.efan.request.request.FileParam;
import com.efan.request.response.Response;
import com.google.gson.Gson;

import java.io.File;

import okhttp3.MediaType;

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
    @ViewInject(id = R.id.alter_portrait)
    private ImageView alter_portrait;


    private String sex;
    private String picturePath;
    private UserEntity user;
    private FileParam fileParam;

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
    @OnClick(value = {R.id.alter_yes, R.id.alter_portrait})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.alter_yes:
                Yes();
                break;
            case R.id.alter_portrait:
                Intent intent = new Intent(this,AlteravatarActivity.class);
                startActivityForResult(intent, 1);
                break;
        }
    }

    //初始化数据
    private void Initialize(){
        picturePath = null;
        user = MainApplication.getInstance().getUser();
        if (user.getSex()){
            btnMan.setChecked(true);
        }
        else {
            btnWoman.setChecked(true);
        }
        alter_name.setText(user.getNickname());
        alter_introduction.setText(user.getIntroduction());
        //alter_portrait.setImageURI(Uri.parse(picturePath));
    }

    public void Yes(){
        if (btnMan.isChecked())
            user.setSex(true);
        else user.setSex(false);
        user.setNickname(alter_name.getText().toString());
        user.setIntroduction(alter_introduction.getText().toString());
        alterdata(user);
    }

    private void alterdata(UserEntity user){
        if (user.getSex()) {
            sex = "true";
        }
        else sex = "false";
        if (picturePath != null) {
            File file = new File(picturePath);
            MediaType mediaType = MediaType.parse("image/png");
            fileParam = new FileParam("sss.png", file, mediaType);
            Log.d("haha","patch");
            RequestUtils.patch()
                    .url(APIConfig.Update_head_image)
                    .addParams("avatar", fileParam)
                    .build()
                    .execute(new Callback() {
                        @Override
                        public void onError(Exception e) {
                            Log.d("haha","onError");
                        }

                        @Override
                        public void onResponse(Response response) {
                            String string = response.getBody();
                            Log.d("haha",string);
                        }
                    });
        }
        RequestUtils.patch()
                .url(APIConfig.Alterdata)
                .addParams("nickname",user.getNickname())
                .addParams("introduction",user.getIntroduction())
                .addParams("sex",sex)
                .build()
                .execute(new Callback() {
                    @Override
                    public void onError(Exception e) {

                    }

                    @Override
                    public void onResponse(Response response) {
                        String string = response.getBody();
                        AlterdataEntity alter = new Gson().fromJson(string, AlterdataEntity.class);
                        finish();
                    }
                });
       }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        picturePath = user.getAvatar().getUrl();
        //alter_portrait.setImageURI(Uri.parse(picturePath));
        Log.d("haha",picturePath);
    }
}
