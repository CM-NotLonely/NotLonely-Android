package com.efan.notlonely_android.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.efan.basecmlib.activity.BaseFragment;
import com.efan.basecmlib.annotate.OnClick;
import com.efan.notlonely_android.R;
import com.efan.notlonely_android.ui.adapter.FragmentTabAdapter;
import com.efan.notlonely_android.view.BlurringView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 一帆 on 2016/3/22.
 */
public class MineFragment extends BaseFragment {

    private FragmentTabAdapter fragmentTabAdapter;
    private List<BaseFragment> list;
    private FragmentManager fManager;
    private FragmentTransaction fTransaction;
    private MineFragment_weidenglu fg1;
    private Intent intent;
    private BlurringView blurringView;
    @Override
    protected View inflaterView(LayoutInflater var1, ViewGroup var2, Bundle var3) {
        return var1.inflate(R.layout.fragment_mine,var2,false);
    }

    @Override
    public void initView() {
        setFrameLayout();
        fg1 = new MineFragment_weidenglu();
        fManager = getFragmentManager();
        fTransaction = fManager.beginTransaction();
        fTransaction.add(R.id.mine_framelayout,fg1).commit();
        blurringView = (BlurringView) getActivity().findViewById(R.id.blurring_view);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    @OnClick(value = {R.id.mine_persinaldetails,R.id.mine_push,R.id.mine_join,R.id.mine_zan})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_persinaldetails:
                intent = new Intent(getContext(),MineFragment_personaldetails.class);
                startActivity(intent);
                break;
            case R.id.mine_push:
                intent = new Intent(getContext(),MineFragment_push.class);
                startActivity(intent);
                break;
            case R.id.mine_join:
                intent = new Intent(getContext(),MineFragment_join.class);
                startActivity(intent);
                break;
            case R.id.mine_zan:
                intent = new Intent(getContext(),MineFragment_zan.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    /**
     * 设置FrameLyout
     */
    private void setFrameLayout() {
        list = new ArrayList<BaseFragment>();
        list.add(new MineFragment_denglu());
        list.add(new MineFragment_weidenglu());
    }

    @Override
    public void onResume() {
        super.onResume();
        blurringView.invalidate();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && blurringView instanceof BlurringView) blurringView.invalidate();
    }
}