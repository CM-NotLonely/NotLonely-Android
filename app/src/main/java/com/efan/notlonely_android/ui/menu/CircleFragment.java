package com.efan.notlonely_android.ui.menu;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.efan.basecmlib.activity.BaseFragment;
import com.efan.basecmlib.annotate.ViewInject;
import com.efan.notlonely_android.R;
import com.efan.notlonely_android.ui.adapter.CircleAdapter;
import com.efan.notlonely_android.utils.ImageUtils;
import com.efan.notlonely_android.utils.blurry.Blurry;
import com.efan.notlonely_android.view.BlurringView;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;


/**
 * Created by linqh0806 on 16-3-23.
 */
public class CircleFragment extends BaseFragment {
    @ViewInject(id = R.id.recyclerview)
    private RecyclerView recyclerView;

    private CircleAdapter adapter;
    private BlurringView blurringView;
    private LinearLayoutManager layoutManager;
    private ArrayList<Drawable> mData;

    @Override
    protected View inflaterView(LayoutInflater var1, ViewGroup var2, Bundle var3) {
        return var1.inflate(R.layout.fragment_circle, var2, false);
    }

    @Override
    public void initView() {
        Fresco.initialize(getContext());
        blurringView = (BlurringView) getActivity().findViewById(R.id.blurring_view);
    }

    @Override
    public void initData() {
        mData = new ArrayList<Drawable>();
        {
            Bitmap bitmap = ImageUtils.drawableToBitmap(getResources().getDrawable(R.mipmap.test));
            Bitmap blurBitmap = ImageUtils.doBlur(bitmap, 30, true);   //bitmap to blur bitmap
            Drawable blurdrawable = ImageUtils.BitmapToDrawble(blurBitmap);
            mData.add(blurdrawable);
        }
        {
            Bitmap bitmap = ImageUtils.drawableToBitmap(getResources().getDrawable(R.mipmap.test1));
            Bitmap blurBitmap = ImageUtils.doBlur(bitmap, 30, true);   //bitmap to blur bitmap
            Drawable blurdrawable = ImageUtils.BitmapToDrawble(blurBitmap);
            mData.add(blurdrawable);
        }
        adapter = new CircleAdapter(getContext(),mData);
        recyclerView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void initEvent() {
        adapter.setOnItemClickListener(new CircleAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Blurry.with(getContext())
                        .radius(5)
                        .sampling(2)
                        .async()
                        .capture(view.findViewById(R.id.iv_background))
                        .into((ImageView) view.findViewById(R.id.iv_background));
            }

            @Override
            public void onItemLongClick(View view, int position) {
                initBlur();
            }
        });
    }

    @Override
    public void onClick(View v) {

    }


    /**
     * 对RecyclerView中的item图片进行高斯模糊处理
     */
    private void initBlur() {
        for (int i = 0; i < adapter.getItemCount(); i++) {
            View views = recyclerView.getChildAt(i);
            if (views != null) {
                CircleAdapter.vHold vHold = (CircleAdapter.vHold) recyclerView.getChildViewHolder(views);
                View blurringView = vHold.imageView;
                Blurry.with(getContext())
                        .radius(5)
                        .sampling(2)
                        .async()
                        .capture(blurringView)
                        .into((ImageView) blurringView);
            }
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
        if (!hidden && blurringView instanceof BlurringView) {
            blurringView.invalidate();
        }
    }
}
