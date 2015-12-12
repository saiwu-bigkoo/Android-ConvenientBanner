package com.bigkoo.convenientbanner.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.bigkoo.convenientbanner.R;

import java.util.List;

/**
 * Created by Sai on 15/7/29.
 */
public class CBPageAdapter<T> extends PagerAdapter {
    protected List<T> mDatas;
    protected CBViewHolderCreator holderCreator;
    //保存被销毁了的View
    private SparseArray<ToDestroy> mToDestroy = new SparseArray<ToDestroy>();
    private View.OnClickListener onItemClickListener;
    private boolean canLoop = true;

    @Override
    public void notifyDataSetChanged() {
        mToDestroy.clear();
        super.notifyDataSetChanged();
    }

    public int toRealPosition(int position) {
        int realCount = getRealCount();
        if (realCount == 0)
            return 0;
        int realPosition = position % realCount;
        return realPosition;
    }

    @Override
    public int getCount() {
        return canLoop ? Integer.MAX_VALUE : getRealCount();
    }

    public int getRealCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int realPosition = toRealPosition(position);
        ToDestroy toDestroy = mToDestroy.get(realPosition);
        if (toDestroy != null) {
            if (toDestroy.view.getParent() != null)
                container.removeView(toDestroy.view);
            container.addView(toDestroy.view);
            return toDestroy.view;
        }
        View view = getView(realPosition, null, container);
        if(onItemClickListener != null) view.setOnClickListener(onItemClickListener);
        container.addView(view);
        mToDestroy.put(realPosition, new ToDestroy(view));
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public void setCanLoop(boolean canLoop) {
        this.canLoop = canLoop;
    }

    /**
     * Container class for caching the boundary views
     */
    static class ToDestroy {
        View view;

        public ToDestroy(View view) {
            this.view = view;
        }
    }

    public CBPageAdapter(CBViewHolderCreator holderCreator, List<T> datas) {
        this.holderCreator = holderCreator;
        this.mDatas = datas;
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


    /**
     * @param <T> 任何你指定的对象
     */
    public interface Holder<T> {
        View createView(Context context);

        void UpdateUI(Context context, int position, T data);
    }

    public void setOnItemClickListener(View.OnClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}