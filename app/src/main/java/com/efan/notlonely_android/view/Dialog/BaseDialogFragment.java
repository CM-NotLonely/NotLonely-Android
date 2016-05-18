package com.efan.notlonely_android.view.Dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

/**
 * Created by linqh0806 on 2016/5/18.
 */
public abstract class BaseDialogFragment extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(setInflaterLayout(), null);
        init(view);
        setEvent(view);
        return view;
    }

    public abstract int setInflaterLayout();
    public abstract void setEvent(View view);
    public abstract void init(View view);
}
