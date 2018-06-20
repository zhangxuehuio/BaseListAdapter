package com.baselistadapter.adapter;

import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import java.util.ArrayList;
import java.util.List;


/**
 * Created by zhangxuehui on 2018/2/27.
 */
public abstract class BaseListAdapter<T> extends BaseAdapter {
    public static final Integer ITEM_HERDER = -10001;//头布局
    public static final Integer ITEM_FOOTER = -10002;//尾布局
    public static final Integer ITEM_SINGLE_CONTENT = -10003;//显示单一
    protected List<T> mList;
    protected SparseIntArray mLayoutIds;
    private int mPosition;
    private boolean mIsAutoHideHeaderAndFooter;//无数据时，是否自动隐藏头布局以及尾布局

    /**
     * 多布局使用
     *
     * @param list
     */
    public BaseListAdapter(@Nullable List<T> list) {
        if (list == null) {
            mList = new ArrayList<>();
        } else {
            this.mList = list;
        }
        mLayoutIds = new SparseIntArray();
        mIsAutoHideHeaderAndFooter = true;
    }

    /**
     * 单布局使用
     *
     * @param list
     * @param layoutId
     */
    public BaseListAdapter(@Nullable List<T> list, @LayoutRes int layoutId) {
        if (list == null) {
            mList = new ArrayList<>();
        } else {
            this.mList = list;
        }
        mLayoutIds = new SparseIntArray();
        mLayoutIds.put(ITEM_SINGLE_CONTENT, layoutId);
        mIsAutoHideHeaderAndFooter = true;
    }


    @Override
    public int getCount() {
        if (mIsAutoHideHeaderAndFooter) {
            if (mList == null || mList.size() == 0) {
                return 0;
            }
        }
        if (isShowHeaderView() && isShowFooterView()) {
            return mList.size() + 2;
        }
        if ((!isShowHeaderView() && isShowFooterView()) || (isShowHeaderView() && !isShowFooterView())) {
            return mList.size() + 1;
        }
        return mList.size();
    }


    /**
     * 获取某一项的数据
     *
     * @param position
     * @return
     */
    @Override
    public T getItem(@IntRange int position) {
        if (isShowHeaderView() && position == 0) {
            return null;
        }
        if (isShowHeaderView() && position <= mList.size()) {
            return mList.get(position - 1);
        }
        if (isShowFooterView() && isShowHeaderView() && position >= mList.size() + 1) {
            return null;
        }
        if (isShowFooterView() && !isShowHeaderView() && position >= mList.size()) {
            return null;
        }
        return mList.get(position);
    }

    @Override
    public int getItemViewType(@IntRange int position) {
        if (isShowHeaderView() && position == 0) {
            return ITEM_HERDER;
        }
        if (isShowFooterView() && isShowHeaderView() && position >= mList.size() + 1) {
            return ITEM_FOOTER;
        }
        if (isShowFooterView() && !isShowHeaderView() && position >= mList.size()) {
            return ITEM_FOOTER;
        }
        return ITEM_SINGLE_CONTENT;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        this.mPosition = position;
        BaseViewHolder holder = BaseViewHolder.getViewHolder(convertView, parent, getLayoutId(getItemViewType(position)));
        if (getItemViewType(position) != ITEM_HERDER && getItemViewType(position) != ITEM_FOOTER) {
            T model = getItem(position);
            if (model == null) {
                LogUtils.e(getClass().getSimpleName() + "_" + position + ":  model  is null");
                return holder.getConvertView();
            }
            convert(holder, model);
        } else {
            convertHeaderAndFooter(holder, getItemViewType(position));
        }
        return holder.getConvertView();
    }

    /**
     * 设置自动隐藏头布局以及尾布局
     *
     * @param b
     */
    public void setAutoHideHeaderAndFooter(boolean b) {
        this.mIsAutoHideHeaderAndFooter = b;
    }

    /**
     * 设置头布局和表尾布局的内容
     *
     * @param holder
     * @param itemViewType
     */
    protected void convertHeaderAndFooter(BaseViewHolder holder, int itemViewType) {
    }

    public int getPosition() {
        return mPosition;
    }

    /**
     * 通过模型获取position
     *
     * @param t
     * @return
     */
    public int getPositionByModel(T t) {
        return mList.indexOf(t);
    }

    //是否添加头布局
    public boolean isShowHeaderView() {
        if (0 == mLayoutIds.get(ITEM_HERDER)) {
            return false;
        }
        return true;
    }

    //添加头布局
    public void addHeaderView(@LayoutRes int layoutId) {
        mLayoutIds.put(ITEM_HERDER, layoutId);
    }

    //删除头布局
    public void removeHeaderView() {
        mLayoutIds.delete(ITEM_HERDER);
    }

    //更新头布局
    public void updateHeaderView(@LayoutRes int layoutId) {
        mLayoutIds.put(ITEM_HERDER, layoutId);
    }

    //是否添加尾布局
    public boolean isShowFooterView() {
        if (0 == mLayoutIds.get(ITEM_FOOTER)) {
            return false;
        }
        return true;
    }


    //添加头布局
    public void addFooterView(@LayoutRes int layoutId) {
        mLayoutIds.put(ITEM_FOOTER, layoutId);
    }

    //删除头布局
    public void removeFooterView() {
        mLayoutIds.delete(ITEM_FOOTER);
    }

    //更新头布局
    public void updateFooterView(@LayoutRes int layoutId) {
        mLayoutIds.put(ITEM_FOOTER, layoutId);
    }

    protected void addLayoutId(int itemType, @LayoutRes int layoutId) {
        mLayoutIds.put(itemType, layoutId);
    }

    protected void addLayoutId(@LayoutRes int layoutId) {
        mLayoutIds.put(ITEM_SINGLE_CONTENT - mLayoutIds.size(), layoutId);
    }

    /**
     * 通过数据类型，获取布局id
     *
     * @param itemType
     * @return
     */
    protected int getLayoutId(int itemType) {
        return mLayoutIds.get(itemType);
    }

    /**
     * 普通list
     *
     * @param holder
     */
    public abstract void convert(BaseViewHolder holder, T model);


    public void setData(@Nullable List<T> data) {
        mList.addAll(data);
        notifyDataSetChanged();
    }

    public void refreshData(@Nullable List<T> data) {
        mList.clear();
        notifyDataSetChanged();
        mList.addAll(data);
        notifyDataSetChanged();
    }

    public void cleanData() {
        mList.clear();
        notifyDataSetChanged();
    }

    public void removeItem(int index) {
        mList.clear();
        if (mList.size() > index + 1) {
            mList.remove(index);
        }
        notifyDataSetChanged();
    }

    /**
     * 获取全部数据
     *
     * @return 列表数据
     */
    @NonNull
    public List<T> getData() {
        return mList;
    }

    /**
     * 对数据判null，防止null异常
     *
     * @param size Need compatible data size
     */
    private void compatibilityDataSizeChanged(int size) {
        final int dataSize = mList == null ? 0 : mList.size();
        if (dataSize == size) {
            notifyDataSetChanged();
        }
    }


}
