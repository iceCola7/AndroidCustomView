package com.cxz.androidcustomview.activity;

import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import com.cxz.androidcustomview.R;
import com.cxz.androidcustomview.base.BaseActivity;
import com.cxz.numkeyborad.VirtualKeyboardView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;

public class NumberKeyboardActivity extends BaseActivity {

    private VirtualKeyboardView virtualKeyboardView;
    private GridView gridView;
    private ArrayList<Map<String, String>> valueList;
    private EditText editText;
    private Animation enterAnim;
    private Animation exitAnim;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_number_keyboard;
    }

    @Override
    protected void initView() {
        String title = getIntent().getStringExtra("title");
        setToolbarTitle(title);

        initAnim();

        editText = findViewById(R.id.editText);

        // 设置不调用系统键盘
        if (android.os.Build.VERSION.SDK_INT <= 10) {
            editText.setInputType(InputType.TYPE_NULL);
        } else {
            this.getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            try {
                Class<EditText> cls = EditText.class;
                Method setShowSoftInputOnFocus;
                setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus",
                        boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(editText, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        virtualKeyboardView = (VirtualKeyboardView) findViewById(R.id.virtualKeyboardView);
        virtualKeyboardView.getLayoutBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                virtualKeyboardView.startAnimation(exitAnim);
                virtualKeyboardView.setVisibility(View.GONE);
            }
        });

        gridView = virtualKeyboardView.getGridView();
        gridView.setOnItemClickListener(onItemClickListener);

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                virtualKeyboardView.setFocusable(true);
                virtualKeyboardView.setFocusableInTouchMode(true);

                virtualKeyboardView.startAnimation(enterAnim);
                virtualKeyboardView.setVisibility(View.VISIBLE);
            }
        });

        valueList = virtualKeyboardView.getValueList();
    }

    /**
     * 数字键盘显示动画
     */
    private void initAnim() {
        enterAnim = AnimationUtils.loadAnimation(this, R.anim.push_bottom_in);
        exitAnim = AnimationUtils.loadAnimation(this, R.anim.push_bottom_out);
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            if (position < 11 && position != 9) { //点击0~9按钮

                String amount = editText.getText().toString().trim();
                amount = amount + valueList.get(position).get("name");
                int index = editText.getSelectionStart();
                if (index == amount.length() - 1) {
                    editText.setText(amount);
                    Editable ea = editText.getText();
                    editText.setSelection(ea.length());
                } else {
                    Editable editable = editText.getText();
                    editable.insert(index, valueList.get(position).get("name"));
                }

            } else {

                if (position == 9) {      //点击退格键
                    String amount = editText.getText().toString().trim();
                    int index = editText.getSelectionStart();
                    if (index == amount.length() - 1) {
                        amount = amount + valueList.get(position).get("name");
                        editText.setText(amount);
                        Editable ea = editText.getText();
                        editText.setSelection(ea.length());
                    } else {
                        Editable editable = editText.getText();
                        editable.insert(index, valueList.get(position).get("name"));
                    }
                }

                if (position == 11) {      //点击退格键
                    String amount = editText.getText().toString().trim();
                    if (amount.length() > 0) {
                        int index = editText.getSelectionStart();
                        if (index == amount.length()) {
                            amount = amount.substring(0, amount.length() - 1);
                            editText.setText(amount);
                            Editable ea = editText.getText();
                            editText.setSelection(ea.length());
                        } else {
                            if (index != 0) {
                                Editable editable = editText.getText();
                                editable.delete(index - 1, index);
                            }
                        }

                    }
                }
            }
        }
    };

}
