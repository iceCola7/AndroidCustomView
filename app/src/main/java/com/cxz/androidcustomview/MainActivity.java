package com.cxz.androidcustomview;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.cxz.androidcustomview.activity.BankCardActivity;
import com.cxz.androidcustomview.activity.BigViewActivity;
import com.cxz.androidcustomview.activity.BubbleDrawViewActivity;
import com.cxz.androidcustomview.activity.CircleIndicatorActivity;
import com.cxz.androidcustomview.activity.DropDownMenuActivity;
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
import com.cxz.androidcustomview.base.BaseActivity;
import com.cxz.androidcustomview.bean.MainBean;
import com.cxz.androidcustomview.widget.SuperDividerItemDecoration;
import com.cxz.behaviorsample.BehaviorActivity;
import com.cxz.bottomnav.sample.BottomNavActivity;
import com.cxz.selectcity.sample.SelectCityActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private MainAdapter mAdapter;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        setToolbarTitle("Main", false);

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
        lists.add(new MainBean(CustomType.TYPE_18, "底部弹出Dialog"));
        lists.add(new MainBean(CustomType.TYPE_19, "雪花飘落效果"));
        lists.add(new MainBean(CustomType.TYPE_20, "通知栏"));
        lists.add(new MainBean(CustomType.TYPE_21, "CoordinatorLayout+Behavior实践滑动动画"));
        lists.add(new MainBean(CustomType.TYPE_22, "BottomNavigationView+Lottie实现底部带动画的导航栏"));
        lists.add(new MainBean(CustomType.TYPE_23, "粒子爆炸效果"));
        lists.add(new MainBean(CustomType.TYPE_24, "流式布局"));
        lists.add(new MainBean(CustomType.TYPE_25, "城市、联系人选择列表"));
        lists.add(new MainBean(CustomType.TYPE_26, "加载长图View"));
        lists.add(new MainBean(CustomType.TYPE_27, "下拉菜单"));
        return lists;
    }

    private void itemClick(BaseQuickAdapter adapter, View view, int position) {
        MainBean bean = (MainBean) adapter.getData().get(position);
        String title = bean.gettitle();
        switch (bean.getType()) {
            case CustomType.TYPE_0:
                startActivity(title, BankCardActivity.class);
                break;
            case CustomType.TYPE_1:
                startActivity(title, PayPsdViewActivity.class);
                break;
            case CustomType.TYPE_2:
                startActivity(title, WaveViewActivity.class);
                break;
            case CustomType.TYPE_3:
                startActivity(title, WaveView2Activity.class);
                break;
            case CustomType.TYPE_4:
                startActivity(title, LoadingActivity.class);
                break;
            case CustomType.TYPE_5:
                startActivity(title, NumberKeyboardActivity.class);
                break;
            case CustomType.TYPE_6:
                startActivity(title, PwdKeyboardActivity.class);
                break;
            case CustomType.TYPE_7:
                startActivity(title, CircleIndicatorActivity.class);
                break;
            case CustomType.TYPE_8:
                startActivity(title, TiltTextViewActivity.class);
                break;
            case CustomType.TYPE_9:
                startActivity(title, RollTextViewActivity.class);
                break;
            case CustomType.TYPE_10:
                startActivity(title, JDPullRefreshActivity.class);
                break;
            case CustomType.TYPE_11:
                startActivity(title, BubbleDrawViewActivity.class);
                break;
            case CustomType.TYPE_12:
                startActivity(title, RangeSeekBarActivity.class);
                break;
            case CustomType.TYPE_13:
                startActivity(title, ImagePreviewActivity.class);
                break;
            case CustomType.TYPE_14:
                startActivity(title, GiftActivity.class);
                break;
            case CustomType.TYPE_15:
                startActivity(title, RoundProgressBarActivity.class);
                break;
            case CustomType.TYPE_16:
                startActivity(title, ShadowImageViewActivity.class);
                break;
            case CustomType.TYPE_17:
                startActivity(title, PaletteImageViewActivity.class);
                break;
            case CustomType.TYPE_18:
                startActivity(title, BottomDialogActivity.class);
                break;
            case CustomType.TYPE_19:
                startActivity(title, FallViewActivity.class);
                break;
            case CustomType.TYPE_20:
                startActivity(title, NotificationActivity.class);
                break;
            case CustomType.TYPE_21:
                startActivity(title, BehaviorActivity.class);
                break;
            case CustomType.TYPE_22:
                startActivity(title, BottomNavActivity.class);
                break;
            case CustomType.TYPE_23:
                startActivity(title, ExplosionViewActivity.class);
                break;
            case CustomType.TYPE_24:
                startActivity(title, FlowLayoutActivity.class);
                break;
            case CustomType.TYPE_25:
                startActivity(title, SelectCityActivity.class);
                break;
            case CustomType.TYPE_26:
                startActivity(title, BigViewActivity.class);
                break;
            case CustomType.TYPE_27:
                startActivity(title, DropDownMenuActivity.class);
                break;
        }
    }

    private void startActivity(String title, Class clazz) {
        Intent intent = new Intent(MainActivity.this, clazz);
        intent.putExtra("title", title);
        startActivity(intent);
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
        static final int TYPE_25 = 25;
        static final int TYPE_26 = 26;
        static final int TYPE_27 = 27;
    }

}
