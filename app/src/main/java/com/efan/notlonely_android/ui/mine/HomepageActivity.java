package com.efan.notlonely_android.ui.mine;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.efan.basecmlib.activity.BaseActivity;
import com.efan.basecmlib.annotate.ContentView;
import com.efan.basecmlib.annotate.OnClick;
import com.efan.basecmlib.annotate.ViewInject;
import com.efan.notlonely_android.MainApplication;
import com.efan.notlonely_android.R;
import com.efan.notlonely_android.config.SPConfig;
import com.efan.notlonely_android.entity.UserEntity;
import com.efan.notlonely_android.utils.PreferencesUtils;
import com.facebook.drawee.view.SimpleDraweeView;

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
    @Override
    public void initView() {
        String uri= PreferencesUtils.getString(HomepageActivity.this,SPConfig.USER_URL);
        //simpleDraweeView.setImageURI(Uri.parse("file:///"+uri));
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
        picturePath = null;
        user = MainApplication.getInstance().getUser();
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
    @OnClick(value = {R.id.iv_left,R.id.tv_alter})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_left:
                finish();
                break;
            case R.id.tv_alter:
                Intent intent =new Intent(HomepageActivity.this,AlterdataActivity.class);
                startActivity(intent);
                break;
        }
    }
}
