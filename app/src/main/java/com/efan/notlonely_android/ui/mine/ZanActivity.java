package com.efan.notlonely_android.ui.mine;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.efan.basecmlib.activity.BaseActivity;
import com.efan.basecmlib.annotate.ContentView;
import com.efan.basecmlib.annotate.ViewInject;
import com.efan.notlonely_android.R;
import com.efan.notlonely_android.ui.adapter.MyzanAdapter;
import com.efan.notlonely_android.view.BlurringView;
import com.efan.notlonely_android.view.Jellyrefresh.JellyRefreshLayout;

/**
 * Created by thinkpad on 2016/4/7.
 */
@ContentView(id = R.layout.activity_zan)
public class ZanActivity extends BaseActivity {

    @ViewInject(id = R.id.praise_recyclerview)
    private RecyclerView recyclerView;
    @ViewInject(id = R.id.praise_refresh_widget)
    private JellyRefreshLayout mRefreshLayout;

    private BlurringView blurringView;
    private MyzanAdapter adapter;

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
        adapter = new MyzanAdapter(this);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void onClick(View v) {

    }
}
