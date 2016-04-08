package com.efan.notlonely_android.ui.message;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.efan.basecmlib.activity.BaseFragment;
import com.efan.basecmlib.annotate.ViewInject;
import com.efan.notlonely_android.R;
import com.efan.notlonely_android.ui.adapter.MyInsterestAdapter;
import com.efan.notlonely_android.view.BlurringView;

/**
 * Created by 一帆 on 2016/3/30.
 */
public class MyInterestFragment extends BaseFragment {

    @ViewInject(id = R.id.recyclerview)
    private RecyclerView recyclerView;
    @ViewInject(id = R.id.swiperefreshlayout)
    private SwipeRefreshLayout swipeRefreshLayout;
    private BlurringView blurringView;

    private MyInsterestAdapter adapter;

    @Override
    protected View inflaterView(LayoutInflater var1, ViewGroup var2, Bundle var3) {
        View view = var1.inflate(R.layout.fragment_myinterest,var2,false);
        return view;
    }

    @Override
    public void initData() {
        adapter = new MyInsterestAdapter(getContext());
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void initEvent() {
        //blurringView = (BlurringView) getActivity().findViewById(R.id.blurring_view);
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
             //   blurringView.invalidate();
            }
        });
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
