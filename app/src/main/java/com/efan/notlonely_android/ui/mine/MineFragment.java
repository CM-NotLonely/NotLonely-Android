package com.efan.notlonely_android.ui.mine;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.efan.basecmlib.activity.BaseFragment;
import com.efan.basecmlib.annotate.ViewInject;
import com.efan.notlonely_android.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.nineoldandroids.view.ViewHelper;

/**
 * Created by 一帆 on 2016/3/31.
 */
public class MineFragment extends BaseFragment implements ObservableScrollViewCallbacks {

    private static final float MAX_TEXT_SCALE_DELTA = 0.3f;

    @ViewInject(id = R.id.image)
    private View mImageView;
    @ViewInject(id = R.id.scroll)
    private ObservableScrollView mScrollView;
    @ViewInject(id = R.id.title)
    private TextView mTitleView;
    @ViewInject(id = R.id.user)
    private RelativeLayout userLayout;
    @ViewInject(id = R.id.user_icon)
    private SimpleDraweeView simpleDraweeView;
    private int mActionBarSize;
    private int mFlexibleSpaceShowFabOffset;
    private int mFlexibleSpaceImageHeight;
    private int mFabMargin;
    private boolean mFabIsShown;

    @Override
    protected View inflaterView(LayoutInflater var1, ViewGroup var2, Bundle var3) {
            Log.d("haha","fwsg");
        View view = var1.inflate(R.layout.fragment_mine,var2,false);
        return view;
    }

    @Override
    public void initData() {
        simpleDraweeView.setImageURI(Uri.parse("res:///"+R.mipmap.touxiang));
        mFlexibleSpaceImageHeight = 300;
        mActionBarSize = 50;
        mScrollView.setScrollViewCallbacks(this);
        ScrollUtils.addOnGlobalLayoutListener(mScrollView, new Runnable() {
            @Override
            public void run() {
                mScrollView.scrollTo(0, -mFlexibleSpaceImageHeight-mActionBarSize);
            }
        });
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        // Translate overlay and image
        ViewHelper.setTranslationY(mImageView,-scrollY / 2);
        ViewHelper.setTranslationY(userLayout,-scrollY / 2);
    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }
}
