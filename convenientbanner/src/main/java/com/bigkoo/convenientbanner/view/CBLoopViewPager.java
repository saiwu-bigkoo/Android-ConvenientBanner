package com.bigkoo.convenientbanner.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.bigkoo.convenientbanner.adapter.CBPageAdapter;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;

public class CBLoopViewPager extends ViewPager {
    private OnPageChangeListener mOuterPageChangeListener;
    private OnItemClickListener onItemClickListener;
    private CBPageAdapter mAdapter;

    private boolean isCanScroll = true;
    private boolean canLoop = true;

    public void setAdapter(PagerAdapter adapter, boolean canLoop) {
        this.canLoop = canLoop;
        mAdapter = (CBPageAdapter) adapter;
        mAdapter.setCanLoop(canLoop);
        mAdapter.setViewPager(this);
        super.setAdapter(mAdapter);

        setCurrentItem(getFirstItem(), false);
    }

    public int getFirstItem() {
        return canLoop ? mAdapter.getRealCount() : 0;
    }

    public int getLastItem() {
        return mAdapter.getRealCount() - 1;
    }

    public boolean isCanScroll() {
        return isCanScroll;
    }

    public void setCanScroll(boolean isCanScroll) {
        this.isCanScroll = isCanScroll;
    }

    private float oldX = 0, newX = 0;
    private static final float SENS = 5;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //是否禁止手动滑动
        boolean isStopManualSliding = false;
        if (onItemClickListener != null) {
            switch (ev.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    oldX = ev.getX();
                    isStopManualSliding = !this.isCanScroll;
                    break;

                case MotionEvent.ACTION_MOVE:
                    isStopManualSliding = !this.isCanScroll;
                    break;

                case MotionEvent.ACTION_UP:
                    newX = ev.getX();
                    if (Math.abs(oldX - newX) < SENS) {
                        onItemClickListener.onItemClick((getRealItem()));
                    }
                    oldX = 0;
                    newX = 0;
                    isStopManualSliding = !this.canLoop && !isCanScroll;
                    break;

                case MotionEvent.ACTION_CANCEL:
                    isStopManualSliding = !this.canLoop && !isCanScroll;
                    break;

                default:
                    isStopManualSliding = !this.canLoop && !isCanScroll;
                    break;
            }
        }
        return isStopManualSliding || super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isCanScroll && super.onInterceptTouchEvent(ev);
    }

    @Override
    public CBPageAdapter getAdapter() {
        return mAdapter;
    }

    public int getRealItem() {
        return mAdapter != null ? mAdapter.toRealPosition(super.getCurrentItem()) : 0;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void setOnPageChangeListener(OnPageChangeListener listener) {
        mOuterPageChangeListener = listener;
    }

    public CBLoopViewPager(Context context) {
        super(context);
        init();
    }

    public CBLoopViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        super.setOnPageChangeListener(onPageChangeListener);
    }

    private OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {
        private float mPreviousPosition = -1;

        @Override
        public void onPageSelected(int position) {
            int realPosition = mAdapter.toRealPosition(position);
            if (mPreviousPosition != realPosition) {
                mPreviousPosition = realPosition;
                if (mOuterPageChangeListener != null) {
                    mOuterPageChangeListener.onPageSelected(realPosition);
                }
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {

            if (mOuterPageChangeListener != null) {
                if (position != mAdapter.getRealCount() - 1) {
                    mOuterPageChangeListener.onPageScrolled(position,
                            positionOffset, positionOffsetPixels);
                } else {
                    if (positionOffset > .5) {
                        mOuterPageChangeListener.onPageScrolled(0, 0, 0);
                    } else {
                        mOuterPageChangeListener.onPageScrolled(position, 0, 0);
                    }
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (mOuterPageChangeListener != null) {
                mOuterPageChangeListener.onPageScrollStateChanged(state);
            }
        }
    };

    public boolean isCanLoop() {
        return canLoop;
    }

    public void setCanLoop(boolean canLoop) {
        this.canLoop = canLoop;
        if (!canLoop) {
            setCurrentItem(getRealItem(), false);
        }
        if (mAdapter == null) {
            return;
        }
        mAdapter.setCanLoop(canLoop);
        mAdapter.notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
