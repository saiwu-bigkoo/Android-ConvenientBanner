package com.bigkoo.convenientbanner.listener;

import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Sai on 15/7/29.
 * 翻页指示器适配器
 */
public class CBPageChangeListener implements ViewPager.OnPageChangeListener {
    private ArrayList<ImageView> pointViews;
    private int[] mPageIndicatorId;
    private ViewPager.OnPageChangeListener onPageChangeListener;

    public CBPageChangeListener(ArrayList<ImageView> pointViews, int[] pageIndicatorId) {
        this.pointViews = pointViews;
        this.mPageIndicatorId = pageIndicatorId;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (onPageChangeListener != null) {
            onPageChangeListener.onPageScrollStateChanged(state);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (onPageChangeListener != null) {
            onPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int index) {
        final int size = pointViews.size();
        for (int i = 0; i < size; i++) {
            pointViews.get(index).setImageResource(mPageIndicatorId[1]);
            if (index != i) {
                pointViews.get(i).setImageResource(mPageIndicatorId[0]);
            }
        }
        if (onPageChangeListener != null) {
            onPageChangeListener.onPageSelected(index);
        }

    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        this.onPageChangeListener = onPageChangeListener;
    }
}
