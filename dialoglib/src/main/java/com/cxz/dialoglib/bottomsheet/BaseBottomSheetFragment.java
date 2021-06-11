package com.cxz.dialoglib.bottomsheet;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;

import com.cxz.dialoglib.R;
import com.cxz.dialoglib.utils.CommonUtils;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

/**
 * @author chenxz
 * @date 2019/3/19
 * @desc
 */
public abstract class BaseBottomSheetFragment extends BottomSheetDialogFragment {

    private View sheetTouchOutsideContainer;
    private CornerRadiusFrameLayout sheetContainer;
    private BottomSheetBehavior behavior;

    // Customizable properties
    private float propertyDim = 0f;
    private float propertyCornerRadius = 0f;
    private int propertyStatusBarColor = 0;
    private boolean propertyIsAlwaysExpanded = false;
    private boolean propertyIsSheetCancelableOnTouchOutside = true;
    private boolean propertyIsSheetCancelable = true;
    private boolean propertyAnimateCornerRadius = true;

    // Bottom sheet properties
    private boolean canSetStatusBarColor = false;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (animateStatusBar()) {
            return new BaseBottomSheetDialog(getContext(), R.style.BottomSheetDialog);
        }
        return new BaseBottomSheetDialog(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        boolean supportsStatusBarColor = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
        canSetStatusBarColor = !CommonUtils.isTablet() && supportsStatusBarColor;

        // Init properties
        propertyDim = getDim();
        propertyCornerRadius = getCornerRadius();
        propertyStatusBarColor = getStatusBarColor();
        propertyIsAlwaysExpanded = isSheetAlwaysExpanded();
        propertyIsSheetCancelable = isSheetCancelable();
        propertyIsSheetCancelableOnTouchOutside = isSheetCancelableOnTouchOutside();
        propertyAnimateCornerRadius = animateCornerRadius();

        if (getDialog() != null) {
            getDialog().setCancelable(propertyIsSheetCancelable);
            boolean isCancelableOnTouchOutside = propertyIsSheetCancelable && propertyIsSheetCancelableOnTouchOutside;
            getDialog().setCanceledOnTouchOutside(isCancelableOnTouchOutside);
        }

        if (getDialog() != null && getDialog().getWindow() != null) {
            Window window = getDialog().getWindow();
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setDimAmount(propertyDim);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                setStatusBarColor(1f);
            }

            if (CommonUtils.isTablet() && getContext() != null && !CommonUtils.isInPortrait(getContext())) {
                window.setGravity(Gravity.CENTER_HORIZONTAL);
                window.setLayout(getResources().getDimensionPixelSize(R.dimen.base_bottom_sheet_width), ViewGroup.LayoutParams.WRAP_CONTENT);
            }

        }

        return attachView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();

        iniBottomSheetUiComponents();

    }

    private void iniBottomSheetUiComponents() {
        // Store views references
        sheetContainer = getDialog().findViewById(R.id.bottom_sheet);
        sheetTouchOutsideContainer = getDialog().findViewById(R.id.touch_outside);

        // Set the bottom sheet radius
        sheetContainer.setBackgroundColor(getBackgroundColor());
        sheetContainer.setCornerRadius(propertyCornerRadius);

        // Load bottom sheet behaviour
        behavior = BottomSheetBehavior.from(sheetContainer);

        if (CommonUtils.isTablet() && getContext() != null && CommonUtils.isInPortrait(getContext())) {
            ViewGroup.LayoutParams layoutParams = sheetContainer.getLayoutParams();
            layoutParams.width = getResources().getDimensionPixelSize(R.dimen.base_bottom_sheet_width);
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            sheetContainer.setLayoutParams(layoutParams);
        }

        // If is always expanded, there is no need to set the peek height
        if (!propertyIsAlwaysExpanded) {
            behavior.setPeekHeight(getPeekHeight());
            sheetContainer.setMinimumHeight(behavior.getPeekHeight());
        } else {
            ViewGroup.LayoutParams layoutParams = sheetContainer.getLayoutParams();
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            sheetContainer.setLayoutParams(layoutParams);
        }

        // Only skip the collapse state when the device is in landscape or the sheet is always expanded
        boolean deviceInLandscape = (!CommonUtils.isTablet() && !CommonUtils.isInPortrait(getContext())) || propertyIsAlwaysExpanded;
        behavior.setSkipCollapsed(deviceInLandscape);

        if (deviceInLandscape) {
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            setStatusBarColor(1);

            // Load content container height
            sheetContainer.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    if (sheetContainer.getHeight() > 0) {
                        sheetContainer.getViewTreeObserver().removeOnPreDrawListener(this);

                        // If the content sheet is expanded set the background and status bar properties
                        if (sheetContainer.getHeight() == sheetTouchOutsideContainer.getHeight()) {
                            setStatusBarColor(0);
                            if (propertyAnimateCornerRadius) {
                                sheetContainer.setCornerRadius(0f);
                            }
                        }
                    }
                    return true;
                }
            });
        }

        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                if (i == BottomSheetBehavior.STATE_HIDDEN) {
                    setStatusBarColor(1f);
                    getDialog().cancel();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                setRoundedCornersOnScroll(bottomSheet, slideOffset);
                setStatusBarColorOnScroll(bottomSheet, slideOffset);
            }
        });

    }

    @UiThread
    private void setStatusBarColorOnScroll(View bottomSheet, Float slideOffset) {
        if (!canSetStatusBarColor) {
            return;
        }

        if (bottomSheet.getHeight() != sheetTouchOutsideContainer.getHeight()) {
            canSetStatusBarColor = false;
            return;
        }

        if (slideOffset.isNaN() || slideOffset <= 0) {
            setStatusBarColor(1f);
            return;
        }

        float invertOffset = 1 - (1 * slideOffset);
        setStatusBarColor(invertOffset);
    }

    @UiThread
    private void setRoundedCornersOnScroll(View bottomSheet, Float slideOffset) {
        if (!propertyAnimateCornerRadius) {
            return;
        }

        if (bottomSheet.getHeight() != sheetTouchOutsideContainer.getHeight()) {
            propertyAnimateCornerRadius = false;
            return;
        }

        if (slideOffset.isNaN() || slideOffset <= 0) {
            sheetContainer.setCornerRadius(propertyCornerRadius);
            return;
        }

        if (propertyAnimateCornerRadius) {
            float radius = propertyCornerRadius - (propertyCornerRadius * slideOffset);
            sheetContainer.setCornerRadius(radius);
        }
    }

    @UiThread
    private void setStatusBarColor(float dim) {
        if (!canSetStatusBarColor) {
            return;
        }
        int color = CommonUtils.calculateColor(propertyStatusBarColor, dim);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (getDialog() != null && getDialog().getWindow() != null) {
                getDialog().getWindow().setStatusBarColor(color);
            }
        }
    }

    protected abstract View attachView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    protected int getPeekHeight() {
        int peekHeightMin = getResources().getDimensionPixelSize(R.dimen.base_bottom_sheet_peek_height);
        int heightPixels = getResources().getDisplayMetrics().heightPixels;
        return Math.max(peekHeightMin, heightPixels - heightPixels * 9 / 16);
    }

    @ColorInt
    protected int getBackgroundColor() {
        return Color.WHITE;
    }

    protected boolean animateCornerRadius() {
        return true;
    }

    protected boolean isSheetCancelableOnTouchOutside() {
        return true;
    }

    protected boolean isSheetCancelable() {
        return true;
    }

    protected boolean isSheetAlwaysExpanded() {
        return false;
    }

    @ColorInt
    protected int getStatusBarColor() {
        if (getContext() != null)
            return CommonUtils.getColorPrimary(getContext());
        return -1;
    }

    protected float getCornerRadius() {
        return getResources().getDimensionPixelSize(R.dimen.base_bottom_sheet_radius);
    }

    protected float getDim() {
        return 0.6F;
    }

    protected boolean animateStatusBar() {
        return true;
    }

}
