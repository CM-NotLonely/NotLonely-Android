package com.efan.notlonely.ui.menu;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.efan.basecmlib.activity.BaseFragment;
import com.efan.basecmlib.annotate.ViewInject;
import com.efan.notlonely.R;
import com.efan.notlonely.utils.ImageUtils;
import com.efan.notlonely.view.BlurringView;

/**
 * Created by linqh0806 on 16-3-23.
 */
public class CircleFragment extends BaseFragment {
    @ViewInject(id = R.id.iv_image)
    private ImageView imageView;
    @ViewInject(id=R.id.blur)
    private BlurringView blur;

    private Drawable drawable;

    @Override
    protected View inflaterView(LayoutInflater var1, ViewGroup var2, Bundle var3) {
        return var1.inflate(R.layout.fragment_circle, var2, false);
    }

    @Override
    public void initData() {
        imageView.setImageResource(R.mipmap.test);
//        drawable=imageView.getDrawable();
//        Bitmap bitmap= ImageUtils.drawableToBitmap(drawable);
//        Drawable drawable1=ImageUtils.BitmapToDrawble(ImageUtils.doBlur(bitmap,30,true));
//        imageView.setImageDrawable(drawable1);
        blur.setBlurredView((View)imageView);
        //blur.invalidate();
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void onClick(View v) {

    }
}
