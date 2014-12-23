package com.pageturning;

import java.lang.reflect.Field;
import java.util.ArrayList;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.PageTransformer;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * 页面翻转控件
 * 
 * @author Sai 支持自动翻页
 */
public class PageTurningView extends LinearLayout {
	private ArrayList<View> pageViews = new ArrayList<View>();
	private ArrayList<ImageView> mPointViews = new ArrayList<ImageView>();
	private ADPageChangeListener pageChangeListener;
	private ADPageAdapter pageAdapter;
	private ScrollViewPager viewPager;
	private ViewGroup loPageTurningPoint;
	private long autoTurningTime;
	private boolean turning;

	public enum Transformer {
		DefaultTransformer("DefaultTransformer"), AccordionTransformer(
				"AccordionTransformer"), BackgroundToForegroundTransformer(
				"BackgroundToForegroundTransformer"), CubeInTransformer(
				"CubeInTransformer"), CubeOutTransformer("CubeOutTransformer"), DepthPageTransformer(
				"DepthPageTransformer"), FlipHorizontalTransformer(
				"FlipHorizontalTransformer"), FlipVerticalTransformer(
				"FlipVerticalTransformer"), ForegroundToBackgroundTransformer(
				"ForegroundToBackgroundTransformer"), RotateDownTransformer(
				"RotateDownTransformer"), RotateUpTransformer(
				"RotateUpTransformer"), StackTransformer("StackTransformer"), TabletTransformer(
				"TabletTransformer"), ZoomInTransformer("ZoomInTransformer"), ZoomOutSlideTransformer(
				"ZoomOutSlideTransformer"), ZoomOutTranformer(
				"ZoomOutTranformer");

		private final String className;

		// 构造器默认也只能是private, 从而保证构造函数只能在内部使用
		Transformer(String className) {
			this.className = className;
		}

		public String getClassName() {
			return className;
		}
	}

	private Handler timeHandler = new Handler();
	private Runnable adSwitchTask = new Runnable() {
		@Override
		public void run() {
			if (viewPager != null && turning) {
				int page = viewPager.getCurrentItem() + 1;
				if (page == pageViews.size())
					viewPager.setCurrentItem(0);
				else
					viewPager.setCurrentItem(page);
				timeHandler.postDelayed(adSwitchTask, autoTurningTime);
			}

		}
	};

	public PageTurningView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		View hView = LayoutInflater.from(context).inflate(
				R.layout.include_viewpager, this, true);
		pageAdapter = new ADPageAdapter(pageViews);
		viewPager = (ScrollViewPager) hView.findViewById(R.id.viewPager);
		loPageTurningPoint = (ViewGroup) hView
				.findViewById(R.id.loPageTurningPoint);
		viewPager.setAdapter(pageAdapter);
		initViewPagerScroll();
	}

	/**
	 * 设置翻页的Items
	 * 
	 * @param itemViews
	 * @return
	 */
	public PageTurningView setItems(ArrayList<View> itemViews) {
		pageViews.clear();
		pageViews.addAll(itemViews);
		pageAdapter.notifyDataSetChanged();
		return this;
	}

	public void notifyDataSetChanged() {
		pageAdapter.notifyDataSetChanged();
	}

	/**
	 * 设置底部指示器是否可见
	 * 
	 * @param visible
	 */
	public PageTurningView setPointViewVisible(boolean visible) {
		loPageTurningPoint.setVisibility(visible ? View.VISIBLE : View.GONE);
		return this;
	}

	/**
	 * 底部指示器资源图片
	 * 
	 * @param page_indicatorId
	 */
	public PageTurningView setPageIndicator(int[] page_indicatorId) {
		for (int count = 0; count < pageViews.size(); count++) {
			// 翻页指示的点
			ImageView pointView = new ImageView(getContext());
			pointView.setPadding(5, 0, 5, 0);
			if (mPointViews.isEmpty())
				pointView.setImageResource(page_indicatorId[1]);
			else
				pointView.setImageResource(page_indicatorId[0]);
			mPointViews.add(pointView);
			loPageTurningPoint.addView(pointView);
		}
		pageChangeListener = new ADPageChangeListener(mPointViews,
				page_indicatorId);
		viewPager.setOnPageChangeListener(pageChangeListener);

		return this;
	}

	public PageTurningView startTurning(long autoTurningTime) {
		this.autoTurningTime = autoTurningTime;
		turning = true;
		timeHandler.postDelayed(adSwitchTask, autoTurningTime);
		return this;
	}

	public void stopTurning() {
		turning = false;
	}

	/**
	 * 自定义翻页动画效果
	 * 
	 * @param transformer
	 * @return
	 */
	public PageTurningView setPageTransformer(PageTransformer transformer) {
		viewPager.setPageTransformer(true, transformer);
		return this;
	}

	/**
	 * 自定义翻页动画效果，使用已存在的效果
	 * 
	 * @param transformer
	 * @return
	 */
	public PageTurningView setPageTransformer(Transformer transformer) {
		try {
			viewPager
					.setPageTransformer(
							true,
							(PageTransformer) Class.forName(
									"com.pageturning.transforms."
											+ transformer.getClassName())
									.newInstance());
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return this;
	}

	/**
	 * 设置ViewPager的滑动速度
	 * */
	private void initViewPagerScroll() {
		try {
			Field mScroller = null;
			mScroller = ViewPager.class.getDeclaredField("mScroller");
			mScroller.setAccessible(true);
			ViewPagerScroller scroller = new ViewPagerScroller(
					viewPager.getContext());
//			scroller.setScrollDuration(1500);
			mScroller.set(viewPager, scroller);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
