package com.efan.notlonely_android.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.efan.basecmlib.activity.BaseFragment;
import com.efan.basecmlib.annotate.OnClick;
import com.efan.notlonely.R;

/**
 * Created by thinkpad on 2016/3/27.
 */
public class MineFragment_weidenglu extends BaseFragment {
    private Intent intent;

    @Override
    protected View inflaterView(LayoutInflater var1, ViewGroup var2, Bundle var3) {
        return var1.inflate(R.layout.fragment_mine_weidenglu,var2,false);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    @OnClick(value = {R.id.mine_denglu,R.id.mine_zhuce})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_denglu:
                intent = new Intent(getContext(),MineFragment_denglu_one.class);
                startActivity(intent);
                break;
            case R.id.mine_zhuce:
                intent = new Intent(getContext(),MineFragment_zhuce_one.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
