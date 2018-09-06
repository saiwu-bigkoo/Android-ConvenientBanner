package com.bigkoo.convenientbanner.view;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.support.v7.widget.RecyclerView;
import android.view.ViewTreeObserver;

/**
 * Created by tanyi
 * on 2018/9/6.
 */
public class RecyclerViewCornerRadius extends RecyclerView.ItemDecoration{
    public static final String TAG = "RecyclerViewCornerRadius";

    private RectF rectF;
    private Path path;

    private int topLeftRadius = 0;
    private int topRightRadius = 0;
    private int bottomLeftRadius = 0;
    private int bottomRightRadius = 0;

    public RecyclerViewCornerRadius(final RecyclerView recyclerView) {
        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rectF = new RectF(0, 0, recyclerView.getMeasuredWidth(), recyclerView.getMeasuredHeight());

                path = new Path();
                path.reset();
                path.addRoundRect(rectF, new float[]{
                        topLeftRadius, topLeftRadius,
                        topRightRadius, topRightRadius,
                        bottomLeftRadius, bottomLeftRadius,
                        bottomRightRadius, bottomRightRadius
                }, Path.Direction.CCW);
                recyclerView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }

    public void setCornerRadius(int radius) {
        this.topLeftRadius = radius;
        this.topRightRadius = radius;
        this.bottomLeftRadius = radius;
        this.bottomRightRadius = radius;
    }

    public void setCornerRadius(int topLeftRadius, int topRightRadius, int bottomLeftRadius, int bottomRightRadius) {
        this.topLeftRadius = topLeftRadius;
        this.topRightRadius = topRightRadius;
        this.bottomLeftRadius = bottomLeftRadius;
        this.bottomRightRadius = bottomRightRadius;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        c.clipRect(rectF);
        c.clipPath(path, Region.Op.REPLACE);
    }
}
