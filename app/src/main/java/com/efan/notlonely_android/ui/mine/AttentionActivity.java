package com.efan.notlonely_android.ui.mine;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.efan.basecmlib.activity.BaseActivity;
import com.efan.basecmlib.annotate.ContentView;
import com.efan.basecmlib.annotate.ViewInject;
import com.efan.notlonely_android.R;
import com.efan.notlonely_android.ui.adapter.MyattentionAdapter;
import com.efan.notlonely_android.view.BlurringView;
import com.efan.notlonely_android.view.Jellyrefresh.JellyRefreshLayout;

import java.util.ArrayList;

/**
 * Created by thinkpad on 2016/4/7.
 */
@ContentView(id = R.layout.activity_attention)
public class AttentionActivity extends BaseActivity{

    @ViewInject(id = R.id.attention_recyclerview)
    private RecyclerView recyclerView;
    @ViewInject(id = R.id.attention_refresh)
    private JellyRefreshLayout mRefreshLayout;

    private MyattentionAdapter adapter;
    private BlurringView blurringView;
    private LinearLayoutManager layoutManager;
    private ArrayList<Drawable> mData;

    @Override
    public void initView() {
        mRefreshLayout.setRefreshListener(new JellyRefreshLayout.JellyRefreshListener() {
            @Override
            public void onRefresh(JellyRefreshLayout jellyRefreshLayout) {
                mRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshLayout.finishRefreshing();
                    }
                }, 3000);
            }
        });
    }

    @Override
    public void initData() {
        mData = new ArrayList<Drawable>();
        mData.add(getResources().getDrawable(R.mipmap.test));
        mData.add(getResources().getDrawable(R.mipmap.test1));
        mData.add(getResources().getDrawable(R.mipmap.test));
        mData.add(getResources().getDrawable(R.mipmap.test1));
        mData.add(getResources().getDrawable(R.mipmap.test1));
        adapter = new MyattentionAdapter(this,mData);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void onClick(View v) {

    }
}
