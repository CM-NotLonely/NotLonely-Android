package com.efan.notlonely.ui.message;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.efan.basecmlib.activity.BaseFragment;
import com.efan.basecmlib.activity.BaseFragmentActivity;
import com.efan.basecmlib.annotate.ViewInject;
import com.efan.notlonely.R;
import com.efan.notlonely.ui.adapter.FragmentTabAdapter;
import com.efan.notlonely.ui.adapter.MsgViewPagerAdapter;
import com.efan.notlonely.ui.menu.ActivityFragment;
import com.efan.notlonely.ui.menu.CircleFragment;
import com.efan.notlonely.view.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by 一帆 on 2016/3/22.
 */
public class MessageFragment extends BaseFragment {
    @ViewInject(id=R.id.slidingTabStrip)
    private PagerSlidingTabStrip slidingTabStrip;
    @ViewInject(id=R.id.viewPager)
    private ViewPager viewPager;

    private MyPagerAdapter adapter;
    private List<BaseFragment> fragments;

    @Override
    protected View inflaterView(LayoutInflater var1, ViewGroup var2, Bundle var3) {
        return var1.inflate(R.layout.fragment_message,var2,false);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        fragments=new ArrayList<BaseFragment>();
        fragments.add(new ActivityFragment());
        fragments.add(new CircleFragment());
        fragments.add(new ActivityFragment());
        adapter=new MyPagerAdapter(getFragmentManager());
        slidingTabStrip.setViewPager(viewPager);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void onClick(View v) {

    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }
    }
}
