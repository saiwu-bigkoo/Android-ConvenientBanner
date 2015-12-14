package com.bigkoo.convenientbannerdemo.recyclerviewdemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bigkoo.convenientbannerdemo.R;

import java.util.List;

/**
 * Created by Sai on 15/8/13.
 */
public class RecyclerViewHFAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;

    private static final int TYPE_FOOTER = 1;

    private static final int TYPE_ITEM = 2;

    private static final int TYPE_EMPTY = 3;

    private View mHeaderView;

    private View mFooterView;

    private View mEmptyView;

    private List<String> items;

    public RecyclerViewHFAdapter(List<String> data) {
        this.items = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_transformer, parent, false);
            return new ItemHolder(v);
        } else if (viewType == TYPE_HEADER) {
            View v = mHeaderView;
            return new HeaderHolder(v);
        } else if (viewType == TYPE_FOOTER) {
            View v = mFooterView;
            return new FooterHolder(v);
        } else if (viewType == TYPE_EMPTY) {
            View v = mEmptyView;
            return new EmptyHolder(v);
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemHolder) {
            String dataItem = getItem(position);
            ((ItemHolder) holder).mTextView.setText(dataItem);
        } else if (holder instanceof HeaderHolder) {

        } else if (holder instanceof FooterHolder) {

        } else if (holder instanceof EmptyHolder) {

        }
    }

    @Override
    public int getItemCount() {

        int count;
        int size = items.size();
        if (size == 0 && null != mEmptyView) {
            count = 1;
        } else {
            count = getHeadViewSize() + size + getFooterViewSize();
        }
        return count;

    }

    @Override
    public int getItemViewType(int position) {
        int size = items.size();
        if (size == 0 && null != mEmptyView) {
            return TYPE_EMPTY;
        } else if (position < getHeadViewSize()) {
            return TYPE_HEADER;
        } else if (position >= getHeadViewSize() + items.size()) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    private int getHeadViewSize() {
        return mHeaderView == null ? 0 : 1;
    }

    private int getFooterViewSize() {
        return mFooterView == null ? 0 : 1;
    }

    private String getItem(int position) {
        return items.get(position - getHeadViewSize());
    }


    //add a header to the adapter
    public void addHeader(View header) {
        mHeaderView = header;
        notifyItemInserted(0);
    }

    //remove a header from the adapter
    public void removeHeader(View header) {
        notifyItemRemoved(0);
        mHeaderView = null;
    }

    //add a footer to the adapter
    public void addFooter(View footer) {
        mFooterView = footer;
        notifyItemInserted(getHeadViewSize() + items.size());
    }

    //remove a footer from the adapter
    public void removeFooter(View footer) {
        notifyItemRemoved(getHeadViewSize() + items.size());
        mFooterView = null;
    }

    //add data
    public void addDatas(List<String> data) {
        items.addAll(data);
        notifyItemInserted(getHeadViewSize() + items.size() - 1);
    }

    //add data
    public void addData(String data) {
        items.add(data);
        notifyItemInserted(getHeadViewSize() + items.size() - 1);
    }

    //refresh data
    public void refreshData(List<String> datas){
        items.clear();
        addDatas(datas);
    }

    public void setEmptyView(View emptyView) {
        mEmptyView = emptyView;
        notifyItemInserted(0);
    }


    class ItemHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        public ItemHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView;
        }
    }

    class HeaderHolder extends RecyclerView.ViewHolder {

        public HeaderHolder(View itemView) {
            super(itemView);
        }
    }

    class FooterHolder extends RecyclerView.ViewHolder {
        public FooterHolder(View itemView) {
            super(itemView);
        }
    }

    class EmptyHolder extends RecyclerView.ViewHolder {
        public EmptyHolder(View itemView) {
            super(itemView);
        }
    }
}
