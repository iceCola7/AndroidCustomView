package com.cxz.androidcustomview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cxz.androidcustomview.R;
import com.cxz.androidcustomview.bean.RollTextBean;
import com.cxz.roll.textview.BaseRollAdapter;
import com.cxz.roll.textview.RollTextView;

import java.util.List;

/**
 * @author chenxz
 * @date 2018/10/22
 * @desc
 */
public class RollTextViewAdapter2 extends BaseRollAdapter<RollTextBean> {

    public RollTextViewAdapter2(List<RollTextBean> datas) {
        super(datas);
    }

    @Override
    public View getView(RollTextView parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_roll_text02, null);
    }

    @Override
    public void setItem(final View view, final RollTextBean data) {
        TextView tv_title = view.findViewById(R.id.tv_title);
        tv_title.setText(data.title);
        TextView tv_tag = view.findViewById(R.id.tv_tag);
        tv_tag.setText(data.tag);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext(), data.url, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
