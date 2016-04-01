package com.efan.notlonely_android.ui.find;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.efan.basecmlib.activity.BaseFragment;
import com.efan.basecmlib.annotate.ViewInject;
import com.efan.notlonely_android.R;
import com.efan.notlonely_android.ui.adapter.FindAdapter;
import com.efan.notlonely_android.view.BlurringView;
import com.efan.notlonely_android.view.Jellyrefresh.JellyRefreshLayout;

import java.util.ArrayList;

/**
 * Created by 一帆 on 2016/3/22.
 */
public class FindFragment extends BaseFragment {
    @ViewInject(id = R.id.recyclerview)
    private RecyclerView recyclerView;
    @ViewInject(id = R.id.refresh_widget)
    private JellyRefreshLayout mRefreshLayout;

    private FindAdapter adapter;
    private BlurringView blurringView;
    private ArrayList<Drawable> mData;
    @Override
    protected View inflaterView(LayoutInflater var1, ViewGroup var2, Bundle var3) {
        return var1.inflate(R.layout.fragment_find,var2,false);
    }


    @Override
    public void initView() {
//        blurringView = (BlurringView) getActivity().findViewById(R.id.blurring_view);
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
        });    }

    @Override
    public void initData() {
        mData = new ArrayList<Drawable>();
        mData.add(getResources().getDrawable(R.mipmap.test));
        mData.add(getResources().getDrawable(R.mipmap.test1));
        mData.add(getResources().getDrawable(R.mipmap.test));
        mData.add(getResources().getDrawable(R.mipmap.test1));
        mData.add(getResources().getDrawable(R.mipmap.test1));
        adapter = new FindAdapter(getContext(), mData,mData);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void initEvent() {
        ItemClickListener();
        RecyclerViewScrollerListener();
    }

    /**
     * RecyclerView的滚动监听，实时变化bottom
     */
    private void RecyclerViewScrollerListener() {
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                blurringView.invalidate();
            }
        });
    }
    /**
     * ItemClickListener点击，加载高斯模糊变化
     */
    private void ItemClickListener() {
        adapter.setOnItemClickListener(new FindAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }

            @Override
            public void onItemLongClick(View view, int position) {
//                if (position != 0) {
//                    Blurry.with(getContext())
//                            .radius(5)
//                            .sampling(2)
//                            .async()
//                            .capture(view.findViewById(R.id.iv_background))
//                            .into((ImageView) view.findViewById(R.id.iv_background));
//                }
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onResume() {
        super.onResume();
//        blurringView.invalidate();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
//        if(!hidden&&blurringView instanceof BlurringView) blurringView.invalidate();
    }
}
