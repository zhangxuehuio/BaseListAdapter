package com.baselistadapter.adapter;

import android.support.annotation.Nullable;


import com.baselistadapter.utils.LogUtils;

import java.util.List;


/**
 * 多布局适配器封装
 * bug记录：itemtype 必须小于 itemTypeCount （待解决）
 * Created by zhangxuehui on 2018/2/28.
 */
public abstract class BaseMultiListAdapter<M extends BaseMultiModel> extends BaseListAdapter<M> {

    public BaseMultiListAdapter(@Nullable List<M> mList) {
        super(mList);
        addLayoutIds();
    }

    protected abstract void addLayoutIds();


    @Override
    public int getItemViewType(int position) {
        int viewType = super.getItemViewType(position);
        if (ITEM_SINGLE_CONTENT == viewType) {
            LogUtils.e(position+"      "+mList.size()+"    "+mList.get(position).getItemType() + "    " + mLayoutIds.indexOfValue(mList.get(position).getItemType()) + "layoutIds==>" + mLayoutIds.toString());
            int index = mLayoutIds.indexOfValue(mList.get(position).getItemType());
            if (index == -1) {
                try {
                    throw new Exception("布局不存在:position==>" + position +
                            "\n layoutId==>" + mList.get(position).getItemType() +
                            "\n layoutIds==>" + mLayoutIds.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            viewType = mLayoutIds.keyAt(index);
            if (viewType == 0) {
                try {
                    throw new Exception("布局不存在:position==>" + position +
                            "\n layoutId==>" + mList.get(position).getItemType() +
                            "\n layoutIds==>" + mLayoutIds.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return viewType;
    }

    @Override
    public M getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getViewTypeCount() {
        return mLayoutIds.size();
    }

//    /**
//     * 通过数据类型，获取布局id
//     *
//     * @param itemType
//     * @return
//     */
//    @Override
//    protected int getLayoutId(int itemType) {
//        return mLayoutIds.get(itemType);
//    }

    /**
     * 检查数据的类型
     *
     * @param position 位置
     * @param cls      类class 用于判断是否是同一个class对像
     * @return
     */
    public boolean checkTypeForModel(int position, Class cls) {
        return getItem(position) != null && cls.equals(getItem(position).getClass());
    }
}
