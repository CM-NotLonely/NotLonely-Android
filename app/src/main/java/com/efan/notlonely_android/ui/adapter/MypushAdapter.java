package com.efan.notlonely_android.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.efan.notlonely_android.R;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by thinkpad on 2016/4/9.
 */
public class MypushAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private int BottomHeight=0;
    private Context mContext;
    private LayoutInflater mInflater;

    public MypushAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        BottomHeight= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,50,mContext.getResources().getDisplayMetrics());
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_mypush, parent, false);
        vHold hold = new vHold(view);
        return hold;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ((vHold) holder).simpleDraweeView.setImageURI(Uri.parse("res:///" + R.mipmap.touxiang));
    }

    @Override
    public int getItemCount() {
        return 10 + 1;
    }

    public class vHold extends RecyclerView.ViewHolder {
        public SimpleDraweeView simpleDraweeView;
        public vHold(View itemView) {
            super(itemView);
            simpleDraweeView= (SimpleDraweeView) itemView.findViewById(R.id.push_face);
        }
    }
}