package com.efan.notlonely_android.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.efan.basecmlib.activity.BaseFragment;
import com.efan.basecmlib.annotate.OnClick;
import com.efan.basecmlib.annotate.ViewInject;
import com.efan.notlonely_android.MainApplication;
import com.efan.notlonely_android.R;
import com.efan.notlonely_android.event.RefreshEvent;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.nineoldandroids.view.ViewHelper;

import org.greenrobot.eventbus.Subscribe;

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
    @ViewInject(id = R.id.login_layout)
    private RelativeLayout userLayout;
    @ViewInject(id = R.id.notlogin_layout)
    private RelativeLayout notLoginLayout;
    @ViewInject(id = R.id.user_icon)
    private SimpleDraweeView simpleDraweeView;
    @ViewInject(id = R.id.register)
    private Button registerButton;
    @ViewInject(id = R.id.login)
    private Button loginButton;

    private ImageView setting;

    private int mActionBarSize;
    private int mFlexibleSpaceImageHeight;

    private boolean isLogin;

    @Subscribe
    public void onEventMainThread(RefreshEvent event){
        if (event.type == RefreshEvent.RefreshType.LOGIN) {
            changeLoginLayout(true);
        }
    }

    @Override
    protected View inflaterView(LayoutInflater var1, ViewGroup var2, Bundle var3) {
        View view = var1.inflate(R.layout.fragment_mine,var2,false);
        return view;
    }

    @Override
    public void initView() {
        isLogin = MainApplication.getInstance().isLogin();
        changeLoginLayout(isLogin);
        setting = (ImageView) getActivity().findViewById(R.id.setting);
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
    @OnClick(value = {R.id.login,R.id.register,R.id.mine_homepage,R.id.mine_attention,R.id.mine_push,R.id.mine_join,R.id.mine_praise, R.id.setting})
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
            case R.id.setting:

                break;
        }
    }

    /**
     * 根据登录状态的改变确定视图
     * @param isLogin
     */
    private void changeLoginLayout(boolean isLogin){
        if(isLogin){
            userLayout.setVisibility(View.VISIBLE);
            notLoginLayout.setVisibility(View.GONE);
        }else{
            userLayout.setVisibility(View.GONE);
            notLoginLayout.setVisibility(View.VISIBLE);
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
