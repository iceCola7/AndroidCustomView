package com.cxz.androidcustomview.activity;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.cxz.androidcustomview.R;
import com.cxz.androidcustomview.base.BaseActivity;
import com.cxz.jd.pulltorefresh.JDPullToRefreshRecyclerView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class JDPullRefreshActivity extends BaseActivity {

    private JDPullToRefreshRecyclerView mRecyclerView;
    private List<String> mDatas = new ArrayList<>();

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_jdpull_refresh;
    }

    @Override
    protected void initView() {
        String title = getIntent().getStringExtra("title");
        setToolbarTitle(title);

        initDatas();
        mRecyclerView = findViewById(R.id.custom_pull_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        final CommonAdapter adapter = new CommonAdapter<String>(this, R.layout.list_item, mDatas) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                holder.setText(com.cxz.jd.pulltorefresh.R.id.item_tv, s);
                holder.setImageResource(com.cxz.jd.pulltorefresh.R.id.item_iv, R.mipmap.ic_launcher);
            }
        };
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setOnRefreshListener(new JDPullToRefreshRecyclerView.PullToRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mDatas.clear();
                        refreshDatas();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                                mRecyclerView.onRefreshComplete();
                            }
                        });
                    }
                }).start();
            }
        });

    }

    private void initDatas() {
        for (int i = 0; i < 20; i++) {
            mDatas.add("买买买!");
        }
    }

    private void refreshDatas() {
        for (int i = 0; i < 20; i++) {
            mDatas.add("剁剁剁!");
        }
    }

}
