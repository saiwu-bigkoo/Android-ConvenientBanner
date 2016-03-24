package com.bigkoo.convenientbanner.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.bigkoo.convenientbanner.R;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.view.CBLoopViewPager;

import java.util.List;

/**
 * Created by Sai on 15/7/29.
 */
public class CBPageAdapter<T> extends PagerAdapter {
    protected List<T> mDatas;
    protected CBViewHolderCreator holderCreator;
//    private View.OnClickListener onItemClickListener;
    private boolean canLoop = true;
    private CBLoopViewPager viewPager;
    private final int MULTIPLE_COUNT = 300;

    private int mChildCount;
    private int mInvalidatedCount;

    public CBPageAdapter(CBViewHolderCreator holderCreator, List<T> datas) {
        this.holderCreator = holderCreator;
        this.mDatas = datas;
        if (datas == null) {
            this.mChildCount = 0;
        } else {
            this.mChildCount = datas.size();
        }
        this.mInvalidatedCount = 0;
    }

    public int toRealPosition(int position) {
        int realCount = getRealCount();
        if (realCount == 0)
            return 0;
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
        int oldCount = mChildCount;
        int newCount = getRealCount();
        if (oldCount == 0) {
            // Both child counts 0, no changes.
            if (newCount == 0) {
                return;
            }
            else {
                viewPager.setAdapter(this);
            }
        } else {
            if (newCount == 0) {
                mInvalidatedCount = oldCount;
                super.notifyDataSetChanged();
            }
            else {
                int oldPos = viewPager.getCurrentItem();
                int newPos = oldPos % mChildCount;
                if (newPos == 0) {
                    newPos = getRealCount();
                }
                else if (newPos >= newCount) {
                    newPos = newCount - 1;
                }
                viewPager.setAdapter(this);
                viewPager.setCurrentItem(newPos, false);
            }
        }
        mChildCount = getRealCount();
    }

    @Override
    public int getCount() {
        return canLoop ? getRealCount()*MULTIPLE_COUNT : getRealCount();
    }

    public int getRealCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int realPosition = toRealPosition(position);

        View view = getView(realPosition, null, container);
//        if(onItemClickListener != null) view.setOnClickListener(onItemClickListener);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        int position = viewPager.getCurrentItem();
        if (position == 0) {
            position = viewPager.getFristItem();
        } else if (position == getCount() - 1) {
            position = viewPager.getLastItem();
        }
        try {
            viewPager.setCurrentItem(position, false);
        }catch (IllegalStateException e){}
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

    public View getView(int position, View view, ViewGroup container) {
        Holder holder = null;
        if (view == null) {
            holder = (Holder) holderCreator.createHolder();
            view = holder.createView(container.getContext());
            view.setTag(R.id.cb_item_tag, holder);
        } else {
            holder = (Holder<T>) view.getTag(R.id.cb_item_tag);
        }
        if (mDatas != null && !mDatas.isEmpty())
            holder.UpdateUI(container.getContext(), position, mDatas.get(position));
        return view;
    }

//    public void setOnItemClickListener(View.OnClickListener onItemClickListener) {
//        this.onItemClickListener = onItemClickListener;
//    }
}
