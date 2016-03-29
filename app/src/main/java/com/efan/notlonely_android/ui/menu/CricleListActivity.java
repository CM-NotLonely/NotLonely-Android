package com.efan.notlonely_android.ui.menu;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.efan.basecmlib.activity.BaseActivity;
import com.efan.basecmlib.annotate.ContentView;
import com.efan.basecmlib.annotate.ViewInject;
import com.efan.notlonely_android.R;
import com.efan.notlonely_android.view.BlurringView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.nineoldandroids.view.ViewHelper;

/**
 * Created by 一帆 on 2016/3/28.
 */
@ContentView(id = R.layout.activity_cricle)
public class CricleListActivity extends BaseActivity implements ObservableScrollViewCallbacks {
    @ViewInject(id = R.id.image)
    private View mImageView;
    @ViewInject(id = R.id.toolbar)
    private View mToolbarView;
    @ViewInject(id = R.id.scroll)
    private ObservableScrollView mScrollView;
    @ViewInject(id = R.id.blurring_view)
    private BlurringView blurringView;
    private int mParallaxImageHeight;
    @Override
    public void initView() {
        setSupportActionBar((Toolbar) mToolbarView);
        mToolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(0, getResources().getColor(R.color.colorPrimary)));
        ((Toolbar) mToolbarView).setNavigationIcon(R.mipmap.ic_back);
        ((Toolbar) mToolbarView).setTitle("圈子详情");
        ((Toolbar) mToolbarView).setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        blurringView.setBlurredView(mImageView);
        mScrollView.setScrollViewCallbacks(this);
        mParallaxImageHeight = getResources().getDimensionPixelSize(R.dimen.parallax_image_height);
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

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        onScrollChanged(mScrollView.getCurrentScrollY(), false, false);
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        int baseColor = getResources().getColor(R.color.colorPrimary);
        float alpha = Math.min(1, (float) scrollY / mParallaxImageHeight);
        mToolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha, baseColor));
        ViewHelper.setTranslationY(mImageView, scrollY / 2);
        blurringView.invalidate();
    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
    }
}
