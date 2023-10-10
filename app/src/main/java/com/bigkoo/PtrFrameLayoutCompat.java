package com.bigkoo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 2019/4/27 create by sai
 **/
public class PtrFrameLayoutCompat extends PtrFrameLayout {
    private float mDownX;
    private float mDownY;

    public PtrFrameLayoutCompat(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getRawX();
                mDownY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = ev.getRawX();
                float moveY = ev.getRawY();
                float diffX = Math.abs(moveX - mDownX);
                float diffY = Math.abs(moveY - mDownY);
                boolean isHorizon = Math.tan(diffY / diffX) < Math.tan(45.0);
                if (isHorizon) {
                    return dispatchTouchEventSupper(ev);
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
