package com.bigkoo.convenientbannerdemo;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.bigkoo.convenientbanner.holder.Holder;

@SuppressWarnings("WeakerAccess")
public class LocalImageHolderView implements Holder<Integer> {
    private AppCompatImageView imageView;

    @Override
    public View createView(Context context) {
        imageView = new AppCompatImageView(context);
        imageView.setScaleType(AppCompatImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public void updateUI(Context context, int position, Integer data) {
        imageView.setImageResource(data);
    }
}
