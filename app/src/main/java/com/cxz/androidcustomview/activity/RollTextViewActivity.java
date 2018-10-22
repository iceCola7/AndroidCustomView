package com.cxz.androidcustomview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cxz.androidcustomview.R;
import com.cxz.androidcustomview.adapter.RollTextViewAdapter;
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
        list.add(new RollTextBean("1111111", "---->>>>1111111"));
        list.add(new RollTextBean("2222222", "---->>>>2222222"));
        list.add(new RollTextBean("3333333", "---->>>>3333333"));
        list.add(new RollTextBean("4444444", "---->>>>4444444"));
        list.add(new RollTextBean("5555555", "---->>>>5555555"));

        RollTextViewAdapter adapter = new RollTextViewAdapter(list);
        final RollTextView roll_tv = findViewById(R.id.roll_text);
        roll_tv.setAdapter(adapter);

        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roll_tv.start();
            }
        });
        findViewById(R.id.btn_stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roll_tv.stop();
            }
        });

    }
}
