package com.efan.notlonely_android.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.efan.notlonely_android.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;


/**
 * Created by linqh0806 on 16-3-25.
 */
public class CircleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_HEADER = 2;

    private Context mContext;
    private int BottomHeight = 0;
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

    public CircleAdapter(Context mContext, ArrayList<Drawable> mData) {
        this.mContext = mContext;
        this.mData = mData;
        mInflater = LayoutInflater.from(mContext);
        BottomHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, mContext.getResources().getDisplayMetrics());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==TYPE_HEADER){
                Button button=new Button(mContext);
                RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(parent.getWidth(),BottomHeight/5*4);
                lp.setMargins(0, BottomHeight/10,0, 0);
                button.setLayoutParams(lp);
                button.setGravity(Gravity.CENTER);
                button.setTextColor(mContext.getResources().getColor(R.color.common));
                button.setText("创建新圈子");
                button.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
                button.setBackgroundResource(R.drawable.shape_join_background);
                HeaderViewHolder holder=new HeaderViewHolder(button);
                return holder;
        }
        if (viewType == TYPE_ITEM) {
            View view = mInflater.inflate(R.layout.item_cardview_circle, parent, false);
            vHold holder = new vHold(view);
            return holder;
        } else if (viewType == TYPE_FOOTER) {
            View view = new View(mContext);
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(0, 0);
            lp.setMargins(0, BottomHeight, 0, 0);
            view.setLayoutParams(lp);
            FooterViewHolder holder = new FooterViewHolder(view);
            return holder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (position + 1 != getItemCount()&&position!=0)
            ((vHold) holder).simpleDraweeView.setImageURI(Uri.parse("res:///"+R.mipmap.test));
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
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else if (position == 0) {
            return TYPE_HEADER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return mData.size() + 2;
    }

    public class vHold extends RecyclerView.ViewHolder {
        public SimpleDraweeView simpleDraweeView;

        public vHold(View itemView) {
            super(itemView);
            simpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.drawee_background);
        }
    }

    private class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder{
        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

}
