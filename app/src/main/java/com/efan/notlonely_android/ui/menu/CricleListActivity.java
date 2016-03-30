package com.efan.notlonely_android.ui.menu;

import android.annotation.TargetApi;
import android.content.res.Configuration;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.efan.basecmlib.activity.BaseActivity;
import com.efan.basecmlib.annotate.ContentView;
import com.efan.basecmlib.annotate.ViewInject;
import com.efan.notlonely_android.R;
import com.efan.notlonely_android.ui.adapter.SimpleHeaderRecyclerAdapter;
import com.efan.notlonely_android.view.BlurringView;
import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;

/**
 * Created by 一帆 on 2016/3/28.
 */
@ContentView(id = R.layout.activity_cricle)
public class CricleListActivity extends BaseActivity implements ObservableScrollViewCallbacks {

    private static final float MAX_TEXT_SCALE_DELTA = 0.3f;

    @ViewInject(id = R.id.toolbar)
    private Toolbar toolbar;
    @ViewInject(id = R.id.image)
    private View mImageView;
    @ViewInject(id = R.id.overlay)
    private View mOverlayView;
    @ViewInject(id = R.id.list_background)
    private View mRecyclerViewBackground;
    @ViewInject(id = R.id.cricle_details)
    private RelativeLayout mTitleView;
    @ViewInject(id=R.id.blurring_view)
    private BlurringView blur;

    private int mActionBarSize;
    private int mFlexibleSpaceImageHeight;

    private SimpleHeaderRecyclerAdapter adapter;

    @Override
    public void initView() {
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(ScrollUtils.getColorWithAlpha(0, getResources().getColor(R.color.colorPrimary)));

        mFlexibleSpaceImageHeight = getResources().getDimensionPixelSize(R.dimen.cricle_image_height);
        mActionBarSize = getResources().getDimensionPixelSize(R.dimen.common_headerbar_height);

        ObservableRecyclerView recyclerView = (ObservableRecyclerView) findViewById(R.id.scroll_recycler);
        recyclerView.setScrollViewCallbacks(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(false);
        final View headerView = LayoutInflater.from(this).inflate(R.layout.recycler_header, null);
        headerView.post(new Runnable() {
            @Override
            public void run() {
                headerView.getLayoutParams().height = mFlexibleSpaceImageHeight;
            }
        });
        ArrayList<String> items = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            items.add("Item " + i);
        }
        adapter = new SimpleHeaderRecyclerAdapter(CricleListActivity.this,items,headerView);
        recyclerView.setAdapter(adapter);

        setTitle(null);

        // mRecyclerViewBackground makes RecyclerView's background except header view.

        //since you cannot programmatically add a header view to a RecyclerView we added an empty view as the header
        // in the adapter and then are shifting the views OnCreateView to compensate
//        final float scale = 1 + MAX_TEXT_SCALE_DELTA;
        mRecyclerViewBackground.post(new Runnable() {
            @Override
            public void run() {
                ViewHelper.setTranslationY(mRecyclerViewBackground, mFlexibleSpaceImageHeight);
            }
        });
        ViewHelper.setTranslationY(mOverlayView, mFlexibleSpaceImageHeight);
        mTitleView.post(new Runnable() {
            @Override
            public void run() {
                ViewHelper.setTranslationY(mTitleView, (int) (mFlexibleSpaceImageHeight - mTitleView.getHeight()));
//                ViewHelper.setTranslationY(mTitleView, (int) 0);
                ViewHelper.setPivotX(mTitleView, 0);
                ViewHelper.setPivotY(mTitleView, 0);
//                ViewHelper.setScaleX(mTitleView, scale);
//                ViewHelper.setScaleY(mTitleView, scale);
            }
        });
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
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        //Translate toolbar background
        int baseColor = getResources().getColor(R.color.colorPrimary);
        float alpha = Math.min(1, (float) scrollY / mFlexibleSpaceImageHeight);
        toolbar.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha, baseColor));

        // Translate overlay and image
        float flexibleRange = mFlexibleSpaceImageHeight - mActionBarSize;
        int minOverlayTransitionY = mActionBarSize - mOverlayView.getHeight();
        ViewHelper.setTranslationY(mOverlayView, ScrollUtils.getFloat(-scrollY, minOverlayTransitionY, 0));
        ViewHelper.setTranslationY(mImageView, ScrollUtils.getFloat(-scrollY / 2, minOverlayTransitionY, 0));

        // Translate list background
        ViewHelper.setTranslationY(mRecyclerViewBackground, Math.max(0, -scrollY + mFlexibleSpaceImageHeight));

        // Change alpha of overlay
        ViewHelper.setAlpha(mOverlayView, ScrollUtils.getFloat((float) scrollY / flexibleRange, 0, 1));

        // Scale title text
        float scale = 1 + ScrollUtils.getFloat((flexibleRange - scrollY) / flexibleRange, 0, MAX_TEXT_SCALE_DELTA);
    //    setPivotXToTitle();
        ViewHelper.setPivotY(mTitleView, 0);
//        ViewHelper.setScaleX(mTitleView, scale);
//        ViewHelper.setScaleY(mTitleView, scale);

        // Translate title text
        int maxTitleTranslationY = (int) (mFlexibleSpaceImageHeight - mTitleView.getHeight());
        int titleTranslationY = maxTitleTranslationY - scrollY;
        ViewHelper.setTranslationY(mTitleView, titleTranslationY);
    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void setPivotXToTitle() {
        Configuration config = getResources().getConfiguration();
        if (Build.VERSION_CODES.JELLY_BEAN_MR1 <= Build.VERSION.SDK_INT
                && config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
            ViewHelper.setPivotX(mTitleView, findViewById(android.R.id.content).getWidth());
        } else {
            ViewHelper.setPivotX(mTitleView, 0);
        }
    }
}
