package com.bigkoo.convenientbannerdemo;

import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;

/**
 * Created by Sai on 15/8/4.
 * 网络图片加载例子
 */
public class NetworkImageHolderView extends Holder<String> {
    private ImageView imageView;

    public NetworkImageHolderView(View itemView) {
        super(itemView);
    }

    @Override
    protected void initView(View itemView) {
        imageView =itemView.findViewById(R.id.ivPost);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
    }

    @Override
    public void updateUI(String data) {

    }
}
