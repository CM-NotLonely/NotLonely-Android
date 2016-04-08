package com.efan.notlonely_android.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.efan.notlonely_android.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by 一帆 on 2016/4/7.
 */
public class UserIconAndNameAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private Context context;
    private List<Integer> mDatas;
    private View headerView = null;

    public UserIconAndNameAdapter(Context context, List<Integer> datas) {
        this.context = context;
        this.mDatas = datas;
    }

    public UserIconAndNameAdapter(Context context, List<Integer> datas, View headerView) {
        this.context = context;
        this.mDatas = datas;
        this.headerView = headerView;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == 0) ? TYPE_HEADER : TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER && headerView != null) {
            return new HeaderViewHolder(headerView);
        } else {
            return new MyViewHolder(LayoutInflater.from(context)
                    .inflate(R.layout.item_grid_user, parent, false));
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(position != 0) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            myViewHolder.userIcon.setImageURI(Uri.parse("res:///" + R.mipmap.touxiang));
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if(manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == TYPE_HEADER
                            ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View view) {
            super(view);
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private SimpleDraweeView userIcon;

        public MyViewHolder(View itemView) {
            super(itemView);
            userIcon = (SimpleDraweeView) itemView.findViewById(R.id.user_icon);
        }
    }
}
