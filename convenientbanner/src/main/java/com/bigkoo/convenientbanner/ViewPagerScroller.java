package com.bigkoo.convenientbanner;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

public class ViewPagerScroller extends Scroller {
	private int mScrollDuration = 1200;// 滑动速度,值越大滑动越慢，滑动太快会使3d效果不明显

	public ViewPagerScroller(Context context) {
		super(context);
	}

	public ViewPagerScroller(Context context, Interpolator interpolator) {
		super(context, interpolator);
	}

	public ViewPagerScroller(Context context, Interpolator interpolator,
			boolean flywheel) {
		super(context, interpolator, flywheel);
	}

	@Override
	public void startScroll(int startX, int startY, int dx, int dy, int duration) {
		super.startScroll(startX, startY, dx, dy, mScrollDuration);
	}

	@Override
	public void startScroll(int startX, int startY, int dx, int dy) {
		super.startScroll(startX, startY, dx, dy, mScrollDuration);
	}

	public int getScrollDuration() {
		return mScrollDuration;
	}

	public void setScrollDuration(int scrollDuration) {
		this.mScrollDuration = scrollDuration;
	}
	
}