package com.cxz.androidcustomview.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

import com.cxz.androidcustomview.R;
import com.cxz.androidcustomview.widget.PayPsdInputView;

public class PayPsdViewActivity extends AppCompatActivity {

    private PayPsdInputView payPsdInputView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_psd_view);

        payPsdInputView = findViewById(R.id.password);

        payPsdInputView.setComparePassword(new PayPsdInputView.onPasswordListener() {

            @Override
            public void onDifference(String oldPsd, String newPsd) {
                //  和上次输入的密码不一致  做相应的业务逻辑处理
                Toast.makeText(PayPsdViewActivity.this, "两次密码输入不同" + oldPsd + "!=" + newPsd, Toast.LENGTH_SHORT).show();
                payPsdInputView.cleanPsd();
            }

            @Override
            public void onEqual(String psd) {
                // 两次输入密码相同，那就去进行支付楼
                Toast.makeText(PayPsdViewActivity.this, "密码相同" + psd, Toast.LENGTH_SHORT).show();
                payPsdInputView.setComparePassword("");
                payPsdInputView.cleanPsd();
            }

            @Override
            public void onFinished(String inputPsd) {
                // 输完逻辑
                Toast.makeText(PayPsdViewActivity.this, "输入完毕：" + inputPsd, Toast.LENGTH_SHORT).show();
                payPsdInputView.setComparePassword(inputPsd);
            }
        });

    }
}
