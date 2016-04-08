package com.efan.notlonely_android.ui.menu;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.efan.basecmlib.activity.BaseActivity;
import com.efan.basecmlib.annotate.ContentView;
import com.efan.basecmlib.annotate.ViewInject;
import com.efan.notlonely_android.R;
import com.efan.notlonely_android.ui.adapter.UserIconAndNameAdapter;

import java.util.ArrayList;

/**
 * Created by 一帆 on 2016/4/1.
 */
@ContentView(id = R.layout.activity_activity)
public class ActivityDetailsActivity extends BaseActivity{

    @ViewInject(id = R.id.recycler)
    private RecyclerView recyclerView;

    private UserIconAndNameAdapter adapter;

    @Override
    public void initView() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setHasFixedSize(false);

        View header = LayoutInflater.from(this).inflate(R.layout.layout_activity_details_recycler_header, null);

        ArrayList<Integer> items = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            items.add(i);
        }
        adapter = new UserIconAndNameAdapter(ActivityDetailsActivity.this,items, header);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void initData() {
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void onClick(View v) {

    }
}
