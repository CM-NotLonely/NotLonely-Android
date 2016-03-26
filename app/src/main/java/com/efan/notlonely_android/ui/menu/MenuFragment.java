package com.efan.notlonely_android.ui.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.efan.basecmlib.activity.BaseFragment;
import com.efan.basecmlib.activity.BaseFragmentActivity;
import com.efan.basecmlib.annotate.ViewInject;
import com.efan.notlonely_android.R;
import com.efan.notlonely_android.ui.adapter.FragmentTabAdapter;
import com.efan.notlonely_android.view.BlurringView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linqh0806 on 2016/3/22.
 */
public class MenuFragment extends BaseFragment {
    private TextView tv_activity;
    private TextView tv_circle;
    private List<BaseFragment> fragments;
    private FragmentTabAdapter fragmentTabAdapter;
    private BlurringView blurringView;
    @Override
    protected View inflaterView(LayoutInflater var1, ViewGroup var2, Bundle var3) {
        return var1.inflate(R.layout.fragment_menu,var2,false);
    }

    @Override
    public void initView() {
        blurringView= (BlurringView) getActivity().findViewById(R.id.blurring_view);
        tv_activity= (TextView) getActivity().findViewById(R.id.tv_activity);
        tv_circle= (TextView) getActivity().findViewById(R.id.tv_circle);
        tv_activity.setOnClickListener(this);
        tv_circle.setOnClickListener(this);
    }

    @Override
    public void initData() {
        fragments=new ArrayList<BaseFragment>();
        fragments.add(new ActivityFragment());
        fragments.add(new CircleFragment());
        fragmentTabAdapter=new FragmentTabAdapter((BaseFragmentActivity) getActivity(),fragments,R.id.framelayout_menu);
        fragmentTabAdapter.setCurrentTab(0);
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_activity:
                fragmentTabAdapter.setCurrentTab(0);
                break;
            case R.id.tv_circle:
                fragmentTabAdapter.setCurrentTab(1);
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        blurringView.invalidate();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden&&blurringView instanceof BlurringView) blurringView.invalidate();
    }
}
