package com.cxz.androidcustomview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.cxz.androidcustomview.R;
import com.github.ybq.android.spinkit.SpinKitView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by chenxz on 2017/12/5.
 */

public class EmptyLayout extends FrameLayout {

    public static final int STATUS_LOADING = 0x01;
    public static final int STATUS_NO_NET = 0x02;
    public static final int STATUS_NO_DATA = 0x03;
    public static final int STATUS_HIDE = 0x04;
    private Context mContext;
    private int mEmptyStatus = STATUS_LOADING;
    private int mBgColor;
    private OnRetryListener mOnRetryListener;

    TextView mTvEmptyMessage;
    View mRlEmptyContainer;
    SpinKitView mEmptyLoading;
    FrameLayout mEmptyLayout;

    public EmptyLayout(@NonNull Context context) {
        this(context, null);
    }

    public EmptyLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.EmptyLayout);
        try {
            mBgColor = a.getColor(R.styleable.EmptyLayout_background_color, Color.WHITE);
        } finally {
            a.recycle();
        }
        View view = View.inflate(mContext, R.layout.layout_empty, this);
        mTvEmptyMessage = view.findViewById(R.id.tv_net_error);
        mRlEmptyContainer = view.findViewById(R.id.rl_empty_container);
        mEmptyLoading = view.findViewById(R.id.empty_loading);
        mEmptyLayout = view.findViewById(R.id.empty_layout);

        mEmptyLayout.setBackgroundColor(mBgColor);
        switchEmptyView();

        mTvEmptyMessage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onRetryClick();
            }
        });
    }

    private void switchEmptyView() {
        switch (mEmptyStatus) {
            case STATUS_LOADING:
                setVisibility(VISIBLE);
                mRlEmptyContainer.setVisibility(GONE);
                mEmptyLoading.setVisibility(VISIBLE);
                break;
            case STATUS_NO_DATA:
            case STATUS_NO_NET:
                setVisibility(VISIBLE);
                mEmptyLoading.setVisibility(GONE);
                mRlEmptyContainer.setVisibility(VISIBLE);
                break;
            case STATUS_HIDE:
                setVisibility(GONE);
                break;
        }
    }

    private void onRetryClick() {
        if (mOnRetryListener != null) {
            mOnRetryListener.onRetry();
        }
    }

    /**
     * 隐藏
     */
    public void hide() {
        mEmptyStatus = STATUS_HIDE;
        switchEmptyView();
    }

    /**
     * 设置状态
     *
     * @param emptyStatus
     */
    public void setEmptyStatus(@EmptyStatus int emptyStatus) {
        mEmptyStatus = emptyStatus;
        switchEmptyView();
    }

    /**
     * 设置重试监听器
     *
     * @param onRetryListener
     */
    public void setOnRetryListener(OnRetryListener onRetryListener) {
        this.mOnRetryListener = onRetryListener;
    }

    /**
     * 重试的接口
     */
    public interface OnRetryListener {
        void onRetry();
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({STATUS_LOADING, STATUS_NO_NET, STATUS_NO_DATA})
    public @interface EmptyStatus {
    }
}
