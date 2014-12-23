package com.pageturning;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 *@author Sai
 *Created on 2014年7月28日 下午2:22:03
 *类说明：首页广告adapter
 */
public class ADPageAdapter extends PagerAdapter{

	private ArrayList<View> pageViews;
	public ADPageAdapter(ArrayList<View> pageViews){
		this.pageViews=pageViews;
	}
	@Override  
    public int getCount() {  
        return pageViews.size();  
    }  

    @Override  
    public boolean isViewFromObject(View arg0, Object arg1) {  
        return arg0 == arg1;  
    }  

    @Override  
    public int getItemPosition(Object object) {  
        // TODO Auto-generated method stub  
        return super.getItemPosition(object);  
    }  

    @Override  
    public void destroyItem(View view, int index, Object arg2) {  
    	((ViewPager)view).removeView(pageViews.get(index));
    }  

    @Override  
    public Object instantiateItem(View view, int index) {  
        ((ViewPager) view).addView(pageViews.get(index));  
        return pageViews.get(index);  
    }  
}
