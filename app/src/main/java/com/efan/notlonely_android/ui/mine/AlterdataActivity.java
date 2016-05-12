package com.efan.notlonely_android.ui.mine;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
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
import com.efan.notlonely_android.config.SPConfig;
import com.efan.notlonely_android.entity.AlterdataEntity;
import com.efan.notlonely_android.entity.UserEntity;
import com.efan.notlonely_android.event.RefreshEvent;
import com.efan.notlonely_android.utils.PreferencesUtils;
import com.efan.request.RequestUtils;
import com.efan.request.callback.Callback;
import com.efan.request.request.FileParam;
import com.efan.request.response.Response;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import org.greenrobot.eventbus.Subscribe;

import java.io.File;

import okhttp3.MediaType;

/**
 * Created by thinkpad on 2016/4/11.
 */
@ContentView(id = R.layout.activity_alterdata)
public class AlterdataActivity extends BaseActivity {

    @ViewInject(id = R.id.iv_left)
    private ImageView iv_left;
    @ViewInject(id = R.id.btnMan)
    private RadioButton btnMan;
    @ViewInject(id = R.id.btnWoman)
    private RadioButton btnWoman;
    @ViewInject(id = R.id.alter_name)
    private EditText alter_name;
    @ViewInject(id = R.id.alter_introduction)
    private EditText alter_introduction;
    @ViewInject(id = R.id.iv_avatar)
    private SimpleDraweeView iv_avatar;


    private String sex;
    private String picturePath;
    private UserEntity user;
    private FileParam fileParam;
    private static final String TAG = "AlterdataActivity";
    private static final int SELECT_AVATAR = 1;

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
    @OnClick(value = {R.id.alter_yes, R.id.alter_portrait, R.id.iv_left})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.alter_yes:
                //上传更新数据，if上传成功，返回response后发送EvenBus更新本地数据
                Yes();
                break;
            case R.id.alter_portrait:
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, SELECT_AVATAR);
                //Intent intent = new Intent(this,AlteravatarActivity.class);
                //startActivity(intent);
                break;
            case R.id.iv_left:
                finish();
                break;
        }
    }

    //初始化数据
    private void Initialize() {
        picturePath = null;
        user = MainApplication.getInstance().getUser();
        if (user.getSex()) {
            btnMan.setChecked(true);
        } else {
            btnWoman.setChecked(true);
        }
        alter_name.setText(user.getNickname());
        alter_introduction.setText(user.getIntroduction());
        iv_avatar.setImageURI(Uri.parse("http:///" + picturePath));
    }

    public void Yes() {
        //首先进行数据更新
        if (btnMan.isChecked()) {
            user.setSex(true);
        } else {
            user.setSex(false);
        }
        user.setNickname(alter_name.getText().toString());
        user.setIntroduction(alter_introduction.getText().toString());
        alterdata(user);
    }

    private boolean alterdata(final UserEntity user) {
        boolean update_avatar = false;
        boolean update_data = false;
        if (user.getSex()) {
            sex = "true";
        } else {
            sex = "false";
        }
        if (picturePath != null) {
            File file = new File(picturePath);
            MediaType mediaType = MediaType.parse("image/*");
            fileParam = new FileParam("sss.png", file, mediaType);
            Log.d("haha", "patch");
            RequestUtils.patch()
                    .url(APIConfig.Update_head_image)
                    .addParams("avatar", fileParam)
                    .build()
                    .execute(new Callback() {
                        @Override
                        public void onError(Exception e) {
                            Log.d("haha", "onError");
                        }

                        @Override
                        public void onResponse(Response response) {
                            String string = response.getBody();
                            Log.d("haha", string);
                        }
                    });
        }
        RequestUtils.patch()
                .url(APIConfig.Alterdata)
                .addParams("nickname", user.getNickname())
                .addParams("introduction", user.getIntroduction())
                .addParams("sex", sex)
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
                        AlterdataEntity alter = new Gson().fromJson(string, AlterdataEntity.class);
                        long code=alter.getCode();
                        if(code==0){
                            MainApplication.getInstance().setUser(user);
                        }
                        finish();
                    }
                });
        //return update_avatar&&update_data;
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case SELECT_AVATAR:
                if (resultCode == RESULT_OK && null != data) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    //avatar.setImageURI(Uri.parse(picturePath));
                    Log.d(TAG, picturePath);
                    Intent intent = new Intent();
                    intent.putExtra("picturePath", picturePath);
                    intent.setClass(AlterdataActivity.this, AlteravatarActivity.class);
                    startActivity(intent);
                }
                break;
        }
        picturePath = user.getAvatar();
        //alter_portrait.setImageURI(Uri.parse(picturePath));
        Log.d("haha", picturePath);
    }

    @Subscribe
    public void onEventMainThread(RefreshEvent event) {
        if (event.type == RefreshEvent.RefreshType.ALTERAVATAR) {
            String user_url = PreferencesUtils.getString(AlterdataActivity.this, SPConfig.USER_URL);

        }
    }
}
