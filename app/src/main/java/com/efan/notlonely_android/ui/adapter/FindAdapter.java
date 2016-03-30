package com.efan.notlonely_android.ui.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.efan.notlonely_android.R;
import com.efan.notlonely_android.view.BlurringView;

import java.util.ArrayList;


/**
 * Created by linqh0806 on 16-3-25.
 */
public class FindAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM_1 = 0;
    private static final int TYPE_ITEM_2 = 1;
    private static final int TYPE_FOOTER = 2;
    private static final int TYPE_HEADER_1 = 3;
    private static final int TYPE_HEADER_2 = 4;

    private Context mContext;
    private int BottomHeight = 0;
    private LayoutInflater mInflater;
    private ArrayList<Drawable> mData_people;
    private ArrayList<Drawable> mData_circle;

    private onItemClickListener onItemClickListener;


    public interface onItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public FindAdapter(Context mContext, ArrayList<Drawable> people, ArrayList<Drawable> circle) {
        this.mContext = mContext;
        this.mData_people = people;
        this.mData_circle = circle;
        mInflater = LayoutInflater.from(mContext);
        BottomHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, mContext.getResources().getDisplayMetrics());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER_1) {
            View view = mInflater.inflate(R.layout.item_circle_header_people, parent, false);
            HeaderViewHolder holder = new HeaderViewHolder(view);
            return holder;
        }
        if (viewType == TYPE_ITEM_1) {
            View view = mInflater.inflate(R.layout.item_cardview_poeple, parent, false);
            vHold holder = new vHold(view);
            return holder;
        }
        if (viewType == TYPE_HEADER_2) {
            View view = mInflater.inflate(R.layout.item_circle_header_interest, parent, false);
            HeaderViewHolder holder = new HeaderViewHolder(view);
            return holder;
        }
        if (viewType == TYPE_ITEM_2) {
            View view = mInflater.inflate(R.layout.item_cardview_circle, parent, false);
            vHold holder = new vHold(view);
            return holder;
        }
        if (viewType == TYPE_FOOTER) {
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
        if(position==0||position==mData_people.size()+1) ((HeaderViewHolder)holder).tv_more.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        if (position > 0 && position < mData_people.size() + 1)
            ((vHold) holder).imageView.setImageDrawable(mData_people.get(position - 1));
        else if (position > mData_people.size() + 1 && position < mData_circle.size() + mData_people.size() + 2)
            ((vHold) holder).imageView.setImageDrawable(mData_circle.get(position - 1-mData_people.size()-1));
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
            return TYPE_HEADER_1;
        } else if (position == mData_people.size() + 1) {
            return TYPE_HEADER_2;
        } else if (position > 0 && position < mData_people.size() + 1) {
            return TYPE_ITEM_1;
        } else {
            return TYPE_ITEM_2;
        }
    }

    @Override
    public int getItemCount() {
        return mData_people.size() + mData_circle.size() + 3;
    }

    public class vHold extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public BlurringView blurringView;

        public vHold(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_background);
            blurringView = (BlurringView) itemView.findViewById(R.id.blurring);
        }
    }

    private class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView tv_more;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            tv_more = (TextView) itemView.findViewById(R.id.tv_more);
        }
    }

}
