package com.efan.notlonely_android.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.efan.notlonely_android.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

/**
 * Created by thinkpad on 2016/4/8.
 */
public class MyattentionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<Drawable> mData;
    private LayoutInflater mInflater;
    private onItemClickListener onItemClickListener;


    public interface onItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public MyattentionAdapter(Context mContext, ArrayList<Drawable> mData) {
        this.mContext = mContext;
        this.mData = mData;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_myattention,parent,false);
        VHold vHold = new VHold(view);
        return vHold;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((VHold) holder).simpleDraweeView.setImageURI(Uri.parse("res:///"+R.mipmap.test));
    }

    @Override
    public int getItemCount() {
        return mData.size() + 2;
    }

    public class VHold extends RecyclerView.ViewHolder {
        public SimpleDraweeView simpleDraweeView;

        public VHold(View itemView) {
            super(itemView);
            simpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.myattention_background);
        }
    }
}
