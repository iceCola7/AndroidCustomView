package com.cxz.androidcustomview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cxz.androidcustomview.activity.BankCardActivity;
import com.cxz.androidcustomview.activity.LoadingActivity;
import com.cxz.androidcustomview.activity.NumberKeyboardActivity;
import com.cxz.androidcustomview.activity.PayPsdViewActivity;
import com.cxz.androidcustomview.activity.WaveView2Activity;
import com.cxz.androidcustomview.activity.WaveViewActivity;
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
        lists.add(new MainBean(1, "自定义支付密码输入框"));
        lists.add(new MainBean(2, "波浪动画-贝塞尔曲线实现"));
        lists.add(new MainBean(3, "波浪动画-正余弦函数实现"));
        lists.add(new MainBean(4, "LoadingView"));
        lists.add(new MainBean(5, "自定义数字键盘"));
        return lists;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        MainBean bean = (MainBean) adapter.getData().get(position);
        switch (bean.getType()) {
            case 0:
                startActivity(BankCardActivity.class);
                break;
            case 1:
                startActivity(PayPsdViewActivity.class);
                break;
            case 2:
                startActivity(WaveViewActivity.class);
                break;
            case 3:
                startActivity(WaveView2Activity.class);
                break;
            case 4:
                startActivity(LoadingActivity.class);
                break;
            case 5:
                startActivity(NumberKeyboardActivity.class);
                break;
        }
    }

    private void startActivity(Class clazz) {
        startActivity(new Intent(MainActivity.this, clazz));
    }

}
