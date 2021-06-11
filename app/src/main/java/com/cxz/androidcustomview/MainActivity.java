package com.cxz.androidcustomview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.cxz.androidcustomview.activity.BankCardActivity;
import com.cxz.androidcustomview.activity.BubbleDrawViewActivity;
import com.cxz.androidcustomview.activity.CircleIndicatorActivity;
import com.cxz.androidcustomview.activity.ExplosionViewActivity;
import com.cxz.androidcustomview.activity.FallViewActivity;
import com.cxz.androidcustomview.activity.FlowLayoutActivity;
import com.cxz.androidcustomview.activity.GiftActivity;
import com.cxz.androidcustomview.activity.ImagePreviewActivity;
import com.cxz.androidcustomview.activity.JDPullRefreshActivity;
import com.cxz.androidcustomview.activity.LoadingActivity;
import com.cxz.androidcustomview.activity.NotificationActivity;
import com.cxz.androidcustomview.activity.NumberKeyboardActivity;
import com.cxz.androidcustomview.activity.PaletteImageViewActivity;
import com.cxz.androidcustomview.activity.PayPsdViewActivity;
import com.cxz.androidcustomview.activity.PwdKeyboardActivity;
import com.cxz.androidcustomview.activity.RangeSeekBarActivity;
import com.cxz.androidcustomview.activity.RollTextViewActivity;
import com.cxz.androidcustomview.activity.RoundProgressBarActivity;
import com.cxz.androidcustomview.activity.ShadowImageViewActivity;
import com.cxz.androidcustomview.activity.TiltTextViewActivity;
import com.cxz.androidcustomview.activity.WaveView2Activity;
import com.cxz.androidcustomview.activity.WaveViewActivity;
import com.cxz.androidcustomview.activity.bottomdialog.BottomDialogActivity;
import com.cxz.androidcustomview.adapter.MainAdapter;
import com.cxz.androidcustomview.bean.MainBean;
import com.cxz.androidcustomview.widget.SuperDividerItemDecoration;
import com.cxz.behaviorsample.BehaviorActivity;
import com.cxz.bottomnav.sample.BottomNavActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MainAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recycler_view);

        mAdapter = new MainAdapter(getDatas());

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SuperDividerItemDecoration.Builder(this).setDividerWidth(0).setDividerColor(getResources().getColor(R.color.colorAccent)).build());
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                itemClick(adapter, view, position);
            }
        });
    }

    private List<MainBean> getDatas() {
        List<MainBean> lists = new ArrayList<>();
        lists.add(new MainBean(CustomType.TYPE_0, "银行卡输入框"));
        lists.add(new MainBean(CustomType.TYPE_1, "自定义支付密码输入框"));
        lists.add(new MainBean(CustomType.TYPE_2, "波浪动画-贝塞尔曲线实现"));
        lists.add(new MainBean(CustomType.TYPE_3, "波浪动画-正余弦函数实现"));
        lists.add(new MainBean(CustomType.TYPE_4, "LoadingView"));
        lists.add(new MainBean(CustomType.TYPE_5, "自定义数字键盘"));
        lists.add(new MainBean(CustomType.TYPE_6, "仿微信自定义弹出键盘"));
        lists.add(new MainBean(CustomType.TYPE_7, "CircleIndicator"));
        lists.add(new MainBean(CustomType.TYPE_8, "倾斜文本"));
        lists.add(new MainBean(CustomType.TYPE_9, "垂直和水平滚动的广告"));
        lists.add(new MainBean(CustomType.TYPE_10, "仿京东下拉刷新"));
        lists.add(new MainBean(CustomType.TYPE_11, "仿QQ气泡拖拽效果"));
        lists.add(new MainBean(CustomType.TYPE_12, "RangeSeekBar"));
        lists.add(new MainBean(CustomType.TYPE_13, "ZoomImageView"));
        lists.add(new MainBean(CustomType.TYPE_14, "礼物列表"));
        lists.add(new MainBean(CustomType.TYPE_15, "自定义圆形进度框"));
        lists.add(new MainBean(CustomType.TYPE_16, "ShadowImageViewActivity"));
        lists.add(new MainBean(CustomType.TYPE_17, "PaletteImageViewActivity"));
        lists.add(new MainBean(CustomType.TYPE_18, "BottomDialogActivity"));
        lists.add(new MainBean(CustomType.TYPE_18, "BottomDialogActivity"));
        lists.add(new MainBean(CustomType.TYPE_19, "FallViewActivity"));
        lists.add(new MainBean(CustomType.TYPE_20, "NotificationActivity"));
        lists.add(new MainBean(CustomType.TYPE_21, "CoordinatorLayout+Behavior实践滑动动画"));
        lists.add(new MainBean(CustomType.TYPE_22, "BottomNavigationView+Lottie实现底部带动画的导航栏"));
        lists.add(new MainBean(CustomType.TYPE_23, "粒子爆炸效果"));
        lists.add(new MainBean(CustomType.TYPE_24, "流式布局"));
        return lists;
    }

    private void itemClick(BaseQuickAdapter adapter, View view, int position) {
        MainBean bean = (MainBean) adapter.getData().get(position);
        switch (bean.getType()) {
            case CustomType.TYPE_0:
                startActivity(BankCardActivity.class);
                break;
            case CustomType.TYPE_1:
                startActivity(PayPsdViewActivity.class);
                break;
            case CustomType.TYPE_2:
                startActivity(WaveViewActivity.class);
                break;
            case CustomType.TYPE_3:
                startActivity(WaveView2Activity.class);
                break;
            case CustomType.TYPE_4:
                startActivity(LoadingActivity.class);
                break;
            case CustomType.TYPE_5:
                startActivity(NumberKeyboardActivity.class);
                break;
            case CustomType.TYPE_6:
                startActivity(PwdKeyboardActivity.class);
                break;
            case CustomType.TYPE_7:
                startActivity(CircleIndicatorActivity.class);
                break;
            case CustomType.TYPE_8:
                startActivity(TiltTextViewActivity.class);
                break;
            case CustomType.TYPE_9:
                startActivity(RollTextViewActivity.class);
                break;
            case CustomType.TYPE_10:
                startActivity(JDPullRefreshActivity.class);
                break;
            case CustomType.TYPE_11:
                startActivity(BubbleDrawViewActivity.class);
                break;
            case CustomType.TYPE_12:
                startActivity(RangeSeekBarActivity.class);
                break;
            case CustomType.TYPE_13:
                startActivity(ImagePreviewActivity.class);
                break;
            case CustomType.TYPE_14:
                startActivity(GiftActivity.class);
                break;
            case CustomType.TYPE_15:
                startActivity(RoundProgressBarActivity.class);
                break;
            case CustomType.TYPE_16:
                startActivity(ShadowImageViewActivity.class);
                break;
            case CustomType.TYPE_17:
                startActivity(PaletteImageViewActivity.class);
                break;
            case CustomType.TYPE_18:
                startActivity(BottomDialogActivity.class);
                break;
            case CustomType.TYPE_19:
                startActivity(FallViewActivity.class);
                break;
            case CustomType.TYPE_20:
                startActivity(NotificationActivity.class);
                break;
            case CustomType.TYPE_21:
                startActivity(BehaviorActivity.class);
                break;
            case CustomType.TYPE_22:
                startActivity(BottomNavActivity.class);
                break;
            case CustomType.TYPE_23:
                startActivity(ExplosionViewActivity.class);
                break;
            case CustomType.TYPE_24:
                startActivity(FlowLayoutActivity.class);
                break;
        }
    }

    private void startActivity(Class clazz) {
        startActivity(new Intent(MainActivity.this, clazz));
    }

    static class CustomType {
        static final int TYPE_0 = 0;
        static final int TYPE_1 = 1;
        static final int TYPE_2 = 2;
        static final int TYPE_3 = 3;
        static final int TYPE_4 = 4;
        static final int TYPE_5 = 5;
        static final int TYPE_6 = 6;
        static final int TYPE_7 = 7;
        static final int TYPE_8 = 8;
        static final int TYPE_9 = 9;
        static final int TYPE_10 = 10;
        static final int TYPE_11 = 11;
        static final int TYPE_12 = 12;
        static final int TYPE_13 = 13;
        static final int TYPE_14 = 14;
        static final int TYPE_15 = 15;
        static final int TYPE_16 = 16;
        static final int TYPE_17 = 17;
        static final int TYPE_18 = 18;
        static final int TYPE_19 = 19;
        static final int TYPE_20 = 20;
        static final int TYPE_21 = 21;
        static final int TYPE_22 = 22;
        static final int TYPE_23 = 23;
        static final int TYPE_24 = 24;
    }

}
