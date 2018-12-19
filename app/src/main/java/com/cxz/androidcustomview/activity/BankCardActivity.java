package com.cxz.androidcustomview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.cxz.androidcustomview.R;
import com.cxz.androidcustomview.widget.AddSpaceTextWatcher;
import com.cxz.androidcustomview.widget.BankCardEditText;

public class BankCardActivity extends AppCompatActivity {

    private AddSpaceTextWatcher[] asTextWatchers = new AddSpaceTextWatcher[3];

    private BankCardEditText et_bank_card;
    private EditText et_bank_card2;
    private EditText et_id_card;
    private EditText et_mobile_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_card);

        et_bank_card = findViewById(R.id.et_bank_card);
        et_bank_card2 = findViewById(R.id.et_bank_card2);
        et_id_card = findViewById(R.id.et_id_card);
        et_mobile_phone = findViewById(R.id.et_mobile_phone);

        asTextWatchers[0] = new AddSpaceTextWatcher(et_bank_card2, 26);
        asTextWatchers[0].setSpaceType(AddSpaceTextWatcher.SpaceType.BANK_CARD_NUMBER_TYPE);

        asTextWatchers[1] = new AddSpaceTextWatcher(et_id_card, 21);
        asTextWatchers[1].setSpaceType(AddSpaceTextWatcher.SpaceType.ID_CARD_NUMBER_TYPE);

        asTextWatchers[2] = new AddSpaceTextWatcher(et_mobile_phone, 13);
        asTextWatchers[2].setSpaceType(AddSpaceTextWatcher.SpaceType.MOBILE_PHONE_NUMBER_TYPE);

    }
}
