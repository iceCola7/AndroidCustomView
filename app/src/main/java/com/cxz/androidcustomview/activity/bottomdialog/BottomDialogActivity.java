package com.cxz.androidcustomview.activity.bottomdialog;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cxz.androidcustomview.R;
import com.cxz.dialoglib.bottom.BaseBottomDialog;
import com.cxz.dialoglib.bottomsheet.BaseBottomSheetFragment;

import java.util.ArrayList;
import java.util.List;

public class BottomDialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_dialog);

        findViewById(R.id.btn_share_bottom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ShareBottomDialog().show(getSupportFragmentManager());
            }
        });

        findViewById(R.id.btn_edit_bottom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new EditBottomDialog().show(getSupportFragmentManager());
            }
        });

        findViewById(R.id.btn_bottom_sheet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestBottomSheetDialog dialog = new TestBottomSheetDialog();
                dialog.show(getSupportFragmentManager(), "bottom_sheet_dialog");
            }
        });
    }

}