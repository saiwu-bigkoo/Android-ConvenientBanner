package com.bigkoo.convenientbanner.listener;


import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Sai on 2018/4/25.
 */

public interface OnPageChangeListener {
    void onScrollStateChanged(RecyclerView recyclerView, int newState);
    void onScrolled(RecyclerView recyclerView, int dx, int dy);
    void onPageSelected(int index);
}
