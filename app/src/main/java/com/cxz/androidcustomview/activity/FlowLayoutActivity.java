package com.cxz.androidcustomview.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.cxz.androidcustomview.R;
import com.cxz.androidcustomview.base.BaseActivity;
import com.cxz.flowlayoutlib.FlowAdapter;
import com.cxz.flowlayoutlib.FlowLayout;

import java.util.Arrays;

public class FlowLayoutActivity extends BaseActivity {

    private FlowLayout flow_layout;

    private LayoutInflater mLayoutInflater;

    private String[] mData = {"Java", "C++", "Python", "JavaScript", "Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
            "Android", "Welcome", "Button ImageView", "TextView", "Helloworld", "Android", "Welcome Hello", "Button Text"};

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_flow_layout;
    }

    @Override
    protected void initView() {
        String title = getIntent().getStringExtra("title");
        setToolbarTitle(title);

        flow_layout = findViewById(R.id.flow_layout);
        mLayoutInflater = LayoutInflater.from(this);

        flow_layout.setAdapter(new FlowAdapter(Arrays.asList(mData)) {
            @Override
            public View getView(int position) {
                View item = mLayoutInflater.inflate(R.layout.item_flow_layout, null);
                TextView tvAttrTag = item.findViewById(R.id.tv_attr_tag);
                tvAttrTag.setText(mData[position]);
                return item;
            }
        });
    }
}