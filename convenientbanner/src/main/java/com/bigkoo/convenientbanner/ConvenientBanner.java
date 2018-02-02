package com.bigkoo.convenientbanner;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.PageTransformer;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bigkoo.convenientbanner.adapter.CBPageAdapter;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.CBPageChangeListener;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bigkoo.convenientbanner.view.CBLoopViewPager;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 页面翻转控件，极方便的广告栏
 * 支持无限循环，自动翻页，翻页特效
 *
 * @author Sai 支持自动翻页
 */
@SuppressWarnings({"WeakerAccess", "FieldCanBeLocal"})
public class ConvenientBanner<T> extends RelativeLayout {
    private static final int START_TURN_CODE = 1;

    private List<T> mDatas;
    private int[] mPageIndicatorId;
    private ArrayList<AppCompatImageView> mPointViews = new ArrayList<>();
    private CBPageChangeListener pageChangeListener;
    private ViewPager.OnPageChangeListener onPageChangeListener;
    private CBPageAdapter pageAdapter;
    private CBLoopViewPager viewPager;
    private ViewPagerScroller scroller;
    private ViewGroup loPageTurningPoint;
    private long autoTurningTime;
    private boolean turning;
    private boolean canTurn = false;
    private boolean canLoop = true;

    public enum PageIndicatorAlign {
        ALIGN_PARENT_LEFT, ALIGN_PARENT_RIGHT, CENTER_HORIZONTAL
    }

    public ConvenientBanner(Context context) {
        super(context);
        init(context);
    }

    public ConvenientBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ConvenientBanner);
        canLoop = a.getBoolean(R.styleable.ConvenientBanner_canLoop, true);
        a.recycle();
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public ConvenientBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ConvenientBanner);
        canLoop = a.getBoolean(R.styleable.ConvenientBanner_canLoop, true);
        a.recycle();
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ConvenientBanner(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ConvenientBanner);
        canLoop = a.getBoolean(R.styleable.ConvenientBanner_canLoop, true);
        a.recycle();
        init(context);
    }

    private void init(Context context) {
        final View hView = LayoutInflater.from(context).inflate(
                R.layout.include_viewpager, this, true);
        viewPager = (CBLoopViewPager) hView.findViewById(R.id.cbLoopViewPager);
        loPageTurningPoint = (ViewGroup) hView
                .findViewById(R.id.loPageTurningPoint);
        initViewPagerScroll();
    }

    /**
     * 设置ViewPager的滑动速度
     */
    @SuppressWarnings("TryWithIdenticalCatches")
    private void initViewPagerScroll() {
        try {
            final Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            scroller = new ViewPagerScroller(
                    viewPager.getContext());
            mScroller.set(viewPager, scroller);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public ConvenientBanner setPages(CBViewHolderCreator holderCreator, List<T> datas) {
        this.mDatas = datas;
        pageAdapter = new CBPageAdapter(holderCreator, mDatas);
        canLoop = mDatas.size() > 1;
        viewPager.setAdapter(pageAdapter, canLoop);

        if (mPageIndicatorId != null) {
            setPageIndicator(mPageIndicatorId);
        }
        return this;
    }

    /**
     * 设置底部指示器是否可见
     */
    public ConvenientBanner setPointViewVisible(boolean visible) {
        loPageTurningPoint.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    /**
     * 底部指示器资源图片
     */
    public ConvenientBanner setPageIndicator(int[] pageIndicatorId) {
        loPageTurningPoint.removeAllViews();
        mPointViews.clear();
        this.mPageIndicatorId = pageIndicatorId;
        if (mDatas == null) {
            return this;
        }
        final int size = mDatas.size();
        //当图片数量不大于1时，不设置指示器
        if (size > 1) {
            for (int count = 0; count < size; count++) {
                // 翻页指示的点
                final AppCompatImageView pointView = new AppCompatImageView(getContext());
                pointView.setPadding(5, 0, 5, 0);
                if (mPointViews.isEmpty()) {
                    pointView.setImageResource(pageIndicatorId[1]);
                } else {
                    pointView.setImageResource(pageIndicatorId[0]);
                }
                mPointViews.add(pointView);
                loPageTurningPoint.addView(pointView);
            }
        }
        pageChangeListener = new CBPageChangeListener(mPointViews, pageIndicatorId);
        viewPager.setOnPageChangeListener(pageChangeListener);
        pageChangeListener.onPageSelected(viewPager.getRealItem());
        if (onPageChangeListener != null) {
            pageChangeListener.setOnPageChangeListener(onPageChangeListener);
        }
        return this;
    }

    /**
     * 指示器的方向
     *
     * @param align 三个方向：
     *              居左 （RelativeLayout.ALIGN_PARENT_LEFT），
     *              居中 （RelativeLayout.CENTER_HORIZONTAL），
     *              居右 （RelativeLayout.ALIGN_PARENT_RIGHT）
     */
    public ConvenientBanner setPageIndicatorAlign(PageIndicatorAlign align) {
        final RelativeLayout.LayoutParams layoutParams =
                (RelativeLayout.LayoutParams) loPageTurningPoint.getLayoutParams();
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT,
                align == PageIndicatorAlign.ALIGN_PARENT_LEFT ? RelativeLayout.TRUE : 0);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,
                align == PageIndicatorAlign.ALIGN_PARENT_RIGHT ? RelativeLayout.TRUE : 0);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL,
                align == PageIndicatorAlign.CENTER_HORIZONTAL ? RelativeLayout.TRUE : 0);
        loPageTurningPoint.setLayoutParams(layoutParams);
        return this;
    }

    /**
     * 自定义翻页动画效果
     */
    public ConvenientBanner setPageTransformer(PageTransformer transformer) {
        viewPager.setPageTransformer(true, transformer);
        return this;
    }

    public ConvenientBanner setManualPageable(boolean manualPageable) {
        viewPager.setCanScroll(manualPageable);
        return this;
    }

    /***
     * 开始翻页
     * @param autoTurningTime 自动翻页时间
     */
    public ConvenientBanner startTurning(long autoTurningTime) {
        this.autoTurningTime = autoTurningTime;
        //设置可以翻页并开启翻页
        canTurn = mDatas.size() > 1;
        //当设置循环变量及图片数量大于1时，进行循环轮播
        if (canLoop) {
            turning = true;
            if (myHandler.hasMessages(START_TURN_CODE)) {
                myHandler.removeMessages(START_TURN_CODE);
            }
            myHandler.sendEmptyMessageDelayed(START_TURN_CODE, autoTurningTime);
        }
        return this;
    }

    /**
     * 设置当前的页面index
     */
    public ConvenientBanner setCurrentItem(int index) {
        if (viewPager != null) {
            viewPager.setCurrentItem(index);
        }
        return this;
    }

    /**
     * 设置翻页监听器
     */
    public ConvenientBanner setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        this.onPageChangeListener = onPageChangeListener;
        //如果有默认的监听器（即是使用了默认的翻页指示器）则把用户设置的依附到默认的上面，否则就直接设置
        if (pageChangeListener != null) {
            pageChangeListener.setOnPageChangeListener(onPageChangeListener);
        } else {
            viewPager.setOnPageChangeListener(onPageChangeListener);
        }
        return this;
    }

    /**
     * 监听item点击
     */
    public ConvenientBanner setOnItemClickListener(OnItemClickListener onItemClickListener) {
        if (onItemClickListener == null) {
            viewPager.setOnItemClickListener(null);
            return this;
        }
        viewPager.setOnItemClickListener(onItemClickListener);
        return this;
    }

    /**
     * 设置ViewPager的滚动速度
     */
    public ConvenientBanner setScrollDuration(int scrollDuration) {
        scroller.setScrollDuration(scrollDuration);
        return this;
    }

    public void setCanLoop(boolean canLoop) {
        this.canLoop = canLoop;
        viewPager.setCanLoop(canLoop);
    }

    public void stopTurning() {
        turning = false;
        myHandler.removeMessages(START_TURN_CODE);
    }

    /**
     * 通知数据变化
     * 如果只是增加数据建议使用 notifyDataSetAdd()
     */
    public void notifyDataSetChanged() {
        viewPager.getAdapter().notifyDataSetChanged();
        if (mPageIndicatorId != null) {
            setPageIndicator(mPageIndicatorId);
        }
    }

    /***
     * 是否正在轮播中
     */
    public boolean isTurning() {
        return turning;
    }

    /**
     * 是否开启自动循环轮播
     */
    public boolean isCanLoop() {
        return viewPager.isCanLoop();
    }

    /**
     * 是否开启手动滑动翻页
     */
    public boolean isManualPageable() {
        return viewPager.isCanScroll();
    }

    /**
     * 获取当前item
     */
    public int getCurrentItem() {
        return viewPager != null ? viewPager.getRealItem() : -1;
    }

    /**
     * 获取滚动速度
     */
    public int getScrollDuration() {
        return scroller.getScrollDuration();
    }

    public CBLoopViewPager getViewPager() {
        return viewPager;
    }

    public ViewPager.OnPageChangeListener getOnPageChangeListener() {
        return onPageChangeListener;
    }

    private MyHandler myHandler = new MyHandler(this);

    private static class MyHandler extends Handler {
        private final WeakReference<ConvenientBanner> mWeakReference;

        public MyHandler(ConvenientBanner convenientBanner) {
            mWeakReference = new WeakReference<>(convenientBanner);
        }

        @Override
        public void handleMessage(Message msg) {
            final ConvenientBanner convenientBanner = mWeakReference.get();
            if (convenientBanner != null) {
                if (convenientBanner.viewPager != null && convenientBanner.turning) {
                    final int page = convenientBanner.viewPager.getCurrentItem() + 1;
                    convenientBanner.viewPager.setCurrentItem(page);
                    convenientBanner.myHandler.sendEmptyMessageDelayed(START_TURN_CODE, convenientBanner.autoTurningTime);
                }
            }
        }
    }

    //触碰控件的时候，翻页应该停止，离开的时候如果之前是开启了翻页的话则重新启动翻页
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        if (action == MotionEvent.ACTION_UP
                || action == MotionEvent.ACTION_CANCEL
                || action == MotionEvent.ACTION_OUTSIDE) {
            // 开始翻页
            if (canTurn) {
                startTurning(autoTurningTime);
            }
        } else if (action == MotionEvent.ACTION_DOWN) {
            // 停止翻页
            if (canTurn) {
                stopTurning();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (viewPager != null) {
            viewPager.requestLayout();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (myHandler != null) {
            myHandler.removeCallbacksAndMessages(null);
        }
        canLoop = false;
    }
}
