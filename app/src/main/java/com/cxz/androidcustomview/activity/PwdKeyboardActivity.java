package com.cxz.androidcustomview.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.cxz.androidcustomview.R;
import com.cxz.numkeyborad.PopEnterPassword;

public class PwdKeyboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd_keyboard);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopEnterPassword popEnterPassword = new PopEnterPassword(PwdKeyboardActivity.this);
                // 显示窗口
                popEnterPassword.showAtLocation(PwdKeyboardActivity.this.findViewById(R.id.content),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
            }
        });

    }
}
