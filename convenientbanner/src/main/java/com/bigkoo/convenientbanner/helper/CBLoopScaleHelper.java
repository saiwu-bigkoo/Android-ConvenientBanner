package com.bigkoo.convenientbanner.helper;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;

import com.bigkoo.convenientbanner.adapter.CBPageAdapter;
import com.bigkoo.convenientbanner.listener.OnPageChangeListener;
import com.bigkoo.convenientbanner.view.CBLoopViewPager;


/**
 * Created by jameson on 8/30/16.
 * changed by 二精-霁雪清虹 on 2017/11/19
 * changed by Sai on 2018/04/25
 */
public class CBLoopScaleHelper {
    private CBLoopViewPager mRecyclerView;

    private int mPagePadding = 0; // 卡片的padding, 卡片间的距离等于2倍的mPagePadding
    private int mShowLeftCardWidth = 0;   // 左边卡片显示大小

    private int mFirstItemPos;

    private CardLinearSnapHelper mLinearSnapHelper = new CardLinearSnapHelper();
    private OnPageChangeListener onPageChangeListener;

    public void attachToRecyclerView(final CBLoopViewPager mRecyclerView) {
        if (mRecyclerView == null) {
            return;
        }
        this.mRecyclerView = mRecyclerView;
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                // Log.e("TAG", "RecyclerView.OnScrollListener onScrollStateChanged");
                int position = getCurrentItem();
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    mLinearSnapHelper.mNoNeedToScroll = position == 0 ||
                            position == mRecyclerView.getAdapter().getItemCount() - 2;
                    if (mLinearSnapHelper.finalSnapDistance[0] == 0
                            && mLinearSnapHelper.finalSnapDistance[1] == 0) {
                        //Log.e("TAG", "滑动停止后最终位置为" + position);
                    }

                } else {
                    mLinearSnapHelper.mNoNeedToScroll = false;
                }
                //这里变换位置实现循环
                CBPageAdapter adapter = (CBPageAdapter) mRecyclerView.getAdapter();
                int count = adapter.getRealItemCount();
                if(adapter.isCanLoop()) {
                    if (position < count) {
                        position = count + position;
                        setCurrentItem(position);
                    } else if (position >= 2 * count) {
                        position = position - count;
                        setCurrentItem(position);
                    }
                }
                onPageChangeListener.onScrollStateChanged(recyclerView, newState);
                onPageChangeListener.onPageSelected(position%count);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                //Log.e("TAG", String.format("onScrolled dx=%s, dy=%s", dx, dy));
                super.onScrolled(recyclerView, dx, dy);
                onPageChangeListener.onScrolled(recyclerView, dx, dy);
                onScrolledChangedCallback();
            }
        });
        initWidth();
        mLinearSnapHelper.attachToRecyclerView(mRecyclerView);
    }

    /**
     * 初始化卡片宽度
     */
    private void initWidth() {
        mRecyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mRecyclerView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                scrollToPosition(mFirstItemPos);
            }
        });
    }

    public void setCurrentItem(int item) {
        setCurrentItem(item,false);
    }

    public void setCurrentItem(int item, boolean smoothScroll) {
        if (mRecyclerView == null) {
            return;
        }
        if (smoothScroll) {
            mRecyclerView.smoothScrollToPosition(item);
        }else {
            scrollToPosition(item);
        }
    }

    public void scrollToPosition(int pos) {
        if (mRecyclerView == null) {
            return;
        }
        ((LinearLayoutManager) mRecyclerView.getLayoutManager()).
                scrollToPositionWithOffset(pos,
                        (mPagePadding + mShowLeftCardWidth));
        //onScrolledChangedCallback();
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                onScrolledChangedCallback();
            }
        });
    }

    public void setFirstItemPos(int firstItemPos) {
        this.mFirstItemPos = firstItemPos;
    }


    /**
     * RecyclerView位移事件监听, view大小随位移事件变化
     */
    private void onScrolledChangedCallback() {

    }

    public int getCurrentItem() {
        return mRecyclerView.getLayoutManager().getPosition(mLinearSnapHelper.findSnapView(mRecyclerView.getLayoutManager()));
    }
    public int getRealCurrentItem() {
        CBPageAdapter adapter = (CBPageAdapter) mRecyclerView.getAdapter();
        int count = adapter.getRealItemCount();
        return mRecyclerView.getLayoutManager().getPosition(mLinearSnapHelper.findSnapView(mRecyclerView.getLayoutManager()))%count;
    }

    public void setPagePadding(int pagePadding) {
        mPagePadding = pagePadding;
    }

    public void setShowLeftCardWidth(int showLeftCardWidth) {
        mShowLeftCardWidth = showLeftCardWidth;
    }

    public int getFirstItemPos() {
        return mFirstItemPos;
    }

    public int getRealItemCount(){
        CBPageAdapter adapter = (CBPageAdapter) mRecyclerView.getAdapter();
        int count = adapter.getRealItemCount();
        return count;
    }

    /**
     * 防止卡片在第一页和最后一页因无法"居中"而一直循环调用onScrollStateChanged-->SnapHelper.snapToTargetExistingView-->onScrollStateChanged
     * Created by jameson on 9/3/16.
     */
    private static class CardLinearSnapHelper extends LinearSnapHelper {
        public boolean mNoNeedToScroll = false;
        public int[] finalSnapDistance = {0, 0};

        @Override
        public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View targetView) {
            //Log.e("TAG", "calculateDistanceToFinalSnap");
            if (mNoNeedToScroll) {
                finalSnapDistance[0] = 0;
                finalSnapDistance[1] = 0;
            } else {
                finalSnapDistance = super.calculateDistanceToFinalSnap(layoutManager, targetView);
            }
            return finalSnapDistance;
        }
    }

    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        this.onPageChangeListener = onPageChangeListener;
    }
}
