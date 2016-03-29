package com.efan.notlonely_android.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.efan.notlonely_android.R;
import com.efan.notlonely_android.utils.blurry.Blurry;
import com.efan.notlonely_android.view.BlurringView;

import java.util.ArrayList;


/**
 * Created by linqh0806 on 16-3-25.
 */
public class CircleAdapter extends RecyclerView.Adapter<CircleAdapter.vHold> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Drawable> mData;

    private onItemClickListener onItemClickListener;


    public interface onItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public CircleAdapter(Context mContext,ArrayList<Drawable> mData) {
        this.mContext = mContext;
        this.mData=mData;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public vHold onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_cardview_circle, parent, false);
        vHold holder = new vHold(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final vHold holder, final int position) {
        holder.imageView.setImageDrawable(mData.get(position));
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(holder.itemView, position);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClickListener.onItemLongClick(holder.itemView, position);
                    return false;
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class vHold extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public BlurringView blurringView;
        public vHold(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_background);
            blurringView= (BlurringView) itemView.findViewById(R.id.blurring);
        }
    }

}
