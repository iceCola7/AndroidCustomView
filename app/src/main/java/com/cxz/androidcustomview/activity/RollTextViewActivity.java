package com.cxz.androidcustomview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cxz.androidcustomview.R;
import com.cxz.androidcustomview.adapter.RollTextViewAdapter;
import com.cxz.androidcustomview.adapter.RollTextViewAdapter2;
import com.cxz.androidcustomview.bean.RollTextBean;
import com.cxz.roll.textview.RollTextView;

import java.util.ArrayList;
import java.util.List;

public class RollTextViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roll_text_view);

        List<RollTextBean> list = new ArrayList<>();
        list.add(new RollTextBean("1111111", "https://www.baidu.com"));
        list.add(new RollTextBean("2222222", "https://www.baidu.com"));
        list.add(new RollTextBean("3333333", "https://www.baidu.com"));
        list.add(new RollTextBean("4444444", "https://www.baidu.com"));
        list.add(new RollTextBean("5555555", "https://www.baidu.com"));

        RollTextViewAdapter adapter = new RollTextViewAdapter(list);
        final RollTextView roll_tv = findViewById(R.id.roll_text);
        roll_tv.setAdapter(adapter);

        List<RollTextBean> list02 = new ArrayList<>();
        list02.add(new RollTextBean("华为Mate新机预售", "https://www.baidu.com", "最新"));
        list02.add(new RollTextBean("欧洲物价最低国家", "https://www.baidu.com", "最热"));
        list02.add(new RollTextBean("别什么都往卧室里放", "https://www.baidu.com", "超赞"));
        list02.add(new RollTextBean("工作100小时赚70万", "https://www.baidu.com", "精华"));

        RollTextViewAdapter2 adapter02 = new RollTextViewAdapter2(list02);
        final RollTextView roll_tv02 = findViewById(R.id.roll_text02);
        roll_tv02.setAdapter(adapter02);

        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roll_tv.start();
                roll_tv02.start();
            }
        });
        findViewById(R.id.btn_stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roll_tv.stop();
                roll_tv02.stop();
            }
        });


    }
}
