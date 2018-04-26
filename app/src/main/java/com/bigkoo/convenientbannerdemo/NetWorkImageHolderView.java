package com.bigkoo.convenientbannerdemo;

import android.graphics.drawable.Animatable;
import android.view.View;

import com.bigkoo.convenientbanner.holder.Holder;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.imagepipeline.image.ImageInfo;

import me.relex.photodraweeview.OnViewTapListener;
import me.relex.photodraweeview.PhotoDraweeView;

/**
 * Created by Sai on 15/8/4.
 * 网络图片加载例子
 */
public class NetWorkImageHolderView extends Holder<String>{
    private PhotoDraweeView ivPost;

    public NetWorkImageHolderView(View itemView, OnViewTapListener onViewTapListener) {
        super(itemView);
        ivPost.setOnViewTapListener(onViewTapListener);
    }

    @Override
    protected void initView(View itemView) {
        ivPost =itemView.findViewById(R.id.ivPost);
    }

    @Override
    public void updateUI(String data) {
        PipelineDraweeControllerBuilder controller = Fresco.newDraweeControllerBuilder();
        controller.setUri(data);
        controller.setOldController(ivPost.getController());
        controller.setControllerListener(new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
                if (imageInfo == null) {
                    return;
                }
                ivPost.update(imageInfo.getWidth(), imageInfo.getHeight());
            }
        });
        ivPost.setController(controller.build());
    }
}
