package com.cxz.androidcustomview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cxz.androidcustomview.activity.BankCardActivity;
import com.cxz.androidcustomview.adapter.MainAdapter;
import com.cxz.androidcustomview.bean.MainBean;
import com.cxz.androidcustomview.widget.SuperDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BaseQuickAdapter.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private MainAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recycler_view);

        mAdapter = new MainAdapter(getDatas());
        mAdapter.setOnItemClickListener(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SuperDividerItemDecoration.Builder(this).setDividerWidth(0).setDividerColor(getResources().getColor(R.color.colorAccent)).build());
        mRecyclerView.setAdapter(mAdapter);

    }

    private List<MainBean> getDatas() {
        List<MainBean> lists = new ArrayList<>();
        lists.add(new MainBean(0, "银行卡输入框"));
        return lists;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        MainBean bean = (MainBean) adapter.getData().get(position);
        switch (bean.getType()) {
            case 0:
                startActivity(BankCardActivity.class);
                break;
        }
    }

    private void startActivity(Class clazz) {
        startActivity(new Intent(MainActivity.this, clazz));
    }

}
