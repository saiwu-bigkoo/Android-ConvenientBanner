package com.bigkoo.convenientbanner;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
/**
 * Created by Sai on 15/7/29.
 */
public class CBPageAdapter extends PagerAdapter {
    private CBPageItemUpdateListener listener;
    private int pageViewsSize;
    public CBPageAdapter(int pageViewsSize){
        this.pageViewsSize = pageViewsSize;
    }
    @Override
    public int getCount() {
        return pageViewsSize;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (listener != null){
            View item = (View) listener.pageItemUpdate(container,position);
            container.addView(item);
            return item;
        }
        return null;
    }
    public void setListener(CBPageItemUpdateListener listener) {
        this.listener = listener;
    }

    public void setPageViewsSize(int pageViewsSize) {
        this.pageViewsSize = pageViewsSize;
    }
}
