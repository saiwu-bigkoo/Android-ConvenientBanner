package com.bigkoo.convenientbanner;

import android.view.ViewGroup;

/**
 * Created by Sai on 15/7/30.
 * 如果是网络加载图片，则设置这个lister来加载
 */
public interface CBPageItemUpdateListener {
    public Object pageItemUpdate(ViewGroup container, int position);
}
