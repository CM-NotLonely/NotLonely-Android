package com.efan.notlonely_android.ui.mine;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.efan.basecmlib.activity.BaseActivity;
import com.efan.basecmlib.annotate.ContentView;
import com.efan.basecmlib.annotate.OnClick;
import com.efan.basecmlib.annotate.ViewInject;
import com.efan.basecmlib.okhttputils.OkHttpUtils;
import com.efan.basecmlib.okhttputils.callback.StringCallback;
import com.efan.notlonely_android.MainApplication;
import com.efan.notlonely_android.R;
import com.efan.notlonely_android.config.APIConfig;
import com.efan.notlonely_android.config.SPConfig;
import com.efan.notlonely_android.entity.UserEntity;
import com.efan.notlonely_android.event.RefreshEvent;
import com.efan.notlonely_android.utils.PreferencesUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by thinkpad on 2016/4/7.
 */
@ContentView(id = R.layout.activity_homepage)
public class HomepageActivity extends BaseActivity{

    @ViewInject(id=R.id.homepage_avatar)
    private SimpleDraweeView simpleDraweeView;
    @ViewInject(id=R.id.homepage_name)
    private TextView mName;
    @ViewInject(id=R.id.homepage_sex)
    private TextView mSex;
    @ViewInject(id=R.id.homepage_account)
    private TextView mAccount;
    @ViewInject(id=R.id.tv_introduction)
    private TextView mIntroduction;

    private String sex;
    private String picturePath;
    private UserEntity user;

    private static final String TAG="HomepageActivity";

    @Subscribe
    public void onEventMainThread(RefreshEvent event){
        if (event.type == RefreshEvent.RefreshType.UPDATA) {
            Log.e(TAG,"EVENBUS IS WORKING");
            initialize();
        }
    }

    @Override
    public void initView() {
        String uri= PreferencesUtils.getString(HomepageActivity.this,SPConfig.USER_URL);
        //simpleDraweeView.setImageURI(Uri.parse("file:///"+uri));
        mAccount.setText(PreferencesUtils.getString(HomepageActivity.this,SPConfig.USER_NAME));
        simpleDraweeView.setImageURI(Uri.parse("res:///"+ R.mipmap.touxiang));
        Initialize();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }
    private void Initialize(){
        user = MainApplication.getInstance().getUser();
        picturePath = user.getUrl();
        if (user.getSex()){
            mSex.setText("男");
        }
        else {
            mSex.setText("女");
        }
        mName.setText(user.getNickname());
        mIntroduction.setText(user.getIntroduction());
        //alter_portrait.setImageURI(Uri.parse(picturePath));
    }

    @Override
    @OnClick(value = {R.id.iv_left,R.id.tv_alter,R.id.rl_sign_out})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_left:
                finish();
                break;
            case R.id.tv_alter:
                Intent intent =new Intent(HomepageActivity.this,AlterdataActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_sign_out:
                //可以加一个PopWindow，选择退出登录/
                OkHttpUtils.delete()
                        .url(APIConfig.Sign_out)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e) {

                            }
                            @Override
                            public void onResponse(String response) {
                                Log.e(TAG,response);
                                MainApplication.getInstance().setLogin(false);
                                EventBus.getDefault().post(new RefreshEvent(RefreshEvent.RefreshType.SIGNOUT));
                                finish();
                            }
                        });
                break;
        }
    }
}
