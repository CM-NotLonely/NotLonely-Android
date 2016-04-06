package com.efan.notlonely_android.ui.mine;

import android.content.Intent;
import android.view.View;

import com.efan.basecmlib.activity.BaseFragmentActivity;
import com.efan.basecmlib.annotate.ContentView;
import com.efan.basecmlib.annotate.OnClick;
import com.efan.notlonely.R;

/**
 * Created by thinkpad on 2016/3/27.
 */
@ContentView(id = R.layout.fragment_mine_zhuce_two)
public class MineFragment_zhuce_two extends BaseFragmentActivity {
    private Intent intent;

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
    @OnClick(value = {R.id.mine_zhuce_zhuce})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_zhuce_zhuce:
                intent = new Intent(this,MineFragment_zhuce_three.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
