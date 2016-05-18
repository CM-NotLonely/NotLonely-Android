package com.efan.notlonely_android.view.Dialog;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.HttpAuthHandler;

import com.efan.notlonely_android.R;
import com.efan.notlonely_android.config.SPConfig;
import com.efan.notlonely_android.event.RefreshEvent;
import com.efan.notlonely_android.ui.mine.LoginActivity;
import com.efan.notlonely_android.utils.ImageUtils;
import com.efan.notlonely_android.utils.PreferencesUtils;
import com.efan.notlonely_android.view.SpinKit.SpinKitView;
import com.efan.notlonely_android.view.SpinKit.style.Circle;
import com.efan.notlonely_android.view.SpinKit.style.ThreeBounce;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by linqh0806 on 2016/5/18.
 */
public class MeDialogFragment extends BaseDialogFragment{
    private SpinKitView spinKitView;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            dismiss();
        }
    };

    @Subscribe
    public void onEventMainThread(RefreshEvent event){
        if (event.type == RefreshEvent.RefreshType.LOGIN) {
            dismiss();
        }
    }

    @Override
    public int setInflaterLayout() {
        return R.layout.dialog_login;
    }

    @Override
    public void init(View view) {
        spinKitView= (SpinKitView) view.findViewById(R.id.spinkit);
        Circle threeBounce=new Circle();
        threeBounce.setColor(getResources().getColor(R.color.common));
        spinKitView.setIndeterminateDrawable(threeBounce);
    }

    @Override
    public void setEvent(View view) {
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                handler.sendEmptyMessage(0);
//            }
//        },1000);
    }

    @Override
    public void onResume() {
        Log.e("dialogSize",String.valueOf(getResources().getDimension(R.dimen.dialog_width)));
        getDialog().getWindow().setLayout((int)getResources().getDimension(R.dimen.dialog_width),(int)getResources().getDimension(R.dimen.dialog_width));
        super.onResume();
    }
}
