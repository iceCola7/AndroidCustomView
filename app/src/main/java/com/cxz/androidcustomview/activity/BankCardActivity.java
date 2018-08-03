package com.cxz.androidcustomview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.cxz.androidcustomview.R;
import com.cxz.androidcustomview.util.XLog;
import com.cxz.androidcustomview.widget.BankCardEditText;

public class BankCardActivity extends AppCompatActivity {

    private BankCardEditText et_bank_card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_card);

        et_bank_card = findViewById(R.id.et_bank_card);

    }
}
