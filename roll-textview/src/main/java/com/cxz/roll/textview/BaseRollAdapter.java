package com.cxz.roll.textview;

import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author chenxz
 * @date 2018/10/22
 * @desc
 */
public abstract class BaseRollAdapter<T> {

    private List<T> mDatas;
    private OnDataChangedListener mOnDataChangedListener;

    public BaseRollAdapter(List<T> datas) {
        this.mDatas = datas;
        if (datas == null || datas.isEmpty()) {
            throw new RuntimeException("no data");
        }
    }

    public BaseRollAdapter(T[] datas) {
        mDatas = new ArrayList<>(Arrays.asList(datas));
    }

    public void setData(List<T> datas) {
        this.mDatas = datas;
        notifyDataChanged();
    }

    void setOnDataChangedListener(OnDataChangedListener listener) {
        mOnDataChangedListener = listener;
    }

    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public T getItem(int position) {
        return mDatas.get(position);
    }

    void notifyDataChanged() {
        mOnDataChangedListener.onChanged();
    }

    /**
     * 设置 RollTextView 的样式
     */
    public abstract View getView(RollTextView parent);

    /**
     * 设置 RollTextView 的数据
     */
    public abstract void setItem(View view, T data);

    interface OnDataChangedListener {
        void onChanged();
    }
}
