package com.bigkoo.convenientbanner.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.bigkoo.convenientbanner.R;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.view.CBLoopViewPager;

import java.util.LinkedList;
import java.util.List;

@SuppressWarnings({"WeakerAccess", "unchecked"})
public class CBPageAdapter<T> extends PagerAdapter {
    protected List<T> mDatas;
    protected CBViewHolderCreator holderCreator;
    private boolean canLoop = true;
    private CBLoopViewPager viewPager;
    private static final int MULTIPLE_COUNT = 300;
    private LinkedList<View> mViewCache;   // view cache

    private int mChildCount;
    private int mInvalidatedCount;

    public CBPageAdapter(CBViewHolderCreator holderCreator, List<T> datas) {
        this.holderCreator = holderCreator;
        this.mDatas = datas;
        this.mViewCache = new LinkedList<>();

        if (datas == null) {
            this.mChildCount = 0;
        } else {
            this.mChildCount = datas.size();
        }
        this.mInvalidatedCount = 0;
    }

    public int toRealPosition(int position) {
        final int realCount = getRealCount();
        if (realCount == 0) {
            return 0;
        }
        return position % realCount;
    }

    @Override
    public int getItemPosition(Object object) {
        if (mInvalidatedCount > 0) {
            mInvalidatedCount--;
            return POSITION_NONE;
        } else {
            return super.getItemPosition(object);
        }
    }

    @Override
    public void notifyDataSetChanged() {
        final int oldCount = mChildCount;
        final int newCount = getRealCount();
        if (oldCount == 0) {
            // Both children count 0, no change.
            if (newCount == 0) {
                return;
            }
            // Reset adapter. notifyDataSetChanged() is not working here
            else {
                viewPager.setAdapter(this);
            }
        } else {
            // call super notifyDataSetChanged()
            if (newCount == 0) {
                mInvalidatedCount = oldCount;
                super.notifyDataSetChanged();
            }
            // Both children are greater than 0. notifyDataSetChanged() is still not working at some time. We donnot know why.
            // So we call setAdapter() again.
            else {
                final int oldPos = viewPager.getCurrentItem();
                int newPos = oldPos % mChildCount;
                if (newPos == 0) {
                    newPos = getRealCount();
                } else if (newPos >= newCount) {
                    newPos = newCount - 1;
                }
                viewPager.setAdapter(this);
                viewPager.setCurrentItem(newPos, false);
            }
        }
        mChildCount = newCount;
    }

    @Override
    public int getCount() {
        return canLoop ? getRealCount() * MULTIPLE_COUNT : getRealCount();
    }

    public int getRealCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final int realPosition = toRealPosition(position);
        final View view = getView(realPosition, container);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        final View view = (View) object;
        container.removeView(view);
        this.mViewCache.add(view);
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        int position = viewPager.getCurrentItem();
        if (position == 0) {
            position = viewPager.getFirstItem();
        } else if (position == getCount() - 1) {
            position = viewPager.getLastItem();
        }
        if (getCount() > position) {
            viewPager.setCurrentItem(position, false);
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public void setCanLoop(boolean canLoop) {
        this.canLoop = canLoop;
    }

    public void setViewPager(CBLoopViewPager viewPager) {
        this.viewPager = viewPager;
    }

    public View getView(int position, ViewGroup container) {
        Holder holder;
        View view;
        if (mViewCache.isEmpty()) {
            holder = (Holder) holderCreator.createHolder();
            view = holder.createView(container.getContext());
            view.setTag(R.id.cb_item_tag, holder);
        } else {
            view = mViewCache.removeFirst();
            holder = (Holder<T>) view.getTag(R.id.cb_item_tag);
        }
        if (mDatas != null && !mDatas.isEmpty()) {
            holder.updateUI(container.getContext(), position, mDatas.get(position));
        }
        return view;
    }
}
