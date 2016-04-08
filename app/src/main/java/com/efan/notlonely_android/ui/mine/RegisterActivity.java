package com.efan.notlonely_android.ui.mine;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.efan.basecmlib.activity.BaseActivity;
import com.efan.basecmlib.annotate.ContentView;
import com.efan.basecmlib.annotate.OnClick;
import com.efan.basecmlib.annotate.ViewInject;
import com.efan.notlonely_android.R;
import com.efan.notlonely_android.view.BlurringView;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by 一帆 on 2016/4/5.
 */
@ContentView(id = R.layout.activity_register)
public class RegisterActivity extends BaseActivity {

    private Intent intent;

    @ViewInject(id = R.id.blurring_view)
    private BlurringView blurringView;
    @ViewInject(id = R.id.background)
    private ImageView background;
    @ViewInject(id = R.id.user_icon)
    private SimpleDraweeView userIcon;

    @Override
    public void initView() {
        blurringView.setBlurredView(background);
        userIcon.setImageURI(Uri.parse("res:///"+R.mipmap.touxiang));
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    @OnClick(value = {R.id.login_register})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_register:
                intent = new Intent(this,IdentityActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
