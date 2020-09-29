package com.cxz.jd.pulltorefresh;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ScaleDrawable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author chenxz
 * @date 2019/3/18
 * @desc 仿京东下拉刷新
 */
public class JDPullToRefreshRecyclerView extends LinearLayout {
    public static final int STATUS_COMPLETE = 0;
    public static final int STATUS_PULL_TO_REFRESH = 1;
    public static final int STATUS_RELEASE_TO_REFRESH = 2;
    public static final int STATUS_REFRESHING = 3;
    private int mCurrentStatus = STATUS_PULL_TO_REFRESH;

    private RecyclerView mRecyclerView;
    private ImageView mPersonImg;
    private ImageView mBoxImg;
    private TextView mStatusText;
    private View mHeaderView;
    private int touchSlop;
    private boolean canPull;
    private ScaleDrawable mPersonDrawable;
    private ScaleDrawable mBoxDrawable;
    private boolean loadOnce;
    private int hideHeaderHeight;
    private MarginLayoutParams headerLayoutParams;
    private float mTouchY;
    private int lastStatus;
    private AnimationDrawable frameAnim;
    private PullToRefreshListener listener;

    public JDPullToRefreshRecyclerView(Context context) {
        this(context, null);
    }

    public JDPullToRefreshRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JDPullToRefreshRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_custom_ptr, this, true);
        mRecyclerView = findViewById(R.id.recycler_view);
        mHeaderView = findViewById(R.id.pull_to_refresh_header);
        mPersonImg = mHeaderView.findViewById(R.id.img_person);
        mPersonDrawable = (ScaleDrawable) mPersonImg.getDrawable();
        mBoxImg = mHeaderView.findViewById(R.id.img_box);
        mBoxDrawable = (ScaleDrawable) mBoxImg.getDrawable();
        mStatusText = mHeaderView.findViewById(R.id.text_status);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        initEvent();
    }

    private void initEvent() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int position = ((LinearLayoutManager) recyclerView.getLayoutManager())
                        .findFirstCompletelyVisibleItemPosition();
                // 当RecyclerView的第一项完全可见时，设置可下拉标志为true
                canPull = position == 0;
            }
        });
        onLevelChanged(0);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed && !loadOnce) {
            hideHeaderHeight = -mHeaderView.getHeight();
            headerLayoutParams = (MarginLayoutParams) mHeaderView.getLayoutParams();
            //初始隐藏headerView
            headerLayoutParams.topMargin = hideHeaderHeight;
            loadOnce = true;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float dy = event.getY() - mTouchY;
                //当RecyclerView移到顶部并且手指向下滑动时，拦截事件，由自己处理Touch事件
                if (dy > 0 && canPull)
                    return true;
            case MotionEvent.ACTION_UP:
            default:
                break;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float dy = event.getY() - mTouchY;
                mTouchY = event.getY();
                if (Math.abs(mTouchY) < touchSlop)
                    break;
                if (mCurrentStatus != STATUS_REFRESHING) {
                    //除以2是增加下拉的阻力感
                    headerLayoutParams.topMargin += dy / 2;
                    //此时为向上滑动到头部完全隐藏的情况，继续滑动的话应该交由RecyclerView处理了，
                    //故返回false自己不消耗事件。
                    if (headerLayoutParams.topMargin <= hideHeaderHeight) {
                        headerLayoutParams.topMargin = hideHeaderHeight;
                        mHeaderView.requestLayout();
                        return false;
                    }
                    //此时为头部完全显示的情况
                    if (headerLayoutParams.topMargin >= 0) {
                        mCurrentStatus = STATUS_RELEASE_TO_REFRESH;
                    } else {
                        mCurrentStatus = STATUS_PULL_TO_REFRESH;
                        //改变图片的大小及透明度
                        onLevelChanged((hideHeaderHeight - headerLayoutParams.topMargin) * 100 / hideHeaderHeight);
                    }
                    mHeaderView.requestLayout();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mCurrentStatus == STATUS_PULL_TO_REFRESH) {
                    //属性动画实现平滑滚动
                    headerAnimation(headerLayoutParams.topMargin, hideHeaderHeight, 350, null);
                } else if (mCurrentStatus == STATUS_RELEASE_TO_REFRESH) {
                    mCurrentStatus = STATUS_REFRESHING;
                    headerAnimation(headerLayoutParams.topMargin, 0, 250, null);
                    if (listener != null)
                        listener.onRefresh();
                }
                break;
        }
        updateHeaderView();
        lastStatus = mCurrentStatus;
        return super.onTouchEvent(event);
    }

    private void onLevelChanged(int progress) {
        mPersonDrawable.setLevel(progress * 10000 / 100);
        mPersonDrawable.setAlpha(progress * 255 / 100);
        mBoxDrawable.setLevel(progress * 10000 / 100);
        mBoxDrawable.setAlpha(progress * 255 / 100);
    }

    private void headerAnimation(int start, int end, int time, AnimatorListenerAdapter listener) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.setDuration(time);
        animator.start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                headerLayoutParams.topMargin = value;
                mHeaderView.requestLayout();
            }
        });
        if (listener != null)
            animator.addListener(listener);
    }

    private void updateHeaderView() {
        if (lastStatus != mCurrentStatus) {
            if (mCurrentStatus == STATUS_PULL_TO_REFRESH) {
                mStatusText.setText("下拉刷新...");
            } else if (mCurrentStatus == STATUS_RELEASE_TO_REFRESH) {
                mStatusText.setText("松开刷新...");
            } else if (mCurrentStatus == STATUS_REFRESHING) {
                mStatusText.setText("更新中...");
                mBoxImg.setImageDrawable(null);
                frameAnim = (AnimationDrawable) getResources().getDrawable(R.drawable.anim_drawable_jd);
                mPersonImg.setImageDrawable(frameAnim);
                frameAnim.start();
            } else if (mCurrentStatus == STATUS_COMPLETE) {
                mStatusText.setText("更新完成");
                frameAnim.stop();
            }
        }
    }

    public void setOnRefreshListener(PullToRefreshListener listener) {
        this.listener = listener;
    }

    public void onRefreshComplete() {
        mCurrentStatus = STATUS_COMPLETE;
        updateHeaderView();
        headerAnimation(headerLayoutParams.topMargin, hideHeaderHeight, 800, new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //更新完成后初始化Drawable
                onLevelChanged(0);
                mPersonImg.setImageDrawable(mPersonDrawable);
                mBoxImg.setImageDrawable(mBoxDrawable);
            }
        });
    }

    public void setLayoutManager(LinearLayoutManager manager) {
        mRecyclerView.setLayoutManager(manager);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration decoration) {
        mRecyclerView.addItemDecoration(decoration);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }

    public interface PullToRefreshListener {
        void onRefresh();
    }
}
