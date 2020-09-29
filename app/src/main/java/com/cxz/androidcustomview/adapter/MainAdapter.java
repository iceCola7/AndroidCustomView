package com.cxz.androidcustomview.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cxz.androidcustomview.R;
import com.cxz.androidcustomview.bean.MainBean;

import java.util.List;

/**
 * Created by chenxz on 2018/8/3.
 */
public class MainAdapter extends BaseQuickAdapter<MainBean, BaseViewHolder> {

    public MainAdapter(@Nullable List<MainBean> data) {
        super(R.layout.item_main_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MainBean item) {
        helper.setText(R.id.tv_title, item.gettitle());
    }
}
