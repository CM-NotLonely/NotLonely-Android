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
@ContentView(id = R.layout.fragment_mine_zhuce_one)
public class MineFragment_zhuce_one extends BaseFragmentActivity {
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
    @OnClick(value = {R.id.mine_zhuce_shenfen})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_zhuce_shenfen:
                intent = new Intent(this,MineFragment_zhuce_two.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
