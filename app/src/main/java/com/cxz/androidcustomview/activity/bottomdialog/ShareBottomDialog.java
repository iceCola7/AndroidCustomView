package com.cxz.androidcustomview.activity.bottomdialog;

import android.view.View;


import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cxz.androidcustomview.R;
import com.cxz.dialoglib.bottom.BaseBottomDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenxz
 * @date 2018/9/10
 * @desc ShareBottomDialog
 */
public class ShareBottomDialog extends BaseBottomDialog {

    private RecyclerView recyclerView;
    private ShareBottomAdapter mAdapter;

    @Override
    public int getLayoutRes() {
        return R.layout.layout_share_bottom_dialog;
    }

    @Override
    public void bindView(View v) {
        v.findViewById(R.id.tv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        recyclerView = v.findViewById(R.id.rv_share_dialog);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        mAdapter = new ShareBottomAdapter(initList());
        recyclerView.setAdapter(mAdapter);
    }

    private List<ItemBean> initList() {
        List<ItemBean> list = new ArrayList<>();
        list.add(new ItemBean("分享到微信", R.mipmap.icon_more_operation_share_friend));
        list.add(new ItemBean("分享到朋友圈", R.mipmap.icon_more_operation_share_moment));
        list.add(new ItemBean("分享到微博", R.mipmap.icon_more_operation_share_weibo));
        list.add(new ItemBean("分享到私信", R.mipmap.icon_more_operation_share_chat));
        list.add(new ItemBean("保存到本地", R.mipmap.icon_more_operation_save));
        return list;
    }

}
