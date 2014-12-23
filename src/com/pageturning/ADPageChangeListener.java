package com.pageturning;

import java.util.ArrayList;

import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.ImageView;

/**
 * @ClassName :  ADpageListener 
 * @Description : 翻页控件的提示点切换监听器
 * @Author Sai
 */
public class ADPageChangeListener implements OnPageChangeListener {  
	  private ArrayList<ImageView> pointViews;
	  private int[] page_indicatorId;
	  public ADPageChangeListener(ArrayList<ImageView> pointViews,int page_indicatorId[]){
		  this.pointViews=pointViews;
		  this.page_indicatorId = page_indicatorId;
	  }
	  @Override  
	  public void onPageScrollStateChanged(int arg0) {  
	      // TODO Auto-generated method stub  

	  }  

	  @Override  
	  public void onPageScrolled(int arg0, float arg1, int arg2) {  
	      // TODO Auto-generated method stub  

	  }  

	  @Override  
	  public void onPageSelected(int index) {  
	      for (int i = 0; i < pointViews.size(); i++) {  
	    	  pointViews.get(index).setImageResource(page_indicatorId[1]);
	          if (index != i) {  
	        	  pointViews.get(i).setImageResource(page_indicatorId[0]);  
	          }  
	      }
	  }  
}
