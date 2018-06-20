package com.baselistadapter.adapter;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.blankj.utilcode.util.Utils;

import java.util.List;

/**
 * Created by zhangxuehui on 2018/6/14.
 */

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder> {
    List<T> mList;
    @LayoutRes
    int layoutId;

    public BaseRecyclerViewAdapter(List<T> list) {
        this.mList.addAll(list);
    }

    public BaseRecyclerViewAdapter(@LayoutRes int layoutId, List<T> list) {
        this.layoutId = layoutId;
        this.mList.addAll(list);
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseRecyclerViewHolder(LayoutInflater.from(Utils.getApp()).inflate(layoutId, parent, false));
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
        convert(holder, position);
    }

    /**
     * 设置具体数据
     *
     * @param holder
     * @param position
     */
    public abstract void convert(BaseRecyclerViewHolder holder, int position);

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
