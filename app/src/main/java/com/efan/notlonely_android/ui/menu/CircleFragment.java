package com.efan.notlonely_android.ui.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.efan.basecmlib.activity.BaseFragment;
import com.efan.basecmlib.annotate.ViewInject;
import com.efan.notlonely_android.R;
import com.efan.notlonely_android.view.BlurringView;

/**
 * Created by linqh0806 on 16-3-23.
 */
public class CircleFragment extends BaseFragment{
    @ViewInject(id=R.id.blur)
    private BlurringView blur;
    @ViewInject(id=R.id.iv_image)
    private ImageView iv;

    private BlurringView blurringView;
    @Override
    protected View inflaterView(LayoutInflater var1, ViewGroup var2, Bundle var3) {
        return var1.inflate(R.layout.fragment_circle,var2,false);
    }

    @Override
    public void initView() {
        blurringView= (BlurringView) getActivity().findViewById(R.id.blurring_view);
    }

    @Override
    public void initData() {
        blur.setBlurredView(iv);
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onResume() {
        super.onResume();
        blurringView.invalidate();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden&&blurringView instanceof BlurringView) blurringView.invalidate();
    }
}
