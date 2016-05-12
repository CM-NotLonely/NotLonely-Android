package com.efan.notlonely_android.ui.mine;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.efan.basecmlib.activity.BaseActivity;
import com.efan.basecmlib.annotate.ContentView;
import com.efan.basecmlib.annotate.OnClick;
import com.efan.basecmlib.annotate.ViewInject;
import com.efan.notlonely_android.R;
import com.efan.notlonely_android.ui.adapter.MypushAdapter;
import com.efan.notlonely_android.view.BlurringView;
import com.efan.notlonely_android.view.Jellyrefresh.JellyRefreshLayout;

/**
 * Created by thinkpad on 2016/4/7.
 */
@ContentView(id = R.layout.activity_push)
public class PushActivity extends BaseActivity{

    @ViewInject(id = R.id.push_recyclerview)
    private RecyclerView recyclerView;
    @ViewInject(id = R.id.push_refresh_widget)
    private JellyRefreshLayout mRefreshLayout;

    private BlurringView blurringView;
    private MypushAdapter adapter;

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
        adapter = new MypushAdapter(this);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void initEvent() {

    }

    @Override
    @OnClick(value = {R.id.iv_left})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_left:
                finish();
                break;
        }
    }
}
