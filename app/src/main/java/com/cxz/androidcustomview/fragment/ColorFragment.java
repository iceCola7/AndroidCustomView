package com.cxz.androidcustomview.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cxz.androidcustomview.R;

/**
 * @author chenxz
 * @date 2018/8/27
 * @desc
 */
public class ColorFragment extends Fragment {


    private static final String ARG_COLOR = "color";

    private int mColor;

    public static ColorFragment newInstance(int param1) {
        ColorFragment fragment = new ColorFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLOR, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public ColorFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColor = getArguments().getInt(ARG_COLOR);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_color, container, false);

        v.setBackgroundColor(mColor);

        return v;
    }

}
