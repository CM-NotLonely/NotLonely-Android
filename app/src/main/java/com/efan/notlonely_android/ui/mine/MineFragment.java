package com.efan.notlonely_android.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.efan.basecmlib.activity.BaseFragment;
import com.efan.basecmlib.annotate.OnClick;
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

    private Intent intent;
    private static final float MAX_TEXT_SCALE_DELTA = 0.3f;

    @ViewInject(id = R.id.image)
    private View mImageView;
    @ViewInject(id = R.id.scroll)
    private ObservableScrollView mScrollView;
    @ViewInject(id = R.id.title)
    private TextView mTitleView;
    @ViewInject(id = R.id.user)
    private RelativeLayout userLayout;
    @ViewInject(id = R.id.not_login)
    private RelativeLayout notLoginLayout;
    @ViewInject(id = R.id.user_icon)
    private SimpleDraweeView simpleDraweeView;
    @ViewInject(id = R.id.register)
    private Button registerButton;
    @ViewInject(id = R.id.login)
    private Button loginButton;

    private int mActionBarSize;
    private int mFlexibleSpaceShowFabOffset;
    private int mFlexibleSpaceImageHeight;
    private int mFabMargin;
    private boolean mFabIsShown;

    @Override
    protected View inflaterView(LayoutInflater var1, ViewGroup var2, Bundle var3) {
        return var1.inflate(R.layout.fragment_mine,var2,false);
    }

    @Override
    public void initView() {

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
    @OnClick(value = {R.id.login,R.id.register,R.id.mine_homepage,R.id.mine_attention,R.id.mine_push,R.id.mine_join,R.id.mine_praise})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                intent = new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.register:
                intent = new Intent(getActivity(),IdentityActivity.class);
                startActivity(intent);
                break;
            case R.id.mine_homepage:
                intent = new Intent(getActivity(),HomepageActivity.class);
                startActivity(intent);
                break;
            case R.id.mine_attention:
                intent = new Intent(getActivity(),AttentionActivity.class);
                startActivity(intent);
                break;
            case R.id.mine_push:
                intent = new Intent(getActivity(),PushActivity.class);
                startActivity(intent);
                break;
            case R.id.mine_join:
                intent = new Intent(getActivity(),JoinActivity.class);
                startActivity(intent);
                break;
            case R.id.mine_praise:
                intent = new Intent(getActivity(),ZanActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        // Translate overlay and image
        ViewHelper.setTranslationY(mImageView,-scrollY / 2);
        ViewHelper.setTranslationY(userLayout,-scrollY / 2);
        ViewHelper.setTranslationY(notLoginLayout,-scrollY / 2);
    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }
}
