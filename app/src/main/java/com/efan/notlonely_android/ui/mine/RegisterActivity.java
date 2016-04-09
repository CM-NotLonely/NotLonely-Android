package com.efan.notlonely_android.ui.mine;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.efan.basecmlib.activity.BaseActivity;
import com.efan.basecmlib.annotate.ContentView;
import com.efan.basecmlib.annotate.OnClick;
import com.efan.basecmlib.annotate.ViewInject;
import com.efan.notlonely_android.R;
import com.efan.notlonely_android.view.BlurringView;

/**
 * Created by thinkpad on 2016/4/6.
 */
@ContentView(id = R.layout.activity_register)
public class RegisterActivity extends BaseActivity {

    private Intent intent;

    @ViewInject(id = R.id.accomplish_blurring_view)
    private BlurringView blurringView;
    @ViewInject(id = R.id.accomplish_background)
    private ImageView background;

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
    @OnClick(value = {R.id.accomplish_btn,R.id.registered_login_in})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.accomplish_btn:
                intent = new Intent(this,SuccessActivity.class);
                startActivity(intent);
                break;
            case R.id.registered_login_in:
                intent = new Intent(this,LoginActivity.class);
                startActivity(intent);
                break;
        }
    }

}
