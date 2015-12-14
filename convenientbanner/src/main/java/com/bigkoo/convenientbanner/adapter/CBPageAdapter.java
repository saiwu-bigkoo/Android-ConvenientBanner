package com.bigkoo.convenientbanner.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.bigkoo.convenientbanner.R;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.salvage.RecyclingPagerAdapter;

import java.util.List;

/**
 * Created by Sai on 15/7/29.
 */
public class CBPageAdapter<T> extends RecyclingPagerAdapter{
    protected List<T> mDatas;
    protected CBViewHolderCreator holderCreator;
    private View.OnClickListener onItemClickListener;

    public CBPageAdapter(CBViewHolderCreator holderCreator,List<T> datas) {
        this.holderCreator = holderCreator;
        this.mDatas = datas;
    }

    @Override public View getView(int position, View view, ViewGroup container) {
        Holder holder = null;
        if (view == null) {
            holder = (Holder) holderCreator.createHolder();
            view = holder.createView(container.getContext());
            view.setTag(R.id.cb_item_tag,holder);
        } else {
            holder = (Holder<T>) view.getTag(R.id.cb_item_tag);
        }
        if(onItemClickListener != null) view.setOnClickListener(onItemClickListener);
        if(mDatas!=null&&!mDatas.isEmpty())
        holder.UpdateUI(container.getContext(), position, mDatas.get(position));
        return view;
    }

    @Override public int getCount() {
        if(mDatas==null)return 0;
        return mDatas.size();
    }



    public void setOnItemClickListener(View.OnClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
