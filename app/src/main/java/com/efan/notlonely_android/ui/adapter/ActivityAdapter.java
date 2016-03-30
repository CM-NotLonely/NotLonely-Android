package com.efan.notlonely_android.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.efan.notlonely_android.R;

/**
 * Created by linqh0806 on 16-3-25.
 */
public class ActivityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private int BottomHeight=0;
    private Context mContext;
    private LayoutInflater mInflater;

    private onItemClickListener onItemClickListener;

    public interface onItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ActivityAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        BottomHeight= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,50,mContext.getResources().getDisplayMetrics());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = mInflater.inflate(R.layout.item_cardview_activity, parent, false);
            vHold hold = new vHold(view);
            return hold;
        } else if (viewType == TYPE_FOOTER) {
            View view = new View(mContext);
            RecyclerView.LayoutParams lp=new RecyclerView.LayoutParams(0,0);
            lp.setMargins(0,BottomHeight,0,0);
            view.setLayoutParams(lp);
            FooterViewHolder holder = new FooterViewHolder(view);
            return holder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
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
    public int getItemViewType(int position) {
        if (position+1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return 10 + 1;
    }

    public class vHold extends RecyclerView.ViewHolder {

        public vHold(View itemView) {
            super(itemView);
        }
    }

    private class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }
}
